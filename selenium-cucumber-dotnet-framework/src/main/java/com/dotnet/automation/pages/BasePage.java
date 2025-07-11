package com.dotnet.automation.pages;

import com.dotnet.automation.drivers.DriverManager;
import com.dotnet.automation.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * Base page class providing common web element interactions
 * Implements reusable methods for all page objects
 * 
 * @author QA Automation Team
 * @version 1.0
 */
public abstract class BasePage {
    
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    
    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Waits for element to be visible and returns it
     * @param locator element locator
     * @return visible WebElement
     */
    protected WebElement waitForElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible within timeout: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Waits for element to be clickable and returns it
     * @param locator element locator  
     * @return clickable WebElement
     */
    protected WebElement waitForElementClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable within timeout: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Clicks on element after waiting for it to be clickable
     * @param locator element locator
     */
    protected void clickElement(By locator) {
        try {
            WebElement element = waitForElementClickable(locator);
            highlightElement(element);
            element.click();
            logger.info("Successfully clicked element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Sends text to input field after clearing it
     * @param locator element locator
     * @param text text to enter
     */
    protected void enterText(By locator, String text) {
        try {
            WebElement element = waitForElementVisible(locator);
            highlightElement(element);
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '{}' into element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to enter text into element: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Gets text content from element
     * @param locator element locator
     * @return element text
     */
    protected String getElementText(By locator) {
        try {
            WebElement element = waitForElementVisible(locator);
            String text = element.getText();
            logger.debug("Retrieved text '{}' from element: {}", text, locator);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Gets attribute value from element
     * @param locator element locator
     * @param attributeName attribute name
     * @return attribute value
     */
    protected String getElementAttribute(By locator, String attributeName) {
        try {
            WebElement element = waitForElementVisible(locator);
            String value = element.getAttribute(attributeName);
            logger.debug("Retrieved attribute '{}' = '{}' from element: {}", attributeName, value, locator);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get attribute '{}' from element: {}", attributeName, locator, e);
            throw e;
        }
    }
    
    /**
     * Checks if element is displayed
     * @param locator element locator
     * @return true if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            boolean displayed = element.isDisplayed();
            logger.debug("Element display status: {} for {}", displayed, locator);
            return displayed;
        } catch (Exception e) {
            logger.debug("Element not found or not displayed: {}", locator);
            return false;
        }
    }
    
    /**
     * Checks if element is enabled
     * @param locator element locator
     * @return true if element is enabled
     */
    protected boolean isElementEnabled(By locator) {
        try {
            WebElement element = waitForElementVisible(locator);
            boolean enabled = element.isEnabled();
            logger.debug("Element enabled status: {} for {}", enabled, locator);
            return enabled;
        } catch (Exception e) {
            logger.error("Failed to check if element is enabled: {}", locator, e);
            return false;
        }
    }
    
    /**
     * Selects option from dropdown by visible text
     * @param locator dropdown locator
     * @param optionText visible text of option
     */
    protected void selectDropdownByText(By locator, String optionText) {
        try {
            WebElement dropdown = waitForElementClickable(locator);
            Select select = new Select(dropdown);
            select.selectByVisibleText(optionText);
            logger.info("Selected option '{}' from dropdown: {}", optionText, locator);
        } catch (Exception e) {
            logger.error("Failed to select option '{}' from dropdown: {}", optionText, locator, e);
            throw e;
        }
    }
    
    /**
     * Selects option from dropdown by value
     * @param locator dropdown locator
     * @param value option value
     */
    protected void selectDropdownByValue(By locator, String value) {
        try {
            WebElement dropdown = waitForElementClickable(locator);
            Select select = new Select(dropdown);
            select.selectByValue(value);
            logger.info("Selected option by value '{}' from dropdown: {}", value, locator);
        } catch (Exception e) {
            logger.error("Failed to select option by value '{}' from dropdown: {}", value, locator, e);
            throw e;
        }
    }
    
    /**
     * Scrolls element into view
     * @param locator element locator
     */
    protected void scrollToElement(By locator) {
        try {
            WebElement element = waitForElementVisible(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.debug("Scrolled to element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Performs mouse hover on element
     * @param locator element locator
     */
    protected void hoverOverElement(By locator) {
        try {
            WebElement element = waitForElementVisible(locator);
            actions.moveToElement(element).perform();
            logger.info("Hovered over element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to hover over element: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Double clicks on element
     * @param locator element locator
     */
    protected void doubleClickElement(By locator) {
        try {
            WebElement element = waitForElementClickable(locator);
            actions.doubleClick(element).perform();
            logger.info("Double clicked element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to double click element: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Gets all elements matching the locator
     * @param locator element locator
     * @return list of WebElements
     */
    protected List<WebElement> findElements(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            logger.debug("Found {} elements for locator: {}", elements.size(), locator);
            return elements;
        } catch (Exception e) {
            logger.error("Failed to find elements: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Waits for page to load completely
     */
    protected void waitForPageLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
            logger.debug("Page loaded completely");
        } catch (Exception e) {
            logger.warn("Page load wait timed out", e);
        }
    }
    
    /**
     * Gets current page title
     * @return page title
     */
    protected String getPageTitle() {
        try {
            String title = driver.getTitle();
            logger.debug("Current page title: {}", title);
            return title;
        } catch (Exception e) {
            logger.error("Failed to get page title", e);
            return "";
        }
    }
    
    /**
     * Highlights element for debugging (if enabled)
     * @param element WebElement to highlight
     */
    private void highlightElement(WebElement element) {
        if (ConfigReader.isElementHighlight()) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String originalStyle = element.getAttribute("style");
                js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
                Thread.sleep(100);
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
            } catch (Exception e) {
                logger.debug("Element highlighting failed", e);
            }
        }
    }
    
    /**
     * Waits for specified duration
     * @param seconds seconds to wait
     */
    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            logger.debug("Waited for {} seconds", seconds);
        } catch (InterruptedException e) {
            logger.warn("Wait interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}