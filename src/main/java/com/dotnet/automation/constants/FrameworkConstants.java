package com.dotnet.automation.constants;

/**
 * Framework constants for Selenium Cucumber .NET automation framework
 */
public final class FrameworkConstants {
    
    private FrameworkConstants() {
        // Private constructor to prevent instantiation
    }
    
    // Browser Constants
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE = "edge";
    public static final String SAFARI = "safari";
    
    // Timeout Constants
    public static final int DEFAULT_TIMEOUT = 20;
    public static final int SHORT_TIMEOUT = 5;
    public static final int LONG_TIMEOUT = 60;
    public static final int IMPLICIT_TIMEOUT = 10;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    
    // File Paths
    public static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";
    public static final String TEST_DATA_PATH = "src/test/resources/testdata/";
    public static final String FEATURES_PATH = "src/test/resources/features/";
    public static final String SCREENSHOTS_PATH = "screenshots/";
    public static final String REPORTS_PATH = "test-output/";
    public static final String LOGS_PATH = "logs/";
    
    // Report Configuration
    public static final String EXTENT_REPORT_PATH = "test-output/ExtentReport.html";
    public static final String EXTENT_CONFIG_PATH = "src/test/resources/extent-config.xml";
    public static final String ALLURE_RESULTS_PATH = "allure-results/";
    public static final String CUCUMBER_REPORTS_PATH = "target/cucumber-reports/";
    
    // Test Data File Names
    public static final String EXCEL_TEST_DATA = "DotNetTestData.xlsx";
    public static final String JSON_TEST_DATA = "DotNetTestData.json";
    
    // .NET Application Specific Constants
    public static final String DOTNET_FRAMEWORK_4_8 = "4.8";
    public static final String DOTNET_CORE_6_0 = "6.0";
    public static final String APPLICATION_TYPE_WEB = "web";
    public static final String APPLICATION_TYPE_DESKTOP = "desktop";
    public static final String APPLICATION_TYPE_API = "api";
    
    // Environment Constants
    public static final String QA_ENVIRONMENT = "qa";
    public static final String STAGING_ENVIRONMENT = "staging";
    public static final String PRODUCTION_ENVIRONMENT = "production";
    public static final String DEV_ENVIRONMENT = "dev";
    
    // Cucumber Tags
    public static final String SMOKE_TAG = "@smoke";
    public static final String REGRESSION_TAG = "@regression";
    public static final String SANITY_TAG = "@sanity";
    public static final String CRITICAL_TAG = "@critical";
    public static final String DOTNET_TAG = "@dotnet";
    
    // Screenshot Names
    public static final String SCREENSHOT_PASS = "Pass_Screenshot_";
    public static final String SCREENSHOT_FAIL = "Fail_Screenshot_";
    public static final String SCREENSHOT_SKIP = "Skip_Screenshot_";
    
    // Excel Sheet Names
    public static final String LOGIN_DATA_SHEET = "LoginData";
    public static final String USER_DATA_SHEET = "UserData";
    public static final String TEST_SCENARIOS_SHEET = "TestScenarios";
    
    // Status Messages
    public static final String TEST_PASSED = "Test Passed";
    public static final String TEST_FAILED = "Test Failed";
    public static final String TEST_SKIPPED = "Test Skipped";
    
    // Log Messages
    public static final String BROWSER_LAUNCHED = "Browser launched successfully: ";
    public static final String NAVIGATED_TO_URL = "Navigated to URL: ";
    public static final String ELEMENT_CLICKED = "Element clicked: ";
    public static final String TEXT_ENTERED = "Text entered: ";
    public static final String SCREENSHOT_CAPTURED = "Screenshot captured: ";
    
    // Wait Strategies
    public static final String WAIT_VISIBLE = "visible";
    public static final String WAIT_CLICKABLE = "clickable";
    public static final String WAIT_INVISIBLE = "invisible";
    public static final String WAIT_PRESENCE = "presence";
    
    // Page Load Strategies
    public static final String PAGE_LOAD_NORMAL = "normal";
    public static final String PAGE_LOAD_EAGER = "eager";
    public static final String PAGE_LOAD_NONE = "none";
    
    // Authentication Types for .NET Applications
    public static final String AUTH_WINDOWS = "windows";
    public static final String AUTH_FORMS = "forms";
    public static final String AUTH_OAUTH = "oauth";
    public static final String AUTH_SAML = "saml";
    
    // Database Constants (for .NET applications)
    public static final String SQL_SERVER = "sqlserver";
    public static final String MYSQL = "mysql";
    public static final String ORACLE = "oracle";
    public static final String POSTGRESQL = "postgresql";
    
    // Performance Constants
    public static final int MAX_PAGE_LOAD_TIME = 30;
    public static final int MAX_ELEMENT_LOAD_TIME = 10;
    public static final int PERFORMANCE_THRESHOLD = 5;
}