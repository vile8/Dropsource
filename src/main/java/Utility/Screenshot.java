package Utility;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Jonathan
 */
public class Screenshot {
    
    private WebDriver driver;
    
    public Screenshot(WebDriver driver){
        this.driver = driver;
    }
    
    public void takeScreenshot(String fileName) throws IOException{
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("src/test/results/screenshots/" + fileName + ".png"));
    }
}
