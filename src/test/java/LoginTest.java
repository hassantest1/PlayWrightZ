import com.microsoft.playwright.options.AriaRole;
import constants.Strings;
import org.testng.Assert;
import org.testng.annotations.*;
import java.sql.SQLException;
import org.testng.asserts.SoftAssert;
import utils.extentreports.ExtentTestManager;
import utils.listeners.AnnotationTransformer;
import utils.listeners.TestListener;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners({TestListener.class, AnnotationTransformer.class})
public class LoginTest extends LoginBase {

    @Test(priority = 1, groups = "negative")
    public void testNegativeLogin() {
        ExtentTestManager.startTest("testNegativeLogin",
                "Verify Error messages are showing with empty fields");
        loginPage.clearInputFields();
        loginPage.clickLoginButton();
        assertThat(page.getByText(Strings.loginPageErrorMessageEmailRequired)).isVisible();
        assertThat(page.getByText(Strings.loginPageErrorMessagePassRequired)).isVisible();
    }

    @Test(priority = 1, groups = "negative")
    public void testNegativeLoginEnterOnlyUserName() {
        ExtentTestManager.startTest("testNegativeLoginEnterOnlyUserName",
                "Verify Error messages are showing with password field empty");
        loginPage.clearInputFields();
        loginPage.enterUsername(userName);
        loginPage.clickLoginButton();
        assertThat(page.getByText(Strings.loginPageErrorMessagePassRequired)).isVisible();
    }

    @Test(priority = 1, groups = "negative")
    public void testNegativeLoginEnterOnlyPass() {
        ExtentTestManager.startTest("testNegativeLoginEnterOnlyPass",
                "Verify Error messages are showing with password field empty");
        loginPage.clearInputFields();
        loginPage.enterPassword("1234");
        loginPage.clickLoginButton();
        assertThat(page.getByText(Strings.loginPageErrorMessageEmailRequired)).isVisible();
    }

    @Test(priority = 2, groups = "negative")
    public void testNegativeLoginEnterIncorrectUserAndPass() {
        ExtentTestManager.startTest("testNegativeLoginEnterIncorrectUserAndPass",
                "Verify is user is able to login using invalid credentials");
        loginPage.login("HASSAN", "12345");
        assertThat(page.getByRole(AriaRole.ALERT)).isVisible();
        assertThat(page.getByRole(AriaRole.ALERT)).containsText(Strings.loginPageAlertErrorInvalidUserNameOrPass);
    }

    @Test(priority = 2, groups = "negative")
    public void testNegativeForgetPass(){
        ExtentTestManager.startTest("testNegativeForgetPass",
                "Verify Error message is showing when no email is provided");
        loginPage.clickOnForgetPassword();
        loginPage.clickOnForgetPasswordPageSubmitButton();
        softAssert = new SoftAssert();
        softAssert.assertTrue(page.getByText(Strings.loginPageErrorMessageEmailRequired).isVisible(),"");
        softAssert.assertAll();
        loginPage.clickOnForgetPasswordPageCancelButton();
    }

    @Test(priority = 3, groups = "positive")
    public void testLoginPositive() throws SQLException {
        ExtentTestManager.startTest("testLoginPositive",
                "Verify user should be able to login using valid credentials");
        Assert.assertEquals(loginPage.getLoginPageTitle(),Strings.loginPageTitle);
        Assert.assertEquals(loginPage.getEmailFieldPlaceHolder(),Strings.EmailFieldPlaceHolderText);
        Assert.assertEquals(loginPage.getPassFieldPlaceHolder(),Strings.passFieldPlaceHolderText);
        Assert.assertEquals(loginPage.getRememberMeLabelText(),Strings.rememberMeLabelText);
        Assert.assertEquals(loginPage.getForgetPassLabelText(),Strings.forgetPassLabelText);
        loginPage.login(userName, userPass);
        Assert.assertEquals(loginPage.getClickToResendLinkText(),Strings.clickToResendText);
        Assert.assertEquals(loginPage.getOtpScreenTitle(),Strings.verifyOtpText);
        Assert.assertEquals(loginPage.getPleaseCheckEmailTitle(),Strings.pleaseCheckEmailText);
        Assert.assertEquals(loginPage.getDidNotGetOtpLabelText(),Strings.didNotGetOtpText);
        loginPage.enterOtp(staticOtp);
        assertThat(page.getByLabel("Breadcrumb").locator("span")).containsText("Dashboard");
        String userID = (String) page.evaluate(Strings.getUserIDFromSession);
        int flag = returnLoginHistory(userID);
        loginPage.logOut();
        Assert.assertEquals(flag,1,"No user login Record is found in Database against UserID="+userID);
    }
}
