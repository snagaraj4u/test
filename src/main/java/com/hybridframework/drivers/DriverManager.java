package com.hybridframework.drivers;

import com.hybridframework.constants.FrameworkConstants;
import com.hybridframework.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * WebDriver manager for creating and managing browser instances
 */
public class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
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
        }
    }
    
    /**
     * Initialize WebDriver based on browser configuration
     * @param browserName Browser name
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver(String browserName) {
        WebDriver webDriver = null;
        
        try {
            switch (browserName.toLowerCase()) {
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
                    logger.error("Browser not supported: " + browserName);
                    throw new IllegalArgumentException("Browser not supported: " + browserName);
            }
            
            // Set driver in ThreadLocal
            setDriver(webDriver);
            
            // Configure timeouts
            configureTimeouts(webDriver);
            
            // Maximize browser if configured
            if (ConfigReader.isMaximize()) {
                webDriver.manage().window().maximize();
            }
            
            logger.info("WebDriver initialized successfully for browser: " + browserName);
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for browser: " + browserName, e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
        
        return webDriver;
    }
    
    /**
     * Create Chrome driver with options
     * @return Chrome WebDriver
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Add Chrome options for headless environments
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--disable-features=TranslateUI");
        options.addArguments("--disable-ipc-flooding-protection");
        options.addArguments("--remote-debugging-port=9222");
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-background-networking");
            options.addArguments("--enable-features=NetworkService,NetworkServiceLogging");
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-sync");
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
        
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox driver with options
     * @return Firefox WebDriver
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }
        
        // Add Firefox preferences
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("media.volume_scale", "0.0");
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Edge driver with options
     * @return Edge WebDriver
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        // Add Edge options
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        
        // Set headless mode if configured
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }
        
        return new EdgeDriver(options);
    }
    
    /**
     * Create Safari driver with options
     * @return Safari WebDriver
     */
    private static WebDriver createSafariDriver() {
        SafariOptions options = new SafariOptions();
        
        // Safari specific options can be added here
        options.setAutomaticInspection(false);
        options.setAutomaticProfiling(false);
        
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
    }
    
    /**
     * Quit WebDriver for current thread
     */
    public static void quitDriver() {
        WebDriver webDriver = getDriver();
        if (Objects.nonNull(webDriver)) {
            try {
                webDriver.quit();
                logger.info("WebDriver quit successfully");
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
                logger.info("WebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Error while closing WebDriver", e);
            }
        }
    }
}