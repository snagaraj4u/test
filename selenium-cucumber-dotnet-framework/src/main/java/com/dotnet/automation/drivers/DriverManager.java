package com.dotnet.automation.drivers;

import com.dotnet.automation.constants.FrameworkConstants;
import com.dotnet.automation.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;
import java.util.Objects;

/**
 * WebDriver manager for creating and managing browser instances in Cucumber framework
 */
public final class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    private DriverManager() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Get WebDriver instance for current thread
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    /**
     * Set WebDriver instance for current thread
     * @param webDriver WebDriver instance
     */
    public static void setDriver(WebDriver webDriver) {
        if (Objects.nonNull(webDriver)) {
            driver.set(webDriver);
            logger.info("WebDriver set for thread: " + Thread.currentThread().getName());
        }
    }
    
    /**
     * Initialize WebDriver based on browser configuration
     * @param browserName Browser name
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver(String browserName) {
        WebDriver webDriver = null;
        String browser = Objects.nonNull(browserName) ? browserName.toLowerCase() : ConfigReader.getBrowser().toLowerCase();
        
        try {
            switch (browser) {
                case FrameworkConstants.CHROME:
                    webDriver = createChromeDriver();
                    break;
                case FrameworkConstants.FIREFOX:
                    webDriver = createFirefoxDriver();
                    break;
                case FrameworkConstants.EDGE:
                    webDriver = createEdgeDriver();
                    break;
                case FrameworkConstants.SAFARI:
                    webDriver = createSafariDriver();
                    break;
                default:
                    logger.error("Browser not supported: " + browser);
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
            
            // Set driver in ThreadLocal
            setDriver(webDriver);
            
            // Configure timeouts
            configureTimeouts(webDriver);
            
            // Maximize browser if configured
            if (ConfigReader.isMaximize()) {
                webDriver.manage().window().maximize();
                logger.info("Browser window maximized");
            }
            
            logger.info("WebDriver initialized successfully for browser: " + browser);
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for browser: " + browser, e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
        
        return webDriver;
    }
    
    /**
     * Create Chrome driver with enhanced options for .NET applications
     * @return Chrome WebDriver
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Enhanced Chrome options for .NET applications and headless environments
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--disable-features=TranslateUI");
        options.addArguments("--disable-ipc-flooding-protection");
        options.addArguments("--remote-debugging-port=9222");
        
        // .NET specific Chrome configurations
        options.addArguments("--disable-features=VizDisplayCompositor");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-popup-blocking");
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-background-networking");
            options.addArguments("--enable-features=NetworkService,NetworkServiceLogging");
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-sync");
            logger.info("Chrome configured for headless mode");
        }
        
        // Set page load strategy
        String pageLoadStrategy = ConfigReader.getPageLoadStrategy();
        switch (pageLoadStrategy.toLowerCase()) {
            case "eager":
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                break;
            case "none":
                options.setPageLoadStrategy(PageLoadStrategy.NONE);
                break;
            default:
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        }
        
        // Disable SSL certificate validation if configured
        if (!ConfigReader.isSslCertificateValidation()) {
            options.addArguments("--ignore-certificate-errors-spki-list");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--ignore-ssl-errors=yes");
            options.addArguments("--allow-running-insecure-content");
        }
        
        // Try to find Chrome binary in common locations
        String[] chromePaths = {
            "/usr/bin/google-chrome",
            "/usr/bin/google-chrome-stable",
            "/usr/bin/chromium-browser",
            "/usr/bin/chromium",
            "/snap/bin/chromium"
        };
        
        for (String path : chromePaths) {
            java.io.File chromeFile = new java.io.File(path);
            if (chromeFile.exists()) {
                options.setBinary(path);
                logger.info("Using Chrome binary: " + path);
                break;
            }
        }
        
        logger.info("Chrome driver created with options");
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox driver with options for .NET applications
     * @return Firefox WebDriver
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
            logger.info("Firefox configured for headless mode");
        }
        
        // Add Firefox preferences for .NET applications
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("media.volume_scale", "0.0");
        options.addPreference("security.tls.insecure_fallback_hosts", "localhost");
        options.addPreference("security.tls.unrestricted_rc4_fallback", true);
        
        // Disable SSL certificate validation if configured
        if (!ConfigReader.isSslCertificateValidation()) {
            options.addPreference("security.ssl.enable_verification", false);
            options.addPreference("security.ssl.enable_ocsp_stapling", false);
        }
        
        logger.info("Firefox driver created with options");
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Edge driver with options for .NET applications
     * @return Edge WebDriver
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        // Add Edge options similar to Chrome for .NET applications
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors");
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
            logger.info("Edge configured for headless mode");
        }
        
        logger.info("Edge driver created with options");
        return new EdgeDriver(options);
    }
    
    /**
     * Create Safari driver with options
     * @return Safari WebDriver
     */
    private static WebDriver createSafariDriver() {
        SafariOptions options = new SafariOptions();
        
        // Safari specific options for .NET applications
        options.setAutomaticInspection(false);
        options.setAutomaticProfiling(false);
        
        logger.info("Safari driver created with options");
        return new SafariDriver(options);
    }
    
    /**
     * Configure timeouts for WebDriver
     * @param webDriver WebDriver instance
     */
    private static void configureTimeouts(WebDriver webDriver) {
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(ConfigReader.getExplicitWait()));
        logger.info("WebDriver timeouts configured");
    }
    
    /**
     * Navigate to application URL
     */
    public static void navigateToApplication() {
        String url = ConfigReader.getApplicationUrl();
        if (Objects.nonNull(url) && !url.trim().isEmpty()) {
            getDriver().get(url);
            logger.info("Navigated to application URL: " + url);
        } else {
            logger.error("Application URL is null or empty");
            throw new RuntimeException("Application URL not configured");
        }
    }
    
    /**
     * Quit WebDriver for current thread
     */
    public static void quitDriver() {
        WebDriver webDriver = getDriver();
        if (Objects.nonNull(webDriver)) {
            try {
                webDriver.quit();
                logger.info("WebDriver quit successfully for thread: " + Thread.currentThread().getName());
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver", e);
            } finally {
                driver.remove();
            }
        }
    }
    
    /**
     * Close current browser window
     */
    public static void closeDriver() {
        WebDriver webDriver = getDriver();
        if (Objects.nonNull(webDriver)) {
            try {
                webDriver.close();
                logger.info("WebDriver closed successfully for thread: " + Thread.currentThread().getName());
            } catch (Exception e) {
                logger.error("Error while closing WebDriver", e);
            }
        }
    }
}