package com.hybridframework.base;

import com.hybridframework.constants.FrameworkConstants;
import com.hybridframework.drivers.DriverManager;
import com.hybridframework.utils.ConfigReader;
import com.hybridframework.utils.ExtentReportManager;
import com.hybridframework.utils.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Base test class providing common setup and teardown functionality
 */
public class BaseTest {
    
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    
    @BeforeSuite
    public void beforeSuite() {
        logger.info("Starting test suite execution");
        ExtentReportManager.initializeExtentReports();
        logger.info("Test suite setup completed");
    }
    
    @BeforeClass
    public void beforeClass() {
        logger.info("Starting test class: " + this.getClass().getSimpleName());
    }
    
    @BeforeMethod
    public void beforeMethod(Method method) {
        logger.info("Starting test method: " + method.getName());
        
        // Initialize WebDriver
        String browserName = ConfigReader.getBrowser();
        DriverManager.initializeDriver(browserName);
        
        // Create test in ExtentReports
        String testName = method.getName();
        String testDescription = getTestDescription(method);
        String testCategory = getTestCategory(method);
        
        if (Objects.nonNull(testCategory)) {
            ExtentReportManager.createTest(testName, testDescription, testCategory);
        } else {
            ExtentReportManager.createTest(testName, testDescription);
        }
        
        // Navigate to application URL
        String applicationUrl = ConfigReader.getApplicationUrl();
        WebDriverUtils.navigateToUrl(applicationUrl);
        
        ExtentReportManager.logInfo("Browser launched: " + browserName);
        ExtentReportManager.logInfo("Navigated to URL: " + applicationUrl);
        
        logger.info("Test method setup completed: " + method.getName());
    }
    
    @AfterMethod
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.info("Finishing test method: " + testName);
        
        // Handle test result
        handleTestResult(result);
        
        // Quit WebDriver
        DriverManager.quitDriver();
        
        // Remove test from ThreadLocal
        ExtentReportManager.removeTest();
        
        logger.info("Test method teardown completed: " + testName);
    }
    
    @AfterClass
    public void afterClass() {
        logger.info("Finishing test class: " + this.getClass().getSimpleName());
    }
    
    @AfterSuite
    public void afterSuite() {
        logger.info("Finishing test suite execution");
        ExtentReportManager.flushReports();
        logger.info("Test suite teardown completed");
    }
    
    /**
     * Handle test result based on test status
     * @param result Test result
     */
    private void handleTestResult(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                logger.info("Test PASSED: " + testName);
                ExtentReportManager.logPass("Test passed successfully");
                
                // Take screenshot on pass if configured
                if (ConfigReader.isScreenshotOnPass()) {
                    String screenshotPath = WebDriverUtils.takeScreenshot(testName);
                    if (Objects.nonNull(screenshotPath)) {
                        ExtentReportManager.addScreenshotOnPass(screenshotPath);
                    }
                }
                break;
                
            case ITestResult.FAILURE:
                logger.error("Test FAILED: " + testName);
                logger.error("Failure reason: " + result.getThrowable().getMessage());
                
                ExtentReportManager.logFail("Test failed: " + result.getThrowable().getMessage());
                
                // Take screenshot on failure if configured
                if (ConfigReader.isScreenshotOnFail()) {
                    String screenshotPath = WebDriverUtils.takeScreenshot(testName);
                    if (Objects.nonNull(screenshotPath)) {
                        ExtentReportManager.addScreenshotOnFailure(screenshotPath);
                    }
                }
                break;
                
            case ITestResult.SKIP:
                logger.warn("Test SKIPPED: " + testName);
                logger.warn("Skip reason: " + result.getThrowable().getMessage());
                
                ExtentReportManager.logSkip("Test skipped: " + result.getThrowable().getMessage());
                break;
                
            default:
                logger.warn("Test result unknown: " + testName);
                break;
        }
    }
    
    /**
     * Get test description from method annotation
     * @param method Test method
     * @return Test description
     */
    private String getTestDescription(Method method) {
        Test testAnnotation = method.getAnnotation(Test.class);
        if (Objects.nonNull(testAnnotation) && !testAnnotation.description().isEmpty()) {
            return testAnnotation.description();
        }
        return "Test method: " + method.getName();
    }
    
    /**
     * Get test category from method annotation
     * @param method Test method
     * @return Test category
     */
    private String getTestCategory(Method method) {
        Test testAnnotation = method.getAnnotation(Test.class);
        if (Objects.nonNull(testAnnotation) && testAnnotation.groups().length > 0) {
            return String.join(", ", testAnnotation.groups());
        }
        return null;
    }
    
    /**
     * Get current test name
     * @return Current test name
     */
    protected String getCurrentTestName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
    
    /**
     * Log info message to both logger and ExtentReports
     * @param message Info message
     */
    protected void logInfo(String message) {
        logger.info(message);
        ExtentReportManager.logInfo(message);
    }
    
    /**
     * Log pass message to both logger and ExtentReports
     * @param message Pass message
     */
    protected void logPass(String message) {
        logger.info(message);
        ExtentReportManager.logPass(message);
    }
    
    /**
     * Log fail message to both logger and ExtentReports
     * @param message Fail message
     */
    protected void logFail(String message) {
        logger.error(message);
        ExtentReportManager.logFail(message);
    }
    
    /**
     * Log warning message to both logger and ExtentReports
     * @param message Warning message
     */
    protected void logWarning(String message) {
        logger.warn(message);
        ExtentReportManager.logWarning(message);
    }
    
    /**
     * Take screenshot and add to report
     * @param screenshotName Screenshot name
     */
    protected void takeScreenshot(String screenshotName) {
        String screenshotPath = WebDriverUtils.takeScreenshot(screenshotName);
        if (Objects.nonNull(screenshotPath)) {
            ExtentReportManager.addScreenshot(screenshotPath, screenshotName);
        }
    }
}