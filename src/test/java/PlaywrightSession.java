import com.microsoft.playwright.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class PlaywrightSession {
    private static PlaywrightSession instance;
    private static final String SESSION_STORAGE_KEY = "SESSION_STORAGE";
    private static final String SESSION_STORAGE_FILE = "sessionStorage.txt";
    private static final String STORAGE_STATE = "storageState.json";


    private BrowserContext context;
    private Page page;

    private PlaywrightSession() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        if (Paths.get(STORAGE_STATE).toFile().exists()) {
            context = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get(STORAGE_STATE)));
        } else {
            context = browser.newContext();
        }
        page = context.newPage();
    }

    public static PlaywrightSession getInstance() {
        if (instance == null) {
            instance = new PlaywrightSession();
        }
        return instance;
    }

    public Page getPage() {
        return page;
    }

    public void storeSessionStorage() {
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(STORAGE_STATE)));
    }


}
