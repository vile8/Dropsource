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
    
    
    @Parameters("browser")
    @BeforeClass(groups = {"before"})
    public void setUp(@Optional("chrome") String browser) {
        super.setUp(browser);
        try {
            login();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @AfterClass(groups = {"after"})
    public void tearDown() {
        try {
            logout();
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            super.tearDown();
        }
    }
    
    @Parameters({"projectName", "pageName"})
    @Test(groups = {"create page"}, threadPoolSize = 3)
    public void addPage(@Optional ("Test Project")String projectName,  @Optional ("i1") String pageName) throws IOException{
        openProject(projectName);
        wb.addPage(pageName);
        res.checkTrue(wb.pageExists(pageName), uniqueID++ + " - Page (" + pageName + ") was not create successfully");
        
    }
    
    @Parameters( "pageName")
    @Test(groups = {"delete page"}, dependsOnGroups = "create page", threadPoolSize = 3)
    public void deletePage(@Optional ("i1") String pageName) throws IOException{
        wb.deletePage(pageName);
        res.checkTrue(!wb.pageExists(pageName), uniqueID++ + " - Page (" + pageName + ") was not create successfully");
        closeProject();
    }
    
}
