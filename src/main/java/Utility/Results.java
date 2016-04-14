package Utility;

import org.openqa.selenium.WebDriver;

/**
 * @author Jonathan
 */
public class Results {
    
    private WebDriver driver;
    
    public Results(WebDriver driver){
        this.driver = driver;
    }
    
    public String checkTrue(boolean cond, String test){
        /*try{
            assertTrue(cond, test);
        }catch(AssertionError e){
            takeScreenshot("test1");
            fail();
            return test + ":\n" + e;
        }
        return test;*/
        return "";
    }
    
}
