package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author jonathandoll
 */

public class LoginPage extends Page{

    private Actions action;
    
    public LoginPage(WebDriver driver){
        super(driver);
        this.action = new Actions(driver);
    }
    
    @Override
    public boolean elementExists() {
        return driver.findElements(By.name("username")).size() > 0;
    }
    
    public void sync(){
        super.sync(elementExists());
    }
    
    private WebElement email(){
        return driver.findElement(By.name("username"));
    }
    
    private WebElement password(){
        return driver.findElement(By.name("password"));
    }
    
    private WebElement btnLogin(){
        return driver.findElement(By.xpath("//button[@data-reactid='.0.1.4.1']"));
    }
    
    private WebElement signUp(){
        return driver.findElement(By.linkText("Sign up Here"));
    }
    
    private WebElement forgotPassword(){
        return driver.findElement(By.linkText("Forgot Password?"));
    }
    
    public void loginInfo(String email, String password){
        email().clear();
        email().sendKeys(email);
        password().clear();
        password().sendKeys(password);
    }
    
    public void loginClick(String email, String password){
        loginInfo(email, password);
        btnLogin().click();
    }
    
    public void loginEnter(String email, String password){
        loginInfo(email, password);
        action.sendKeys(Keys.ENTER).perform();
    }
    
}
