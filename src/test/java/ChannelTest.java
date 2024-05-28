
import constants.Strings;
import constants.ZboxUrls;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.common.ApiRequest;
import utils.common.CommonFun;
import utils.extentreports.ExtentTestManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ChannelTest extends LoginBase {

    String CheckerID;
    String channelName;

    @Test(priority = 2, groups = "positive")
    public void testLoginPositive() throws SQLException {
        ExtentTestManager.startTest("testLoginPositive",
                "Verify user should be able to login using valid credentials");
        loginPage.login(userName, userPass);
        loginPage.enterOtp(staticOtp);
        navigation.navigateToChannel();
    }

    @Test(priority = 3, groups = "positive")
    public void testNegativeFieldValidation(){
        addChannelPage.clickAddButton();
        addChannelPage.clickSubmitButton();
        addChannelPage.checkNameFieldErrorMessageIsVisible();
        addChannelPage.checkDesFieldErrorMessageIsVisible();
        assertThat(page.getByText(Strings.thisFieldIsRequiredText)).isVisible();
        assertThat(page.getByText(Strings.thisFieldIsRequiredText)).isVisible();
    }

    @Test(priority = 3, groups = "positive")
    public void testFieldValidationWithOnlyChannelName(){
        addChannelPage.clickAddButton();
        addChannelPage.enterChannelName(CommonFun.getRandomName("AutomatedDescription"));
        addChannelPage.clickSubmitButton();
        addChannelPage.checkDesFieldErrorMessageIsVisible();
        assertThat(page.getByText(Strings.thisFieldIsRequiredText)).isVisible();
    }

    @Test(priority = 3, groups = "positive")
    public void testFieldValidationWithOnlyChannelDesc(){
        addChannelPage.clickAddButton();
        addChannelPage.clickAddButton();
        addChannelPage.emptyAllInputFields();
        addChannelPage.enterChannelDesc(CommonFun.getRandomName("AutomatedDescription"));
        addChannelPage.clickSubmitButton();
        addChannelPage.checkNameFieldErrorMessageIsVisible();
        assertThat(page.getByText(Strings.thisFieldIsRequiredText)).isVisible();
    }

    @Test(priority = 3, groups = "positive")
    public void testChannelFields(){
        addChannelPage.clickAddButton();
        channelName = CommonFun.getRandomName("AutomatedChannel");
        addChannelPage.enterChannelName(channelName);
        addChannelPage.enterChannelDesc(CommonFun.getRandomName("AutomatedDescription"));
        addChannelPage.clickSubmitButton();
        addChannelPage.dialogBoxIsVisible();
        CheckerID = addChannelPage.getCheckerID();
    }

    @Test(priority = 4, groups = "positive")
    public void checkerApproval() throws SQLException {
        CheckerTest checkerTest = new CheckerTest(CheckerID);
        checkerTest.testLoginPositive();
        checkerTest.searchByCheckerId();
        addChannelPage.clickDialogBoxOkButton();
        page.pause();
    }

    @Test(priority = 5, groups = "positive")
    public void hybridApproach(){
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
