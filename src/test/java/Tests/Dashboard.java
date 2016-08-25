package Tests;

import java.io.IOException;
import static org.testng.Assert.fail;
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
    }

    @AfterClass(groups = {"after"})
    public void tearDown() {
        super.tearDown();
    }

    @Parameters("projectName")
    @Test(groups = {"smoke", "create project"}, threadPoolSize = 3)
    public void createProject(@Optional("Test Project") String projectName) throws IOException {
        login();

        db.createBlankProject(projectName);
        res.checkTrue(db.projectExists(projectName), uniqueID++ + " - Project just created (" + projectName + ") not found in list");
        
        try{
            res.checkTrue(db.getBannerText().equals("New Project Has Been Created"), uniqueID++ + " - Banner text does not match expected text");
        }catch(AssertionError e){
            fail(e.toString());
        }finally{
            logout();
        }
    }

    @Parameters("projectName")
    @Test(groups = {"smoke", "delete project"}, dependsOnGroups = "create project", threadPoolSize = 3)
    public void deleteProject(@Optional("Test Project") String projectName) throws IOException {
        login();

        db.deleteProject(projectName);
        String expectedConfirmText = "Deleting this project will permanently remove it which includes the iOS and Android apps, unlink any used plugins, and remove it from your project list.";
        res.checkTrue(db.getConfrimDeleteText().equals(expectedConfirmText), uniqueID++ + " - Confirm delete text doesn't match expected text");
        
        db.confirmDelete();
        res.checkTrue(!db.projectExists(projectName), uniqueID++ + " - Project just deleted (" + projectName + ") is still found in list");
        
        try{
        res.checkTrue(db.getBannerText().equals("A project has been deleted"), uniqueID++ + " - Banner text does not match expected text");
        }catch(AssertionError e){
            fail(e.toString());
        }finally{
            logout();
        }
    }

}
