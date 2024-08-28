package pagefactory;
import com.microsoft.playwright.ElementHandle;
import constants.Strings;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import utils.common.CommonFun;

public class ProductCatalogPage {
    private final Page page;
    private final String addButtonLocator = "//button[@aria-label='Add New']";
    private final String productNameFieldLocator = "//input[@id='productCatalogName']";
    private final String productDescriptionFieldLocator = "//input[@id='productCatalogDescription']";
    private final String productTransitionListLocator = "//div[@class='p-multiselect-label-container']/child::*";
    //div[@class='p-multiselect-label p-placeholder']
    private final String typeTransitionLocator = "//input[@role='textbox']";
    private final String clickTransitionLocator = "(//*[@class='p-checkbox-box'])[2]";
    private final String submitButtonLocator = "//span[normalize-space()='Submit']";

    private final String addNewTextLabelLocator = "//div[@class='Form__Header Full__Width']/h2";
    private final String nameLabelLocator = "(//label[@class='Label__Text'])[1]";
    private final String desLabelLocator ="(//label[@class='Label__Text'])[2]";
    private final String transactionLabelLocator = "//label[@for='transactionList']";

    private final String nameFieldErrorMessageLocator = "//*[@placeholder='Enter Name']/following-sibling::*[1]";
    private final String desFieldErrorMessageLocator = "//*[@placeholder='Enter Discription']/following-sibling::*[1]";
    private final String transactionListErrorMessageLocator = "//*[@for='transactionList']/following-sibling::*[2]";

    private final String successDialogMsgLocator = "//div[@class='DeleteLabel__Text']//label[@for='moduleDescr']";
    private final String dialogBoxOkButtonLocator = "//button[@aria-label='Okay' and @class='p-button p-component Btn__Dark__Ok']";

    public ProductCatalogPage(Page page) {
        this.page = page;
    }
    public void enterProductName(String productName){
        page.fill(productNameFieldLocator,productName);
    }
    public void enterProductDesc(String productDesc){
        page.fill(productDescriptionFieldLocator, productDesc);
    }
    public void selectFromListOfTransaction(String transactionName){
        page.click(productTransitionListLocator);
        page.fill(typeTransitionLocator, transactionName);
        page.click(clickTransitionLocator);
    }
    public void clickSubmitButton(){
        page.click(submitButtonLocator);
    }

    public void clickAddButton(){
        page.click(addButtonLocator);
    }

    public String getNameFieldErrorMessage(){
        page.locator(nameFieldErrorMessageLocator).isVisible();
        return page.querySelector(nameFieldErrorMessageLocator).innerText();
    }

    public String getDesFieldErrorMessage(){
        page.locator(desFieldErrorMessageLocator).isVisible();
        return page.querySelector(desFieldErrorMessageLocator).innerText();
    }

    public String getTranListErrorMessage(){
        page.locator(transactionListErrorMessageLocator).isVisible();
        return page.querySelector(transactionListErrorMessageLocator).innerText();
    }

    public void emptyAllInputFields(){

        page.fill(productNameFieldLocator,"");
        page.fill(productDescriptionFieldLocator,"");
       // page.locator(productTransitionListLocator.in).fill("");
        page.click("#transactionList");
        page.click("//div[@class='p-multiselect-header']/div[@role='checkbox']");
        page.click("//div[@class='p-multiselect-header']/div[@role='checkbox']");
        page.click("//button[@class='p-multiselect-close p-link']");


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
    public String getListOfTransactionLabelText(){
        page.waitForSelector(transactionLabelLocator);
        return page.querySelector(transactionLabelLocator).innerText();
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

    public String getCheckerID(){
        page.waitForSelector(successDialogMsgLocator);
        ElementHandle element = page.querySelector(successDialogMsgLocator);
        return CommonFun.getCheckerId(element.innerText());
    }
}
