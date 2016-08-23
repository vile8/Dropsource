/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import Utility.DropsourceConstants;
import Utility.Wait;
import java.io.IOException;
import java.util.Random;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author John
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
    
    @Test
    public void createProject() throws IOException{
        LoginPage login  = new LoginPage(driver);
        login.loginClick(DropsourceConstants.loginEmail, DropsourceConstants.loginPassword);
        
        DashboardPage db = new DashboardPage(driver);
        db.waitForLoader();
        db.sync();
        
        res.checkTrue(db.elementExists(), uniqueID++ + " - Login Attempt (By Click)");
        
        Random r = new Random();
        String projectName = r.nextInt(1000000) + ""; 
        db.createBlankProject(projectName);
        //db.waitForBanner();
        //System.out.println(db.successBannerAppears());
        res.checkTrue(db.projectExists(projectName), uniqueID++ + " - Project just created (" + projectName + ") not found in list");
        //res.checkTrue(db.successBannerAppears(), uniqueID++ + " - Project create banner didn't appear");
    }
}
