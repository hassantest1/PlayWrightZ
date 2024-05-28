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
import utils.configrations.ConfigManager;
import utils.extentreports.ExtentTestManager;

import java.io.IOException;
import java.sql.SQLException;

public class CheckerTest {
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
    public CheckerTest(String checkerID) throws SQLException {
        String[] parts = checkerID.split(",");
        checkerId = parts[0];
        checkerName = parts[1];
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
    public void searchByCheckerId(){
        checkerPage.enterCheckID(checkerId);
        checkerPage.clickEditButton();
        checkerPage.selectApproveFromDropDown();
        checkerPage.enterCheckerComments("Approve");
        checkerPage.clickUpdateButton();
    }

}
