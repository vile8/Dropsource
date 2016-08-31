package Tests;

import Utility.DataReader;
import Utility.DropsourceConstants;
import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/*
 * @author Jonathan Doll
 */

public class Workbench extends TestSetup{
    
    
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
    
    @Parameters("pageName")
    @Test(groups = {"smoke", "create page"}, threadPoolSize = 3)
    public void addPage(@Optional ("i1") String pageName) throws IOException{
        wb.addPage(pageName);
        res.checkTrue(wb.pageExists(pageName), uniqueID++ + " - Page (" + pageName + ") was not created successfully");
    }
    
    @Parameters( "deletePageName")
    @Test(groups = {"smoke", "delete page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void deletePage(@Optional ("delete name") String deletePageName) throws IOException{
        wb.addPage(deletePageName);
        res.checkTrue(wb.pageExists(deletePageName), uniqueID++ + " - Page (" + deletePageName + ") was not created successfully");
        wb.deletePage(deletePageName);
        res.checkTrue(!wb.pageExists(deletePageName), uniqueID++ + " - Page (" + deletePageName + ") was not deleted successfully");
    }
    
    @Parameters({"pageName3", "newPageName"})
    @Test(groups = {"smoke", "rename page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void renamePage(@Optional ("i3") String pageName3, @Optional ("rename name") String newPageName) throws IOException{
        wb.addPage(pageName3);
        res.checkTrue(wb.pageExists(pageName3), uniqueID++ + " - Page (" + pageName3 + ") was not created successfully");
        wb.renamePage(pageName3, newPageName);
        res.checkTrue(wb.pageExists(newPageName), uniqueID++ + " - Page (" + newPageName + ") was not renamed successfully");
    }
    
    @Parameters({"pageName", "pageName2"})
    @Test(groups = {"smoke", "rearrange page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void rearrangePages(@Optional ("i1") String pageName, @Optional ("i2") String pageName2) throws IOException{
        wb.addPage(pageName2);
        res.checkTrue(wb.pageExists(pageName2), uniqueID++ + " - Page (" + pageName2 + ") was not created successfully");
        wb.rearrangePage(pageName, pageName2);
        res.checkTrue(wb.getFirstPageName().equals(pageName2), uniqueID++ + " - Pages weren't rearranged successfully. " + pageName2 + " should be first.");
    }
    
    @DataProvider
    public Object[][] iOSElements() throws FileNotFoundException, IOException{
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "iOSElements.txt");
        return data.getData();
    }
    
    //Checks that expected elements are there, not that unexpected elements aren't there
    @Test(groups ={"smoke", "check elements"}, dataProvider = "iOSElements", threadPoolSize = 3)
    public void checkIOSElements(String elementName) throws IOException{
        res.checkTrue(wb.checkElementExists(elementName), uniqueID++ + " - Element (" + elementName + ") isn't in the element list");
    }
       
}
