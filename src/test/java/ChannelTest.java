import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import constants.Strings;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pagefactory.LoginPage;
import utils.common.CommonFun;
import utils.extentreports.ExtentTestManager;

import java.sql.SQLException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static dbfactory.LoginPageScripts.returnLoginHistory;

public class ChannelTest extends LoginBase {

    String CheckerID;
    @Test(priority = 2, groups = "positive")
    public void testLoginPositive() throws SQLException {
        ExtentTestManager.startTest("testLoginPositive",
                "Verify user should be able to login using valid credentials");
        loginPage.login(userName, userPass);
        loginPage.enterOtp(staticOtp);
        navigation.navigateToChannel();
    }
    @Test(priority = 3, groups = "positive")
    public void testChannelFields(){
        addChannelPage.clickAddButton();
        addChannelPage.enterChannelName(CommonFun.getRandomName("AutomatedChannel"));
        addChannelPage.enterChannelDesc(CommonFun.getRandomName("AutomatedDescription"));
        addChannelPage.clickSubmitButton();
        addChannelPage.dialogBoxIsVisible();
        CheckerID = addChannelPage.getCheckerID();
    }

    @Test(priority = 4, groups = "positive")
    public void checkerApproval() throws SQLException {
        CheckerTest checkerTest = new CheckerTest(CheckerID);
        checkerTest.testLoginPositive();
        checkerTest.searchByCheckerId();
        addChannelPage.clickDialogBoxOkButton();
    }

}
