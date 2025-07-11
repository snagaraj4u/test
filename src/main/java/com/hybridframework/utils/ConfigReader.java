package com.hybridframework.utils;

import com.hybridframework.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading configuration properties
 */
public class ConfigReader {
    
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties = new Properties();
    
    static {
        loadProperties();
    }
    
    /**
     * Load properties from configuration file
     */
    private static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
            properties.load(fis);
            logger.info("Configuration properties loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load configuration properties: " + e.getMessage());
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }
    
    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found: " + key);
        }
        return value;
    }
    
    /**
     * Get property value by key with default value
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        if (value.equals(defaultValue)) {
            logger.warn("Property not found, using default value: " + key + " = " + defaultValue);
        }
        return value;
    }
    
    /**
     * Get browser name from configuration
     * @return Browser name
     */
    public static String getBrowser() {
        return getProperty("browser", FrameworkConstants.CHROME);
    }
    
    /**
     * Get application URL from configuration
     * @return Application URL
     */
    public static String getApplicationUrl() {
        String environment = getProperty("environment", FrameworkConstants.QA);
        return getProperty(environment + ".url", getProperty("app.url"));
    }
    
    /**
     * Get implicit wait timeout from configuration
     * @return Implicit wait timeout
     */
    public static int getImplicitWait() {
        String timeout = getProperty("implicit.wait", String.valueOf(FrameworkConstants.IMPLICIT_WAIT));
        return Integer.parseInt(timeout);
    }
    
    /**
     * Get explicit wait timeout from configuration
     * @return Explicit wait timeout
     */
    public static int getExplicitWait() {
        String timeout = getProperty("explicit.wait", String.valueOf(FrameworkConstants.EXPLICIT_WAIT));
        return Integer.parseInt(timeout);
    }
    
    /**
     * Get page load timeout from configuration
     * @return Page load timeout
     */
    public static int getPageLoadTimeout() {
        String timeout = getProperty("page.load.timeout", String.valueOf(FrameworkConstants.PAGE_LOAD_TIMEOUT));
        return Integer.parseInt(timeout);
    }
    
    /**
     * Check if browser should run in headless mode
     * @return True if headless mode is enabled
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    /**
     * Check if browser should be maximized
     * @return True if maximize is enabled
     */
    public static boolean isMaximize() {
        return Boolean.parseBoolean(getProperty("maximize", "true"));
    }
    
    /**
     * Check if screenshot should be taken on pass
     * @return True if screenshot on pass is enabled
     */
    public static boolean isScreenshotOnPass() {
        return Boolean.parseBoolean(getProperty("screenshot.on.pass", "false"));
    }
    
    /**
     * Check if screenshot should be taken on fail
     * @return True if screenshot on fail is enabled
     */
    public static boolean isScreenshotOnFail() {
        return Boolean.parseBoolean(getProperty("screenshot.on.fail", "true"));
    }
    
    /**
     * Get retry count for failed tests
     * @return Retry count
     */
    public static int getRetryCount() {
        String retryCount = getProperty("retry.count", "1");
        return Integer.parseInt(retryCount);
    }
    
    /**
     * Check if parallel execution is enabled
     * @return True if parallel execution is enabled
     */
    public static boolean isParallelExecution() {
        return Boolean.parseBoolean(getProperty("parallel.execution", "false"));
    }
    
    /**
     * Get thread count for parallel execution
     * @return Thread count
     */
    public static int getThreadCount() {
        String threadCount = getProperty("thread.count", "2");
        return Integer.parseInt(threadCount);
    }
    
    /**
     * Get test data path
     * @return Test data path
     */
    public static String getTestDataPath() {
        return getProperty("test.data.path", FrameworkConstants.TEST_DATA_PATH);
    }
    
    /**
     * Get Excel test data file name
     * @return Excel test data file name
     */
    public static String getExcelTestData() {
        return getProperty("excel.test.data", FrameworkConstants.EXCEL_TEST_DATA);
    }
    
    /**
     * Get screenshot path
     * @return Screenshot path
     */
    public static String getScreenshotPath() {
        return getProperty("screenshot.path", FrameworkConstants.SCREENSHOTS_PATH);
    }
    
    /**
     * Get extent report path
     * @return Extent report path
     */
    public static String getExtentReportPath() {
        return getProperty("extent.report.path", FrameworkConstants.EXTENT_REPORT_PATH);
    }
    
    /**
     * Get extent report title
     * @return Extent report title
     */
    public static String getExtentReportTitle() {
        return getProperty("extent.report.title", FrameworkConstants.EXTENT_REPORT_TITLE);
    }
    
    /**
     * Get extent report name
     * @return Extent report name
     */
    public static String getExtentReportName() {
        return getProperty("extent.report.name", FrameworkConstants.EXTENT_REPORT_NAME);
    }
}