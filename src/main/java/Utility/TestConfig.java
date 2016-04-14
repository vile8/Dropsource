package Utility;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * @author Jonathan
 */
public class TestConfig {
    
    private WebDriver driver;
    
    public TestConfig(WebDriver driver){
        this.driver = driver;
    }
    
    public void setBrowser(String browser){
        switch(browser.toLowerCase()){
            case("chrome"):
                if(System.getenv("OS") == null){
                    System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPathMac);
                }else if(System.getenv("OS").toLowerCase().contains("windows")){
                    System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPathWin);
                }
                break;
            default:
                break;
        }
    }
}
