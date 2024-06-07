import com.microsoft.playwright.*;
import constants.ZboxUrls;
import dbfactory.LoginPageScripts;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import pagefactory.AddChannelPage;
import pagefactory.CheckerPage;
import pagefactory.LoginPage;
import pagefactory.NavigationPage;
import utils.configrations.ConfigManager;

import java.io.IOException;

public class BaseClass extends LoginPageScripts {

    protected BrowserContext browser;
    protected Page page;
    protected LoginPage loginPage;
    protected AddChannelPage addChannelPage;
    protected NavigationPage navigation;
    protected CheckerPage checkerPage;
    protected String userName;
    protected String userPass;
    protected String staticOtp;
    protected Playwright playwright;
    protected SoftAssert softAssert;

    @BeforeClass
    public void setUp() throws IOException {

        //playwrightSession = PlaywrightSession.getInstance();
        ConfigManager getKey = new ConfigManager();
        userName = getKey.getKeyValue("user_name");
        userPass = getKey.getKeyValue("user_pass");
        staticOtp = getKey.getKeyValue("otp");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext();
        page = browser.newPage();
       // page = playwrightSession.getPage();
        page.setDefaultTimeout(120000);
        loginPage = new LoginPage(page);
        addChannelPage = new AddChannelPage(page);
        checkerPage = new CheckerPage(page);
        navigation = new NavigationPage(page);
        page.navigate(ZboxUrls.ZBOX_BASE_URL_QA);

    }

    @AfterClass
    public void tearDown() {
        //playwrightSession.storeSessionStorage();
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

}
