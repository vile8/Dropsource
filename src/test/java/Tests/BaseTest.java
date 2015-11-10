package Tests;

import Utility.DropsourceConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author jonathandoll
 */

public class BaseTest {

    public WebDriver driver;
    
    public void setUp(String browser){
        switch(browser.toLowerCase()){
            case("chrome"):
                System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPath);
                break;
            default:
                break;
            
        }
        
        driver = new ChromeDriver();
                
    }
    
}
