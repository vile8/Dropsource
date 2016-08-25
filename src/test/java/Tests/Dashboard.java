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

public class Dashboard extends TestSetup{
    
    @BeforeClass(groups = {"before"})
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        super.setUp(browser);
    }
    
    @AfterClass(groups = {"after"})
    public void tearDown(){
       super.tearDown();
    }
    
    @Parameters("projectName")
    @Test(groups = {"smoke"}, threadPoolSize = 3)
    public void createProject(@Optional("Test Project") String projectName) throws IOException{
        login();
        
        db.createBlankProject(projectName);
        res.checkTrue(db.projectExists(projectName), uniqueID++ + " - Project just created (" + projectName + ") not found in list");
        res.checkTrue(db.getBannerText().equals("New Project Has Been Created"), uniqueID++ + " - Banner text does not match expected text");
        
        logout();
        
    }
    
    @Parameters("projectName")
    //@Test(groups = {"smoke"}, threadPoolSize = 3)
    public void deleteProject(@Optional("Test Project") String projectName) throws IOException{
        login();
        
        db.deleteProject(projectName);
        db.confirmDelete();
        
        logout();
    }
    
}
