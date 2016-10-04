package Tests;

import Utility.DataReader;
import Utility.DropsourceConstants;
import static com.thoughtworks.selenium.SeleneseTestBase.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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

    @AfterMethod
    public void cleanUp() {
        cleanUpPage();
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
        wb.initiateIOSBrowserBuild();
        res.checkTrue(wb.btnBuildCancelExists(), uniqueID++ + " - Build did not initiate successfully");
        wb.waitForBuild();
        res.checkTrue(wb.buildSuccess(), uniqueID++ + " - Build was not successful");
        wb.closeRunMenu();
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
    
    @Test(groups = {"smoke", "element bad search"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void elementBadSearch() throws IOException{
        String expectedText = "Oops! We can't find any elements that match your search term. Please clear the search bar and try again.";
        wb.elementSearch("zzzzzzz");
        res.checkTrue(wb.noElementResultsPlaceholderExists(), uniqueID++ + " - No element results placeholder didn't appear with an invalid search term");
        res.checkTrue(wb.getNoElementResultsPlaceholderText().equals(expectedText), uniqueID++ + " - No element search results placeholder text was incorrect");
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
        wb.uploadImage(DropsourceConstants.dataSheetLocation + "profilepicture.jpg");
        res.checkTrue(wb.checkImageExists("profilepicture.jpg"), uniqueID++ + " - Image was not uploaded successfully");
        res.checkTrue(!wb.progressBarExists(), uniqueID++ + " - Image was still uploading after 10 seconds or failed to upload");
        wb.closeModal();
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

    @Test(groups = {"smoke", "check actions"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void checkActions() throws FileNotFoundException, IOException {
        wb.openEventsModal("Page Loaded");
        wb.openActionList();
        boolean failed = false;
        String missingActions = "";

        try {
            res.checkTrue(wb.getIOSActionCount() == DropsourceConstants.iOSActionCount, uniqueID++ + " - " + wb.getIOSActionCount() + " actions found but expected " + DropsourceConstants.iOSActionCount);
        } catch (AssertionError e) {
            missingActions = e.toString() + ", ";
            failed = true;
        }

        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "iOSActionList.txt");
        String[][] actions = data.getData();

        for (String[] action : actions) {
            try {
                res.checkTrue(wb.rpActionExists(action[0]), uniqueID++ + " - Action (" + action[0] + ") was not found in the list");
            } catch (AssertionError e) {
                missingActions += action[0] + " is missing, ";
                failed = true;
            }
        }

        wb.closeModal();

        if (failed) {
            fail(missingActions);
        }
    }

    //Data sheet has hardcoded search results for now
    @Test(groups = {"smoke", "search actions"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void searchActions() throws FileNotFoundException, IOException {
        wb.openEventsModal("Page Loaded");
        wb.openActionList();

        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "iOSSearchActions.txt");
        String[][] searches = data.getData();

        for (String[] search : searches) {
            wb.actionSearch(search[0]);
            res.checkTrue(wb.getIOSActionCount() == (search.length - 2), uniqueID++ + " - Search " + search[0] + " returned (" + wb.getIOSActionCount() + ") results when it should have had (" + (search.length - 2) + ") results");
            for (int i = 1; i < search.length - 1; i++) {
                res.checkTrue(wb.rpActionExists(search[i]), uniqueID++ + " - Action (" + search[i] + ") was not found in the list for search (" + search[0] + ")");
            }
            res.checkTrue(!wb.rpActionExists(search[search.length - 1]), uniqueID++ + " - Action (" + search[search.length - 1] + ") was found in the list");
        }
        
        wb.clearActionSearch();
        res.checkTrue(wb.getIOSActionCount() == DropsourceConstants.iOSActionCount, uniqueID++ + " - " + wb.getIOSActionCount() + " actions found but expected " + DropsourceConstants.iOSActionCount);
        wb.closeModal();
    }

    @Parameters("actionName")
    @Test(groups = {"smoke", "add action"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void addAction(@Optional("Hide Keyboard") String actionName) throws IOException {
        wb.openEventsModal("Page Loaded");
        wb.openActionList();
        wb.addAction(actionName);
        res.checkTrue(wb.lpActionExists(actionName), uniqueID++ + " - Action (" + actionName + ") wasn't successfully added");
        wb.closeModal();
    }

    @Test(groups = {"smoke", "action bad search"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void actionBadSearch() throws IOException{
        wb.openEventsModal("Page Loaded");
        wb.openActionList();
        String expectedText = "Oops! We can't find any actions that match your search term. Please clear the search bar and try again.";
        wb.actionSearch("zzzzzzz");
        res.checkTrue(wb.noActionResultsPlaceholderExists(), uniqueID++ + " - No action results placeholder didn't appear with an invalid search term");
        res.checkTrue(wb.getNoActionResultsPlaceholderText().equals(expectedText), uniqueID++ + " - No action search results placeholder text was incorrect");
    }
    
    @Test(groups = {"smoke", "add demo api"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void addDemoAPI() throws IOException{
        wb.openAddAPIModal();
        res.checkTrue(wb.apiModalExists(), uniqueID++ + " - Add API modal didn't open successfully");
        ArrayList<String> expectedAPIs = new ArrayList<>();
        expectedAPIs.add("Buzzfeed API");
        expectedAPIs.add("Slack Channels API");
        expectedAPIs.add("Open Weather Map API");
        expectedAPIs.add("Google Places API");
        res.checkTrue(expectedAPIs.size() == wb.getDemoAPIListSize(), uniqueID++ + " - The number of expected demo APIs (" + expectedAPIs.size() + ") doesn't match the actual count (" + wb.getDemoAPIListSize() + ")");
        
        ArrayList<String> actualAPIs = wb.getDemoAPIList();
        for(String api: actualAPIs){
            res.checkTrue(expectedAPIs.contains(api), uniqueID++ + " - " + api + " was not found in the expected API list");
        }
        wb.addDemoAPI("Open Weather Map API");
        res.checkTrue(wb.successAPIMessageExists(), uniqueID++ + " - API was not uploaded successfully");
        wb.closeModal();
        res.checkTrue(wb.apiExists("OpenWeatherMap"), uniqueID++ + " - API (OpenWeatherMap) wasn't found in the API list");
    }
    
    @Test(groups = {"smoke", "add url api"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void addUrlAPI() throws IOException{
        //Google places
        String url = "https://api.stoplight.io/v1/versions/ryDPQuGG5NZZFN2bW/export/oas.json";
        wb.addUrlAPI(url);
        res.checkTrue(wb.successAPIMessageExists(), uniqueID++ + " - API was not uploaded successfully");
        wb.closeModal();
        res.checkTrue(wb.apiExists("Google Places"), uniqueID++ + " - API (Google Places) wasn't found in the API list");
    }
    
    @Test(groups = {"smoke", "add file api"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void addFileAPI() throws IOException{
        wb.uploadAPI(DropsourceConstants.dataSheetLocation + "optimalprint.json");
        res.checkTrue(wb.successAPIMessageExists(), uniqueID++ + " - API was not uploaded successfully");
        wb.closeModal();
        res.checkTrue(wb.apiExists("Optimalprint API"), uniqueID++ + " - API (Optimalprint API) wasn't found in the API list");
    }
    
    @Test(groups = {"smoke", "delete api"}, dependsOnGroups = "add demo api", threadPoolSize = 3)
    public void deleteAPI() throws IOException{
        wb.openAddAPIModal();
        wb.addDemoAPI("Buzzfeed API");
        res.checkTrue(wb.successAPIMessageExists(), uniqueID++ + " - API was not uploaded successfully");
        wb.closeModal();
        res.checkTrue(wb.apiExists("Buzzfeed Api"), uniqueID++ + " - API (Buzzfeed API) wasn't found in the API list");
        wb.deleteAPI("Buzzfeed Api");
        String errorMessage = "Deleting this API will permanently remove it from this project. You will no longer be able to reference it on any pages in your app. Are you sure you want to delete it?";
        res.checkTrue(wb.getAlertText().equals(errorMessage), uniqueID++ + " - Delete API alert text doesn't match expected text");
        wb.confirmDeleteAPI();
    }
    
    @Test(groups = {"smoke", "check page errors"}, threadPoolSize = 3)
    public void checkPageErrors() throws IOException{
        String maxLengthErrorText = "Name must be less than 64 characters long.";
        String numberUnderscoreErrorText = "Names can't start with a number or underscore.";
        String specialCharacterErrorText = "Special characters are not allowed.";
        
        wb.goToNamePageModalScreen();
        
        wb.nameItem("1");
        res.checkTrue(wb.pageNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        res.checkTrue(wb.getPageNumberUnderscoreErrorText().equals(numberUnderscoreErrorText), uniqueID++ + " - Page number/underscore error text did not match expected text");
        
        wb.nameItem("_");
        res.checkTrue(wb.pageNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        res.checkTrue(wb.getPageNumberUnderscoreErrorText().equals(numberUnderscoreErrorText), uniqueID++ + " - Page number/underscore error text did not match expected text");
        
        wb.nameItem("ß");
        res.checkTrue(wb.pageSpecialCharacterErrorExists(), uniqueID++ + " - Page special character error didn't successfully display");
        res.checkTrue(wb.getPageSpecialCharacterErrorText().equals(specialCharacterErrorText), uniqueID++ + " - Page special character error text did not match expected text");
        
        wb.nameItem("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLM");
        res.checkTrue(wb.pageLengthErrorExists(), uniqueID++ + " - Page max length error didn't successfully display");
        res.checkTrue(wb.getPageLengthErrorText().equals(maxLengthErrorText), uniqueID++ + " - Page max length error text did not match expected text");
        
        wb.nameItem("1ß");
        res.checkTrue(wb.pageNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        res.checkTrue(wb.pageSpecialCharacterErrorExists(), uniqueID++ + " - Page special character error didn't successfully display");
        
        wb.nameItem("1ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLM");
        res.checkTrue(wb.pageLengthErrorExists(), uniqueID++ + " - Page max length error didn't successfully display");
        res.checkTrue(wb.pageNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        
        wb.nameItem("ßABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLM");
        res.checkTrue(wb.pageLengthErrorExists(), uniqueID++ + " - Page max length error didn't successfully display");
        res.checkTrue(wb.pageSpecialCharacterErrorExists(), uniqueID++ + " - Page special character error didn't successfully display");
        
        wb.nameItem("1ßABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLM");
        res.checkTrue(wb.pageNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        res.checkTrue(wb.pageSpecialCharacterErrorExists(), uniqueID++ + " - Page special character error didn't successfully display");
        res.checkTrue(wb.pageLengthErrorExists(), uniqueID++ + " - Page max length error didn't successfully display");
        
        wb.closeModal();
    }
    
    @Test(groups = {"smoke", "check page variable errors"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void checkPageVariableErrors() throws IOException{
        String maxLengthErrorText = "Name must be less than 64 characters long.";
        String numberUnderscoreErrorText = "Names can't start with a number or underscore.";
        String specialCharacterErrorText = "Special characters are not allowed.";
        
        wb.openPageVariableModal();
        
        wb.nameItem("1");
        res.checkTrue(wb.pageVariableNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        res.checkTrue(wb.getPageVariableNumberUnderscoreErrorText().equals(numberUnderscoreErrorText), uniqueID++ + " - Page number/underscore error text did not match expected text");
        
        wb.nameItem("_");
        res.checkTrue(wb.pageVariableNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        res.checkTrue(wb.getPageVariableNumberUnderscoreErrorText().equals(numberUnderscoreErrorText), uniqueID++ + " - Page number/underscore error text did not match expected text");
        
        wb.nameItem("ß");
        res.checkTrue(wb.pageVariableSpecialCharacterErrorExists(), uniqueID++ + " - Page special character error didn't successfully display");
        res.checkTrue(wb.getPageVariableSpecialCharacterErrorText().equals(specialCharacterErrorText), uniqueID++ + " - Page special character error text did not match expected text");
        
        wb.nameItem("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLM");
        res.checkTrue(wb.pageVariableLengthErrorExists(), uniqueID++ + " - Page max length error didn't successfully display");
        res.checkTrue(wb.getPageVariableLengthErrorText().equals(maxLengthErrorText), uniqueID++ + " - Page max length error text did not match expected text");
        
        wb.nameItem("1ß");
        res.checkTrue(wb.pageVariableNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        res.checkTrue(wb.pageVariableSpecialCharacterErrorExists(), uniqueID++ + " - Page special character error didn't successfully display");
        
        wb.nameItem("1ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLM");
        res.checkTrue(wb.pageVariableLengthErrorExists(), uniqueID++ + " - Page max length error didn't successfully display");
        res.checkTrue(wb.pageVariableNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        
        wb.nameItem("ßABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLM");
        res.checkTrue(wb.pageVariableLengthErrorExists(), uniqueID++ + " - Page max length error didn't successfully display");
        res.checkTrue(wb.pageVariableSpecialCharacterErrorExists(), uniqueID++ + " - Page special character error didn't successfully display");
        
        wb.nameItem("1ßABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLM");
        res.checkTrue(wb.pageVariableNumberUnderscoreErrorExists(), uniqueID++ + " - Page number/underscore error didn't successfully display");
        res.checkTrue(wb.pageVariableSpecialCharacterErrorExists(), uniqueID++ + " - Page special character error didn't successfully display");
        res.checkTrue(wb.pageVariableLengthErrorExists(), uniqueID++ + " - Page max length error didn't successfully display");
        
        wb.closeModal();
    }
}
