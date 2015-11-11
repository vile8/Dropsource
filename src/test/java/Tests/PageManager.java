package Tests;

import Utility.DropsourceConstants;
import Utility.TestConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author jonathandoll
 */
public class PageManager{
    
    private WebDriver driver;
    
    @BeforeClass
    public void setUp(){
        TestConfig.setBrowser("chrome");
        driver = new ChromeDriver();
    }
    
    @AfterClass
    public void tearDown(){
        
    }
    
    @Test
    public void test(){
        driver.get(DropsourceConstants.workbenchURL);
    }
    
}
