package Pages;

import Utility.DropsourceConstants;
import Utility.Wait;
import java.util.List;
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
        return driver.findElements(By.xpath("//div[@data-reactid='.0.0.0.0.1.0.0.0.0.0:$java=1android.0.0']")).size() > 0;
    }

    public void sync() {
        super.sync(elementExists());
    }

    private boolean loader() {
        return driver.findElements(By.xpath("//div[@data-test='loading-message']")).size() > 0;
    }

    public void waitForLoader() {
        super.sync(loader());
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time < DropsourceConstants.pageTimeoutLimit * 1000 && loader());
    }

    public WebElement pagesDrawer() {
        return driver.findElement(By.xpath("//button[@data-reactid='.0.0.1.$leftButtons.$pages']"));
    }

    public boolean pageDrawerActive() {
        return pagesDrawer().getAttribute("class").contains("active");
    }

    public WebElement androidIcon() {
        return driver.findElement(By.xpath("//div[@data-reactid='.0.0.0.0.1.0.0.0.0.0:$java=1android.0.0']"));
    }

    public boolean androidTabOpen() {
        return androidIcon().getAttribute("class").contains("active");
    }

    public WebElement iosIcon() {
        return driver.findElement(By.xpath("//div[@data-reactid='.0.0.1.$leftCabinetDrawer.0.$pages.0.0.0.0:$ios.0.0']"));
    }

    public boolean iosTabOpen() {
        return iosIcon().getAttribute("class").contains("active");
    }

    private List<WebElement> pages() {
        //return driver.findElements(By.cssSelector("div[data-sortable-id]"));
        return driver.findElements(By.className("sortable-handle"));
    }

    private WebElement findPage(String pageName) {
        WebElement page = null;
        if (pages().size() > 0) {
            for (WebElement p : pages()) {
                if (p.getText().equals(pageName)) {
                    page = p;
                    break;
                }
            }
        }
        return page;
    }

    private WebElement ellipsis(String pageName) {
        return findPage(pageName).findElement(By.className("ellipsis-icon"));
    }

    private WebElement delPage(String pageName) {
        return findPage(pageName).findElement(By.className("delete-button"));
    }

    private WebElement confirmDelete() {
        return driver.findElement(By.xpath("//button[contains(text(), 'Confirm Delete')]"));
    }

    private WebElement addAPage() {
        return driver.findElement(By.xpath("//button[@data-reactid='.0.0.1.$leftCabinetDrawer.0.$pages.0.1.0.0.1.1.0.0']"));
    }

    private WebElement nameYourPage() {
        return driver.findElement(By.xpath("//input[@placeholder='Page Name']"));
    }

    private WebElement btnCreate() {
        return driver.findElement(By.xpath("//button[contains(text(), 'Create')]"));
    }

    public boolean pageExists(String pageName) {
        return (findPage(pageName) != null);
    }

    private boolean savingHeader() {
        return driver.findElements(By.className("saving")).size() > 0;
    }

    private boolean savedHeader() {
        return driver.findElements(By.className("saved")).size() > 0;
    }

    public boolean loadingCoverExists() {
        return driver.findElements(By.className("loading-cover")).size() > 0;
    }

    public boolean pageDrawerOpened() {
        return androidIcon().isDisplayed();
    }

    public String[] getPageNames() {
        String[] names = new String[pages().size()];
        int i = 0;
        for (WebElement p : pages()) {
            names[i++] = p.getText();
        }
        return names;
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
        action.moveToElement(pagesDrawer()).perform();
        action.moveToElement(ellipsis(pageName)).perform();
        action.moveToElement(delPage(pageName)).click().perform();
        confirmDelete().click();
    }

    public void addPage(String pageName) {
        wait.waitSecs(3);
        if (!pageDrawerActive()) {
            pagesDrawer().click();
        }
        wait.waitSecs(3);
        addAPage().click();
        nameYourPage().sendKeys(pageName);
        btnCreate().click();
        wait.waitSecs(3);
    }

}
