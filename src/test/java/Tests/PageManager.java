package Tests;

import Pages.WorkbenchPage;
import Utility.DropsourceConstants;
import Utility.Fail;
import Utility.Screenshot;
import Utility.TestConfig;
import java.awt.image.BufferedImage;
import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author jonathandoll
 */
public class PageManager{
    
    public WebDriver driver;
    private WorkbenchPage wb;
    private Screenshot ss;
    
    @BeforeClass
    public void setUp(){
        TestConfig.setBrowser("chrome");
        driver = new ChromeDriver();
        //driver.get(DropsourceConstants.workbenchURL);
        driver.get("http://www.hulu.com");
        wb = new WorkbenchPage(driver);
        ss = new Screenshot("/Users/jonathandoll/Documents");
    }
    
    @AfterClass
    public void tearDown(){
       driver.close();
    }
    
    @Test(groups = "create", threadPoolSize = 3)
    public void createPage(){
        //WorkbenchPage wb = new WorkbenchPage(driver);
        //wb.addPage("Page1");
        //assertTrue(!wb.pageExists("Page1"),"Page wasn't found");
        System.out.println("before fail");
        Fail.Fail(driver, true);
        System.out.println("after fail");
        ss.takeScreenshot(driver, "testTesttestTest2");
    }
    
    /*@Test(groups = "rename", dependsOnGroups = "create", threadPoolSize = 3)
    public void renamePage(){
        
    }
    
    @Test(groups = "delete", dependsOnGroups = "rename", threadPoolSize = 3)
    public void deletePage(){
        wb.deletePage("Page1a");
        assertTrue(wb.pageExists("Page2"), "Page was not deleted");
    }*/
    
}
