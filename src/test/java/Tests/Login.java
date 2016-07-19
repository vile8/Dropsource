package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Utility.DataReader;
import Utility.DropsourceConstants;
import Utility.Results;
import Utility.TestConfig;
import Utility.Wait;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
        
        login.loginEnter("jdoll+120@dropsource.com", "Password1");
        db.waitForLoader();
        db.sync();
        
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt (By Enter)");
        
        db.logout();
        login.sync();
        
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt");
    }
    
    @Test(groups = "login", threadPoolSize = 3)
    public void EnterLoginTest() throws IOException{
        LoginPage login = new LoginPage(driver);
        login.loginEnter("jdoll+120@dropsource.com", "Password1");
        
        DashboardPage db = new DashboardPage(driver);
        db.waitForLoader();
        db.sync();
        
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt (By Click)");
        
        db.logout();
        login.sync();
        
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt");
    }
    
    @DataProvider
    public Object[][] loginData() throws FileNotFoundException, IOException{
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "LoginErrors.txt");
        return data.getData();
    }
    
    @Test(groups = "errors", dataProvider = "loginData", threadPoolSize = 3)
    public void errorCheck(String email, String password, String error) throws IOException{
        LoginPage login = new LoginPage(driver);
        login.loginClick(email, password);
        wait.waitSecs(3);
        if(login.emailErrorExists()){
            res.checkTrue(login.emailErrorText().equals(error), uniqueID++ + " - Email error doesn't match");
        }else if(login.passwordErrorExists()){
            res.checkTrue(login.passwordErrorText().equals(error), uniqueID++ + " - Password error doesn't match");
        }else{
            res.checkTrue(false, uniqueID++ + " - No error message displayed");
        }
    }
    
}