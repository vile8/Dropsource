package Tests;

import Pages.WorkbenchPage;
import Utility.DropsourceConstants;
import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/*
 * @author Jonathan Doll
 */
public class Dashboard extends TestSetup {

    @BeforeClass(groups = {"before"})
    @Parameters("browser")
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

    @Parameters("projectName")
    @Test(groups = {"smoke", "create project"}, threadPoolSize = 3)
    public void createProject(@Optional("Test Project") String projectName) throws IOException {
        db.btnCreateNewProjectClick();
        res.checkTrue(!db.projectLimitReachedExists(), uniqueID++ + " - Project limit error appeared");
        db.createBlankProject(projectName);
        res.checkTrue(db.projectExists(projectName), uniqueID++ + " - Project just created (" + projectName + ") not found in list");
        res.checkTrue(db.getBannerText().equals("New Project Has Been Created"), uniqueID++ + " - Banner text does not match expected text");
    }

    @Parameters("projectName")
    @Test(groups = {"smoke end", "delete project"}, dependsOnGroups = "open project", threadPoolSize = 3)
    public void deleteProject(@Optional("Test Project") String projectName) throws IOException {
        db.deleteProject(projectName);
        String expectedConfirmText = "Deleting this project will permanently remove it which includes the iOS and Android apps, unlink any used plugins, and remove it from your project list.";
        res.checkTrue(db.getConfrimDeleteText().equals(expectedConfirmText), uniqueID++ + " - Confirm delete text doesn't match expected text");

        db.confirmDelete();
        res.checkTrue(!db.projectExists(projectName), uniqueID++ + " - Project just deleted (" + projectName + ") is still found in list");
        res.checkTrue(db.getBannerText().equals("A project has been deleted"), uniqueID++ + " - Banner text does not match expected text");
    }

    @Parameters("projectName")
    @Test(groups = {"smoke", "open project"}, dependsOnGroups = "create project", threadPoolSize = 3)
    public void openProject(@Optional("Test Project") String projectName) throws IOException {
        db.openProject(projectName);
        ArrayList<String> workbench = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(workbench.get(1));

        wb.waitForLoader();
        wb.sync();
        res.checkTrue(wb.elementExists(), uniqueID++ + " - Open project failed");

        driver.close();
        driver.switchTo().window(workbench.get(0));
    }

    @Test(groups = {"max projects"}, dependsOnGroups = "delete project", threadPoolSize = 3)
    //@Test(groups = {"max projects"}, threadPoolSize = 3)
    public void createMaxProjects() throws IOException {
        for (int i = 0; i < DropsourceConstants.projectLimit; i++) {
            String projectName = "Test " + i;
            db.btnCreateNewProjectClick();
            res.checkTrue(!db.projectLimitReachedExists(), uniqueID++ + " - Project limit reached after only " + (i + 1) + " projects have been created");
            db.createBlankProject(projectName);
            res.checkTrue(db.projectExists(projectName), uniqueID++ + " - Project just created (" + projectName + ") not found in list");
            res.checkTrue(db.getBannerText().equals("New Project Has Been Created"), uniqueID++ + " - Banner text does not match expected text");
        }

    }

    @Test(groups = {"delete all projects"}, dependsOnGroups = "max projects", threadPoolSize = 3)
    //@Test(groups = {"delete all projects"}, threadPoolSize = 3)
    public void deleteAllProjects() throws IOException {
        for (int i = DropsourceConstants.projectLimit - 1; i > -1; i--) {
            String projectName = "Test " + i;
            db.deleteProject(projectName);
            db.confirmDelete();
            res.checkTrue(!db.projectExists(projectName), uniqueID++ + " - Project just deleted (" + projectName + ") is still found in list");
            res.checkTrue(db.getBannerText().equals("A project has been deleted"), uniqueID++ + " - Banner text does not match expected text");
        }
    }

}
