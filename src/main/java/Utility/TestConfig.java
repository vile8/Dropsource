package Utility;

/**
 * @author Jonathan
 */
public class TestConfig {
    public void setBrowser(String browser){
        
        switch(browser.toLowerCase()){
            case("chrome"):
                System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPath);
                break;
            default:
                break;
        }
        
    }
}
