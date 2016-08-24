package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Utility.DropsourceConstants;
import Utility.Results;
import Utility.TestConfig;
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
    }
    
    @AfterClass(groups = {"after"})
    public void tearDown(){
       driver.close();
    }
    
}
