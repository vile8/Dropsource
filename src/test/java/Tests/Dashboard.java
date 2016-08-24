package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Utility.DropsourceConstants;
import java.io.IOException;
import java.util.Random;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
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
       driver.close();
    }
    
    public void login() throws IOException{
        login.loginClick(DropsourceConstants.loginEmail, DropsourceConstants.loginPassword);
        db.waitForLoader();
        db.sync();
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt Failed (By Click)");
    }
    
    public void logout() throws IOException{
        db.logout();
        login.sync();
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt Failed");
    }
    
    @Test(groups = {"smoke"}, threadPoolSize = 3)
    public void createProject(@Optional("test project") String projectName) throws IOException{
        login();
        
        db.createBlankProject(projectName);
        res.checkTrue(db.projectExists(projectName), uniqueID++ + " - Project just created (" + projectName + ") not found in list");
        res.checkTrue(db.getBannerText().equals("New Project Has Been Created"), uniqueID++ + " - Banner text does not match expected text");
        
        logout();
    }
    
    @Test(groups = {"smoke"}, threadPoolSize = 3)
    public void deleteProject(@Optional("test project") String projectName) throws IOException{
        login();
        
        
        
        logout();
    }
    
}
