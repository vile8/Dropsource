package Pages;

import Utility.DropsourceConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Jonathan Doll
 */
public abstract class Page {
    
    public WebDriver driver;
    private long time;
    
    public Page(WebDriver driver){
        this.driver = driver;
    }
    
    public void sync(boolean elementExists){
        time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < DropsourceConstants.pageTimeoutLimit * 1000 && !elementExists());
    }
    
    public abstract boolean elementExists(); 
    
}
