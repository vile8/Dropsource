package Pages;

import org.openqa.selenium.WebDriver;

/*
 * @author Jonathan Doll
 */

public class IOSCanvasPage extends Page{
    
    public IOSCanvasPage(WebDriver driver){
        super(driver);
    }

    @Override
    public boolean elementExists() {
        return false;
    }
    
}
