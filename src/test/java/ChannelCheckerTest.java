import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import constants.ZboxUrls;
import org.testng.Assert;
import org.testng.annotations.Test;
import pagefactory.CheckerPage;
import pagefactory.LoginPage;
import pagefactory.NavigationPage;
import utils.configrations.ConfigManager;
import utils.extentreports.ExtentTestManager;

import java.io.IOException;
import java.sql.SQLException;

public class ChannelCheckerTest {
    private Browser browser;
    private Playwright playwright;
    private Page page;
    private CheckerPage checkerPage;
    private String checkerId = "8189";
    private String userPass;
    private String staticOtp;
    private String checkerName;
    private LoginPage loginPage;
    private NavigationPage navigation;
    public ChannelCheckerTest()  {
        try {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            page = browser.newPage();
            page.setDefaultTimeout(140000);
            loginPage = new LoginPage(page);
            navigation = new NavigationPage(page);
            checkerPage = new CheckerPage(page);
            loginPage.navigateToLoginPage(ZboxUrls.ZBOX_BASE_URL_QA);
            ConfigManager getKey = null;
            try {
                getKey = new ConfigManager();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            userPass = getKey.getKeyValue("user_pass");
            staticOtp = getKey.getKeyValue("otp");
        }catch (Exception e){
            System.out.println("EXCEPTION "+e);
        }

    }
    public void splitCheckIdAndName(String CheckerID){
        String[] parts = CheckerID.split(",");
        checkerId = parts[0];
        checkerName = parts[1];
    }
    @Test(priority = 2, groups = "positive")
    public void testLoginPositive() throws SQLException {
        ExtentTestManager.startTest("testLoginPositive",
                "Verify user should be able to login using valid credentials");
        loginPage.login(checkerName, userPass);
        loginPage.enterOtp(staticOtp);
        navigation.navigateToChecker();
    }

    @Test(priority = 3, groups = "positive")
    public void approveRecordFromChecker(){
        checkerPage.enterCheckID(checkerId);
        checkerPage.clickEditButton();
        checkerPage.selectApproveFromDropDown();
        checkerPage.enterCheckerComments("Approve");
        checkerPage.clickUpdateButton();
        checkerPage.verifyDialogBoxMessage();
        if (!checkerPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            checkerPage.clickDialogBoxOkButton();
            checkerPage.clickUpdateButton();
            checkerPage.verifyDialogBoxMessage();
            Assert.assertTrue(checkerPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Channel was not created due to"+ checkerId );
        }
        checkerPage.clickDialogBoxOkButton();
    }

    @Test(priority = 3, groups = "positive")
    public void rejectRecordFromChecker(){
        checkerPage.enterCheckID(checkerId);
        checkerPage.clickEditButton();
        checkerPage.selectRejectFromDropDown();
        checkerPage.enterCheckerComments("Rejected");
        checkerPage.clickUpdateButton();
        if (!checkerPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            checkerPage.clickDialogBoxOkButton();
            checkerPage.clickUpdateButton();
            checkerPage.verifyDialogBoxMessage();
            Assert.assertTrue(checkerPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Channel was not created due to"+ checkerId );
        }
        checkerPage.clickDialogBoxOkButton();
    }

    @Test(priority = 3, groups = "positive")
    public void assignBackRecordFromChecker(){
        checkerPage.enterCheckID(checkerId);
        checkerPage.clickEditButton();
        checkerPage.selectAssignBackFromDropDown();
        checkerPage.enterCheckerComments("Assign Back");
        checkerPage.clickUpdateButton();
        if (!checkerPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            checkerPage.clickDialogBoxOkButton();
            checkerPage.clickUpdateButton();
            checkerPage.verifyDialogBoxMessage();
            Assert.assertTrue(checkerPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Channel was not created due to"+ checkerId );
        }
        checkerPage.clickDialogBoxOkButton();
    }

}
