package Utility;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * @author Jonathan Doll
 */

public class Screenshot {
    
    private String folderLocation;
    
    public Screenshot(String folderLocation){
        this.folderLocation = folderLocation;
    }

    public void takeScreenshot(WebDriver driver, String fileName){
        try{
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(folderLocation + "/" + fileName + ".png"));
        }catch(IOException e){
            System.err.println(e);
        }
    }
    
    
}
