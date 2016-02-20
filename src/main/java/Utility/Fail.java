package Utility;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * @author Jonathan Doll
 */

public class Fail {
    
    public static void Fail(WebDriver driver, boolean fail){
        if(fail){
            new Screenshot("/Users/jonathandoll/Documents").takeScreenshot(driver, "testingss");
            Assert.fail();
        }else{
            System.out.println("passed");
        }
    }
    
}
