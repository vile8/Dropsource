package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Jonathan
 */
public class DashboardPage extends Page{
    
    public DashboardPage(WebDriver driver){
        super(driver);
    }

    @Override
    public boolean elementExists() {
        return driver.findElements(By.xpath("//button[@data-reactid='.0.2.0.0.0.1.0.0']")).size() > 0;
    }
    
    
    
}
