package Tests;

import Pages.WorkbenchPage;
import Utility.DropsourceConstants;
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
    private TestConfig test;
    
    @BeforeClass
    public void setUp(){
        test = new TestConfig(driver);
        test.setBrowser("chrome");
        driver = new ChromeDriver();
        //driver.get(DropsourceConstants.workbenchURL);
        driver.get("http://www.hulu.com");
        wb = new WorkbenchPage(driver);
    }
    
    @AfterClass
    public void tearDown(){
       driver.close();
    }
    
    @Test(groups = "create", threadPoolSize = 3)
    public void createPage(){
        TestConfig t = new TestConfig(driver);
        //WorkbenchPage wb = new WorkbenchPage(driver);
        wb.addPage("Page1");
        //TestConfig.takeScreenshot(driver, "test");
        //assertTrue(wb.pageExists("Page1"),"Page wasn't found");
        //System.out.println(t.checkTrue(wb.pageExists("Page1"), "Page was found"));
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
