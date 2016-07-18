package Pages;

import Utility.DropsourceConstants;
import Utility.Wait;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Jonathan
 */
public class random {
    
    public static void main(String[] args) throws AWTException{
        /*Wait wait = new Wait();
        System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPathWin);
        WebDriver driver = new FirefoxDriver();
        System.out.println("yay");
        driver.get("https://images.google.com/?gws_rd=ssl");
        System.out.println("yay");
        WebElement thing = driver.findElement(By.id("qbi"));
        System.out.println("yay");
        thing.click();
        System.out.println("yay");
        wait.waitSecs(3);
        WebElement thing2 = driver.findElement(By.xpath("//a[contains(text(),'Upload an image')]"));
        System.out.println("yay");
        wait.waitSecs(3);
        thing2.click();
        System.out.println("yay");
        wait.waitSecs(3);
        WebElement fs = driver.findElement(By.id("qbfile"));
        System.out.println("yay");
        fs.sendKeys("C:\\Users\\Jonathan\\Pictures\\Dropsource Testing\\stepper.PNG");
        System.out.println("yay");
        
        /*driver.get("https://dropsource.com/dashboard/account/profile");
        WebElement fs = driver.findElement(By.xpath("//input[@data-reactid='.0.2.1.1.0.0.0.0.1.0.0.1']"));
        fs.sendKeys("C:\\Users\\Jonathan\\Pictures\\Dropsource Testing\\stepper.PNG");
        
        driver.get("https://devworkbench.dropsource.biz/login");
        
        LoginPage login = new LoginPage(driver);
        login.loginClick("jdoll+100@dropsource.com", "Password1");
        System.out.println("Logged in");
        wait.waitSecs(5);
        
        driver.get("https://devworkbench.dropsource.biz/workbench/project/734754883345131463/734754883348604352");
        
        System.out.println("going to new page");
        wait.waitSecs(2);
        
        
        WebElement button = driver.findElement(By.xpath("//div[@data-reactid='.0.0.0.1.$centerRightContainer.0.0.0.1.$558cb070-50fc-434b-ab4c-71e7206d6569.0.1.0.1:$7eb178cf-fdaa-4792-bf20-0bb3bdb35b4f.1']"));
        
        Robot r = new Robot();
        int x = button.getLocation().getX();
        int y = button.getLocation().getY();
        
        r.mouseMove(x+25, y+100);
        wait.waitSecs(.5);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        wait.waitSecs(.5);
        r.mouseMove(x + 35, y+150);
        wait.waitSecs(.5);
        r.mouseMove(x + 45, y+200);
        wait.waitSecs(.5);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        wait.waitSecs(.5);
        
        System.out.println("dragged button");*/
        
        Wait wait = new Wait();
        System.setProperty("webdriver.chrome.driver", DropsourceConstants.chromeDriverPathWin);
        WebDriver driver = new ChromeDriver();
        driver.get("https://app.dropsource.biz/login");
        
        LoginPage login = new LoginPage(driver);
        login.loginClick("jdoll+120@dropsource.com", "Password1");
        System.out.println("Logged in");
        wait.waitSecs(5);
        
        driver.get("https://app.dropsource.biz/workbench/project/746349253572566361/746349253572514989");
        
        wait.waitSecs(3);
        
        WebElement drawer = driver.findElement(By.xpath("//button[@data-test='drawer-elements']"));
        drawer.click();
        
        wait.waitSecs(3);
        
        WebElement button = driver.findElement(By.xpath("//div[@data-test='draggables-dropsource-switch']"));
        
        Robot r = new Robot();
        int x = button.getLocation().getX();
        int y = button.getLocation().getY();
        
        for(int i = 0; i < 10; i++){
        r.mouseMove(x+25, y+100);
        wait.waitSecs(.5);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        wait.waitSecs(.5);
        r.mouseMove(x + 205, y+150 + (i*10));
        wait.waitSecs(.5);
        r.mouseMove(x + 405, y+200 + (i*10));
        wait.waitSecs(.5);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        wait.waitSecs(.5);
        }
        
    }
    
}





















//PREPARE TO BE AMAZED BY THE POWER OF JAVA
