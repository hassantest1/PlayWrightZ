package utils.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.extentreports.ExtentManager;
import utils.logs.Log;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static utils.extentreports.ExtentTestManager.getTest;

public class TestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
    @Override
    public void onStart(ITestContext iTestContext) {
        Log.info("I am in onStart method " + iTestContext.getName());
        //iTestContext.setAttribute("WebDriver", this.driver);
    }
    @Override
    public void onFinish(ITestContext iTestContext) {
        Log.info("I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for ExtentReports reporting!
        ExtentManager.extentReports.flush();
        openReportInBrowser();
    }
    @Override
    public void onTestStart(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is starting.");
        System.out.println(getTestMethodName(iTestResult) + " test is starting.");
    }
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is succeed.");
        //ExtentReports log operation for passed tests.
        System.out.println(getTestMethodName(iTestResult) + " test is succeed.");
        getTest().log(Status.PASS, "Test passed");
    }
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        // Get the Playwright browser instance from BaseTest and assign to a local variable.
//        Object testClass = iTestResult.getInstance();
//        Browser browser = ((BaseTest) testClass).getBrowser();
//
//        // Take screenshot
//        byte[] screenshot = page.screenshot();
//
//        // Convert screenshot to base64
//        String base64Screenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(screenshot);
//
//        // Log and add screenshot to extent report
//        getTest().log(Status.FAIL, "Test Failed",
//                getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
        Log.info(getTestMethodName(iTestResult) + " test is Failed.");
        System.out.println(getTestMethodName(iTestResult) + " test is Failed.");
        getTest().log(Status.FAIL, iTestResult.getThrowable());
       // Throwable throwable = iTestResult.getThrowable();
//        if (iTestResult.getThrowable()!=null){
////            String exceptionMessage = throwable.getMessage();
////            getTest().log(Status.FAIL, exceptionMessage);
//            // Add complete error log
//            String stackTrace = getStackTrace(throwable);
//            getTest().log(Status.FAIL,stackTrace);
//        }

    }
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        System.out.println(getTestMethodName(iTestResult) + " test is skipped.");
        getTest().log(Status.SKIP, "Test Skipped");
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            stackTrace.append(element.toString()).append("\n");
        }
        return stackTrace.toString();
    }

    private void openReportInBrowser() {
        String filePath = "extent-reports/extent-report.html";
        File file = new File(filePath);
        if (file.exists()) {
            try {
                Desktop.getDesktop().browse(file.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
