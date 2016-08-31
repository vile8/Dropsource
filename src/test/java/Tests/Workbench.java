package Tests;

import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
    
    @Parameters({"workbenchProject", "pageName"})
    @Test(groups = {"smoke", "create page"}, threadPoolSize = 3)
    public void addPage(@Optional ("Workbench Testing")String workbenchProject,  @Optional ("i1") String pageName) throws IOException{
        openProject(workbenchProject);
        wb.addPage(pageName);
        res.checkTrue(wb.pageExists(pageName), uniqueID++ + " - Page (" + pageName + ") was not created successfully");
        res.checkTrue(wb.checkIfSaved(10), uniqueID++ + " - Workbench did not save changes");
    }
    
    @Parameters( "newPageName")
    @Test(groups = {"smoke", "delete page"}, dependsOnGroups = "rename page", threadPoolSize = 3)
    public void deletePage(@Optional ("rename name") String newPageName) throws IOException{
        wb.deletePage(newPageName);
        res.checkTrue(!wb.pageExists(newPageName), uniqueID++ + " - Page (" + newPageName + ") was not deleted successfully");
        res.checkTrue(wb.checkIfSaved(10), uniqueID++ + " - Workbench did not save changes");
        closeProject();
    }
    
    @Parameters({"pageName", "newPageName"})
    @Test(groups = {"smoke", "rename page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void renamePage(@Optional ("i1") String pageName, @Optional ("rename name") String newPageName) throws IOException{
        wb.renamePage(pageName, newPageName);
        res.checkTrue(wb.pageExists(newPageName), uniqueID++ + " - Page (" + newPageName + ") was not renamed successfully");
        res.checkTrue(wb.checkIfSaved(10), uniqueID++ + " - Workbench did not save changes");
    }
    
    public void rearrangePages(){
        
    }
    
    public void addButtonToCanvas(){
        
    }
    
    public void deleteButton(){
        
    }
    
    public void checkElements(){
        
    }
    
    
    
}
