package com.hybridframework.tests;

import com.hybridframework.utils.ConfigReader;
import com.hybridframework.utils.ExtentReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Framework validation test class - tests framework components without browser
 */
public class FrameworkTest {
    
    private static final Logger logger = LogManager.getLogger(FrameworkTest.class);
    
    @BeforeSuite
    public void beforeSuite() {
        logger.info("Starting Framework Validation Suite");
        ExtentReportManager.initializeExtentReports();
    }
    
    @BeforeMethod
    public void beforeMethod() {
        logger.info("Starting framework component test");
    }
    
    @Test(priority = 1, description = "Validate ConfigReader functionality", groups = {"framework", "smoke"})
    public void validateConfigReader() {
        logger.info("Test: Validate ConfigReader functionality");
        
        // Test configuration reading
        String browser = ConfigReader.getBrowser();
        Assert.assertNotNull(browser, "Browser configuration should not be null");
        Assert.assertFalse(browser.isEmpty(), "Browser configuration should not be empty");
        
        boolean isHeadless = ConfigReader.isHeadless();
        logger.info("Headless mode: " + isHeadless);
        
        int implicitWait = ConfigReader.getImplicitWait();
        Assert.assertTrue(implicitWait > 0, "Implicit wait should be greater than 0");
        
        String appUrl = ConfigReader.getApplicationUrl();
        Assert.assertNotNull(appUrl, "Application URL should not be null");
        
        logger.info("ConfigReader validation passed successfully");
    }
    
    @Test(priority = 2, description = "Validate ExtentReport Manager", groups = {"framework", "smoke"})
    public void validateExtentReportManager() {
        logger.info("Test: Validate ExtentReport Manager");
        
        // Test ExtentReport functionality
        ExtentReportManager.createTest("Framework Test", "Testing ExtentReport functionality");
        ExtentReportManager.logInfo("This is an info message");
        ExtentReportManager.logPass("This is a pass message");
        ExtentReportManager.logWarning("This is a warning message");
        
        logger.info("ExtentReport Manager validation passed successfully");
    }
    
    @Test(priority = 3, description = "Validate Logger functionality", groups = {"framework", "smoke"})
    public void validateLogger() {
        logger.info("Test: Validate Logger functionality");
        
        // Test different log levels - just verify no exceptions are thrown
        logger.debug("Debug message test");
        logger.info("Info message test");
        logger.warn("Warning message test");
        logger.error("Error message test (this is just a test)");
        
        // Just verify logger is not null
        Assert.assertNotNull(logger, "Logger should not be null");
        
        logger.info("Logger validation passed successfully");
    }
    
    @Test(priority = 4, description = "Validate Framework Constants", groups = {"framework", "smoke"})
    public void validateFrameworkConstants() {
        logger.info("Test: Validate Framework Constants");
        
        // Test that we can access framework constants
        try {
            Class.forName("com.hybridframework.constants.FrameworkConstants");
            logger.info("FrameworkConstants class found and accessible");
        } catch (ClassNotFoundException e) {
            Assert.fail("FrameworkConstants class not found: " + e.getMessage());
        }
        
        logger.info("Framework Constants validation passed successfully");
    }
    
    @Test(priority = 5, description = "Validate Test Data Path Configuration", groups = {"framework", "smoke"})
    public void validateTestDataPath() {
        logger.info("Test: Validate Test Data Path Configuration");
        
        String testDataPath = ConfigReader.getTestDataPath();
        Assert.assertNotNull(testDataPath, "Test data path should not be null");
        Assert.assertFalse(testDataPath.isEmpty(), "Test data path should not be empty");
        
        logger.info("Test data path: " + testDataPath);
        logger.info("Test Data Path validation passed successfully");
    }
    
    @Test(priority = 6, description = "Validate ExtentReport Configuration", groups = {"framework", "smoke"})
    public void validateExtentReportConfiguration() {
        logger.info("Test: Validate ExtentReport Configuration");
        
        String reportPath = ConfigReader.getExtentReportPath();
        Assert.assertNotNull(reportPath, "Report path should not be null");
        Assert.assertFalse(reportPath.isEmpty(), "Report path should not be empty");
        
        String reportTitle = ConfigReader.getExtentReportTitle();
        Assert.assertNotNull(reportTitle, "Report title should not be null");
        
        logger.info("Report path: " + reportPath);
        logger.info("Report title: " + reportTitle);
        logger.info("ExtentReport Configuration validation passed successfully");
    }
    
    @AfterMethod
    public void afterMethod() {
        logger.info("Framework component test completed");
    }
    
    @AfterSuite
    public void afterSuite() {
        logger.info("Framework Validation Suite completed");
        ExtentReportManager.flushReports();
    }
}