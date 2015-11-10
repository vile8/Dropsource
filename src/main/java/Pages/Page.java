package Pages;

import org.openqa.selenium.WebDriver;

/**
 * @author Jonathan Doll
 */
public abstract class Page {
    
    public WebDriver driver;
    
    public Page(WebDriver driver){
        this.driver = driver;
    }
    
    public abstract boolean elementExists();    
    
}
