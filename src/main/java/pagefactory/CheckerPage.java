package pagefactory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class CheckerPage {
    private final Page page;
    private static String actionIdInputFieldLocator = "#actionId";
    private static String searchButtonLocator = "(//button[@class='p-button p-component Btn__Dark'])[1]";
    private static String editButtonLocator = "//button[@class='p-button p-component p-button-rounded p-button-primary p-button-icon-only']";
    private static String dropDownFieldLocator = "#action";
    private static String selectApprovedFromDropDown = "li[role='option'][aria-label='Approved']";
    private static String clickOnUpdateButton = "//button[@class='p-button p-component Btn__Dark' and @aria-label='Update']";
    private static String checkCommentTextAreaLocator = "#checkerComments";

    public CheckerPage(Page page) {
        this.page = page;
    }
    public void enterCheckID(String checkId){
        page.fill(actionIdInputFieldLocator,checkId);
        clickSearchButton();
    }
    public void clickSearchButton(){
        page.click(searchButtonLocator);
    }
    public void clickEditButton(){page.click(editButtonLocator);}
    public void selectApproveFromDropDown(){
        page.click("#action");
        page.locator(selectApprovedFromDropDown).click();
    }
    public void enterCheckerComments(String comments){
        page.fill(checkCommentTextAreaLocator,comments);
    }
    public void clickUpdateButton(){
        page.click(clickOnUpdateButton);
    }
}
