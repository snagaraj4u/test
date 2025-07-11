package com.hybridframework.base;

import com.hybridframework.drivers.DriverManager;
import com.hybridframework.utils.ConfigReader;
import com.hybridframework.utils.ExtentReportManager;
import com.hybridframework.utils.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base page class providing common page functionality
 */
public class BasePage {
    
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Constructor to initialize page components
     */
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        String title = WebDriverUtils.getPageTitle();
        logger.info("Page title: " + title);
        ExtentReportManager.logInfo("Page title: " + title);
        return title;
    }
    
    /**
     * Get current URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        String url = WebDriverUtils.getCurrentUrl();
        logger.info("Current URL: " + url);
        ExtentReportManager.logInfo("Current URL: " + url);
        return url;
    }
    
    /**
     * Click element
     * @param locator Element locator
     */
    protected void clickElement(By locator) {
        WebDriverUtils.clickElement(locator);
        ExtentReportManager.logInfo("Clicked element: " + locator);
    }
    
    /**
     * Click element using JavaScript
     * @param locator Element locator
     */
    protected void clickElementUsingJS(By locator) {
        WebDriverUtils.clickElementUsingJS(locator);
        ExtentReportManager.logInfo("Clicked element using JavaScript: " + locator);
    }
    
    /**
     * Send keys to element
     * @param locator Element locator
     * @param text Text to send
     */
    protected void sendKeys(By locator, String text) {
        WebDriverUtils.sendKeys(locator, text);
        ExtentReportManager.logInfo("Entered text: " + text + " in element: " + locator);
    }
    
    /**
     * Get text from element
     * @param locator Element locator
     * @return Element text
     */
    protected String getText(By locator) {
        String text = WebDriverUtils.getText(locator);
        ExtentReportManager.logInfo("Retrieved text: " + text + " from element: " + locator);
        return text;
    }
    
    /**
     * Get attribute value from element
     * @param locator Element locator
     * @param attributeName Attribute name
     * @return Attribute value
     */
    protected String getAttribute(By locator, String attributeName) {
        String attributeValue = WebDriverUtils.getAttribute(locator, attributeName);
        ExtentReportManager.logInfo("Retrieved attribute: " + attributeName + " = " + attributeValue + " from element: " + locator);
        return attributeValue;
    }
    
    /**
     * Check if element is displayed
     * @param locator Element locator
     * @return True if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        boolean isDisplayed = WebDriverUtils.isElementDisplayed(locator);
        ExtentReportManager.logInfo("Element displayed: " + isDisplayed + " for element: " + locator);
        return isDisplayed;
    }
    
    /**
     * Check if element is enabled
     * @param locator Element locator
     * @return True if element is enabled
     */
    protected boolean isElementEnabled(By locator) {
        boolean isEnabled = WebDriverUtils.isElementEnabled(locator);
        ExtentReportManager.logInfo("Element enabled: " + isEnabled + " for element: " + locator);
        return isEnabled;
    }
    
    /**
     * Check if element is selected
     * @param locator Element locator
     * @return True if element is selected
     */
    protected boolean isElementSelected(By locator) {
        boolean isSelected = WebDriverUtils.isElementSelected(locator);
        ExtentReportManager.logInfo("Element selected: " + isSelected + " for element: " + locator);
        return isSelected;
    }
    
    /**
     * Wait for element to be visible
     * @param locator Element locator
     * @return WebElement
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        return WebDriverUtils.waitForElementToBeVisible(locator);
    }
    
    /**
     * Wait for element to be clickable
     * @param locator Element locator
     * @return WebElement
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        return WebDriverUtils.waitForElementToBeClickable(locator);
    }
    
    /**
     * Wait for element to be invisible
     * @param locator Element locator
     * @return Boolean
     */
    protected boolean waitForElementToBeInvisible(By locator) {
        return WebDriverUtils.waitForElementToBeInvisible(locator);
    }
    
    /**
     * Find element with wait
     * @param locator Element locator
     * @return WebElement
     */
    protected WebElement findElementWithWait(By locator) {
        return WebDriverUtils.findElementWithWait(locator);
    }
    
    /**
     * Find elements with wait
     * @param locator Element locator
     * @return List of WebElements
     */
    protected List<WebElement> findElementsWithWait(By locator) {
        return WebDriverUtils.findElementsWithWait(locator);
    }
    
    /**
     * Select dropdown option by text
     * @param locator Dropdown locator
     * @param optionText Option text
     */
    protected void selectDropdownByText(By locator, String optionText) {
        WebDriverUtils.selectDropdownByText(locator, optionText);
        ExtentReportManager.logInfo("Selected dropdown option: " + optionText + " from dropdown: " + locator);
    }
    
    /**
     * Select dropdown option by value
     * @param locator Dropdown locator
     * @param optionValue Option value
     */
    protected void selectDropdownByValue(By locator, String optionValue) {
        WebDriverUtils.selectDropdownByValue(locator, optionValue);
        ExtentReportManager.logInfo("Selected dropdown option by value: " + optionValue + " from dropdown: " + locator);
    }
    
    /**
     * Select dropdown option by index
     * @param locator Dropdown locator
     * @param optionIndex Option index
     */
    protected void selectDropdownByIndex(By locator, int optionIndex) {
        WebDriverUtils.selectDropdownByIndex(locator, optionIndex);
        ExtentReportManager.logInfo("Selected dropdown option by index: " + optionIndex + " from dropdown: " + locator);
    }
    
    /**
     * Scroll to element
     * @param locator Element locator
     */
    protected void scrollToElement(By locator) {
        WebDriverUtils.scrollToElement(locator);
        ExtentReportManager.logInfo("Scrolled to element: " + locator);
    }
    
    /**
     * Accept alert
     */
    protected void acceptAlert() {
        WebDriverUtils.acceptAlert();
        ExtentReportManager.logInfo("Alert accepted");
    }
    
    /**
     * Dismiss alert
     */
    protected void dismissAlert() {
        WebDriverUtils.dismissAlert();
        ExtentReportManager.logInfo("Alert dismissed");
    }
    
    /**
     * Get alert text
     * @return Alert text
     */
    protected String getAlertText() {
        String alertText = WebDriverUtils.getAlertText();
        ExtentReportManager.logInfo("Alert text: " + alertText);
        return alertText;
    }
    
    /**
     * Send keys to alert
     * @param text Text to send
     */
    protected void sendKeysToAlert(String text) {
        WebDriverUtils.sendKeysToAlert(text);
        ExtentReportManager.logInfo("Sent text to alert: " + text);
    }
    
    /**
     * Switch to window by title
     * @param windowTitle Window title
     */
    protected void switchToWindowByTitle(String windowTitle) {
        WebDriverUtils.switchToWindowByTitle(windowTitle);
        ExtentReportManager.logInfo("Switched to window: " + windowTitle);
    }
    
    /**
     * Switch to frame by index
     * @param frameIndex Frame index
     */
    protected void switchToFrameByIndex(int frameIndex) {
        WebDriverUtils.switchToFrameByIndex(frameIndex);
        ExtentReportManager.logInfo("Switched to frame by index: " + frameIndex);
    }
    
    /**
     * Switch to frame by name or id
     * @param frameNameOrId Frame name or id
     */
    protected void switchToFrameByNameOrId(String frameNameOrId) {
        WebDriverUtils.switchToFrameByNameOrId(frameNameOrId);
        ExtentReportManager.logInfo("Switched to frame: " + frameNameOrId);
    }
    
    /**
     * Switch to default content
     */
    protected void switchToDefaultContent() {
        WebDriverUtils.switchToDefaultContent();
        ExtentReportManager.logInfo("Switched to default content");
    }
    
    /**
     * Refresh page
     */
    protected void refreshPage() {
        WebDriverUtils.refreshPage();
        ExtentReportManager.logInfo("Page refreshed");
    }
    
    /**
     * Navigate back
     */
    protected void navigateBack() {
        WebDriverUtils.navigateBack();
        ExtentReportManager.logInfo("Navigated back");
    }
    
    /**
     * Navigate forward
     */
    protected void navigateForward() {
        WebDriverUtils.navigateForward();
        ExtentReportManager.logInfo("Navigated forward");
    }
    
    /**
     * Wait for specific time
     * @param seconds Seconds to wait
     */
    protected void waitForSeconds(int seconds) {
        WebDriverUtils.waitForSeconds(seconds);
        ExtentReportManager.logInfo("Waited for " + seconds + " seconds");
    }
    
    /**
     * Verify page title
     * @param expectedTitle Expected page title
     * @return True if title matches
     */
    public boolean verifyPageTitle(String expectedTitle) {
        String actualTitle = getPageTitle();
        boolean matches = actualTitle.equals(expectedTitle);
        
        if (matches) {
            ExtentReportManager.logPass("Page title verification passed. Expected: " + expectedTitle + ", Actual: " + actualTitle);
        } else {
            ExtentReportManager.logFail("Page title verification failed. Expected: " + expectedTitle + ", Actual: " + actualTitle);
        }
        
        return matches;
    }
    
    /**
     * Verify current URL
     * @param expectedUrl Expected URL
     * @return True if URL matches
     */
    public boolean verifyCurrentUrl(String expectedUrl) {
        String actualUrl = getCurrentUrl();
        boolean matches = actualUrl.equals(expectedUrl);
        
        if (matches) {
            ExtentReportManager.logPass("URL verification passed. Expected: " + expectedUrl + ", Actual: " + actualUrl);
        } else {
            ExtentReportManager.logFail("URL verification failed. Expected: " + expectedUrl + ", Actual: " + actualUrl);
        }
        
        return matches;
    }
    
    /**
     * Verify element text
     * @param locator Element locator
     * @param expectedText Expected text
     * @return True if text matches
     */
    public boolean verifyElementText(By locator, String expectedText) {
        String actualText = getText(locator);
        boolean matches = actualText.equals(expectedText);
        
        if (matches) {
            ExtentReportManager.logPass("Element text verification passed. Expected: " + expectedText + ", Actual: " + actualText);
        } else {
            ExtentReportManager.logFail("Element text verification failed. Expected: " + expectedText + ", Actual: " + actualText);
        }
        
        return matches;
    }
    
    /**
     * Verify element is displayed
     * @param locator Element locator
     * @return True if element is displayed
     */
    public boolean verifyElementIsDisplayed(By locator) {
        boolean isDisplayed = isElementDisplayed(locator);
        
        if (isDisplayed) {
            ExtentReportManager.logPass("Element is displayed: " + locator);
        } else {
            ExtentReportManager.logFail("Element is not displayed: " + locator);
        }
        
        return isDisplayed;
    }
}