package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.WorkbenchPage;
import Utility.DropsourceConstants;
import Utility.Results;
import Utility.TestConfig;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

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
    
    public void setUp(String browser){
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
    
    public void tearDown(){
       driver.close();
    }
    
    public void login() throws IOException{
        login.loginClick(DropsourceConstants.loginEmail, DropsourceConstants.loginPassword);
        db.waitForLoader();
        db.sync();
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt Failed (By Click)");
    }
    
    public void logout() throws IOException{
        res.checkTrue(db.logout(), uniqueID++ + " - Logout link blocked");
        login.sync();
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt Failed");
    }
    
}
