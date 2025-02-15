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

public class ChannelCheckerTest extends CheckerTest {
    private Browser browser;
    private Playwright playwright;
    private Page page;
    private CheckerPage checkerPage;



    @Test(priority = 1, groups = "positive")
    public void approveRecordFromChecker(){
        ExtentTestManager.startTest("approveRecordFromChecker",
                "Verify checker approves the Transaction");
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
        ExtentTestManager.startTest("testRejectRecord",
                "Verify Checker rejects the Transaction");
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
