package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Utility.DataReader;
import Utility.DropsourceConstants;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author jonathandoll
 */
public class Login extends TestSetup{
    
    @BeforeClass(groups = {"before"})
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        super.setUp(browser);
    }
    
    @AfterClass(groups = {"after"})
    public void tearDown(){
      super.tearDown();
    }
    
    @Test(groups = {"login", "smoke"}, threadPoolSize = 3)
    public void ClickLoginTest() throws IOException{
        LoginPage login = new LoginPage(driver);
        login.loginClick(DropsourceConstants.loginEmail, DropsourceConstants.loginPassword);
        
        DashboardPage db = new DashboardPage(driver);
        db.waitForLoader();
        db.sync();
        
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt (By Click)");
        
        db.logout();
        login.sync();
        
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt");
    }
    
    @Test(groups = {"login", "smoke"}, threadPoolSize = 3)
    public void EnterLoginTest() throws IOException{
        LoginPage login = new LoginPage(driver);
        login.loginEnter(DropsourceConstants.loginEmail, DropsourceConstants.loginPassword);
        
        DashboardPage db = new DashboardPage(driver);
        db.waitForLoader();
        db.sync();
        
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt (By Click)");
        
        db.logout();
        login.sync();
        
        res.checkTrue(login.elementExists(), uniqueID++ + " - Logout Attempt");
    }
    
    @DataProvider
    public Object[][] loginData() throws FileNotFoundException, IOException{
        DataReader data = new DataReader(DropsourceConstants.dataSheetLocation + "LoginErrors.txt");
        return data.getData();
    }
    
    @Test(groups = "loginErrors", dataProvider = "loginData", threadPoolSize = 3)
    public void errorCheck(String email, String password, String error) throws IOException{
        LoginPage login = new LoginPage(driver);
        login.loginClick(email, password);
        login.waitForError();
        login.errorVanishCheck();
        if(login.emailErrorExists()){
            res.checkTrue(login.emailErrorText().equals(error), uniqueID++ + " - Email error doesn't match");
        }else if(login.passwordErrorExists()){
            res.checkTrue(login.passwordErrorText().equals(error), uniqueID++ + " - Password error doesn't match");
        }else{
            res.checkTrue(false, uniqueID++ + " - No error message displayed");
        }
    }
    
}