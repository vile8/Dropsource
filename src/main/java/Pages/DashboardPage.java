package Pages;

import Utility.DropsourceConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Jonathan
 */
public class DashboardPage extends Page{
    
    private Actions action;
    
    public DashboardPage(WebDriver driver){
        super(driver);
        this.action = new Actions(driver);
    }

    @Override
    public boolean elementExists() {
        return driver.findElements(By.xpath("//button[@data-reactid='.0.2.0.0.0.1.0.0']")).size() > 0;
    }
    
    public void sync(){
        super.sync(elementExists());
    }
    
    private boolean loader(){
        return driver.findElements(By.xpath("//div[@data-reactid='.0.0.0']")).size() > 0;
    }
    
    public void waitForLoader(){
        super.sync(loader());
        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < DropsourceConstants.pageTimeoutLimit * 1000 && loader());
    }
    
    private WebElement profilePicture(){
        return driver.findElement(By.xpath("//img[@data-reactid='.0.1.0.0.0.1.0.3.0.0.0.0']"));
    }
    
    private WebElement logoutLink(){
        return driver.findElement(By.xpath("//a[contains(text(), 'Log Out')]"));
    }
    
    public void logout(){
        action.moveToElement(profilePicture()).perform();
        logoutLink().click();
    }
}
