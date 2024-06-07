package pagefactory;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.common.CommonFun;

public class AddChannelPage {
    private final Page page;
    private final String addButtonLocator = "//button[@class='p-button p-component Btn__Add__' and @aria-label='Add New']";
    private final String channelNameFieldLocator = "#channelName";
    private final String channelNameFieldLocatorOnViewPage = "(//input[@class='p-inputtext p-component p-disabled Input__Round'])[1]";
    private final String channeldescFieldLocatorOnViewPage = "(//input[@class='p-inputtext p-component p-disabled Input__Round'])[2]";
    private final String channelNameFieldOnUpdatePageLocator ="#channelname";
    private final String channelDescriptionFieldLocator = "#channelDescr";
    private final String successDialogLocator = "#pr_id_2";
    private final String submitButtonLocator = "//button[@type='submit' and @class='p-button p-component Btn__Dark']";
    private final String cancelButtonLocator = "//button[@aria-label='Cancel'and @class='p-button p-component Btn__Transparent']";
    private final String successDialogMsgLocator = "//div[@class='DeleteLabel__Text']//label[@for='moduleDescr']";
    private final String dialogBoxOkButtonLocator = "//button[@aria-label='Okay' and @class='p-button p-component Btn__Dark__Ok']";
    private final String nameFieldErrorMessageLocator = "(//small[@class='p-error'])[1]";
    private final String desFieldErrorMessageLocator = "(//small[@class='p-error'])[2]";
    private final String alertSidePopupDialogLocator = "//div[@role='alert' and @class='Toastify__toast-body']";
    private final String addNewTextLabelLocator = "//div[@class='Form__Header Full__Width']/h2";
    private final String nameLabelLocator = "(//label[@class='Label__Text'])[1]";
    private final String desLabelLocator ="(//label[@class='Label__Text'])[2]";
    private final String searchButtonLocator = "//button[@aria-label='Search' and @class='p-button p-component Btn__Dark']";
    private final String resetButtonLocator = "//button[@aria-label='Reset' and @class='p-button p-component Btn__Transparent']";
    private final String dataTableViewButtonLocator = "//button[@class='p-button p-component p-button-rounded p-button-icon-only']";
    private final String dataTableEditButtonLocator = "//button[@class='p-button p-component p-button-rounded p-button-primary p-button-icon-only']";
    private final String dataTableActiveInactiveSwitchLocator = "//div[@class='p-inputswitch p-component p-inputswitch-checked']";
    private final String okButtonLocator = "//button[@class='p-button p-component Btn__Dark' and @aria-label='Okay']";
    private final String updateButtonLocator = "//button[@class='p-button p-component Btn__Dark' and @aria-label='Update']";

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
        page.waitForSelector(cancelButtonLocator);
        page.click(cancelButtonLocator);
    }
    public void clickAddButton(){
        page.click(addButtonLocator);
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
    public void checkNameFieldErrorMessageIsVisible(){
        page.locator(nameFieldErrorMessageLocator).isVisible();
    }
    //Wrong Locator
    public void checkDesFieldErrorMessageIsVisible(){
        page.locator(nameFieldErrorMessageLocator).isVisible();
    }
    public void emptyAllInputFields(){
        page.fill(channelNameFieldLocator,"");
        page.fill(channelDescriptionFieldLocator,"");
    }

    public void emptyDescriptionField(){
        page.fill(channelDescriptionFieldLocator,"");
    }

    public String getCheckerID(){
        page.waitForSelector(successDialogMsgLocator);
        ElementHandle element = page.querySelector(successDialogMsgLocator);
        return CommonFun.getCheckerId(element.innerText());
    }
    public String getAlertMessageText(){
        Locator element = page.locator(alertSidePopupDialogLocator);
        if(element.count()>0){
            return element.innerText();
        }
        return "not Found";
    }
    public String getAddNewText(){
        page.waitForSelector(addNewTextLabelLocator);
        return page.querySelector(addNewTextLabelLocator).innerText();
    }
    public String getNameLabelText(){
        page.waitForSelector(nameLabelLocator);
        return page.querySelector(nameLabelLocator).innerText();
    }
    public String getDescriptionLabelText(){
        page.waitForSelector(desLabelLocator);
        return page.querySelector(desLabelLocator).innerText();
    }
    public void clickOnSearchButton(){
        page.waitForSelector(searchButtonLocator);
        page.click(searchButtonLocator);
    }
    public void clickOnResetButton(){
        page.click(resetButtonLocator);
    }
    public void clickOnViewButton(){
        page.waitForSelector(dataTableViewButtonLocator);
        page.locator(dataTableViewButtonLocator).first().click();
    }
    public void clickOnEditButton(){
        page.waitForSelector(dataTableEditButtonLocator);
        page.locator(dataTableEditButtonLocator).first().click();
    }
    public void clickOnActiveInactiveButton(){
        page.waitForSelector(dataTableActiveInactiveSwitchLocator);
        page.locator(dataTableActiveInactiveSwitchLocator).first().click();
    }
    public String getChannelNameFieldText(){
        page.waitForSelector(channelNameFieldLocator);
        return page.querySelector(channelNameFieldLocator).innerText();
    }

    public String getChannelNameFieldTextOnViewPage(){
        page.waitForSelector(channelNameFieldLocatorOnViewPage);
        return page.querySelector(channelNameFieldLocatorOnViewPage).getAttribute("placeholder");
    }
    public String getChannelDescFieldTextOnViewPage(){
        page.waitForSelector(channeldescFieldLocatorOnViewPage);
        return page.querySelector(channeldescFieldLocatorOnViewPage).getAttribute("placeholder");
    }
    public String getChannelNameFieldTextFromUpdatePage(){
        page.waitForSelector(channelNameFieldOnUpdatePageLocator);
        return page.querySelector(channelNameFieldOnUpdatePageLocator).getAttribute("placeholder");
    }
    public String getChannelDescriptionFieldText(){
        page.waitForSelector(channelDescriptionFieldLocator);
        return page.querySelector(channelDescriptionFieldLocator).getAttribute("value");
    }
    public void clickOnOkButton(){
        page.waitForSelector(okButtonLocator);
        page.click(okButtonLocator);
    }
    public void clickOnUpdateButton(){
        page.waitForSelector(updateButtonLocator);
        page.click(updateButtonLocator);
    }


}
