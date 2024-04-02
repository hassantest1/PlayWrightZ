package utils.listeners;

import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.extentreports.ExtentManager;

import java.util.Objects;

import static utils.extentreports.ExtentTestManager.getTest;

public class ExtentReportListener implements ITestListener {

        private static String getTestMethodName(ITestResult iTestResult) {
            return iTestResult.getMethod().getConstructorOrMethod().getName();
        }
        @Override
        public void onStart(ITestContext iTestContext) {
            System.out.println("Test Suite Started");
        }
        @Override
        public void onFinish(ITestContext iTestContext) {
            System.out.println("Test Suite Finished");
            ExtentManager.extentReports.flush();
        }
        @Override
        public void onTestStart(ITestResult iTestResult) {
            System.out.println(getTestMethodName(iTestResult) + " test is starting.");

        }
        @Override
        public void onTestSuccess(ITestResult iTestResult) {
            System.out.println(getTestMethodName(iTestResult) + " test is succeed.");
            //ExtentReports log operation for passed tests.
            getTest().log(Status.PASS, "Test passed");
        }
        @Override
        public void onTestFailure(ITestResult iTestResult) {
//            Log.info(getTestMethodName(iTestResult) + " test is failed.");
//            //Get driver from BaseTest and assign to local webdriver variable.
//            Object testClass = iTestResult.getInstance();
//            WebDriver driver = ((BaseTest) testClass).getDriver();
//            //Take base64Screenshot screenshot for extent reports
//            String base64Screenshot =
//                    "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
//            //ExtentReports log and screenshot operations for failed tests.
//            getTest().log(Status.FAIL, "Test Failed",
//                    getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
        }
        @Override
        public void onTestSkipped(ITestResult iTestResult) {
            //Log.info(getTestMethodName(iTestResult) + " test is skipped.");
            //ExtentReports log operation for skipped tests.
            getTest().log(Status.SKIP, "Test Skipped");
        }
        @Override
        public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
            //Log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
        }

}
