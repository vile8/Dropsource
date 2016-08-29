package Tests;

import Utility.Wait;
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
    @Test
    public void AddDeletePage(@Optional ("Test Project")String projectName,  @Optional ("i1") String pageName) throws IOException{
        Wait wait = new Wait();
        openProject(projectName);
        wb.addPage(pageName);
        
        wait.waitSecs(5);
        wb.deletePage(pageName);
        closeProject();
    }
    
}
