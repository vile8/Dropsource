package Pages;

import org.openqa.selenium.WebDriver;

/*
 * @author Jonathan Doll
 */

public class AndroidCanvasPage extends Page{
    
    public AndroidCanvasPage(WebDriver driver){
        super(driver);
    }

    @Override
    public boolean elementExists() {
        return false;
    }
    
}
