package Tests;

import Utility.DataReader;
import Utility.DropsourceConstants;
import static com.thoughtworks.selenium.SeleneseTestBase.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/*
 * @author Jonathan Doll
 */
public class Workbench extends TestSetup {

    @Parameters({"browser", "workbenchProject"})
    @BeforeClass(groups = {"before"})
    public void setUp(@Optional("chrome") String browser, @Optional("Workbench Testing") String workbenchProject) {
        super.setUp(browser);
        try {
            login();
            createProject(workbenchProject);
            openProject(workbenchProject);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Parameters("workbenchProject")
    @AfterClass(groups = {"after"})
    public void tearDown(@Optional("Workbench Testing") String workbenchProject) {
        try {
            tearDownCloseAllTabsExceptDashboard();
            deleteProject(workbenchProject);
            logout();
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            super.tearDown();
        }
    }

    /*@Test(groups = {"smoke", "todo"}, threadPoolSize = 3)
    public void todoPreCreate() throws IOException {
        System.out.println(wb.checkTodoErrorAmount());
        res.checkTrue(wb.checkTodoErrorAmount() > 0, uniqueID++ + " - No todo errors are displayed");

        ArrayList<String> errors = wb.getTodoErrors();
        String error1 = "This app doesn't have any pages. Click the Pages button to add one.";
        String error2 = "1 key page has not been assigned a page";

        res.checkTrue(errors.contains(error1), uniqueID++ + " - Todo list missing no page error");
        res.checkTrue(errors.contains(error2), uniqueID++ + " - Todo list missing no key pages error");
    }

    @Parameters("pageName")
    @Test(groups = {"smoke", "create page"}, dependsOnGroups = "todo", threadPoolSize = 3)
    public void addPage(@Optional("i1") String pageName) throws IOException {
        wb.addPage(pageName);
        res.checkTrue(!wb.btnCreateExists(), uniqueID++ + " - Create page modal never closed");
        res.checkTrue(wb.pageExists(pageName), uniqueID++ + " - Page (" + pageName + ") was not created successfully");
    }

    @Parameters("deletePageName")
    @Test(groups = {"smoke", "delete page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void deletePage(@Optional("delete name") String deletePageName) throws IOException {
        wb.addPage(deletePageName);
        res.checkTrue(!wb.btnCreateExists(), uniqueID++ + " - Create page modal never closed");
        res.checkTrue(wb.pageExists(deletePageName), uniqueID++ + " - Page (" + deletePageName + ") was not created successfully");
        wb.deletePage(deletePageName);
        res.checkTrue(!wb.pageExists(deletePageName), uniqueID++ + " - Page (" + deletePageName + ") was not deleted successfully");
    }

    @Parameters({"pageName3", "newPageName"})
    @Test(groups = {"smoke", "rename page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void renamePage(@Optional("i3") String pageName3, @Optional("rename name") String newPageName) throws IOException {
        wb.addPage(pageName3);
        res.checkTrue(!wb.btnCreateExists(), uniqueID++ + " - Create page modal never closed");
        res.checkTrue(wb.pageExists(pageName3), uniqueID++ + " - Page (" + pageName3 + ") was not created successfully");
        wb.renamePage(pageName3, newPageName);
        res.checkTrue(!wb.btnRenameExists(), uniqueID++ + " - Rename page modal never closed");
        res.checkTrue(wb.pageExists(newPageName), uniqueID++ + " - Page (" + newPageName + ") was not renamed successfully");
    }

    @Parameters({"pageName", "pageName2"})
    @Test(groups = {"smoke", "rearrange page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void rearrangePages(@Optional("i1") String pageName, @Optional("i2") String pageName2) throws IOException {
        wb.addPage(pageName2);
        res.checkTrue(wb.pageExists(pageName2), uniqueID++ + " - Page (" + pageName2 + ") was not created successfully");
        wb.rearrangePage(pageName, pageName2);
        res.checkTrue(wb.getFirstPageName().equals(pageName2), uniqueID++ + " - Pages weren't rearranged successfully. " + pageName2 + " should be first.");
    }

    @DataProvider
    public Object[][] iOSElements() throws FileNotFoundException, IOException {
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "iOSElements.txt");
        return data.getData();
    }

    //Checks that expected elements are there, not that unexpected elements aren't there
    @Test(groups = {"smoke", "check elements"}, dataProvider = "iOSElements", threadPoolSize = 3)
    public void checkIOSElements(String elementName) throws IOException {
        res.checkTrue(wb.checkElementExists(elementName), uniqueID++ + " - Element (" + elementName + ") isn't in the element list");
    }

    @Test(groups = {"smoke", "build"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void buildIOSBrowser() throws IOException {
        try {
            wb.initiateIOSBrowserBuild();
            res.checkTrue(wb.btnCancelExists(), uniqueID++ + " - Build did not initiate successfully");
            wb.waitForBuild();
            res.checkTrue(wb.buildSuccess(), uniqueID++ + " - Build was not sucessful");
        } catch (AssertionError e) {
            fail(e.toString());
        } finally {
            wb.closeRunMenu();
        }
    }
    
    @DataProvider
    public Object[][] searchElements() throws FileNotFoundException, IOException{
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "SearchElements.txt");
        return data.getData();
    }
    
    @Test(groups = {"smoke", "search"}, dataProvider = "searchElements", threadPoolSize = 3)
    public void elementSearch(String... searchAndResults) throws IOException{
        wb.elementSearch(searchAndResults[0]);
        for(int i = 1; i < searchAndResults.length-1; i++){
            res.checkTrue(wb.checkElementExists(searchAndResults[i]), uniqueID++ + " - Element (" + searchAndResults[i] + ") was not found in the results list");
        }
        res.checkTrue(!wb.checkElementExists(searchAndResults[searchAndResults.length-1]), uniqueID++ + " - Element (" + searchAndResults[searchAndResults.length-1] + ") was found in the results list");
        wb.clearElementSearch();
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "iOSElements.txt");
        String[][] elements = data.getData();
        for(String element: elements[0]){
            res.checkTrue(wb.checkElementExists(element), uniqueID++ + " - Element (" + element + ") isn't in the element list");
        }
    }
    
    @Parameters("appName")
    @Test(groups = {"smoke", "change app name"}, threadPoolSize = 3)
    public void changeAppName(@Optional ("Best App Ever") String appName) throws IOException{
        wb.changeAppName(appName);
        res.checkTrue(!wb.btnSaveExists(), uniqueID++ + " - Preferences modal never closed");
        res.checkTrue(wb.getAppNameText().equals(appName), uniqueID++ + " - The app name doesn't match the expected name (" + appName + ")");
    }*/
    
    @Test(groups = {"smoke", "upload image"}, threadPoolSize = 3)
    public void uploadImage(){
        wb.uploadImage(DropsourceConstants.dataSheetLocation + "profilepicture.png");
        wb.checkImageExists("profilepicture.png");
        wb.closeModal();
    }

}
