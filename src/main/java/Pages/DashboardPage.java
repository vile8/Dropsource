package Pages;

import Utility.DropsourceConstants;
import Utility.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Jonathan
 */
public class DashboardPage extends Page{
    
    private Actions action;
    private Wait wait;
    
    public DashboardPage(WebDriver driver){
        super(driver);
        this.action = new Actions(driver);
        wait = new Wait();
    }

    @Override
    public boolean elementExists() {
        return driver.findElements(By.xpath("//button[@data-reactid='.0.2.0.0.0.1.0.0']")).size() > 0;
    }
    
    public void sync(){
        super.sync(elementExists());
    }
    
    private boolean loader(){
        return driver.findElements(By.xpath("//div[@data-reactid='.0.0.0']")).size() > 0;
    }
    
    public void waitForLoader(){
        super.sync(loader());
        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < DropsourceConstants.pageTimeoutLimit * 1000 && loader());
    }
    
    private WebElement profilePicture(){
        return driver.findElement(By.xpath("//img[@data-reactid='.0.1.0.0.0.1.0.3.0.0.0.0']"));
    }
    
    private WebElement logoutLink(){
        return driver.findElement(By.xpath("//a[contains(text(), 'Log Out')]"));
    }
    
    private WebElement btnCreateNewProject(){
        return driver.findElement(By.xpath("//div[contains(text(), 'Create New Project')]"));
    }
    
    private WebElement btnNext(){
        return driver.findElement(By.xpath("//button[contains(text(), 'Next')]"));
    }
    
    private WebElement blankTemplate(){
        return driver.findElement(By.xpath("//div[contains(text(), 'Blank')]"));
    }
    
    private WebElement projectName(){
        return driver.findElement(By.xpath("//input[@data-reactid = '.0.3.1.$=1$modal-modal.1.1.0.$=1$chooseProjectName.0.0.1']"));
    }
    
    private WebElement btnCreate(){
        return driver.findElement(By.xpath("//button[@data-reactid = '.0.3.1.$=1$modal-modal.1.0.1.$create']"));
    }
    
    private WebElement successBanner(){
        return driver.findElement(By.xpath("//div[@data-reactid = '.0.2.1.0.1.0.0']"));
    }
    
    public void logout(){
        action.moveToElement(profilePicture()).perform();
        logoutLink().click();
    }
    
    public boolean bannerExists(){
        return driver.findElements(By.xpath("//div[@data-reactid = '.0.2.1.0.1.0.0']")).size() > 0;
    }
    
    public void createBlankProject(String projectName){
        btnCreateNewProject().click();
        wait.waitMilliSecs(500);
        blankTemplate().click();
        btnNext().click();
        wait.waitMilliSecs(500);
        projectName().sendKeys(projectName);
        btnCreate().click();
        wait.waitMilliSecs(3000);
        System.out.println(successBanner().isDisplayed());
        System.out.println(successBanner().isEnabled());
        wait.waitSecs(3);
        System.out.println(successBanner().isDisplayed());
        System.out.println(successBanner().isEnabled());
    }
    
    public boolean projectExists(String projectName){
        return driver.findElements(By.xpath("//span[contains(text(),'" + projectName + "')]")).size() > 0;
    }
    
    public void waitForBanner(){
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && true);
    }
    
    public void deleteProject(String projectName){
        
    }
    
    public void deleteAllProjects(){
        
    }
    
    public void openProject(String projectName){
        
    }
}
