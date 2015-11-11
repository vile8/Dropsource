package Utility;

/**
 * @author Jonathan
 */
public class TestConfig {
    
    public static void setBrowser(String browser){
        switch(browser.toLowerCase()){
            case("chrome"):
                if(System.getenv(browser).toLowerCase().contains("windows")){
                    System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPathWin);
                }else{
                    System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPathMac);
                }
                break;
            default:
                break;
        }
    }
    
}
