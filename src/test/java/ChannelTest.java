
import constants.Strings;
import constants.ZboxUrls;
import dbfactory.ChannelScripts;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.common.ApiRequest;
import utils.common.CommonFun;
import utils.extentreports.ExtentTestManager;
import utils.listeners.AnnotationTransformer;
import utils.listeners.TestListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@Listeners({TestListener.class, AnnotationTransformer.class})
public class ChannelTest extends BaseClass {
//add login session, if error message is displayed except success retry
    String CheckerID;
    String channelName;
    String channelDescription;

    @Test(priority = 1, groups = "positive")
    public void testLoginPositive() throws InterruptedException {
        ExtentTestManager.startTest("testLoginPositive",
                "Verify user should be able to login using valid credentials");
        loginPage.login(userName, userPass);
        loginPage.enterOtp(staticOtp);
        navigation.navigateToChannel();
    }

    @Test(priority = 2, groups = "negative",enabled = false)
    public void verifyLabelsAreVisible(){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addChannelPage.getAddNewText(),Strings.addNewText);
        softAssert.assertEquals(addChannelPage.getNameLabelText(),Strings.nameText);
        softAssert.assertEquals(addChannelPage.getDescriptionLabelText(),Strings.descriptionText);
        softAssert.assertAll();
    }

    @Test(priority = 2, groups = "negative",enabled = false)
    public void testNegativeFieldValidation(){
        ExtentTestManager.startTest("testNegativeFieldValidation",
                "Verify that Field validation message is visible when submit button is pressed on empty fields");
        addChannelPage.clickAddButton();
        addChannelPage.clickSubmitButton();
        addChannelPage.checkNameFieldErrorMessageIsVisible();
        addChannelPage.checkDesFieldErrorMessageIsVisible();
        assertThat(page.getByText(Strings.thisFieldIsRequiredText).first()).isVisible();
        //assertThat(page.getByText(Strings.thisFieldIsRequiredText).nth(2)).isVisible();
    }

    @Test(priority = 3, groups = "negative",enabled = false)
    public void testFieldValidationWithOnlyChannelName(){
        ExtentTestManager.startTest("testFieldValidationWithOnlyChannelName",
                "Verify that channel description Field validation message is visible when submit button is pressed on empty fields");
        addChannelPage.emptyAllInputFields();
        addChannelPage.enterChannelName(CommonFun.getRandomName("AutomatedDescription"));
        addChannelPage.clickSubmitButton();
        addChannelPage.checkDesFieldErrorMessageIsVisible();
        assertThat(page.getByText(Strings.thisFieldIsRequiredText)).isVisible();
    }

    @Test(priority = 4, groups = "negative",enabled = false)
    public void testFieldValidationWithOnlyChannelDesc(){
        ExtentTestManager.startTest("testFieldValidationWithOnlyChannelName",
                "Verify that channel name Field validation message is visible when submit button is pressed on empty fields");
        addChannelPage.emptyAllInputFields();
        addChannelPage.emptyAllInputFields();
        addChannelPage.enterChannelDesc(CommonFun.getRandomName("AutomatedDescription"));
        addChannelPage.clickSubmitButton();
        addChannelPage.checkNameFieldErrorMessageIsVisible();
        assertThat(page.getByText(Strings.thisFieldIsRequiredText)).isVisible();
    }

    @Test(priority = 5, groups = "positive",enabled = false)
    public void testChannelCreation() throws SQLException {
        ExtentTestManager.startTest("testFieldValidationWithOnlyChannelName",
                "Verify that user should be able to create channel");
        addChannelPage.emptyAllInputFields();
        channelName = CommonFun.getRandomName("AutomatedChannel");
        channelDescription = CommonFun.getRandomName("AutomatedDescription");
        addChannelPage.enterChannelName(channelName);
        addChannelPage.enterChannelDesc(channelDescription);
        addChannelPage.clickSubmitButton();
        addChannelPage.verifyDialogBoxMessage();
        if (!addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            addChannelPage.clickDialogBoxOkButton();
            addChannelPage.clickSubmitButton();
            addChannelPage.verifyDialogBoxMessage();
            Assert.assertTrue(addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Channel was not created due to"+addChannelPage.getCheckerID());
        }
        CheckerID = addChannelPage.getCheckerID();
        Assert.assertEquals(ChannelScripts.verifyNewlyCreatedChannelInDB(channelName),1,"No Channel Record is found in Database against Name"+channelName);
        addChannelPage.clickDialogBoxOkButton();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
    }

    @Test(priority = 6, groups = "positive",enabled = false)
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

    @Test(priority = 6, groups = "positive",dependsOnMethods = "testChannelCreation",enabled = false)
    public void checkerApproval() throws SQLException {
        ExtentTestManager.startTest("testFieldValidationWithOnlyChannelName",
                "Verify that checker user can approve new created channel");
        ChannelCheckerTest checkerTest = new ChannelCheckerTest();
        checkerTest.splitCheckIdAndName(CheckerID);
        checkerTest.testLoginPositive();
        checkerTest.approveRecordFromChecker();

    }

    @Test(priority = 7, groups = "positive",dependsOnMethods = "checkerApproval",enabled = false)
    public void hybridApproach(){
        ExtentTestManager.startTest("testFieldValidationWithOnlyChannelName",
                "Verify that created channel is visible in API "+ZboxUrls.GET_ALL_CHANNELS);
        Response response = ApiRequest.getApiResponse(ZboxUrls.GET_ALL_CHANNELS);
        assert response != null;
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        List<Map<String, Object>> payloadList = jsonPath.getList("payLoad");
        String expectedAttributeName = channelName;
        boolean isAttributeFound = false;
        for (Map<String, Object> payload : payloadList) {
            String name = (String) payload.get("name");
            if (expectedAttributeName.equals(name)) {
                isAttributeFound = true;
                break;
            }
        }
        Assert.assertTrue(isAttributeFound, "The attribute name '" + expectedAttributeName + "' was not found in the payload.");
    }

}
