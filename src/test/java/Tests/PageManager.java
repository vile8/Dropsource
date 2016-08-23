package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.WorkbenchPage;
import Utility.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.*;

/**
 * @author jonathandoll
 */
public class PageManager extends TestSetup{
    
    @BeforeClass(groups = {"before"})
    @Parameters("browser")
    public void setUp(@Optional String browser){
        super.setUp(browser);
    }
    
    @AfterClass(groups = {"after"})
    public void tearDown(){
       super.tearDown();
    }
    
    @Test(groups = {"login", "smoke"}, threadPoolSize = 3)
    public void ClickLoginTest() throws IOException{
        LoginPage login = new LoginPage(driver);
        login.loginClick("jdoll+120@dropsource.com", "Password1");
        
        DashboardPage db = new DashboardPage(driver);
        db.waitForLoader();
        db.sync();
        
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt (By Click)");
        
        db.logout();
        login.sync();
        
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt");
    }
    
    //@Test(groups = "create", threadPoolSize = 3)
    public void createPage(){
        TestConfig t = new TestConfig(driver);
        //WorkbenchPage wb = new WorkbenchPage(driver);
      //  wb.addPage("Page1");
        //TestConfig.takeScreenshot(driver, "test");
        //assertTrue(wb.pageExists("Page1"),"Page wasn't found");
        //System.out.println(t.checkTrue(wb.pageExists("Page1"), "Page was found"));
    }

    @DataProvider
    public Object[][] loginData() throws FileNotFoundException, IOException{
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "LoginErrors.txt");
        return data.getData();
    }

    @Test(groups = "loginErrors", dataProvider = "loginData", threadPoolSize = 3)
    public void errorCheck(String email, String password, String error) throws IOException{
        LoginPage login = new LoginPage(driver);
        login.loginClick(email, password);
        login.waitForError();
        login.errorVanishCheck();
        if(login.emailErrorExists()){
            res.checkTrue(login.emailErrorText().equals(error), uniqueID++ + " - Email error doesn't match");
        }else if(login.passwordErrorExists()){
            res.checkTrue(login.passwordErrorText().equals(error), uniqueID++ + " - Password error doesn't match");
        }else{
            res.checkTrue(false, uniqueID++ + " - No error message displayed");
        }
    }
    
    /*@Test(groups = "rename", dependsOnGroups = "create", threadPoolSize = 3)
    public void renamePage(){
        
    }
    
    @Test(groups = "delete", dependsOnGroups = "rename", threadPoolSize = 3)
    public void deletePage(){
        wb.deletePage("Page1a");
        assertTrue(wb.pageExists("Page2"), "Page was not deleted");
    }*/
    
}
