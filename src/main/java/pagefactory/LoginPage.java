package pagefactory;

import com.microsoft.playwright.*;

public class LoginPage {
    private final Page page;
    private final String emailFieldSelector = "#email";
    private final String passwordFieldSelector = "input[name='password']";
    private final String loginButtonSelector = "//button[@type='submit']";
    private final String loginHeadingSelector = "p.Signin__Heading";
    private final String otpLocatorBox1 = "//div[@class='verifyOTP']/div/div/input[1]";
    private final String otpLocatorBox2 = "//div[@class='verifyOTP']/div/div/input[2]";
    private final String otpLocatorBox3 = "//div[@class='verifyOTP']/div/div/input[3]";
    private final String otpLocatorBox4 = "//div[@class='verifyOTP']/div/div/input[4]";
    private final String otpSubmitButtonLocator = "//button[@type='submit' and @aria-label='Submit']";
    private final String logOutButton = "//div[@class='details_section']//i[@class='pi pi-power-off']";
    private final String forgetPassLabelLocator= "//div[@class='ForgotPassword__Alignment']//label[@class='forgot_label']";
    private final String rememberMeLabelLocator= "//label[@for='binary' and @class='Remember_label']";
    private final String otpScreenTileLocator= "//p[normalize-space()='Verify OTP' and @class='Signin__Heading mb-0']";
    private final String pleaseCheckEmailTitleLocator= "//p[normalize-space()='Please Check Your Email' and @class='Signin__Heading mb-0']";
    private final String didNotGetOtpLabelLocator= "//p[@class='text-white mb-0']";
    private final String clickToResendLinkLocator= "//u[normalize-space()='Click to resend']";
    private final String forgetPassEmailFieldLocator = "input[name='email']";
    private final String forgetPasswordPageSubmitButtonLocator = "//button[@aria-label='Submit' and @type='submit']";
    private final String forgetPasswordPageCancelButtonLocator = "//button[@aria-label='Cancel']";


    public LoginPage(Page page) {
        this.page = page;
    }

    public String getLoginPageTitle(){
        ElementHandle element = page.querySelector(loginHeadingSelector);
        String text = element.innerText();
        return text;
    }
    public void enterEmail(String email){
        page.waitForSelector(forgetPassEmailFieldLocator);
        page.fill(forgetPassEmailFieldLocator,email);
    }

    public String getEmailFieldPlaceHolderOnForgetPassPage(){
        return page.querySelector(forgetPassEmailFieldLocator).getAttribute("placeholder");
    }

    public void clickOnForgetPasswordPageSubmitButton(){
        page.waitForSelector(forgetPasswordPageSubmitButtonLocator);
        page.click(forgetPasswordPageSubmitButtonLocator);
    }
    public void clickOnForgetPasswordPageCancelButton(){
        page.waitForSelector(forgetPasswordPageCancelButtonLocator);
        page.click(forgetPasswordPageCancelButtonLocator);
    }

    public String getEmailFieldPlaceHolder(){
        return page.querySelector(emailFieldSelector).getAttribute("placeholder");
    }
    public String getPassFieldPlaceHolder(){
        return page.querySelector(passwordFieldSelector).getAttribute("placeholder");
    }

    public String getRememberMeLabelText(){
        return page.querySelector(rememberMeLabelLocator).innerText();
    }

    public String getForgetPassLabelText(){
        return page.querySelector(forgetPassLabelLocator).innerText();
    }

    public void clickOnForgetPassword(){
        page.click(forgetPassLabelLocator);
    }

    public String getOtpScreenTitle(){
        page.waitForSelector(otpScreenTileLocator);
        return page.querySelector(otpScreenTileLocator).innerText();
    }
    public String getPleaseCheckEmailTitle(){
        page.waitForSelector(pleaseCheckEmailTitleLocator);
        return page.querySelector(pleaseCheckEmailTitleLocator).innerText();
    }
    public String getDidNotGetOtpLabelText(){
        page.waitForSelector(didNotGetOtpLabelLocator);
        return page.querySelector(didNotGetOtpLabelLocator).innerText();
    }
    public String getClickToResendLinkText(){
        page.waitForSelector(clickToResendLinkLocator);
        return page.querySelector(clickToResendLinkLocator).innerText();
    }
    public void navigateToLoginPage(String url) {
        page.navigate(url);
    }

    public void enterUsername(String username) {
        page.fill(emailFieldSelector, username);
    }

    public void enterPassword(String password) {
        page.fill(passwordFieldSelector, password);
    }

    public void clickLoginButton() {
        page.waitForSelector(loginButtonSelector);
        page.click(loginButtonSelector);
    }

    public void clickOtpSubmitBtn(){
        page.click(otpSubmitButtonLocator);
    }

    public void enterOtp(String otp){
        String string1 = String.valueOf(otp.charAt(0));
        String string2 = String.valueOf(otp.charAt(1));
        String string3 = String.valueOf(otp.charAt(2));
        String string4 = String.valueOf(otp.charAt(3));

        page.fill(otpLocatorBox1,string1);
        page.fill(otpLocatorBox2,string2);
        page.fill(otpLocatorBox3,string3);
        page.fill(otpLocatorBox4,string4);
        clickOtpSubmitBtn();
    }
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    public void clearEmailField(){
        page.fill(emailFieldSelector,"");
    }

    public void clearPassField(){
        page.fill(passwordFieldSelector,"");
    }

    public void clearInputFields(){
        clearEmailField();
        clearPassField();
    }
    public void logOut(){
        page.click(logOutButton);
    }
}


