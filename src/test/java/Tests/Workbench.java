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

    @Test(groups = {"smoke", "todo"}, threadPoolSize = 3)
    public void todoPreCreate() throws IOException {
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
        String confirmationText = "Deleting a page will delete this page, it's elements, events, and requests, as well as all references to this page in other requests and events";
        res.checkTrue(wb.getAlertText().equals(confirmationText), uniqueID++ + " - Delete page confirmation text doesn't match the expected");
        wb.confirmDelete();
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

    @Test(groups = {"smoke", "element count"}, threadPoolSize = 3)
    public void elementCount() throws IOException {
        res.checkTrue(wb.getIOSElementCount() == DropsourceConstants.iOSElementCount, uniqueID++ + " - " + wb.getIOSElementCount() + " elements found but expected " + DropsourceConstants.iOSElementCount);
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
    public Object[][] searchElements() throws FileNotFoundException, IOException {
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "SearchElements.txt");
        return data.getData();
    }

    @Test(groups = {"smoke", "search"}, dataProvider = "searchElements", threadPoolSize = 3)
    public void elementSearch(String... searchAndResults) throws IOException {
        wb.elementSearch(searchAndResults[0]);
        for (int i = 1; i < searchAndResults.length - 1; i++) {
            res.checkTrue(wb.checkElementExists(searchAndResults[i]), uniqueID++ + " - Element (" + searchAndResults[i] + ") was not found in the results list");
        }
        res.checkTrue(!wb.checkElementExists(searchAndResults[searchAndResults.length - 1]), uniqueID++ + " - Element (" + searchAndResults[searchAndResults.length - 1] + ") was found in the results list");
        wb.clearElementSearch();
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "iOSElements.txt");
        String[][] elements = data.getData();
        for (String element : elements[0]) {
            res.checkTrue(wb.checkElementExists(element), uniqueID++ + " - Element (" + element + ") isn't in the element list");
        }
    }

    @Parameters("appName")
    @Test(groups = {"smoke", "change app name"}, threadPoolSize = 3)
    public void changeAppName(@Optional("Best App Ever") String appName) throws IOException {
        wb.changeAppName(appName);
        res.checkTrue(!wb.btnSaveExists(), uniqueID++ + " - Preferences modal never closed");
        res.checkTrue(wb.getAppNameText().equals(appName), uniqueID++ + " - The app name doesn't match the expected name (" + appName + ")");
    }

    @Test(groups = {"smoke", "upload image"}, threadPoolSize = 3)
    public void uploadImage() throws IOException {
        try {
            wb.uploadImage(DropsourceConstants.dataSheetLocation + "profilepicture.jpg");
            res.checkTrue(wb.checkImageExists("profilepicture.jpg"), uniqueID++ + " - Image was not uploaded successfully");
            res.checkTrue(!wb.progressBarExists(), uniqueID++ + " - Image was still uploading after 10 seconds or failed to upload");
        } catch (AssertionError e) {
            fail(e.toString());
        } finally {
            wb.closeModal();
        }
    }

    @Parameters({"pvName", "pvType"})
    @Test(groups = {"smoke", "create page variable"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void createPageVariable(@Optional("pvString") String pvName, @Optional("String") String pvType) throws IOException {
        wb.addPageVariable(pvName, pvType);
        res.checkTrue(wb.pageVariableExists(pvName), uniqueID++ + " - Page variable (" + pvName + ") wasn't created successfully");
        res.checkTrue(wb.correctPageVariableType(pvName, pvType), uniqueID++ + " - Page variabe (" + pvName + ") was not of the correct type (" + pvType + ")");
    }

    @Parameters({"pvOldName", "pvOldType", "pvNewName", "pvNewType"})
    @Test(groups = {"smoke", "edit page variable"}, dependsOnGroups = "create page variable", threadPoolSize = 3)
    public void editPageVariable(@Optional("pvOld") String pvOldName, @Optional("Number") String pvOldType, @Optional("pvNew") String pvNewName, @Optional("Boolean") String pvNewType) throws IOException {
        wb.addPageVariable(pvOldName, pvOldType);
        res.checkTrue(wb.pageVariableExists(pvOldName), uniqueID++ + " - Page variable (" + pvOldName + ") wasn't created successfully");
        res.checkTrue(wb.correctPageVariableType(pvOldName, pvOldType), uniqueID++ + " - Page variabe (" + pvOldName + ") was not of the correct type (" + pvOldType + ")");
        wb.editPageVariable(pvOldName, pvNewName, pvNewType);
        res.checkTrue(wb.pageVariableExists(pvNewName), uniqueID++ + " - Page variable (" + pvNewName + ") wasn't edited successfully");
        res.checkTrue(wb.correctPageVariableType(pvNewName, pvNewType), uniqueID++ + " - Page variabe (" + pvNewName + ") was not of the correct type (" + pvNewType + ")");
    }

    @Parameters({"pvDelName", "pvDelType"})
    @Test(groups = {"smoke", "delete page variable"}, dependsOnGroups = "create page variable", threadPoolSize = 3)
    public void deletePageVariable(@Optional("delete") String pvDelName, @Optional("String") String pvDelType) throws IOException {
        wb.addPageVariable(pvDelName, pvDelType);
        res.checkTrue(wb.pageVariableExists(pvDelName), uniqueID++ + " - Page variable (" + pvDelName + ") wasn't created successfully");
        res.checkTrue(wb.correctPageVariableType(pvDelName, pvDelType), uniqueID++ + " - Page variabe (" + pvDelName + ") was not of the correct type (" + pvDelType + ")");
        wb.deletePageVariable(pvDelName);
        String confirmationText = "Are you sure you want to delete this inbound context? Doing so will permanently delete it and remove any reference of it from parameters and actions it is used in.";
        res.checkTrue(wb.getAlertText().equals(confirmationText), uniqueID++ + " - Delete page variable confirmation text doesn't match the expected");
        wb.confirmDelete();
        res.checkTrue(!wb.pageVariableExists(pvDelName), uniqueID++ + " - Page variable (" + pvDelName + ") wasn't deleted successfully");
    }

    
    //Test is slightly flawed since it checks substring so Go Forward might trigger
    //inside Go Forward in a Web View.  Will need to make a test tag based off name
    //similar to the way elements was done in order to accurately test this.
    @Test(groups = {"smoke", "check actions"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void checkActions() throws FileNotFoundException, IOException {
        wb.openEventsModal("Page Loaded");
        wb.openActionList();
        boolean failed = false;
        String missingActions = "";
        
        try{
        res.checkTrue(wb.getIOSActionCount() == DropsourceConstants.iOSActionCount, uniqueID++ + " - " + wb.getIOSActionCount() + " actions found but expected " + DropsourceConstants.iOSActionCount);
        }catch(AssertionError e){
            missingActions = e.toString() + ", ";
            failed = true;
        }
        
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "iOSActionList.txt");
        String[][] actions = data.getData();
        
        for (String[] action : actions) {
            try{
                res.checkTrue(wb.rpActionExists(action[0]), uniqueID++ + " - Action (" + action[0] + ") doesn't exist in the list");
            }catch(AssertionError e){
                missingActions += action[0] + " is missing, ";
                failed = true;
            }
        }
        
        wb.closeModal();
        
        if(failed)fail(missingActions);
    }

    //@Test(groups = {"smoke", "search actions"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void searchActions() {
        
    }
    
    @Parameters("actionName")
    @Test(groups = {"smoke", "add action"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void addAction(@Optional ("Hide Keyboard") String actionName) throws IOException {
        wb.openEventsTab();
        wb.openEventsModal("Page Loaded");
        wb.openActionList();
        wb.addAction(actionName);
        try{
            res.checkTrue(wb.lpActionExists(actionName), uniqueID++ + " - Action (" + actionName + ") wasn't successfully added");
        }catch(AssertionError e){
            fail(e.toString());
        }finally{
            wb.closeModal();
        }
    }
    
    //Add invalid search tests for actions and elements

}
