package com.Automation.APIAutomation.helper;

import lombok.extern.slf4j.Slf4j;
import org.testng.*;

/**
 * @author psawale
 */
@Slf4j
public class TestListeners implements ITestListener, ISuiteListener, IClassListener {
    @Override
    public void onStart(ISuite suite) {
        //code to create csv file
    }
    @Override
    public void onFinish(ISuite suite) {
        ISuiteListener.super.onFinish(suite);
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Started execution {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Success execution {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("Failed execution {}", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
    }
}
