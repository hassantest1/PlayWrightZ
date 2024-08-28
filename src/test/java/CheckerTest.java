import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import constants.ZboxUrls;
import lombok.Getter;
import org.testng.Assert;
import org.testng.annotations.Test;
import pagefactory.CheckerPage;
import pagefactory.LoginPage;
import pagefactory.NavigationPage;
import utils.configrations.ConfigManager;
import utils.extentreports.ExtentTestManager;

import java.io.IOException;
import java.sql.SQLException;

public class CheckerTest {
    protected Browser browser;
    protected Playwright playwright;
    protected Page page;
    protected CheckerPage checkerPage;
    protected String checkerId;
    protected String userPass;
    protected String staticOtp;
    protected String checkerName;
    protected LoginPage loginPage;
    protected NavigationPage navigation;
    public CheckerTest()  {
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
    public void testCheckerLoginPositive() throws SQLException {
        ExtentTestManager.startTest("testCheckerLoginPositive",
                "Verify user should be able to login using valid credentials");
        loginPage.login(checkerName, userPass);
        loginPage.enterOtp(staticOtp);
        navigation.navigateToChecker();
    }

    public void approveRecordFromChecker(){};
    public void rejectRecordFromChecker(){};
    public void assignBackRecordFromChecker(){};

}
