package Tests;

import Pages.LoginPage;
import Pages.WorkbenchPage;
import Utility.DropsourceConstants;
import Utility.TestConfig;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private WorkbenchPage wb;
    private TestConfig test;
    
    @BeforeClass
    public void setUp(){
        test = new TestConfig(driver);
        test.setBrowser("chrome");
        driver = new ChromeDriver();
        driver.get(DropsourceConstants.loginURL);
        wb = new WorkbenchPage(driver);
    }
    
    @AfterClass
    public void tearDown(){
       driver.close();
    }
    
    @Test(groups = "create", threadPoolSize = 3)
    public void LoginTest(){
        LoginPage login = new LoginPage(driver);
        login.loginEnter("jdoll+110@dropsource.com", "Password1");
    }
    
}