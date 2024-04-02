package utils.listeners;

import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Base64;

import static utils.extentreports.ExtentTestManager.getTest;

public class Retry implements IRetryAnalyzer {
    private        int count  = 0;
    private static int maxTry = 0; //Run the failed test 2 times
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {                     //Check if test not succeed
            if (count < maxTry) {                           //Check if maxTry count is reached
                count++;                                    //Increase the maxTry count by 1
                iTestResult.setStatus(ITestResult.FAILURE); //Mark test as failed and take base64Screenshot
                extendReportsFailOperations(iTestResult);   //ExtentReports fail operations
                return true;                                //Tells TestNG to re-run the test
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);     //If test passes, TestNG marks it as passed
        }
        return false;
    }
    public void extendReportsFailOperations(ITestResult iTestResult) {
        getTest().log(Status.FAIL, iTestResult.getThrowable());

//        Object testClass = iTestResult.getInstance();
//        Browser browser = ((BaseTest) testClass).getBrowser();
//        byte[] screenshot = ((Playwright) browser).screenshot();
//        String base64Screenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(screenshot);
//        getTest().log(Status.FAIL, "Test Failed",
//                getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
    }


}
