package pagefactory;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import utils.common.CommonFun;

public class AddChannelPage {
    private final Page page;
    private final String addButtonLocator = "//button[@class='p-button p-component Btn__Add__' and @aria-label='Add New']";
    private final String channelNameFieldLocator = "#channelName";
    private final String channelDescriptionFieldLocator = "#channelDescr";
    private final String successDialogLocator = "#pr_id_2";
    private final String submitButtonLocator = "//button[@type='submit' and @class='p-button p-component Btn__Dark']";
    private final String cancelButtonLocator = "//button[@aria-label='Cancel'and @class='p-button p-component Btn__Transparent']";
    private final String successDialogMsgLocator = "//div[@class='DeleteLabel__Text']//label[@for='moduleDescr']";
    private final String dialogBoxOkButtonLocator = "//button[@aria-label='Okay' and @class='p-button p-component Btn__Dark__Ok']";

    public AddChannelPage(Page page) {
        this.page = page;
    }
    public void enterChannelName(String channelName){
        page.fill(channelNameFieldLocator,channelName);
    }
    public void enterChannelDesc(String channelDesc){
        page.fill(channelDescriptionFieldLocator,channelDesc);
    }
    public void clickSubmitButton(){
        page.click(submitButtonLocator);
    }
    public void clickCancelButton(){
        page.click(cancelButtonLocator);
    }
    public void clickAddButton(){
        page.click(addButtonLocator);
    }

    public void dialogBoxIsVisible(){
        page.isVisible(successDialogLocator);
    }

    public void clickDialogBoxOkButton(){
        page.click(dialogBoxOkButtonLocator);
    }

    public String getCheckerID(){
        page.waitForSelector(successDialogMsgLocator);
        ElementHandle element = page.querySelector(successDialogMsgLocator);
        return CommonFun.getCheckerId(element.innerText());
    }
}
