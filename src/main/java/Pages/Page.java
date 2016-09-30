package Pages;

import Utility.DropsourceConstants;
import org.openqa.selenium.WebDriver;

/**
 * @author Jonathan Doll
 */

public abstract class Page {
    
    public WebDriver driver;
    private long timer;
    
    public Page(WebDriver driver){
        this.driver = driver;
    }
    
    public void sync(){
        timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < DropsourceConstants.pageTimeoutLimit * 1000 && !elementExists());
    }
    
    public abstract boolean elementExists(); 
    
}
