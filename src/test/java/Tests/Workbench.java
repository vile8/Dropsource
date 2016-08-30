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
        res.checkTrue(wb.pageExists(pageName), uniqueID++ + " - Page (" + pageName + ") was not create successfully");
    }
    
    @Parameters( "pageName")
    @Test(groups = {"smoke", "delete page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void deletePage(@Optional ("i1") String pageName) throws IOException{
        wb.deletePage(pageName);
        res.checkTrue(!wb.pageExists(pageName), uniqueID++ + " - Page (" + pageName + ") was not create successfully");
        closeProject();
    }
    
}
