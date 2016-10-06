package Tests;

import Utility.DataReader;
import Utility.DropsourceConstants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import static org.testng.Assert.fail;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author Jonathan Doll
 */
public class Elements extends TestSetup{
    
    @Parameters({"browser", "workbenchProject"})
    @BeforeClass(groups = {"before"})
    public void setUp(@Optional("chrome") String browser, @Optional("Elements") String workbenchProject) {
        super.setUp(browser);
        try {
            login("jdoll+135@dropsource.com", "Password1");
            openProject(workbenchProject);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @AfterClass(groups = {"after"})
    public void tearDown() {
        try {
            tearDownCloseAllTabsExceptDashboard();
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
    
    @DataProvider
    public Object[][] propertyData() throws FileNotFoundException, IOException{
        return new DataReader(DropsourceConstants.dataSheetLocation + "iOSElementProperties.txt").getData();
    }
    
    //checks that expected properties are there
    //doesn't check if unexpected properties are there
    @Test(groups = {"smoke", "property check"}, dataProvider = "propertyData", threadPoolSize = 3)
    public void propertyCheck(String... properties) throws IOException{
        wb.selectElementTreeElement(properties[0]);
        wb.openStylesTab();
        boolean failed = false;
        String errors = "";
        for(int i = 1; i < properties.length; i++){
            try{
            res.checkTrue(wb.propertyExists(properties[i]), uniqueID++ + " - Property (" + properties[i] + ") not found for element (" + properties[0] + ")");
            }catch(AssertionError e){
                failed = true;
                errors += e + ", ";
            }
        }
        if(failed)fail(errors);
    }
    
    //Will need to wait for move route
    @Parameters("elementName")
    //@Test
    public void constraintsCheck(@Optional ("Button 1") String elementName) throws IOException{
        wb.selectElementTreeElement(elementName);
        int top = wb.getTopValue();
        int left = wb.getLeftValue();
        //move up
        res.checkTrue(wb.getTopValue() == top - 1, uniqueID++ + " - The element (" + elementName + ") did not successfully move up");
        //move down
        res.checkTrue(wb.getTopValue() == top, uniqueID++ + " - The element (" + elementName + ") did not successfully move down");
        //move left
        res.checkTrue(wb.getLeftValue() == left - 1, uniqueID++ + " - The element (" + elementName + ") did not successfully move left");
        //move right
        res.checkTrue(wb.getLeftValue() == left, uniqueID++ + " - The element (" + elementName + ") did not successfully move right");
    }
    
    @Parameters("errorElementName")
    @Test(groups = {"smoke", "check element name errors"}, threadPoolSize = 3)
    public void checkElementNameErrors(@Optional ("Button 1") String errorElementName) throws IOException{
        String maxLengthErrorText = "Name must be less than 64 characters long.";
        String numberUnderscoreErrorText = "Names can't start with a number or underscore.";
        String specialCharacterErrorText = "Special characters are not allowed.";
        
        wb.selectElementTreeElement("Button 1");
        wb.openRenameElementModal();
        
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
    
    @Parameters({"oldElementName", "newElementName"})
    @Test(groups = {"smoke", "rename element"}, threadPoolSize = 3)
    public void renameElement(@Optional ("Button 1") String oldElementName, @Optional ("New name") String newElementName) throws IOException{
        wb.renameElement(oldElementName, newElementName);
        res.checkTrue(wb.getElementName().equals(newElementName), uniqueID++ + " - Element name was not successfully changed (" + newElementName + ")");
        wb.renameElement(newElementName, oldElementName);
        res.checkTrue(wb.getElementName().equals(oldElementName), uniqueID++ + " - Element name was not successfully changed (" + oldElementName + ")");
    }
    
    @Parameters("deleteElement")
    @Test(groups = {"smoke", "delete element"}, threadPoolSize = 3)
    public void deleteElement(@Optional ("Button 2") String deleteElement) throws IOException{
        wb.deleteElement(deleteElement);
        String alertText = "Deleting an element will delete this element, any of its children, and disconnect this element from any request";
        boolean failed = false;
        String reason = "";
        try{
            res.checkTrue(wb.getAlertText().equals(alertText), uniqueID++ + " - Alert text doesn't match expected");
        }catch(AssertionError e){
            reason = e.toString();
            failed = true;
        }
        wb.confirmDeleteAPI();
        res.checkTrue(!wb.elementTreeElementExists(deleteElement), uniqueID++ + " - Element (" + deleteElement + ") was not successfully deleted");
        if(failed)fail(reason);
    }
    
    @DataProvider
    public Object[][] eventsList() throws FileNotFoundException, IOException{
        return new DataReader(DropsourceConstants.dataSheetLocation + "iOSEventList.txt").getData();
    }
    
    @Test(groups = {"smoke", "check element events"}, dataProvider = "eventsList", threadPoolSize = 3)
    public void checkElementEvents(String... events) throws IOException{
        wb.selectElementTreeElement(events[0]);
        ArrayList<String> eventNames = wb.getEventList();
        for(int i = 1; i < events.length; i++){
            res.checkTrue(eventNames.contains(events[i]), uniqueID++ + " - Expected event (" + events[i] + ") did not exist in the event list");
        }
        res.checkTrue(eventNames.size() == events.length - 1, uniqueID++ + " - Actual number of events (" + eventNames.size() + ") for (" + events[0] + ") doesn't match expected number of events (" + (events.length - 1) + ")");
    }
}
