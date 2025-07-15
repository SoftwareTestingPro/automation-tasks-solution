package com.qa.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.base.BaseTest;
import com.qa.reports.ExtentManager;
import com.qa.utils.ScreenshotUtil;

public class TestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    public void onTestFailure(ITestResult result) {
    	WebDriver driver = ((BaseTest) result.getInstance()).getDriver();
        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());

        test.get().fail(result.getThrowable(),
            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }

    public void onTestSuccess(ITestResult result) {
        test.get().pass("✅ Test passed");
    }

    @AfterSuite
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
