package pagefactory;

import com.microsoft.playwright.*;

public class CheckerPage {
    private final Page page;
    private final String actionIdInputFieldLocator = "#actionId";
    private final String searchButtonLocator = "(//button[@class='p-button p-component Btn__Dark'])[1]";
    private final String editButtonLocator = "//button[@class='p-button p-component p-button-rounded p-button-primary p-button-icon-only']";
    private final String dropDownFieldLocator = "#action";
    private final String selectApprovedFromDropDown = "li[role='option'][aria-label='Approved']";
    private final String selectRejectFromDropDown = "li[role='option'][aria-label='Rejected']";
    private final String selectAssignBackFromDropDown = "li[role='option'][aria-label='Assign Back']";
    private final String clickOnUpdateButton = "//button[@class='p-button p-component Btn__Dark' and @aria-label='Update']";
    private final String checkCommentTextAreaLocator = "#checkerComments";
    private final String checkerActionsDropDownLocator = "#action";
    private final String successDialogMsgLocator = "//div[@class='DeleteLabel__Text']//label[@for='moduleDescr']";
    private final String dialogBoxOkButtonLocator = "//button[@aria-label='Okay' and @class='p-button p-component Btn__Dark__Ok']";


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
        page.waitForSelector(selectApprovedFromDropDown);
        page.locator(selectApprovedFromDropDown).click();
    }
    public void selectRejectFromDropDown(){
        page.click(checkerActionsDropDownLocator);
        page.waitForSelector(selectRejectFromDropDown);
        page.locator(selectRejectFromDropDown).click();
    }
    public void enterCheckerComments(String comments){
        page.fill(checkCommentTextAreaLocator,comments);
    }
    public void clickUpdateButton(){
        page.click(clickOnUpdateButton);
    }
    public void selectAssignBackFromDropDown(){
        page.click(checkerActionsDropDownLocator);
        page.waitForSelector(selectAssignBackFromDropDown);
        page.locator(selectAssignBackFromDropDown).click();
    }

    public String verifyDialogBoxMessage(){
        page.waitForSelector(successDialogMsgLocator);
        ElementHandle element = page.querySelector(successDialogMsgLocator);
        return element.innerText();
    }
    public void clickDialogBoxOkButton(){
        page.waitForSelector(dialogBoxOkButtonLocator);
        page.click(dialogBoxOkButtonLocator);
    }

}
