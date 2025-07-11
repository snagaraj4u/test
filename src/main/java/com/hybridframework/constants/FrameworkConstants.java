package com.hybridframework.constants;

/**
 * Framework constants for paths, timeouts, and other static values
 */
public class FrameworkConstants {
    
    // File paths
    public static final String CONFIG_FILE_PATH = "src/main/resources/config/config.properties";
    public static final String TEST_DATA_PATH = "src/main/resources/testdata/";
    public static final String SCREENSHOTS_PATH = "screenshots/";
    public static final String LOGS_PATH = "logs/";
    public static final String REPORTS_PATH = "test-output/";
    
    // Timeouts
    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 20;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    public static final int FLUENT_WAIT_TIMEOUT = 30;
    public static final int FLUENT_WAIT_POLLING = 2;
    
    // Test data
    public static final String EXCEL_TEST_DATA = "TestData.xlsx";
    public static final String JSON_TEST_DATA = "TestData.json";
    
    // Browser names
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE = "edge";
    public static final String SAFARI = "safari";
    
    // Environment names
    public static final String QA = "qa";
    public static final String STAGING = "staging";
    public static final String PRODUCTION = "production";
    
    // Test result messages
    public static final String PASS = "PASS";
    public static final String FAIL = "FAIL";
    public static final String SKIP = "SKIP";
    
    // Report configuration
    public static final String EXTENT_REPORT_PATH = "test-output/ExtentReport.html";
    public static final String EXTENT_REPORT_TITLE = "Selenium TestNG Hybrid Framework Report";
    public static final String EXTENT_REPORT_NAME = "Test Execution Report";
    
    // Screenshot configuration
    public static final String SCREENSHOT_EXTENSION = ".png";
    public static final String SCREENSHOT_PREFIX = "Screenshot_";
    
    // Excel sheet names
    public static final String LOGIN_SHEET = "Login";
    public static final String USER_SHEET = "Users";
    public static final String TESTCASE_SHEET = "TestCases";
    
    // Database configuration
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USERNAME_KEY = "db.username";
    public static final String DB_PASSWORD_KEY = "db.password";
    
    // Email configuration
    public static final String EMAIL_SMTP_HOST = "smtp.gmail.com";
    public static final String EMAIL_SMTP_PORT = "587";
    public static final String EMAIL_SUBJECT = "Test Execution Report";
    
    // File extensions
    public static final String PROPERTIES_EXTENSION = ".properties";
    public static final String EXCEL_EXTENSION = ".xlsx";
    public static final String JSON_EXTENSION = ".json";
    public static final String LOG_EXTENSION = ".log";
    
    // Private constructor to prevent instantiation
    private FrameworkConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}