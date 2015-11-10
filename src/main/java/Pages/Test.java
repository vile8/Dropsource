package Pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/*
 * @author Jonathan Doll
 */

public class Test extends Page{
    
    public Test(WebDriver driver){
        super(driver);
    }

    @Override
    public boolean elementExists() {
        return false;
    }
    
    public List<WebElement> element(){
        return driver.findElements(By.className("canvas-element"));
    }
    
    public WebElement drag(){
        return driver.findElement(By.xpath("//div[@data-reactid='.0.0.1.$leftCabinetDrawer.0.$pages.0.2.$b7dfbd84-10f9-4c4b-ba20-b4e7c49f55fc.$b7dfbd84-10f9-4c4b-ba20-b4e7c49f55fc.0.0.0']"));
    }
    
}
