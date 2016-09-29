package Pages;

import Utility.DropsourceConstants;
import Utility.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/*
 * @author Jonathan Doll
 */

public class AccountManagementPage extends Page{

    private Wait wait;
    private Actions action;
    
    public AccountManagementPage(WebDriver driver){
        super(driver);
        wait = new Wait();
        action = new Actions(driver);
    }
    
    @Override
    public boolean elementExists() {
        //profile panel title
        return driver.findElements(By.xpath("//span[contains(text(), 'Profile')]")).size() > 0;
    }
    
    @Override
    public WebElement syncElement(){
        return driver.findElement(By.xpath("//span[contains(text(), 'Profile')]"));
    }
    
    private WebElement name(){
        return driver.findElement(By.xpath("//div[@data-test='profile-name']"));
    }
    
    private WebElement edit(){
        return driver.findElement(By.xpath("//div[@data-test='edit-profile-name']"));
    }
    
    private WebElement firstNameField(){
        return driver.findElement(By.xpath("//input[@data-test='first-name-field']"));
    }
    
    private WebElement lastNameField(){
        return driver.findElement(By.xpath("//input[@data-test='last-name-field']"));
    }
    
    private WebElement btnSave(){
        return driver.findElement(By.xpath("//button[@data-test='dialog-button']"));
    }
    
    public boolean btnSaveExists(){
        return driver.findElements(By.xpath("//button[@data-test='dialog-button']")).size() > 0;
    }
    
    private WebElement btnChangePassword(){
        return driver.findElement(By.xpath("//div[@data-test='change-profile-password']"));
    }
    
    private WebElement profilePicture(){
        return driver.findElement(By.xpath("//div[@data-test='profile-image']"));
    }
    
    private WebElement inputProfilePicture(){
        return driver.findElement(By.xpath("//input[@data-test='input-profile-image']"));
    }
    
    private WebElement editProfilePicture(){
        return driver.findElement(By.xpath("//span[@data-test='edit-profile-image']"));
    }
    
    private WebElement currentPasswordField(){
        return driver.findElement(By.xpath("//input[@data-test='previous-password-input']"));
    }
    
    private WebElement newPasswordField(){
        return driver.findElement(By.xpath("//input[@data-test='new-password-input']"));
    }
    
    private WebElement confirmPasswordField(){
        return driver.findElement(By.xpath("//input[@data-test='verify-password-input']"));
    }
    
    private WebElement dropsourceLogo(){
        return driver.findElement(By.xpath("//div[@data-test='logo-link-img']"));
    }
    
    private WebElement projects(){
        return driver.findElement(By.linkText("Projects"));
    }
    
    public void changeName(String firstName, String lastName){
        action.moveToElement(name()).perform();
        edit().click();
        wait.animation();
        firstNameField().clear();
        firstNameField().sendKeys(firstName);
        lastNameField().clear();
        lastNameField().sendKeys(lastName);
        btnSave().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && btnSaveExists());
    }
    
    public void changePassword(String currentPassword, String newPassword, String confirmNewPassword){
        btnChangePassword().click();
        wait.animation();
        currentPasswordField().sendKeys(currentPassword);
        newPasswordField().sendKeys(newPassword);
        confirmPasswordField().sendKeys(confirmNewPassword);
        btnSave().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && btnSaveExists());
    }
    
    public void addProfilePhoto(String filePath){
        inputProfilePicture().sendKeys(DropsourceConstants.codeDir + filePath);
    }
    
    public void clickDropsourceLogo(){
        dropsourceLogo().click();
    }
    
    public void clickProjectsLink(){
        projects().click();
    }
    
}
