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
    
    public boolean coverExists(){
        return driver.findElements(By.className("cover")).size() > 0;
    }
    
    public boolean btnCancelExists(){
        return driver.findElements(By.xpath("//button[contains(text(), 'Cancel')]")).size() > 0;
    }
    
    public boolean buildSuccess(){
        return driver.findElements(By.className("build-result-success")).size() > 0;
    }
    
    public boolean buildFailure(){
        return driver.findElements(By.className("build-result-failure")).size() > 0;
    }
    
    private List<WebElement> todoErrors(){
        return driver.findElements(By.className("item-message"));
    }
    
    private WebElement elementSearchbar(){
        return driver.findElement(By.xpath("//input[@placeholder='Search Elements']"));
    }
    
    private WebElement btnClearElementSearch(){
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
    
    private WebElement nameField(){
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
        return driver.findElement(By.className("icon-media"));
    }
    
    private WebElement btnUpload(){
        return driver.findElement(By.id("media-manager-file-input"));
    }
    
    public boolean progressBarExists(){
        return driver.findElements(By.xpath("//progress[@max='100']")).size() > 0;
    }
    
    private WebElement btnX(){
        return driver.findElement(By.className("icon-cancel"));
    }
    
    public boolean btnXExists(){
        return driver.findElements(By.className("icon-cancel")).size() > 0;
    }
    
    private WebElement pageVariableTab(){
        return driver.findElement(By.xpath("//td[@data-reactid='.0.0.0.1.$centerRightContainer.0.1.0.0.0.0.$variables']"));
    }
    
    private boolean pageVariableTabActive(){
        return pageVariableTab().getAttribute("class").contains("active");
    }
    
    private WebElement pageVariablePlaceholderText(){
        return driver.findElement(By.xpath("//div[@data-reactid='.0.0.0.1.$centerRightContainer.0.1.1.0.0.0.1.0.1']"));
    }
    
    private WebElement btnAddPageVariable(){
        return driver.findElement(By.xpath("//button[@data-reactid='.0.0.0.1.$centerRightContainer.0.1.1.0.0.0.0.1.0.0.0']"));
    }
    
    public boolean failedUpload(){
        return driver.findElements(By.xpath("//div[contains(text(), 'Upload error. Please retry.')]")).size() > 0;
    }
    
    public WebElement btnType(){
        return driver.findElement(By.className("context-selector-trigger"));
    }
    
    private WebElement primitivesContainer(){
        return driver.findElement(By.xpath("//span[contains(text(),'Primitives')]"));
    }
    
    private WebElement primitive(String type){
        return driver.findElement(By.xpath("//div[@title='" + type + "']"));
    }
    
    private WebElement btnDone(){
        return driver.findElement(By.className("done"));
    }
    
    private List<WebElement> pageVariableList(){
        return driver.findElements(By.className("variable-item"));
    }
    
    private WebElement alertContent(){
        return driver.findElement(By.className("content"));
    }
    
    private WebElement pageVariableMoreOptions(String name){
        List<WebElement> pvList = pageVariableList();
        for(WebElement pv: pvList){
            if(pv.findElements(By.xpath(".//span[contains(text(), '" + name + "')]")).size() > 0){
                return pv.findElement(By.className("icon-more-options"));
            }
        }
        return null;
    }
    
    private List<WebElement> elementList(){
        return driver.findElements(By.className("draggable-element"));
    }
    
    private WebElement events(){
        return driver.findElement(By.xpath("//td[contains(text(), 'events')]"));
    }
    
    private List<WebElement> eventList(){
        return driver.findElements(By.className("event-list-item"));
    }
    
    private WebElement btnEventManage(String eventName){
        List<WebElement> eventList = eventList();
        for(WebElement event: eventList){
            if(event.findElements(By.xpath(".//div[contains(text(), '" + eventName + "')]")).size() > 0){
                return event.findElement(By.xpath(".//button[contains(text(), 'Manage')]"));
            }
        }
        return null;
    }
    
    private WebElement btnAddAction(){
        return driver.findElement(By.xpath("//button[@data-reactid='.0.1.1.$=1$modal-modal.1.1.0.0.1:$trigger-add.0']"));
    }
    
    //left panel
    public boolean lpActionExists(String actionName){
        return driver.findElements(By.xpath("//button[contains(text(), '" + actionName + "')]")).size() > 0;
    }
    
    private WebElement lpAction(String actionName){
        return driver.findElement(By.xpath("//button[contains(text(), '" + actionName + "')]"));
    }
    
    //right panel    
    public boolean rpActionExists(String actionName){
        return driver.findElements(By.xpath("//div[contains(text(), '" + actionName + "')]")).size() > 0;
    }
    
    private WebElement rpAction(String actionName){
        return driver.findElement(By.xpath("//div[contains(text(), '" + actionName + "')]"));
    }
    
    private List<WebElement> actionList(){
        return driver.findElements(By.className("trigger-add-action"));
    }
    
    private WebElement actionSearchbar(){
        return driver.findElement(By.xpath("//input[@placeholder='Search Actions']"));
    }
    
    private WebElement btnClearActionSearch(){
        return driver.findElement(By.xpath("//button[@data-reactid='.0.1.1.$=1$modal-modal.1.2.0.0.1.0.0.0.2.0']"));
    }
    
    private WebElement noElementResultsPlaceholder(){
        return driver.findElement(By.xpath("//div[@data-reactid='.0.0.0.1.$leftCabinet.0.$elements.$elements.1.2.0.1']"));
    }
        
    public boolean noElementResultsPlaceholderExists(){
        return driver.findElements(By.xpath("//div[@data-reactid='.0.0.0.1.$leftCabinet.0.$elements.$elements.1.2.0.1']")).size() > 0;
    }
    
    private WebElement noActionResultsPlaceholder(){
        return driver.findElement(By.xpath("//div[@data-reactid='.0.1.1.$=1$modal-modal.1.2.0.1.0.1']"));
    }
    
    public boolean noActionResultsPlaceholderExists(){
        return driver.findElements(By.xpath("//div[@data-reactid='.0.1.1.$=1$modal-modal.1.2.0.1.0.1']")).size() > 0;
    }
    
    private WebElement apiTab(){
        return driver.findElement(By.xpath("//td[@data-reactid='.0.0.0.1.$centerRightContainer.0.1.0.0.0.0.$api']"));
    }
    
    private boolean apiTabActive(){
        return apiTab().getAttribute("class").contains("active");
    }
    
    private WebElement btnAddAPI(){
        return driver.findElement(By.xpath("//button[@data-reactid='.0.0.0.1.$centerRightContainer.0.1.1.0.0.0.0.0.1.0.0.0']"));
    }
    
    public boolean apiModalExists(){
        return driver.findElements(By.className("api-adder-container")).size() > 0;
    }
    
    private WebElement demoAPISelectBox(){
        return driver.findElement(By.className("custom-select-input"));
    }
    
    private List<WebElement> demoAPIList(){
        return driver.findElements(By.xpath(".//option"));
    }
    
    private WebElement btnAddDemoAPI(){
        return driver.findElement(By.xpath("//button[@data-reactid='.0.1.1.$=1$modal-modal.1.1.0.1.1.0']"));
    }
    
    private WebElement apiUrlField(){
        return driver.findElement(By.xpath("//input[@placeholder='Enter API Spec URL']"));
    }
    
    private WebElement btnAddUrlAPI(){
        return driver.findElement(By.xpath("//button[@data-reactid='.0.1.1.$=1$modal-modal.1.1.2.1.1.0']"));
    }
    
    private WebElement btnUploadAPI(){
        return driver.findElement(By.id("swagger-file-input"));
    }
    
    private boolean addingAPIMessageExists(){
        return driver.findElements(By.xpath("//div[contains(text(), 'Adding to your project...')]")).size() > 0;
    }
    
    private boolean refreshAPIMessageExists(){
        return driver.findElements(By.xpath("//div[contains(text(), 'Refreshing your list of APIs')]")).size() > 0;
    }
    
    public boolean successAPIMessageExists(){
        return driver.findElements(By.xpath("//div[contains(text(), 'Success!')]")).size() > 0;
    }
    
    public boolean apiExists(String apiName){
        return driver.findElements(By.xpath("//span[contains(text(), '" + apiName + "')]")).size() > 0;
    }
    
    public boolean pageVariableExists(String name){
        boolean found = false;
        List<WebElement> pvList = pageVariableList();
        for(WebElement pv: pvList){
            if(pv.findElements(By.xpath(".//span[contains(text(), '" + name + "')]")).size() > 0){
                found = true;
                break;
            }
        }
        return found;
    }
    
    public boolean correctPageVariableType(String name, String type){
        List<WebElement> pvList = pageVariableList();
        for(WebElement pv: pvList){
            if(pv.findElements(By.xpath(".//span[contains(text(), '" + name + "')]")).size() > 0){
                return pv.findElement(By.className("type")).getText().equals(type);
            }
        }
        return false;
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
    }
    
    public String getAlertText(){
        return alertContent().getText();
    }
    
    public void confirmDelete(){
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
        wait.animation();
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
        cover().click();
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
        btnClearElementSearch().click();
        wait.animation();
    }
    
    public void changeAppName(String appName){
        if (!settingsDrawerActive()) {
            settingsDrawer().click();
            wait.animation();
        }
        btnEdit().click();
        nameField().clear();
        nameField().sendKeys(appName);
        btnSave().click();
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && btnSaveExists());
    }
    
    public String getAppNameText(){
        return appName().getText();
    }
    
    public void uploadImage(String filePath){
        btnMedia().click();
        wait.animation();
        btnUpload().sendKeys(DropsourceConstants.codeDir + filePath);
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && progressBarExists());
    }
    
    public boolean checkImageExists(String fileName){
        return driver.findElements(By.xpath("//span[contains(text(), '" + fileName + "')]")).size() > 0;
    }
    
    public void closeModal(){
        btnX().click();
        wait.animation();
    }
    
    public String getPageVariablePlaceholderText(){
        return pageVariablePlaceholderText().getText();
    }
    
    public void addPageVariable(String name, String type){
        if (!pageVariableTabActive()) {
            pageVariableTab().click();
        }
        btnAddPageVariable().click();
        wait.animation();
        nameField().sendKeys(name);
        btnType().click();
        primitivesContainer().click();
        primitive(type).click();
        btnDone().click();
        btnSave().click();
        wait.animation();
    }
    
    public void editPageVariable(String name, String newName, String type){
        if (!pageVariableTabActive()) {
            pageVariableTab().click();
        }
        action.moveToElement(pagesDrawer()).perform();
        action.moveToElement(pageVariableMoreOptions(name)).perform();
        btnRenamePage().click();
        wait.animation();
        nameField().clear();
        nameField().sendKeys(newName);
        btnType().click();
        primitive(type).click();
        btnDone().click();
        btnSave().click();
        wait.animation();
    }
    
    public void deletePageVariable(String name){
        if (!pageVariableTabActive()) {
            pageVariableTab().click();
        }
        action.moveToElement(pagesDrawer()).perform();
        action.moveToElement(pageVariableMoreOptions(name)).perform();
        btnDelPage().click();
        wait.animation();
    }
    
    public int getIOSElementCount(){
        if (!elementsDrawerActive()) {
            elementsDrawer().click();
            wait.animation();
        }
        return elementList().size();
    }
    
    public int getIOSActionCount(){
        return actionList().size();
    }
    
    public void openEventsTab(){
        canvas().click();
        canvas().click();
        events().click();
    }
    
    public void openEventsModal(String event){
        openEventsTab();
        btnEventManage(event).click();
        wait.animation();
    }
    
    public void openActionList(){
        btnAddAction().click();
        wait.animation();
    }
    
    public void addAction(String actionName){
        rpAction(actionName).click();
    }
    
    public void actionSearch(String search){
        actionSearchbar().clear();
        actionSearchbar().sendKeys(search);
        //hard wait since dynamic search removes in chunks with no indicator of when finished
        wait.waitMilliSecs(750);
    }
    
    public void clearActionSearch(){
        btnClearActionSearch().click();
        wait.animation();
    }
    
    public String getNoElementResultsPlaceholderText(){
        return noElementResultsPlaceholder().getText();
    }
    
    public String getNoActionResultsPlaceholderText(){
        return noActionResultsPlaceholder().getText();
    }
    
    public void openAddAPIModal(){
        if (!apiTabActive()) {
            apiTab().click();
        }
        btnAddAPI().click();
        wait.animation();
    }
    
    public void addDemoAPI(String API){
        demoAPISelectBox().sendKeys(API);
        btnAddDemoAPI().click();
        uploadProcess();
    }
    
    public void addUrlAPI(String url){
        openAddAPIModal();
        //apiUrlField().clear();
        apiUrlField().sendKeys(url);
        btnAddUrlAPI().click();
        uploadProcess();
    }
    
    public void uploadAPI(String filepath){
        openAddAPIModal();
        btnUploadAPI().sendKeys(DropsourceConstants.codeDir + filepath);
        uploadProcess();
    }
    
    public ArrayList<String> getDemoAPIList(){
        ArrayList<String> apis = new ArrayList<>();
        for(WebElement api: demoAPIList()){
            apis.add(api.getText());
        }
        return apis;
    }
    
    public int getDemoAPIListSize(){
        return demoAPIList().size();
    }
    
    public void uploadProcess(){
        long timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && !addingAPIMessageExists());
        timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && addingAPIMessageExists());
        timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && refreshAPIMessageExists());
        timer = System.currentTimeMillis();
        while(System.currentTimeMillis() - timer < 10000 && !successAPIMessageExists());
    }
}
