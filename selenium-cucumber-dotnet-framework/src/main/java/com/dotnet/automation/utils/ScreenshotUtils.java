package com.dotnet.automation.utils;

import com.dotnet.automation.constants.FrameworkConstants;
import com.dotnet.automation.drivers.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Screenshot utility for capturing test evidence and debugging
 * 
 * @author QA Team
 * @version 1.0
 * @since 2024
 */
public final class ScreenshotUtils {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    private ScreenshotUtils() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Captures screenshot for Cucumber scenario attachment
     * @return byte array of screenshot
     */
    public static byte[] takeScreenshotAsBytes() {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (Objects.nonNull(driver)) {
                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
                byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
                logger.info("Screenshot captured successfully");
                return screenshot;
            } else {
                logger.warn("Driver instance not available for screenshot");
                return new byte[0];
            }
        } catch (Exception e) {
            logger.error("Screenshot capture failed", e);
            return new byte[0];
        }
    }
    
    /**
     * Saves screenshot to file system with test name
     * @param testName name of the test for file naming
     * @return path to saved screenshot file
     */
    public static String takeScreenshot(String testName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (Objects.nonNull(driver)) {
                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
                File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
                
                String timestamp = LocalDateTime.now().format(DATE_FORMAT);
                String fileName = sanitizeFileName(testName) + "_" + timestamp + ".png";
                String screenshotPath = ConfigReader.getScreenshotPath() + fileName;
                
                File destFile = new File(screenshotPath);
                createScreenshotDirectory();
                
                FileUtils.copyFile(sourceFile, destFile);
                logger.info("Screenshot saved: {}", screenshotPath);
                return screenshotPath;
                
            } else {
                logger.warn("Driver not initialized, screenshot not taken");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to save screenshot for test: {}", testName, e);
            return null;
        }
    }
    
    /**
     * Creates screenshot directory structure if needed
     */
    private static void createScreenshotDirectory() {
        try {
            String screenshotDir = ConfigReader.getScreenshotPath();
            File directory = new File(screenshotDir);
            
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    logger.debug("Screenshot directory created: {}", screenshotDir);
                } else {
                    logger.warn("Could not create screenshot directory: {}", screenshotDir);
                }
            }
        } catch (Exception e) {
            logger.error("Error setting up screenshot directory", e);
        }
    }
    
    /**
     * Sanitizes file name for cross-platform compatibility
     * @param fileName raw file name
     * @return sanitized file name
     */
    private static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9]", "_");
    }
    
    /**
     * Captures screenshot on test failure
     * @param testName test identifier
     * @return screenshot file path
     */
    public static String captureFailureScreenshot(String testName) {
        return takeScreenshot(FrameworkConstants.SCREENSHOT_FAIL + testName);
    }
}