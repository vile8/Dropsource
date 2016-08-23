package Tests;

import Utility.DropsourceConstants;
import Utility.Results;
import Utility.TestConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

/*
 * @author Jonathan Doll
 */
public class TestSetup {
    
    public WebDriver driver;
    public TestConfig test;
    public Results res;
    public long uniqueID;
    
    public void setUp(String browser){
        test = new TestConfig(driver);
        test.setBrowser(browser);
        driver = test.getDriver(browser);
        driver.get(DropsourceConstants.loginURL);
        driver.manage().window().maximize();
        res = new Results(driver);
        uniqueID = System.currentTimeMillis();
    }
    
    @AfterClass(groups = {"after"})
    public void tearDown(){
       driver.close();
    }
    
}
