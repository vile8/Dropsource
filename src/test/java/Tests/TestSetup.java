package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.WorkbenchPage;
import Utility.DropsourceConstants;
import Utility.Results;
import Utility.TestConfig;
import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;

/*
 * @author Jonathan Doll
 */
public abstract class TestSetup {

    public WebDriver driver;
    public TestConfig test;
    public Results res;
    public long uniqueID;
    public LoginPage login;
    public DashboardPage db;
    public WorkbenchPage wb;
    private ArrayList<String> tabs;
    private ArrayList<String> closeTabs;

    public void setUp(String browser) {
        test = new TestConfig(driver);
        test.setBrowser(browser);
        driver = test.getDriver(browser);
        driver.get(DropsourceConstants.loginURL);
        driver.manage().window().maximize();
        res = new Results(driver);
        uniqueID = System.currentTimeMillis();
        login = new LoginPage(driver);
        db = new DashboardPage(driver);
        wb = new WorkbenchPage(driver);
    }

    public void tearDown() {
        driver.close();
    }

    public void login() throws IOException {
        login.loginClick(DropsourceConstants.loginEmail, DropsourceConstants.loginPassword);
        db.waitForLoader();
        db.sync();
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt Failed (By Click)");
    }

    public void logout() throws IOException {
        res.checkTrue(db.logout(), uniqueID++ + " - Logout link blocked");
        login.sync();
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt Failed");
    }

    public void openProject(String projectName) throws IOException {
        db.openProject(projectName);
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        wb.waitForLoader();
        wb.sync();
        res.checkTrue(wb.elementExists(), uniqueID++ + " - Open project failed");
    }
    
    public void closeProject(){
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }
    
    public void createProject(String projectName) throws IOException{
        db.btnCreateNewProjectClick();
        res.checkTrue(!db.projectLimitReachedExists(), uniqueID++ + " - Project limit error appeared");
        db.createBlankProject(projectName);
        res.checkTrue(db.projectExists(projectName), uniqueID++ + " - Project just created (" + projectName + ") not found in list");
        res.checkTrue(db.getBannerText().equals("New Project Has Been Created"), uniqueID++ + " - Banner text does not match expected text");
    }
    
    public void deleteProject(String projectName) throws IOException{
        db.deleteProject(projectName);
        String expectedConfirmText = "Deleting this project will permanently remove it which includes the iOS and Android apps, unlink any used plugins, and remove it from your project list.";
        res.checkTrue(db.getConfirmDeleteText().equals(expectedConfirmText), uniqueID++ + " - Confirm delete text doesn't match expected text");

        db.confirmDelete();
        res.checkTrue(!db.projectExists(projectName), uniqueID++ + " - Project just deleted (" + projectName + ") is still found in list");
        res.checkTrue(db.getBannerText().equals("A project has been deleted"), uniqueID++ + " - Banner text does not match expected text");
    }
    
    public void tearDownCloseAllTabsExceptDashboard(){
        closeTabs = new ArrayList<String>(driver.getWindowHandles());
        int tabs = closeTabs.size();
        
        for(int i = tabs - 1; i > 0; i--){
            driver.switchTo().window(closeTabs.get(i));
            driver.close();
        }
        
        driver.switchTo().window(closeTabs.get(0));
    }

}
