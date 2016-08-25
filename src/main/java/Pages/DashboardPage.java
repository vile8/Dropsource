package Pages;

import Utility.DropsourceConstants;
import Utility.Wait;
import java.util.Iterator;
import java.util.List;
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
    
    private boolean btnCreateExists(){
        return driver.findElements(By.xpath("//button[@data-reactid = '.0.3.1.$=1$modal-modal.1.0.1.$create']")).size() > 0;
    }
    
    private WebElement banner(){
        return driver.findElement(By.xpath("//div[@data-reactid = '.0.2.1.0.1.0.0']"));
    }
    
    private List<WebElement> allProjects(){
        return driver.findElements(By.className("project-entry-row"));
    }
    
    private int projectIndex(String projectName){
        for(int i = 0; i < allProjects().size(); i++){
            if(allProjects().get(i).findElements(By.xpath(".//span[contains(text(), '" + projectName + "')]")).size() > 0){
                return i;
            }
        }
        return -1;
    }
    
    private WebElement getProject(int projectIndex){
        return allProjects().get(projectIndex);
    }
    
    private WebElement moreOptions(int projectIndex){
        return allProjects().get(projectIndex).findElement(By.className("icon-more-options"));
    }
    
    private WebElement deleteOption(int projectIndex){
        return allProjects().get(projectIndex).findElement(By.xpath(".//a[contains(text(), 'Delete Project')]"));
    }
    
    private WebElement btnConfirmDelete(){
        return driver.findElement(By.xpath("//button[@data-test = 'confirm-ok-button']"));
    }
    
    private boolean btnConfirmDeleteExists(){
        return driver.findElements(By.xpath("//button[@data-test = 'confirm-ok-button']")).size() > 0;
    }
    
    private WebElement confirmDeleteText(){
        return driver.findElement(By.xpath("//div[@data-reactid = '.0.4.1.$=10.1']"));
    }
    
    public void logout(){
        action.moveToElement(profilePicture()).perform();
        logoutLink().click();
    }
    
    public void createBlankProject(String projectName){
        btnCreateNewProject().click();
        wait.waitMilliSecs(500);
        blankTemplate().click();
        btnNext().click();
        wait.waitMilliSecs(500);
        projectName().sendKeys(projectName);
        btnCreate().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && btnCreateExists());
    }
    
    public boolean projectExists(String projectName){
        return driver.findElements(By.xpath("//span[contains(text(),'" + projectName + "')]")).size() > 0;
    }
    
    public String getBannerText(){
        return banner().getText();
    }
    
    public void deleteProject(String projectName){
        int i = projectIndex(projectName);
        
        action.moveToElement(moreOptions(i)).perform();
        deleteOption(i).click();
        wait.waitMilliSecs(500);
    }
    
    public void confirmDelete(){
        btnConfirmDelete().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && btnConfirmDeleteExists());
    }
    
    public String getConfrimDeleteText(){
        return confirmDeleteText().getText();
    }
    
    public void deleteAllProjects(){
        
    }
    
    public void openProject(String projectName){
        int i = projectIndex(projectName);
        
        getProject(i).click();
    }
}
