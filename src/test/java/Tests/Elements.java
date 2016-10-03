package Tests;

import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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
    
}
