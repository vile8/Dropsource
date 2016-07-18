package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Pages.WorkbenchPage;
import Utility.DropsourceConstants;
import Utility.Results;
import Utility.TestConfig;
import Utility.Wait;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author jonathandoll
 */
public class Login{
    
    private WebDriver driver;
    private TestConfig test;
    private Results res;
    private long uniqueID;
    private Wait wait;
    
    @BeforeClass
    public void setUp(){
        test = new TestConfig(driver);
        test.setBrowser("chrome");
        driver = new ChromeDriver();
        driver.get(DropsourceConstants.loginURL);
        driver.manage().window().maximize();
        res = new Results(driver);
        uniqueID = System.currentTimeMillis();
        wait = new Wait();
    }
    
    @AfterClass
    public void tearDown(){
       driver.close();
    }
    
    @Test(groups = "login", threadPoolSize = 3)
    public void LoginTest() throws IOException{
        LoginPage login = new LoginPage(driver);
        login.loginClick("jdoll+120@dropsource.com", "Password1");
        
        DashboardPage db = new DashboardPage(driver);
        db.waitForLoader();
        db.sync();
        
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt (By Click)");
        
        db.logout();
        login.sync();
        
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt");
        
        login.loginEnter("jdoll+120@dropsource.com", "Password1");
        db.waitForLoader();
        db.sync();
        
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt (By Enter)");
        
        db.logout();
        login.sync();
        
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt");
    }
    
}