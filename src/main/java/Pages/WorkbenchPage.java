package Pages;

import Utility.DropsourceConstants;
import Utility.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author Jonathan Doll
 */
public class WorkbenchPage extends Page {

    private Actions action;
    private Wait wait;

    public WorkbenchPage(WebDriver driver) {
        super(driver);
        this.action = new Actions(driver);
        wait = new Wait();
    }

    @Override
    public boolean elementExists() {
        //android tab
        return driver.findElements(By.xpath("//div[@data-reactid='.0.0.0.0.1.0.0.0.0.0:$java=1android.0.0']")).size() > 0;
    }

    private boolean loader() {
        return driver.findElements(By.xpath("//div[@data-test='loading-message']")).size() > 0;
    }

    public void waitForLoader() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time < DropsourceConstants.pageTimeoutLimit * 1000 && loader());
    }

    public WebElement pagesDrawer() {
        return driver.findElement(By.xpath("//button[@data-test='drawer-pages']"));
    }

    public boolean pageDrawerActive() {
        return pagesDrawer().getAttribute("class").contains("active");
    }

    public WebElement androidIcon() {
        return driver.findElement(By.xpath("//td[@data-reactid='.0.0.0.0.1.0.0.0.0.0:$java=1android']"));
    }

    public boolean androidTabOpen() {
        return androidIcon().getAttribute("class").contains("active");
    }

    public WebElement iosIcon() {
        return driver.findElement(By.xpath("//td[@data-reactid='.0.0.0.0.1.0.0.0.0.0:$swift=1ios']"));
    }

    public boolean iosTabOpen() {
        return iosIcon().getAttribute("class").contains("active");
    }

    private WebElement getPage(String pageName){
        return driver.findElement(By.xpath("//tr[@data-test='page-manager-" + pageName + "']"));
    }

    private WebElement ellipsis(String pageName) {
        return getPage(pageName).findElement(By.className("icon-more-options"));
    }

    private WebElement btnDelPage(){
        return driver.findElement(By.xpath("//button[@data-test='item-delete']"));
    }

    private WebElement btnConfirmDelete() {
        return driver.findElement(By.xpath("//button[contains(text(), 'Confirm Delete')]"));
    }

    private WebElement btnAddPage() {
        return driver.findElement(By.xpath("//button[@data-test='create-page-button']"));
    }

    private WebElement btnNext(){
        return driver.findElement(By.xpath("//button[contains(text(), 'Next')]"));
    }
    
    private WebElement nameYourPage() {
        return driver.findElement(By.xpath("//input[@placeholder='Page Name']"));
    }

    private WebElement btnCreate() {
        return driver.findElement(By.xpath("//button[contains(text(), 'Create')]"));
    }
    
    private boolean btnCreateExists(){
        return driver.findElements(By.xpath("//button[contains(text(), 'Create')]")).size() > 0;
    }

    public boolean pageExists(String pageName){
        return driver.findElements(By.xpath("//tr[@data-test='page-manager-" + pageName + "']")).size() > 0;
    }
    
    private boolean savingHeader() {
        return driver.findElements(By.className("saving")).size() > 0;
    }

    private boolean savedHeader() {
        return driver.findElements(By.className("saved")).size() > 0;
    }
    
    private WebElement btnRenamePage(){
        return driver.findElement(By.xpath("//button[@data-test='item-rename']"));
    }
    
    private WebElement btnRename(){
        return driver.findElement(By.xpath("//button[contains(text(), 'Rename')]"));
    }
    
    private boolean btnRenameExists(){
        return driver.findElements(By.xpath("//button[contains(text(), 'Rename')]")).size() > 0;
    }
    
    public boolean checkIfSaved(int timeout) {
        timeout *= 1000;
        long time = System.currentTimeMillis();
        long diff = 0;
        if (savingHeader() || !savedHeader()) {
            while ((diff = System.currentTimeMillis() - time) < (timeout) && !savedHeader());
        }
        return diff < timeout;
    }

    public void deletePage(String pageName) {
        if (!pageDrawerActive()) {
            pagesDrawer().click();
            wait.animation();
        }
        action.moveToElement(pagesDrawer()).perform();
        action.moveToElement(ellipsis(pageName)).perform();
        btnDelPage().click();
        wait.animation();
        btnConfirmDelete().click();
        wait.animation();
    }

    public void addPage(String pageName) {
        if (!pageDrawerActive()) {
            pagesDrawer().click();
        }
        wait.animation();
        btnAddPage().click();
        wait.animation();
        btnNext().click();
        wait.animation();
        nameYourPage().sendKeys(pageName);
        btnCreate().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && btnCreateExists());
    }
    
    public void renamePage(String pageName, String newPageName){
        if (!pageDrawerActive()) {
            pagesDrawer().click();
            wait.animation();
        }
        action.moveToElement(pagesDrawer()).perform();
        action.moveToElement(ellipsis(pageName)).perform();
        btnRenamePage().click();
        wait.animation();
        nameYourPage().clear();
        nameYourPage().sendKeys(newPageName);
        btnRename().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && btnRenameExists());
        
    }

}
