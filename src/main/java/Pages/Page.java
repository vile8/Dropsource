package Pages;

import Utility.DropsourceConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Jonathan Doll
 */

public abstract class Page {
    
    public WebDriver driver;
    private long timer;
    public WebDriverWait wdw;
    
    public Page(WebDriver driver){
        this.driver = driver;
        wdw = new WebDriverWait(driver, DropsourceConstants.pageTimeoutLimit);
    }
    
    public void sync(){
        //timer = System.currentTimeMillis();
        //while(System.currentTimeMillis() - timer < DropsourceConstants.pageTimeoutLimit * 1000 && !elementExists());
        wdw.until(ExpectedConditions.visibilityOf(syncElement()));
    }
    
    public abstract boolean elementExists(); 
    
    public abstract WebElement syncElement();
    
}
