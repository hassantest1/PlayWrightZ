import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import constants.ZboxUrls;
import dbfactory.LoginPageScripts;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import pagefactory.LoginPage;
import utils.common.CommonFun;
import utils.configrations.ConfigManager;
import utils.database.DBQueryExecutor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginBase extends LoginPageScripts {

    protected Browser browser;
    protected Page page;
    protected LoginPage loginPage;
    protected String userName;
    protected String userPass;
    protected String staticOtp;
    protected Playwright playwright;

    protected SoftAssert softAssert;


    @BeforeClass
    public void setUp() throws IOException {

        ConfigManager getKey = new ConfigManager();
        userName = getKey.getKeyValue("user_name");
        userPass = getKey.getKeyValue("user_pass");
        staticOtp = getKey.getKeyValue("otp");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.setDefaultTimeout(90000);
        loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage(ZboxUrls.ZBOX_BASE_URL_QA);

    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

}
