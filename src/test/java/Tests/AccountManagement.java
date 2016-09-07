package Tests;

import Utility.DropsourceConstants;
import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/*
 * @author Jonathan Doll
 */

public class AccountManagement extends TestSetup{
    
    @Parameters("browser")
    @BeforeClass(groups = {"before"})    
    public void setUp(@Optional ("chrome") String browser){
        super.setUp(browser);
        try {
            login();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    @AfterClass(groups = "after")
    public void tearDown(){
         try {
            logout();
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            super.tearDown();
        }
    }
    
    @Test
    public void thing(){
        am.addProfilePhoto(DropsourceConstants.dataSheetLocation + "profilepicture.jpg");
    }
    
}
