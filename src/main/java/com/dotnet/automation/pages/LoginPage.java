package com.dotnet.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login page object for .NET application authentication
 * Handles various authentication scenarios including Windows Auth
 * 
 * @author QA Automation Team
 */
public class LoginPage extends BasePage {
    
    // Page elements using FindBy annotations
    @FindBy(id = "userName")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login")
    private WebElement loginButton;
    
    @FindBy(xpath = "//button[contains(text(), 'Sign In')]")
    private WebElement signInButton;
    
    @FindBy(className = "alert-danger")
    private WebElement errorMessage;
    
    @FindBy(id = "rememberMe")
    private WebElement rememberMeCheckbox;
    
    @FindBy(linkText = "Forgot Password?")
    private WebElement forgotPasswordLink;
    
    @FindBy(className = "login-title")
    private WebElement pageTitle;
    
    // Locators for programmatic access
    private final By usernameLocator = By.id("userName");
    private final By passwordLocator = By.id("password");
    private final By loginButtonLocator = By.id("login");
    private final By errorMessageLocator = By.className("alert-danger");
    private final By loadingSpinnerLocator = By.className("loading-spinner");
    
    /**
     * Default constructor
     */
    public LoginPage() {
        super();
        waitForPageLoad();
    }
    
    /**
     * Enters username in the login form
     * @param username user identifier
     */
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        enterText(usernameLocator, username);
    }
    
    /**
     * Enters password in the login form
     * @param password user credential
     */
    public void enterPassword(String password) {
        logger.info("Entering password for authentication");
        enterText(passwordLocator, password);
    }
    
    /**
     * Clicks the login button to submit credentials
     */
    public void clickLoginButton() {
        logger.info("Submitting login credentials");
        clickElement(loginButtonLocator);
        waitForLoginProcessing();
    }
    
    /**
     * Performs complete login flow
     * @param username user identifier
     * @param password user credential
     */
    public void login(String username, String password) {
        logger.info("Performing login for user: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    /**
     * Performs login with remember me option
     * @param username user identifier
     * @param password user credential
     * @param rememberMe whether to remember credentials
     */
    public void loginWithRememberMe(String username, String password, boolean rememberMe) {
        logger.info("Performing login with remember me option: {}", rememberMe);
        enterUsername(username);
        enterPassword(password);
        
        if (rememberMe && isElementDisplayed(By.id("rememberMe"))) {
            clickElement(By.id("rememberMe"));
            logger.info("Remember me option selected");
        }
        
        clickLoginButton();
    }
    
    /**
     * Checks if login page is loaded properly
     * @return true if page is loaded
     */
    public boolean isLoginPageLoaded() {
        try {
            boolean usernameVisible = isElementDisplayed(usernameLocator);
            boolean passwordVisible = isElementDisplayed(passwordLocator);
            boolean loginButtonVisible = isElementDisplayed(loginButtonLocator);
            
            boolean pageLoaded = usernameVisible && passwordVisible && loginButtonVisible;
            logger.info("Login page load status: {}", pageLoaded);
            return pageLoaded;
            
        } catch (Exception e) {
            logger.error("Error checking login page load status", e);
            return false;
        }
    }
    
    /**
     * Gets error message displayed on failed login
     * @return error message text
     */
    public String getErrorMessage() {
        try {
            if (isElementDisplayed(errorMessageLocator)) {
                String error = getElementText(errorMessageLocator);
                logger.info("Error message displayed: {}", error);
                return error;
            }
            return "";
        } catch (Exception e) {
            logger.error("Failed to retrieve error message", e);
            return "";
        }
    }
    
    /**
     * Checks if error message is displayed
     * @return true if error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        boolean errorVisible = isElementDisplayed(errorMessageLocator);
        logger.debug("Error message visibility: {}", errorVisible);
        return errorVisible;
    }
    
    /**
     * Clears username field
     */
    public void clearUsername() {
        logger.info("Clearing username field");
        WebElement element = waitForElementVisible(usernameLocator);
        element.clear();
    }
    
    /**
     * Clears password field
     */
    public void clearPassword() {
        logger.info("Clearing password field");
        WebElement element = waitForElementVisible(passwordLocator);
        element.clear();
    }
    
    /**
     * Clears all form fields
     */
    public void clearAllFields() {
        logger.info("Clearing all login form fields");
        clearUsername();
        clearPassword();
    }
    
    /**
     * Gets current username field value
     * @return username field content
     */
    public String getCurrentUsername() {
        return getElementAttribute(usernameLocator, "value");
    }
    
    /**
     * Gets current password field value
     * @return password field content
     */
    public String getCurrentPassword() {
        return getElementAttribute(passwordLocator, "value");
    }
    
    /**
     * Checks if username field is enabled
     * @return true if field is enabled
     */
    public boolean isUsernameFieldEnabled() {
        return isElementEnabled(usernameLocator);
    }
    
    /**
     * Checks if password field is enabled
     * @return true if field is enabled
     */
    public boolean isPasswordFieldEnabled() {
        return isElementEnabled(passwordLocator);
    }
    
    /**
     * Checks if login button is enabled
     * @return true if button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return isElementEnabled(loginButtonLocator);
    }
    
    /**
     * Clicks forgot password link
     */
    public void clickForgotPasswordLink() {
        if (isElementDisplayed(By.linkText("Forgot Password?"))) {
            logger.info("Clicking forgot password link");
            clickElement(By.linkText("Forgot Password?"));
        } else {
            logger.warn("Forgot password link not found");
        }
    }
    
    /**
     * Gets page title text
     * @return page title
     */
    public String getPageTitleText() {
        try {
            if (isElementDisplayed(By.className("login-title"))) {
                return getElementText(By.className("login-title"));
            }
            return getPageTitle();
        } catch (Exception e) {
            logger.error("Failed to get page title", e);
            return "";
        }
    }
    
    /**
     * Waits for login processing to complete
     */
    private void waitForLoginProcessing() {
        try {
            // Wait for loading spinner to disappear if present
            if (isElementDisplayed(loadingSpinnerLocator)) {
                wait.until(driver -> !isElementDisplayed(loadingSpinnerLocator));
                logger.debug("Login processing completed");
            }
            
            // Small wait for page transition
            waitForSeconds(2);
            
        } catch (Exception e) {
            logger.debug("No loading indicator found, proceeding");
        }
    }
    
    /**
     * Validates login form elements are present
     * @return true if all required elements are present
     */
    public boolean validateLoginFormElements() {
        logger.info("Validating login form elements presence");
        
        boolean usernamePresent = isElementDisplayed(usernameLocator);
        boolean passwordPresent = isElementDisplayed(passwordLocator);
        boolean loginButtonPresent = isElementDisplayed(loginButtonLocator);
        
        boolean allElementsPresent = usernamePresent && passwordPresent && loginButtonPresent;
        
        logger.info("Login form validation result: {}", allElementsPresent);
        return allElementsPresent;
    }
}