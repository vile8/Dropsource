package Tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * @author jonathandoll
 */
public class PageManager extends BaseTest{
    
    private WebDriver driver;
    
    @BeforeClass
    public void setUp(){
        super.setUp("chrome");
    }
    
    @AfterClass
    public void tearDown(){
        
    }
    
    
    
}
