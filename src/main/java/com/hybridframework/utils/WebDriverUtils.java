package com.hybridframework.utils;

import com.hybridframework.constants.FrameworkConstants;
import com.hybridframework.drivers.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Utility class for common WebDriver operations
 */
public class WebDriverUtils {
    
    private static final Logger logger = LogManager.getLogger(WebDriverUtils.class);
    
    /**
     * Navigate to URL
     * @param url URL to navigate to
     */
    public static void navigateToUrl(String url) {
        try {
            DriverManager.getDriver().get(url);
            logger.info("Navigated to URL: " + url);
        } catch (Exception e) {
            logger.error("Failed to navigate to URL: " + url, e);
            throw new RuntimeException("Failed to navigate to URL: " + url, e);
        }
    }
    
    /**
     * Get current URL
     * @return Current URL
     */
    public static String getCurrentUrl() {
        return DriverManager.getDriver().getCurrentUrl();
    }
    
    /**
     * Get page title
     * @return Page title
     */
    public static String getPageTitle() {
        return DriverManager.getDriver().getTitle();
    }
    
    /**
     * Get page source
     * @return Page source
     */
    public static String getPageSource() {
        return DriverManager.getDriver().getPageSource();
    }
    
    /**
     * Refresh page
     */
    public static void refreshPage() {
        try {
            DriverManager.getDriver().navigate().refresh();
            logger.info("Page refreshed successfully");
        } catch (Exception e) {
            logger.error("Failed to refresh page", e);
            throw new RuntimeException("Failed to refresh page", e);
        }
    }
    
    /**
     * Navigate back
     */
    public static void navigateBack() {
        try {
            DriverManager.getDriver().navigate().back();
            logger.info("Navigated back successfully");
        } catch (Exception e) {
            logger.error("Failed to navigate back", e);
            throw new RuntimeException("Failed to navigate back", e);
        }
    }
    
    /**
     * Navigate forward
     */
    public static void navigateForward() {
        try {
            DriverManager.getDriver().navigate().forward();
            logger.info("Navigated forward successfully");
        } catch (Exception e) {
            logger.error("Failed to navigate forward", e);
            throw new RuntimeException("Failed to navigate forward", e);
        }
    }
    
    /**
     * Find element with explicit wait
     * @param locator Element locator
     * @return WebElement
     */
    public static WebElement findElementWithWait(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not found: " + locator, e);
            throw new RuntimeException("Element not found: " + locator, e);
        }
    }
    
    /**
     * Find elements with explicit wait
     * @param locator Element locator
     * @return List of WebElements
     */
    public static List<WebElement> findElementsWithWait(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            logger.error("Elements not found: " + locator, e);
            throw new RuntimeException("Elements not found: " + locator, e);
        }
    }
    
    /**
     * Wait for element to be clickable
     * @param locator Element locator
     * @return WebElement
     */
    public static WebElement waitForElementToBeClickable(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable: " + locator, e);
            throw new RuntimeException("Element not clickable: " + locator, e);
        }
    }
    
    /**
     * Wait for element to be visible
     * @param locator Element locator
     * @return WebElement
     */
    public static WebElement waitForElementToBeVisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible: " + locator, e);
            throw new RuntimeException("Element not visible: " + locator, e);
        }
    }
    
    /**
     * Wait for element to be invisible
     * @param locator Element locator
     * @return Boolean
     */
    public static boolean waitForElementToBeInvisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element still visible: " + locator, e);
            return false;
        }
    }
    
    /**
     * Click element
     * @param locator Element locator
     */
    public static void clickElement(By locator) {
        try {
            WebElement element = waitForElementToBeClickable(locator);
            element.click();
            logger.info("Clicked element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to click element: " + locator, e);
            throw new RuntimeException("Failed to click element: " + locator, e);
        }
    }
    
    /**
     * Click element using JavaScript
     * @param locator Element locator
     */
    public static void clickElementUsingJS(By locator) {
        try {
            WebElement element = findElementWithWait(locator);
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].click();", element);
            logger.info("Clicked element using JavaScript: " + locator);
        } catch (Exception e) {
            logger.error("Failed to click element using JavaScript: " + locator, e);
            throw new RuntimeException("Failed to click element using JavaScript: " + locator, e);
        }
    }
    
    /**
     * Send keys to element
     * @param locator Element locator
     * @param text Text to send
     */
    public static void sendKeys(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Sent keys to element: " + locator + " with text: " + text);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: " + locator, e);
            throw new RuntimeException("Failed to send keys to element: " + locator, e);
        }
    }
    
    /**
     * Get text from element
     * @param locator Element locator
     * @return Element text
     */
    public static String getText(By locator) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            String text = element.getText();
            logger.info("Got text from element: " + locator + " - " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + locator, e);
            throw new RuntimeException("Failed to get text from element: " + locator, e);
        }
    }
    
    /**
     * Get attribute value from element
     * @param locator Element locator
     * @param attributeName Attribute name
     * @return Attribute value
     */
    public static String getAttribute(By locator, String attributeName) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            String attributeValue = element.getAttribute(attributeName);
            logger.info("Got attribute value from element: " + locator + " - " + attributeName + " = " + attributeValue);
            return attributeValue;
        } catch (Exception e) {
            logger.error("Failed to get attribute value from element: " + locator, e);
            throw new RuntimeException("Failed to get attribute value from element: " + locator, e);
        }
    }
    
    /**
     * Check if element is displayed
     * @param locator Element locator
     * @return True if element is displayed
     */
    public static boolean isElementDisplayed(By locator) {
        try {
            WebElement element = DriverManager.getDriver().findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is enabled
     * @param locator Element locator
     * @return True if element is enabled
     */
    public static boolean isElementEnabled(By locator) {
        try {
            WebElement element = DriverManager.getDriver().findElement(locator);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is selected
     * @param locator Element locator
     * @return True if element is selected
     */
    public static boolean isElementSelected(By locator) {
        try {
            WebElement element = DriverManager.getDriver().findElement(locator);
            return element.isSelected();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Select dropdown option by visible text
     * @param locator Dropdown locator
     * @param optionText Option text to select
     */
    public static void selectDropdownByText(By locator, String optionText) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select dropdown = new Select(element);
            dropdown.selectByVisibleText(optionText);
            logger.info("Selected dropdown option by text: " + optionText);
        } catch (Exception e) {
            logger.error("Failed to select dropdown option by text: " + optionText, e);
            throw new RuntimeException("Failed to select dropdown option by text: " + optionText, e);
        }
    }
    
    /**
     * Select dropdown option by value
     * @param locator Dropdown locator
     * @param optionValue Option value to select
     */
    public static void selectDropdownByValue(By locator, String optionValue) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select dropdown = new Select(element);
            dropdown.selectByValue(optionValue);
            logger.info("Selected dropdown option by value: " + optionValue);
        } catch (Exception e) {
            logger.error("Failed to select dropdown option by value: " + optionValue, e);
            throw new RuntimeException("Failed to select dropdown option by value: " + optionValue, e);
        }
    }
    
    /**
     * Select dropdown option by index
     * @param locator Dropdown locator
     * @param optionIndex Option index to select
     */
    public static void selectDropdownByIndex(By locator, int optionIndex) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            Select dropdown = new Select(element);
            dropdown.selectByIndex(optionIndex);
            logger.info("Selected dropdown option by index: " + optionIndex);
        } catch (Exception e) {
            logger.error("Failed to select dropdown option by index: " + optionIndex, e);
            throw new RuntimeException("Failed to select dropdown option by index: " + optionIndex, e);
        }
    }
    
    /**
     * Take screenshot
     * @param testName Test name for screenshot file
     * @return Screenshot file path
     */
    public static String takeScreenshot(String testName) {
        String screenshotPath = null;
        try {
            TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getDriver();
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = FrameworkConstants.SCREENSHOT_PREFIX + testName + "_" + timestamp + FrameworkConstants.SCREENSHOT_EXTENSION;
            screenshotPath = ConfigReader.getScreenshotPath() + fileName;
            
            File destinationFile = new File(screenshotPath);
            FileUtils.copyFile(sourceFile, destinationFile);
            
            logger.info("Screenshot taken: " + screenshotPath);
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
        }
        return screenshotPath;
    }
    
    /**
     * Switch to window by title
     * @param windowTitle Window title
     */
    public static void switchToWindowByTitle(String windowTitle) {
        try {
            Set<String> windowHandles = DriverManager.getDriver().getWindowHandles();
            for (String windowHandle : windowHandles) {
                DriverManager.getDriver().switchTo().window(windowHandle);
                if (DriverManager.getDriver().getTitle().contains(windowTitle)) {
                    logger.info("Switched to window: " + windowTitle);
                    return;
                }
            }
            throw new RuntimeException("Window not found: " + windowTitle);
        } catch (Exception e) {
            logger.error("Failed to switch to window: " + windowTitle, e);
            throw new RuntimeException("Failed to switch to window: " + windowTitle, e);
        }
    }
    
    /**
     * Switch to frame by index
     * @param frameIndex Frame index
     */
    public static void switchToFrameByIndex(int frameIndex) {
        try {
            DriverManager.getDriver().switchTo().frame(frameIndex);
            logger.info("Switched to frame by index: " + frameIndex);
        } catch (Exception e) {
            logger.error("Failed to switch to frame by index: " + frameIndex, e);
            throw new RuntimeException("Failed to switch to frame by index: " + frameIndex, e);
        }
    }
    
    /**
     * Switch to frame by name or id
     * @param frameNameOrId Frame name or id
     */
    public static void switchToFrameByNameOrId(String frameNameOrId) {
        try {
            DriverManager.getDriver().switchTo().frame(frameNameOrId);
            logger.info("Switched to frame by name or id: " + frameNameOrId);
        } catch (Exception e) {
            logger.error("Failed to switch to frame by name or id: " + frameNameOrId, e);
            throw new RuntimeException("Failed to switch to frame by name or id: " + frameNameOrId, e);
        }
    }
    
    /**
     * Switch to default content
     */
    public static void switchToDefaultContent() {
        try {
            DriverManager.getDriver().switchTo().defaultContent();
            logger.info("Switched to default content");
        } catch (Exception e) {
            logger.error("Failed to switch to default content", e);
            throw new RuntimeException("Failed to switch to default content", e);
        }
    }
    
    /**
     * Accept alert
     */
    public static void acceptAlert() {
        try {
            Alert alert = DriverManager.getDriver().switchTo().alert();
            alert.accept();
            logger.info("Alert accepted");
        } catch (Exception e) {
            logger.error("Failed to accept alert", e);
            throw new RuntimeException("Failed to accept alert", e);
        }
    }
    
    /**
     * Dismiss alert
     */
    public static void dismissAlert() {
        try {
            Alert alert = DriverManager.getDriver().switchTo().alert();
            alert.dismiss();
            logger.info("Alert dismissed");
        } catch (Exception e) {
            logger.error("Failed to dismiss alert", e);
            throw new RuntimeException("Failed to dismiss alert", e);
        }
    }
    
    /**
     * Get alert text
     * @return Alert text
     */
    public static String getAlertText() {
        try {
            Alert alert = DriverManager.getDriver().switchTo().alert();
            String alertText = alert.getText();
            logger.info("Got alert text: " + alertText);
            return alertText;
        } catch (Exception e) {
            logger.error("Failed to get alert text", e);
            throw new RuntimeException("Failed to get alert text", e);
        }
    }
    
    /**
     * Send keys to alert
     * @param text Text to send
     */
    public static void sendKeysToAlert(String text) {
        try {
            Alert alert = DriverManager.getDriver().switchTo().alert();
            alert.sendKeys(text);
            logger.info("Sent keys to alert: " + text);
        } catch (Exception e) {
            logger.error("Failed to send keys to alert", e);
            throw new RuntimeException("Failed to send keys to alert", e);
        }
    }
    
    /**
     * Scroll to element
     * @param locator Element locator
     */
    public static void scrollToElement(By locator) {
        try {
            WebElement element = findElementWithWait(locator);
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: " + locator, e);
            throw new RuntimeException("Failed to scroll to element: " + locator, e);
        }
    }
    
    /**
     * Wait for specific time
     * @param seconds Seconds to wait
     */
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            logger.info("Waited for " + seconds + " seconds");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Wait interrupted", e);
        }
    }
}