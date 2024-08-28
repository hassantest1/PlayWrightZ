import constants.Strings;
import dbfactory.ProductScripts;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.common.CommonFun;
import utils.extentreports.ExtentTestManager;
import utils.listeners.AnnotationTransformer;
import utils.listeners.TestListener;

import java.sql.SQLException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@Listeners({TestListener.class, AnnotationTransformer.class})
public class ProductCatalogTest extends BaseClass{

    //add login session, if error message is displayed except success retry
    String CheckerID;
    String productName;
    String productDescription;
    String productTransaction;

    @Test(priority = 1, groups = "positive")
    public void testLoginPositive() throws InterruptedException {
        ExtentTestManager.startTest("testLoginPositive",
                "Verify user should be able to login using valid credentials");
        loginPage.login(userName, userPass);
        loginPage.enterOtp(staticOtp);
        navigation.navigateToProduct();
        productCatalogPage.clickAddButton();

    }
    @Test(priority = 2, groups = "negative")
    public void testLabelsAreVisible(){

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(productCatalogPage.getNameLabelText(),Strings.nameText);
        softAssert.assertEquals(productCatalogPage.getDescriptionLabelText(),Strings.descriptionText);
        softAssert.assertEquals(productCatalogPage.getListOfTransactionLabelText(),Strings.transactionText);
        softAssert.assertAll();

    }

    @Test(priority = 2, groups = "negative")
    public void testEmptyFieldValidation(){
        SoftAssert softAssert = new SoftAssert();
        ExtentTestManager.startTest("testNegativeFieldValidation",
                "Verify that Field validation message is visible when submit button is pressed on empty fields");
        productCatalogPage.clickSubmitButton();
        page.waitForTimeout(5000);
        softAssert.assertEquals(productCatalogPage.getNameFieldErrorMessage(), Strings.thisFieldIsRequiredText);
        softAssert.assertEquals(productCatalogPage.getDesFieldErrorMessage(), Strings.thisFieldIsRequiredText);
        softAssert.assertEquals(productCatalogPage.getTranListErrorMessage(), Strings.transactionListErrorMsg);
        softAssert.assertAll();

    }

    @Test(priority = 2, groups = "negative")
    public void testFieldValidationWithOnlyProductName(){
        ExtentTestManager.startTest("testFieldValidationWithOnlyProductName",
                "Verify that Product description and Transaction Validation message, when only Product name is entered ");
        SoftAssert softAssert = new SoftAssert();
        productCatalogPage.emptyAllInputFields();
        productCatalogPage.enterProductName(CommonFun.getRandomName("AutomatedDescription"));
        productCatalogPage.clickSubmitButton();
        softAssert.assertEquals(productCatalogPage.getDesFieldErrorMessage(), Strings.thisFieldIsRequiredText);
        softAssert.assertEquals(productCatalogPage.getTranListErrorMessage(), Strings.transactionListErrorMsg);
        softAssert.assertAll();
    }

    @Test(priority = 2, groups = "negative")
    public void testFieldValidationWithOnlyProductDesc(){
        ExtentTestManager.startTest("testFieldValidationWithOnlyProductDescription",
                "Verify that Product Name and Transaction Validation message, when only Product Description is entered ");
        SoftAssert softAssert = new SoftAssert();
        productCatalogPage.emptyAllInputFields();
        productCatalogPage.enterProductDesc(CommonFun.getRandomName("AutomatedDescription"));
        productCatalogPage.clickSubmitButton();
        softAssert.assertEquals(productCatalogPage.getNameFieldErrorMessage(), Strings.thisFieldIsRequiredText);
        softAssert.assertEquals(productCatalogPage.getTranListErrorMessage(), Strings.transactionListErrorMsg);
        softAssert.assertAll();

    }

    @Test(priority = 2, groups = "negative")
    public void testFieldValidationWithOnlyProductTrans(){
        ExtentTestManager.startTest("testFieldValidationWithOnlyProductTransaction",
                "Verify that Product Name and Description Validation message, when only Product Transaction is entered ");
        SoftAssert softAssert = new SoftAssert();
        productCatalogPage.emptyAllInputFields();
        productCatalogPage.selectFromListOfTransaction(Strings.transactionName);
        productCatalogPage.clickSubmitButton();
        softAssert.assertEquals(productCatalogPage.getNameFieldErrorMessage(), Strings.thisFieldIsRequiredText);
        softAssert.assertEquals(productCatalogPage.getDesFieldErrorMessage(), Strings.thisFieldIsRequiredText);
        softAssert.assertAll();

    }

    @Test(priority = 5, groups = "positive")
    public void testProductCreation() throws SQLException {
        ExtentTestManager.startTest("testProductCreation",
                "Verify that user should be able to create Product");
        productCatalogPage.emptyAllInputFields();
        productName = CommonFun.getRandomName("AutomatedProduct");
        productDescription = CommonFun.getRandomName("AutomatedDescription");
        productTransaction = Strings.transactionName;
        productCatalogPage.enterProductName(productName);
        productCatalogPage.enterProductDesc(productDescription);
        productCatalogPage.selectFromListOfTransaction(productTransaction);
        productCatalogPage.clickSubmitButton();
        productCatalogPage.verifyDialogBoxMessage();
        if (!productCatalogPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            productCatalogPage.clickDialogBoxOkButton();
            productCatalogPage.clickSubmitButton();
            productCatalogPage.verifyDialogBoxMessage();
            Assert.assertTrue(productCatalogPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Product was not created due to"+productCatalogPage.getCheckerID());
        }
        CheckerID = productCatalogPage.getCheckerID();
        Assert.assertEquals(ProductScripts.verifyNewlyCreatedProductInDB(productName),1,"No Product Record is found in Database against Name"+productName);
        productCatalogPage.clickDialogBoxOkButton();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Product");
    }


    @Test(priority = 6, groups = "positive",dependsOnMethods = "testProductCreation")
    public void checkerApproval() throws SQLException {
        ExtentTestManager.startTest("testCheckProductCreationWithID",
                "Verify that checker user can approve new created Product");
        ProductCheckerTest productCheckerTest = new ProductCheckerTest();
        productCheckerTest.splitCheckIdAndName(CheckerID);
        productCheckerTest.testCheckerLoginPositive();
        productCheckerTest.approveRecordFromChecker();
        //productCatalogPage.clickDialogBoxOkButton();
    }

/*
    @Test(priority = 6, groups = "positive")
    public void searchCreatedChannelAndNavigateToViewPageAndVerifyValues(){
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        addChannelPage.enterChannelName(channelName);
        addChannelPage.clickOnSearchButton();
        addChannelPage.clickOnViewButton();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addChannelPage.getAddNewText(),Strings.viewDetailsText);
        softAssert.assertEquals(addChannelPage.getNameLabelText(),Strings.nameText);
        softAssert.assertEquals(addChannelPage.getDescriptionLabelText(),Strings.descriptionText);
        softAssert.assertEquals(addChannelPage.getChannelNameFieldTextOnViewPage(),channelName);
        softAssert.assertEquals(addChannelPage.getChannelDescFieldTextOnViewPage(),channelDescription);
        softAssert.assertAll();
        addChannelPage.clickOnOkButton();
        page.pause();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
    }

*/





}
