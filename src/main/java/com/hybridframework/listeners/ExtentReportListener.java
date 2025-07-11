package com.hybridframework.listeners;

import com.hybridframework.utils.ExtentReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * ExtentReport listener for handling test reporting events
 */
public class ExtentReportListener implements ITestListener, ISuiteListener {
    
    private static final Logger logger = LogManager.getLogger(ExtentReportListener.class);
    
    @Override
    public void onStart(ISuite suite) {
        logger.info("Test suite started: " + suite.getName());
        ExtentReportManager.initializeExtentReports();
    }
    
    @Override
    public void onFinish(ISuite suite) {
        logger.info("Test suite finished: " + suite.getName());
        ExtentReportManager.flushReports();
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        String[] groups = result.getMethod().getGroups();
        
        if (groups.length > 0) {
            String category = String.join(", ", groups);
            ExtentReportManager.createTest(testName, testDescription, category);
        } else {
            ExtentReportManager.createTest(testName, testDescription);
        }
        
        logger.info("ExtentReport test created: " + testName);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.logPass("Test passed successfully");
        logger.info("ExtentReport: Test passed - " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.logFail("Test failed: " + result.getThrowable().getMessage());
        logger.error("ExtentReport: Test failed - " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.logSkip("Test skipped: " + result.getThrowable().getMessage());
        logger.warn("ExtentReport: Test skipped - " + result.getMethod().getMethodName());
    }
}