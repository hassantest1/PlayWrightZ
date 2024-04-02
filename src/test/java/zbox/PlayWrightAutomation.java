package zbox;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import constants.ZboxUrls;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.testng.Assert.assertEquals;


public class PlayWrightAutomation extends PlayWrightBase {

    @Test
    public void AccountProfileChange() {

        // page.navigate("https://10.248.101.81:445/home");
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setIgnoreHTTPSErrors(true));
        //page = context.newPage();
        page.navigate(ZboxUrls.ZBOX_BASE_URL_QA);
        page.locator("input#email").fill("KASHIF");
        page.locator("input[name='password']").fill("1234");
        page.pause();
//        page.locator("input#username").fill("sed");
//        page.locator("input#password").fill("ib@12345");
//        page.click("button#login");
//        page.click(".close.icon.icon-cross");
//        page.waitForSelector("h1");
//        assertEquals("Home", page.title());
//        page.click("input#search-field");
//        page.locator("input#search-field").fill("3477032148");
//        page.click("form  .icon.icon-search");
//        page.click("//*[@id=\"app\"]/div/main/div/div/div[1]/div[2]/ul/li[1]/ul/li[1]/a");
//        page.click("div:nth-of-type(12) > .root > .buttons.root  .edit.icon.icon-edit");
//        page.locator("//*[@id=\"general-profile\"]").fill("BVS");
//        page.locator("div:nth-of-type(12) > .root  .e-select > .dropdown-menu > datalist > option:nth-of-type(54)").click();
//        page.click("//*[@id=\"app\"]/div/main/div/div/div[2]/div[2]/div/div[2]/div[1]/div/ul/li[1]/div[2]/div/div[12]/div/div[2]/span[2]");
//        page.locator("//*[@id=\"app\"]/div/div/div/div/form/div[2]/textarea").fill("Automation Engineering");
//        page.click("//*[@id=\"app\"]/div/div/div/div/form/div[3]/button[2]");
//
//        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("login.png")));

    }
}
