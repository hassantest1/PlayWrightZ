import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.Test;
import pagefactory.CheckerPage;
import utils.extentreports.ExtentTestManager;

public class ProductCheckerTest extends CheckerTest {


    public ProductCheckerTest(){
        super();
    }

    @Override
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

    @Override
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

    @Override
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
