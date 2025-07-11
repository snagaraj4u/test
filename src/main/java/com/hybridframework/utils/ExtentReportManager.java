package com.hybridframework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * ExtentReports manager for test reporting
 */
public class ExtentReportManager {
    
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    /**
     * Initialize ExtentReports
     */
    public static void initializeExtentReports() {
        if (Objects.isNull(extent)) {
            String reportPath = ConfigReader.getExtentReportPath();
            
            // Create directory if it doesn't exist
            File reportFile = new File(reportPath);
            if (!reportFile.getParentFile().exists()) {
                reportFile.getParentFile().mkdirs();
            }
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            configureSparkReporter(sparkReporter);
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            setSystemInfo();
            
            logger.info("ExtentReports initialized successfully");
        }
    }
    
    /**
     * Configure Spark Reporter
     * @param sparkReporter Spark reporter instance
     */
    private static void configureSparkReporter(ExtentSparkReporter sparkReporter) {
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setReportName(ConfigReader.getExtentReportName());
        sparkReporter.config().setDocumentTitle(ConfigReader.getExtentReportTitle());
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setJs("document.getElementsByClassName('brand-logo')[0].innerHTML='<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/5+hHgAHggJ/PchI7wAAAABJRU5ErkJggg==\" width=\"50\" height=\"50\">Selenium TestNG Framework';");
    }
    
    /**
     * Set system information
     */
    private static void setSystemInfo() {
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("OS Version", System.getProperty("os.version"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("Environment", ConfigReader.getProperty("environment"));
        extent.setSystemInfo("Application URL", ConfigReader.getApplicationUrl());
        extent.setSystemInfo("Test Execution Time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
    
    /**
     * Create test in ExtentReports
     * @param testName Test name
     * @param testDescription Test description
     */
    public static void createTest(String testName, String testDescription) {
        ExtentTest extentTest = extent.createTest(testName, testDescription);
        test.set(extentTest);
        logger.info("Test created in ExtentReports: " + testName);
    }
    
    /**
     * Create test in ExtentReports with category
     * @param testName Test name
     * @param testDescription Test description
     * @param category Test category
     */
    public static void createTest(String testName, String testDescription, String category) {
        ExtentTest extentTest = extent.createTest(testName, testDescription);
        extentTest.assignCategory(category);
        test.set(extentTest);
        logger.info("Test created in ExtentReports: " + testName + " with category: " + category);
    }
    
    /**
     * Get current test
     * @return Current ExtentTest
     */
    public static ExtentTest getTest() {
        return test.get();
    }
    
    /**
     * Log info message
     * @param message Info message
     */
    public static void logInfo(String message) {
        if (Objects.nonNull(getTest())) {
            getTest().info(message);
        }
    }
    
    /**
     * Log pass message
     * @param message Pass message
     */
    public static void logPass(String message) {
        if (Objects.nonNull(getTest())) {
            getTest().pass(message);
        }
    }
    
    /**
     * Log fail message
     * @param message Fail message
     */
    public static void logFail(String message) {
        if (Objects.nonNull(getTest())) {
            getTest().fail(message);
        }
    }
    
    /**
     * Log skip message
     * @param message Skip message
     */
    public static void logSkip(String message) {
        if (Objects.nonNull(getTest())) {
            getTest().skip(message);
        }
    }
    
    /**
     * Log warning message
     * @param message Warning message
     */
    public static void logWarning(String message) {
        if (Objects.nonNull(getTest())) {
            getTest().warning(message);
        }
    }
    
    /**
     * Add screenshot to test
     * @param screenshotPath Screenshot path
     * @param message Screenshot message
     */
    public static void addScreenshot(String screenshotPath, String message) {
        if (Objects.nonNull(getTest()) && Objects.nonNull(screenshotPath)) {
            try {
                getTest().addScreenCaptureFromPath(screenshotPath, message);
            } catch (Exception e) {
                logger.error("Failed to add screenshot to ExtentReports", e);
            }
        }
    }
    
    /**
     * Add screenshot to failed test
     * @param screenshotPath Screenshot path
     */
    public static void addScreenshotOnFailure(String screenshotPath) {
        if (Objects.nonNull(getTest()) && Objects.nonNull(screenshotPath)) {
            try {
                getTest().fail("Test failed - Screenshot attached").addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to add failure screenshot to ExtentReports", e);
            }
        }
    }
    
    /**
     * Add screenshot to passed test
     * @param screenshotPath Screenshot path
     */
    public static void addScreenshotOnPass(String screenshotPath) {
        if (Objects.nonNull(getTest()) && Objects.nonNull(screenshotPath)) {
            try {
                getTest().pass("Test passed - Screenshot attached").addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to add pass screenshot to ExtentReports", e);
            }
        }
    }
    
    /**
     * Flush ExtentReports
     */
    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
            logger.info("ExtentReports flushed successfully");
        }
    }
    
    /**
     * Remove test from ThreadLocal
     */
    public static void removeTest() {
        test.remove();
    }
    
    /**
     * Get ExtentReports instance
     * @return ExtentReports instance
     */
    public static ExtentReports getExtentReports() {
        return extent;
    }
    
    /**
     * Clean up ExtentReports
     */
    public static void cleanup() {
        if (Objects.nonNull(extent)) {
            extent = null;
        }
        removeTest();
    }
}