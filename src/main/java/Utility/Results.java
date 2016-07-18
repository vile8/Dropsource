package Utility;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * @author Jonathan
 */
public class Results {
    
    private WebDriver driver;
    private Screenshot ss;
    
    public Results(WebDriver driver){
        this.driver = driver;
        this.ss = new Screenshot(driver);
    }
    
    public void checkTrue(boolean cond, String test) throws IOException{
        try{
            assertTrue(cond, test);
        }catch(AssertionError e){
            ss.takeScreenshot(test);
            fail();
        }
    }
    
}
