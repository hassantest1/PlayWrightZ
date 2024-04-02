package zbox;

import com.microsoft.playwright.*;
import org.testng.annotations.BeforeTest;

public class PlayWrightBase {
    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

    @BeforeTest
    public void Setup() {

        playwright = Playwright.create();
        browser = playwright.firefox().
                launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
        context = browser.newContext();
        page = context.newPage();
        //page.setDefaultTimeout(60000);
    }
}
