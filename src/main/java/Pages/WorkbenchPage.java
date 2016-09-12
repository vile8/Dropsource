package Pages;

import Utility.DropsourceConstants;
import Utility.Wait;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
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
        //run button
        return driver.findElements(By.xpath("//div[contains(text(), 'Run')]")).size() > 0;
    }

    private boolean loader() {
        return driver.findElements(By.xpath("//div[@data-test='loading-message']")).size() > 0;
    }

    public void waitForLoader() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time < DropsourceConstants.pageTimeoutLimit * 1000 && loader());
    }

    private WebElement pagesDrawer() {
        return driver.findElement(By.xpath("//button[@data-test='drawer-pages']"));
    }

    private boolean pageDrawerActive() {
        return pagesDrawer().getAttribute("class").contains("active");
    }
    
    private WebElement elementsDrawer(){
        return driver.findElement(By.xpath("//button[@data-test='drawer-elements']"));
    }
    
    private boolean elementsDrawerActive(){
        return elementsDrawer().getAttribute("class").contains("active");
    }
    
    private WebElement todoDrawer(){
        return driver.findElement(By.xpath("//button[@data-test='drawer-todo']"));
    }
    
    private boolean todoDrawerActive(){
        return todoDrawer().getAttribute("class").contains("active");
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
    
    public boolean btnCreateExists(){
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
    
    public boolean btnRenameExists(){
        return driver.findElements(By.xpath("//button[contains(text(), 'Rename')]")).size() > 0;
    }
    
    private WebElement dragHandle(String pageName){
        return getPage(pageName).findElement(By.className("icon-drag-handle"));
    }
    
    private WebElement getElement(String elementName){
        return driver.findElement(By.xpath("//div[@data-test='draggables-dropsource-" + elementName + "']"));
    }
    
    private boolean elementExists(String elementName){
        return driver.findElements(By.xpath("//div[@data-test='draggables-dropsource-" + elementName + "']")).size() > 0;
    }
    
    private WebElement canvas(){
        return driver.findElement(By.xpath("//div[@data-test='canvas-root']"));
    }
    
    //not really future proof.  Need a better identifier for pages that isn't based off their name.
    private List<WebElement> pageList(){
        //return driver.findElements(By.className("affordance-clickable"));
        return driver.findElements(By.xpath("//div//table//tbody//tr//td//div//div"));
    }
    
    private WebElement run(){
        return driver.findElement(By.xpath("//div[contains(text(), 'Run')]"));
    }
    
    private WebElement iOSSimulator(){
        return driver.findElement(By.xpath("//button[contains(text(), 'iOS Simulator')]"));
    }
    
    private boolean iOSSimulatorExists(){
        return driver.findElements(By.xpath("//button[contains(text(), 'iOS Simulator')]")).size() > 0;
    }
    
    private WebElement btnCancel(){
        return driver.findElement(By.xpath("//button[contains(text(), 'Cancel')]"));
    }
    
    private WebElement cover(){
        return driver.findElement(By.className("cover"));
    }
    
    public boolean btnCancelExists(){
        return driver.findElements(By.xpath("//button[contains(text(), 'Cancel')]")).size() > 0;
    }
    
    public boolean buildSuccess(){
        return driver.findElements(By.className("build-result-success")).size() > 0;
    }
    
    private List<WebElement> todoErrors(){
        return driver.findElements(By.className("item-message"));
    }
    
    private WebElement elementSearchbar(){
        return driver.findElement(By.xpath("//input[@placeholder='Search Elements']"));
    }
    
    private WebElement btnClearSearch(){
        return driver.findElement(By.xpath("//button[@data-reactid='.0.0.0.1.$leftCabinet.0.$elements.$elements.1.1.0.0.0.2.0']"));
    }
    
    private WebElement settingsDrawer(){
        return driver.findElement(By.xpath("//button[@data-test='drawer-settings']"));
    }
    
    private boolean settingsDrawerActive(){
        return settingsDrawer().getAttribute("class").contains("active");
    }
    
    private WebElement btnEdit(){
        return driver.findElement(By.xpath("//button[contains(text(), 'Edit')]"));
    }
    
    private WebElement appNameField(){
        return driver.findElement(By.id("input-name"));
    }
    
    private WebElement btnSave(){
        return driver.findElement(By.xpath("//button[@data-test='dialog-button']"));
    }
    
    public boolean btnSaveExists(){
        return driver.findElements(By.xpath("//button[@data-test='dialog-button']")).size() > 0;
    }
    
    private WebElement appName(){
        return driver.findElement(By.className("title"));
    }
    
    private WebElement btnMedia(){
        
    }
    
    private WebElement btnUpload(){
        
    }
    
    private boolean progressBarExists(){
        
    }
    
    public boolean checkIfSaved(int timeout) {
        timeout *= 1000;
        long time = System.currentTimeMillis();
        long diff = timeout + 1;
        if (savingHeader() || !savedHeader()) {
            if(diff == timeout + 1) diff = 0;
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
            wait.animation();
        }
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
    
    public void rearrangePage(String pageName, String newPageSlot){
        if (!pageDrawerActive()) {
            pagesDrawer().click();
            wait.animation();
        }
        action.dragAndDrop(dragHandle(newPageSlot), dragHandle(pageName)).perform();
    }
    
    //Works for Windows at least but isn't reliable unless window is visible
    public void addElementToCanvas(String elementName) throws AWTException{
        if (!elementsDrawerActive()) {
            elementsDrawer().click();
            wait.animation();
        }
        Point thing = driver.manage().window().getPosition();
        Robot r = new Robot();
        int offX = thing.x + 10;
        int offY = thing.y + 85;
        int elx = getElement(elementName).getLocation().getX() + offX;
        int ely = getElement(elementName).getLocation().getY() + offY;
        int clx = canvas().getLocation().getX() + offX;
        int cly = canvas().getLocation().getY() + offY;
        r.mouseMove(elx, ely);
        wait.animation();
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        wait.animation();
        r.mouseMove((clx + elx) / 2, (cly + ely) / 2);
        wait.animation();
        r.mouseMove(clx, cly);
        wait.animation();
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    public boolean checkElementExists(String elementName){
        if (!elementsDrawerActive()) {
            elementsDrawer().click();
            wait.animation();
        }
        return elementExists(elementName);
    }
    
    public String getFirstPageName(){
        if (!pageDrawerActive()) {
            pagesDrawer().click();
            wait.animation();
        }
        return pageList().get(0).getText();
    }
    
    public void initiateIOSBrowserBuild(){
        run().click();
        wait.animation();
        iOSSimulator().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && iOSSimulatorExists());
        
    }
    
    public void waitForBuild(){
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 600000 && btnCancelExists());
    }
    
    public void closeRunMenu(){
        if(iOSSimulatorExists() || buildSuccess() || btnCancelExists()){
            cover().click();
        }
    }
    
    public int checkTodoErrorAmount(){
        if (!todoDrawerActive()) {
            todoDrawer().click();
            wait.animation();
        }
        return todoErrors().size();
        
    }
    
    public ArrayList<String> getTodoErrors(){
        if (!todoDrawerActive()) {
            todoDrawer().click();
            wait.animation();
        }
        ArrayList<String> errors = new ArrayList<>();
        for(WebElement e : todoErrors()){
            errors.add(e.findElement(By.xpath(".//span")).getText());
        }
        return errors;
    }
    
    public void elementSearch(String search){
        if (!elementsDrawerActive()) {
            elementsDrawer().click();
            wait.animation();
        }
        elementSearchbar().clear();
        elementSearchbar().sendKeys(search);
        wait.animation();
    }
    
    public void clearElementSearch(){
        if (!elementsDrawerActive()) {
            elementsDrawer().click();
            wait.animation();
        }
        btnClearSearch().click();
        wait.animation();
    }
    
    public void changeAppName(String appName){
        if (!settingsDrawerActive()) {
            settingsDrawer().click();
            wait.animation();
        }
        btnEdit().click();
        appNameField().clear();
        appNameField().sendKeys(appName);
        btnSave().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && btnSaveExists());
    }
    
    public String getAppNameText(){
        return appName().getText();
    }
    
    public void mediaModalUpload(String filePath){
        btnMedia().click();
        wait.animation();
        btnUpload().sendKeys(DropsourceConstants.codeDir + filePath);
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && progressBarExists());
    }
    
    public boolean imageExists(String fileName){
        return driver.findElements(By.xpath("//span[contains(text(), '" + fileName + "')]")).size() > 0;
    }
    
}
