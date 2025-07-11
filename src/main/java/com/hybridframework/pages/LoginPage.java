package com.hybridframework.pages;

import com.hybridframework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login page object model
 */
public class LoginPage extends BasePage {
    
    // Page elements using @FindBy annotations
    @FindBy(id = "userName")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login")
    private WebElement loginButton;
    
    @FindBy(id = "newUser")
    private WebElement newUserButton;
    
    @FindBy(className = "mr-2")
    private WebElement loginPageTitle;
    
    @FindBy(id = "output")
    private WebElement errorMessage;
    
    // Alternative locators using By class
    private final By usernameFieldLocator = By.id("userName");
    private final By passwordFieldLocator = By.id("password");
    private final By loginButtonLocator = By.id("login");
    private final By newUserButtonLocator = By.id("newUser");
    private final By loginPageTitleLocator = By.className("mr-2");
    private final By errorMessageLocator = By.id("output");
    
    /**
     * Constructor
     */
    public LoginPage() {
        super();
    }
    
    /**
     * Check if login page is loaded
     * @return True if login page is loaded
     */
    public boolean isLoginPageLoaded() {
        logger.info("Checking if login page is loaded");
        boolean isLoaded = isElementDisplayed(loginPageTitleLocator) && 
                          isElementDisplayed(usernameFieldLocator) && 
                          isElementDisplayed(passwordFieldLocator) && 
                          isElementDisplayed(loginButtonLocator);
        
        if (isLoaded) {
            logger.info("Login page is loaded successfully");
        } else {
            logger.error("Login page is not loaded");
        }
        
        return isLoaded;
    }
    
    /**
     * Enter username
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        logger.info("Entering username: " + username);
        sendKeys(usernameFieldLocator, username);
    }
    
    /**
     * Enter password
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        logger.info("Entering password");
        sendKeys(passwordFieldLocator, password);
    }
    
    /**
     * Click login button
     */
    public void clickLoginButton() {
        logger.info("Clicking login button");
        clickElement(loginButtonLocator);
    }
    
    /**
     * Click new user button
     */
    public void clickNewUserButton() {
        logger.info("Clicking new user button");
        clickElement(newUserButtonLocator);
    }
    
    /**
     * Get login page title
     * @return Login page title
     */
    public String getLoginPageTitle() {
        logger.info("Getting login page title");
        return getText(loginPageTitleLocator);
    }
    
    /**
     * Get error message
     * @return Error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(errorMessageLocator);
    }
    
    /**
     * Check if error message is displayed
     * @return True if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        return isElementDisplayed(errorMessageLocator);
    }
    
    /**
     * Clear username field
     */
    public void clearUsername() {
        logger.info("Clearing username field");
        waitForElementToBeVisible(usernameFieldLocator);
        usernameField.clear();
    }
    
    /**
     * Clear password field
     */
    public void clearPassword() {
        logger.info("Clearing password field");
        waitForElementToBeVisible(passwordFieldLocator);
        passwordField.clear();
    }
    
    /**
     * Clear all fields
     */
    public void clearAllFields() {
        logger.info("Clearing all fields");
        clearUsername();
        clearPassword();
    }
    
    /**
     * Get username field placeholder
     * @return Username field placeholder text
     */
    public String getUsernameFieldPlaceholder() {
        logger.info("Getting username field placeholder");
        return getAttribute(usernameFieldLocator, "placeholder");
    }
    
    /**
     * Get password field placeholder
     * @return Password field placeholder text
     */
    public String getPasswordFieldPlaceholder() {
        logger.info("Getting password field placeholder");
        return getAttribute(passwordFieldLocator, "placeholder");
    }
    
    /**
     * Check if username field is enabled
     * @return True if username field is enabled
     */
    public boolean isUsernameFieldEnabled() {
        logger.info("Checking if username field is enabled");
        return isElementEnabled(usernameFieldLocator);
    }
    
    /**
     * Check if password field is enabled
     * @return True if password field is enabled
     */
    public boolean isPasswordFieldEnabled() {
        logger.info("Checking if password field is enabled");
        return isElementEnabled(passwordFieldLocator);
    }
    
    /**
     * Check if login button is enabled
     * @return True if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        logger.info("Checking if login button is enabled");
        return isElementEnabled(loginButtonLocator);
    }
    
    /**
     * Check if new user button is enabled
     * @return True if new user button is enabled
     */
    public boolean isNewUserButtonEnabled() {
        logger.info("Checking if new user button is enabled");
        return isElementEnabled(newUserButtonLocator);
    }
    
    /**
     * Perform login with username and password
     * @param username Username
     * @param password Password
     */
    public void login(String username, String password) {
        logger.info("Performing login with username: " + username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    /**
     * Perform login and wait for page to load
     * @param username Username
     * @param password Password
     */
    public void loginAndWait(String username, String password) {
        logger.info("Performing login and waiting for page to load");
        login(username, password);
        waitForSeconds(2); // Wait for page to load
    }
    
    /**
     * Verify login page elements
     * @return True if all elements are present
     */
    public boolean verifyLoginPageElements() {
        logger.info("Verifying login page elements");
        boolean allElementsPresent = true;
        
        if (!verifyElementIsDisplayed(usernameFieldLocator)) {
            allElementsPresent = false;
        }
        
        if (!verifyElementIsDisplayed(passwordFieldLocator)) {
            allElementsPresent = false;
        }
        
        if (!verifyElementIsDisplayed(loginButtonLocator)) {
            allElementsPresent = false;
        }
        
        if (!verifyElementIsDisplayed(newUserButtonLocator)) {
            allElementsPresent = false;
        }
        
        return allElementsPresent;
    }
    
    /**
     * Verify login page title
     * @param expectedTitle Expected title
     * @return True if title matches
     */
    public boolean verifyLoginPageTitle(String expectedTitle) {
        logger.info("Verifying login page title: " + expectedTitle);
        return verifyElementText(loginPageTitleLocator, expectedTitle);
    }
    
    /**
     * Verify error message
     * @param expectedErrorMessage Expected error message
     * @return True if error message matches
     */
    public boolean verifyErrorMessage(String expectedErrorMessage) {
        logger.info("Verifying error message: " + expectedErrorMessage);
        return verifyElementText(errorMessageLocator, expectedErrorMessage);
    }
    
    /**
     * Wait for login page to load
     */
    public void waitForLoginPageToLoad() {
        logger.info("Waiting for login page to load");
        waitForElementToBeVisible(loginPageTitleLocator);
        waitForElementToBeVisible(usernameFieldLocator);
        waitForElementToBeVisible(passwordFieldLocator);
        waitForElementToBeVisible(loginButtonLocator);
    }
    
    /**
     * Wait for error message to appear
     */
    public void waitForErrorMessage() {
        logger.info("Waiting for error message to appear");
        waitForElementToBeVisible(errorMessageLocator);
    }
    
    /**
     * Get current entered username
     * @return Current username value
     */
    public String getCurrentUsername() {
        logger.info("Getting current username value");
        return getAttribute(usernameFieldLocator, "value");
    }
    
    /**
     * Get current entered password
     * @return Current password value
     */
    public String getCurrentPassword() {
        logger.info("Getting current password value");
        return getAttribute(passwordFieldLocator, "value");
    }
    
    /**
     * Wait for specified seconds (public method for test access)
     * @param seconds Number of seconds to wait
     */
    public void waitForSeconds(int seconds) {
        logger.info("Waiting for " + seconds + " seconds");
        super.waitForSeconds(seconds);
    }
}