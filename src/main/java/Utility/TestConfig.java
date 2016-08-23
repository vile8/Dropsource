package Utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
    
    public WebDriver getDriver(String browser){
        if(browser.equalsIgnoreCase("firefox")){
            return new FirefoxDriver();
        }else if(browser.equalsIgnoreCase("chrome")){
            return new ChromeDriver();
        }else{
            return new ChromeDriver();
        }
    }
}
