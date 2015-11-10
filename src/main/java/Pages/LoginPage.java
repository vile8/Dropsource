package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author jonathandoll
 */

public class LoginPage extends Page{

    public LoginPage(WebDriver driver){
        super(driver);
    }
    
    @Override
    public boolean elementExists() {
        return driver.findElements(By.name("Email")).size() > 0;
    }
    
    private WebElement email(){
        return driver.findElement(By.name("Email"));
    }
    
    private WebElement password(){
        return driver.findElement(By.name("password"));
    }
    
    private WebElement btnLogin(){
        return driver.findElement(By.className("loginButton"));
    }
    
    private WebElement signUp(){
        return driver.findElement(By.linkText("Sign up Here"));
    }
    
    private WebElement forgotPassword(){
        return driver.findElement(By.linkText("Forgot Password?"));
    }
    
    private void login(String email, String password){
        email().clear();
        email().sendKeys(email);
        password().clear();
        password().sendKeys(password);
    }
    
    public void loginClick(String email, String password){
        login(email, password);
        btnLogin().click();
    }
    
    public void loginEnter(String email, String password){
        login(email, password);
        password().submit();
    }
    
}
