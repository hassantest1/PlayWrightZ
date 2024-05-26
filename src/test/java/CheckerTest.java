import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import constants.ZboxUrls;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagefactory.CheckerPage;
import pagefactory.LoginPage;
import pagefactory.NavigationPage;
import utils.extentreports.ExtentTestManager;

import java.sql.SQLException;

public class CheckerTest {
    private Browser browser;
    private Playwright playwright;
    private Page page;
    private CheckerPage checkerPage;
    private String checkerId = "8189";
    private LoginPage loginPage;
    private NavigationPage navigation;
    public CheckerTest(String checkerID) throws SQLException {
        checkerId = checkerID;
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.setDefaultTimeout(140000);
        loginPage = new LoginPage(page);
        navigation = new NavigationPage(page);
        checkerPage = new CheckerPage(page);
        loginPage.navigateToLoginPage(ZboxUrls.ZBOX_BASE_URL_QA);
    }
    @Test(priority = 2, groups = "positive")
    public void testLoginPositive() throws SQLException {
        ExtentTestManager.startTest("testLoginPositive",
                "Verify user should be able to login using valid credentials");
        loginPage.login("USAMA", "1234");
        loginPage.enterOtp("1234");
        navigation.navigateToChecker();
    }

    @Test(priority = 3, groups = "positive")
    public void searchByCheckerId(){
        checkerPage.enterCheckID(checkerId);
        checkerPage.clickEditButton();
        checkerPage.selectApproveFromDropDown();
        checkerPage.enterCheckerComments("Approve");
        checkerPage.clickUpdateButton();
    }

}
