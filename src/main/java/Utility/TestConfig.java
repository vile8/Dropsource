package Utility;

/**
 * @author Jonathan
 */
public class TestConfig {
    
    public static void setBrowser(String browser){
        switch(browser.toLowerCase()){
            case("chrome"):
                if(System.getenv("OS") == null){
                    System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPathMac);
                }else if(System.getenv(browser).toLowerCase().contains("windows")){
                    System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPathWin);
                }
                break;
            default:
                break;
        }
    }
    
}
