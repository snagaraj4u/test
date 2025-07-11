package com.dotnet.automation.utils;

import com.dotnet.automation.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading configuration properties
 */
public final class ConfigReader {
    
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    
    static {
        loadProperties();
    }
    
    private ConfigReader() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Load properties from config file
     */
    private static void loadProperties() {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
            logger.info("Configuration properties loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load configuration properties: " + e.getMessage());
            throw new RuntimeException("Configuration file not found or could not be loaded", e);
        }
    }
    
    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    private static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            logger.warn("Property value is null or empty for key: " + key);
        }
        return value;
    }
    
    /**
     * Get property value with default fallback
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    private static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        return value.trim().isEmpty() ? defaultValue : value;
    }
    
    // Browser Configuration
    public static String getBrowser() {
        return getProperty("browser", FrameworkConstants.CHROME);
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "true"));
    }
    
    public static boolean isMaximize() {
        return Boolean.parseBoolean(getProperty("maximize", "true"));
    }
    
    // Timeout Configuration
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }
    
    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }
    
    // Application Configuration
    public static String getApplicationUrl() {
        return getProperty("app.url");
    }
    
    public static String getApplicationName() {
        return getProperty("app.name", ".NET Application");
    }
    
    public static String getApplicationVersion() {
        return getProperty("app.version", "1.0");
    }
    
    public static String getEnvironment() {
        return getProperty("environment", "qa");
    }
    
    // Environment URLs
    public static String getQaUrl() {
        return getProperty("qa.url");
    }
    
    public static String getStagingUrl() {
        return getProperty("staging.url");
    }
    
    public static String getProductionUrl() {
        return getProperty("production.url");
    }
    
    // Test Data Configuration
    public static String getTestDataPath() {
        return getProperty("test.data.path", FrameworkConstants.TEST_DATA_PATH);
    }
    
    public static String getExcelTestData() {
        return getProperty("excel.test.data", FrameworkConstants.EXCEL_TEST_DATA);
    }
    
    public static String getJsonTestData() {
        return getProperty("json.test.data", FrameworkConstants.JSON_TEST_DATA);
    }
    
    // Screenshot Configuration
    public static boolean isScreenshotOnPass() {
        return Boolean.parseBoolean(getProperty("screenshot.on.pass", "false"));
    }
    
    public static boolean isScreenshotOnFail() {
        return Boolean.parseBoolean(getProperty("screenshot.on.fail", "true"));
    }
    
    public static String getScreenshotPath() {
        return getProperty("screenshot.path", FrameworkConstants.SCREENSHOTS_PATH);
    }
    
    // Reporting Configuration
    public static String getExtentReportPath() {
        return getProperty("extent.report.path", FrameworkConstants.EXTENT_REPORT_PATH);
    }
    
    public static String getExtentReportTitle() {
        return getProperty("extent.report.title", ".NET Application Automation Report");
    }
    
    public static String getExtentReportName() {
        return getProperty("extent.report.name", "Cucumber BDD Test Report");
    }
    
    public static String getAllureResultsPath() {
        return getProperty("allure.results.path", FrameworkConstants.ALLURE_RESULTS_PATH);
    }
    
    // Cucumber Configuration
    public static String getCucumberFeaturesPath() {
        return getProperty("cucumber.features.path", FrameworkConstants.FEATURES_PATH);
    }
    
    public static String getCucumberGluePath() {
        return getProperty("cucumber.glue.path", "com.dotnet.automation.stepdefinitions");
    }
    
    public static String getCucumberPlugin() {
        return getProperty("cucumber.plugin", "pretty,html:target/cucumber-reports");
    }
    
    // Parallel Execution Configuration
    public static boolean isParallelExecution() {
        return Boolean.parseBoolean(getProperty("parallel.execution", "false"));
    }
    
    public static int getThreadCount() {
        return Integer.parseInt(getProperty("thread.count", "3"));
    }
    
    public static boolean isCucumberParallelEnabled() {
        return Boolean.parseBoolean(getProperty("cucumber.execution.parallel.enabled", "false"));
    }
    
    // Database Configuration
    public static String getDatabaseUrl() {
        return getProperty("db.url");
    }
    
    public static String getDatabaseUsername() {
        return getProperty("db.username");
    }
    
    public static String getDatabasePassword() {
        return getProperty("db.password");
    }
    
    public static String getDatabaseDriver() {
        return getProperty("db.driver");
    }
    
    // Email Configuration
    public static String getEmailSmtpHost() {
        return getProperty("email.smtp.host");
    }
    
    public static String getEmailSmtpPort() {
        return getProperty("email.smtp.port");
    }
    
    public static String getEmailUsername() {
        return getProperty("email.username");
    }
    
    public static String getEmailPassword() {
        return getProperty("email.password");
    }
    
    public static String getEmailRecipients() {
        return getProperty("email.recipients");
    }
    
    // Retry Configuration
    public static int getRetryCount() {
        return Integer.parseInt(getProperty("retry.count", "2"));
    }
    
    public static boolean isRetryFailedTests() {
        return Boolean.parseBoolean(getProperty("retry.failed.tests", "true"));
    }
    
    // .NET Specific Configuration
    public static String getDotNetFrameworkVersion() {
        return getProperty("dotnet.framework.version", FrameworkConstants.DOTNET_FRAMEWORK_4_8);
    }
    
    public static String getDotNetCoreVersion() {
        return getProperty("dotnet.core.version", FrameworkConstants.DOTNET_CORE_6_0);
    }
    
    public static String getApplicationType() {
        return getProperty("application.type", FrameworkConstants.APPLICATION_TYPE_WEB);
    }
    
    public static boolean isWindowsAuthentication() {
        return Boolean.parseBoolean(getProperty("windows.authentication", "false"));
    }
    
    public static boolean isSslCertificateValidation() {
        return Boolean.parseBoolean(getProperty("ssl.certificate.validation", "false"));
    }
    
    // Performance Configuration
    public static String getPageLoadStrategy() {
        return getProperty("page.load.strategy", FrameworkConstants.PAGE_LOAD_NORMAL);
    }
    
    public static String getWaitStrategy() {
        return getProperty("wait.strategy", "explicit");
    }
    
    public static boolean isElementHighlight() {
        return Boolean.parseBoolean(getProperty("element.highlight", "false"));
    }
    
    // Convenience methods for validation tests
    public static String getAppUrl() {
        return getApplicationUrl();
    }
    
    public static String getReportsPath() {
        return getExtentReportPath();
    }
    
    public static boolean isWindowsAuthenticationEnabled() {
        return isWindowsAuthentication();
    }
    
    public static boolean isSslCertificateValidationEnabled() {
        return isSslCertificateValidation();
    }
}