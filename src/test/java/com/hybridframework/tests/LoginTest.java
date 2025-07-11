package com.hybridframework.tests;

import com.hybridframework.base.BaseTest;
import com.hybridframework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login test class containing various login test scenarios
 */
public class LoginTest extends BaseTest {
    
    private LoginPage loginPage;
    
    @Test(priority = 1, description = "Verify login page is loaded successfully", groups = {"smoke", "regression"})
    public void verifyLoginPageLoaded() {
        logInfo("Test: Verify login page is loaded");
        
        loginPage = new LoginPage();
        
        // Verify page title
        String expectedTitle = "Login";
        Assert.assertTrue(loginPage.verifyPageTitle(expectedTitle), "Login page title verification failed");
        
        // Verify login page elements are present
        Assert.assertTrue(loginPage.verifyLoginPageElements(), "Login page elements verification failed");
        
        // Verify login page is loaded
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page is not loaded");
        
        logPass("Login page loaded successfully");
    }
    
    @Test(priority = 2, description = "Verify valid login functionality", groups = {"smoke", "regression"})
    public void verifyValidLogin() {
        logInfo("Test: Verify valid login functionality");
        
        loginPage = new LoginPage();
        
        // Test data
        String validUsername = "testuser";
        String validPassword = "Test@123";
        
        // Perform login
        loginPage.login(validUsername, validPassword);
        
        // Wait for page to load
        loginPage.waitForSeconds(2);
        
        // Verify login success (this depends on the actual application behavior)
        // For demo purposes, we'll check if we're redirected or if no error message appears
        if (loginPage.isErrorMessageDisplayed()) {
            String errorMessage = loginPage.getErrorMessage();
            logFail("Login failed with error: " + errorMessage);
            Assert.fail("Valid login failed with error: " + errorMessage);
        } else {
            logPass("Valid login successful");
        }
    }
    
    @Test(priority = 3, description = "Verify invalid login with wrong username", groups = {"negative", "regression"})
    public void verifyInvalidLoginWithWrongUsername() {
        logInfo("Test: Verify invalid login with wrong username");
        
        loginPage = new LoginPage();
        
        // Test data
        String invalidUsername = "invaliduser";
        String validPassword = "Test@123";
        
        // Perform login
        loginPage.login(invalidUsername, validPassword);
        
        // Wait for error message
        loginPage.waitForSeconds(2);
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for invalid username");
        
        String errorMessage = loginPage.getErrorMessage();
        logInfo("Error message displayed: " + errorMessage);
        
        logPass("Invalid login with wrong username test passed");
    }
    
    @Test(priority = 4, description = "Verify invalid login with wrong password", groups = {"negative", "regression"})
    public void verifyInvalidLoginWithWrongPassword() {
        logInfo("Test: Verify invalid login with wrong password");
        
        loginPage = new LoginPage();
        
        // Test data
        String validUsername = "testuser";
        String invalidPassword = "wrongpassword";
        
        // Perform login
        loginPage.login(validUsername, invalidPassword);
        
        // Wait for error message
        loginPage.waitForSeconds(2);
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for invalid password");
        
        String errorMessage = loginPage.getErrorMessage();
        logInfo("Error message displayed: " + errorMessage);
        
        logPass("Invalid login with wrong password test passed");
    }
    
    @Test(priority = 5, description = "Verify login with empty username", groups = {"negative", "regression"})
    public void verifyLoginWithEmptyUsername() {
        logInfo("Test: Verify login with empty username");
        
        loginPage = new LoginPage();
        
        // Test data
        String emptyUsername = "";
        String validPassword = "Test@123";
        
        // Perform login
        loginPage.login(emptyUsername, validPassword);
        
        // Wait for error message
        loginPage.waitForSeconds(2);
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for empty username");
        
        String errorMessage = loginPage.getErrorMessage();
        logInfo("Error message displayed: " + errorMessage);
        
        logPass("Login with empty username test passed");
    }
    
    @Test(priority = 6, description = "Verify login with empty password", groups = {"negative", "regression"})
    public void verifyLoginWithEmptyPassword() {
        logInfo("Test: Verify login with empty password");
        
        loginPage = new LoginPage();
        
        // Test data
        String validUsername = "testuser";
        String emptyPassword = "";
        
        // Perform login
        loginPage.login(validUsername, emptyPassword);
        
        // Wait for error message
        loginPage.waitForSeconds(2);
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for empty password");
        
        String errorMessage = loginPage.getErrorMessage();
        logInfo("Error message displayed: " + errorMessage);
        
        logPass("Login with empty password test passed");
    }
    
    @Test(priority = 7, description = "Verify login with both empty fields", groups = {"negative", "regression"})
    public void verifyLoginWithBothEmptyFields() {
        logInfo("Test: Verify login with both empty fields");
        
        loginPage = new LoginPage();
        
        // Test data
        String emptyUsername = "";
        String emptyPassword = "";
        
        // Perform login
        loginPage.login(emptyUsername, emptyPassword);
        
        // Wait for error message
        loginPage.waitForSeconds(2);
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for both empty fields");
        
        String errorMessage = loginPage.getErrorMessage();
        logInfo("Error message displayed: " + errorMessage);
        
        logPass("Login with both empty fields test passed");
    }
    
    @Test(priority = 8, description = "Verify login form field properties", groups = {"functional", "regression"})
    public void verifyLoginFormFieldProperties() {
        logInfo("Test: Verify login form field properties");
        
        loginPage = new LoginPage();
        
        // Verify username field properties
        Assert.assertTrue(loginPage.isUsernameFieldEnabled(), "Username field should be enabled");
        String usernamePlaceholder = loginPage.getUsernameFieldPlaceholder();
        logInfo("Username field placeholder: " + usernamePlaceholder);
        
        // Verify password field properties
        Assert.assertTrue(loginPage.isPasswordFieldEnabled(), "Password field should be enabled");
        String passwordPlaceholder = loginPage.getPasswordFieldPlaceholder();
        logInfo("Password field placeholder: " + passwordPlaceholder);
        
        // Verify login button properties
        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled");
        
        // Verify new user button properties
        Assert.assertTrue(loginPage.isNewUserButtonEnabled(), "New user button should be enabled");
        
        logPass("Login form field properties verification passed");
    }
    
    @Test(priority = 9, description = "Verify login form field clearing functionality", groups = {"functional", "regression"})
    public void verifyLoginFormFieldClearing() {
        logInfo("Test: Verify login form field clearing functionality");
        
        loginPage = new LoginPage();
        
        // Test data
        String testUsername = "testuser";
        String testPassword = "testpass";
        
        // Enter data in fields
        loginPage.enterUsername(testUsername);
        loginPage.enterPassword(testPassword);
        
        // Verify data is entered
        String enteredUsername = loginPage.getCurrentUsername();
        String enteredPassword = loginPage.getCurrentPassword();
        
        Assert.assertEquals(enteredUsername, testUsername, "Username should be entered correctly");
        Assert.assertEquals(enteredPassword, testPassword, "Password should be entered correctly");
        
        // Clear fields
        loginPage.clearAllFields();
        
        // Verify fields are cleared
        String clearedUsername = loginPage.getCurrentUsername();
        String clearedPassword = loginPage.getCurrentPassword();
        
        Assert.assertTrue(clearedUsername.isEmpty(), "Username field should be cleared");
        Assert.assertTrue(clearedPassword.isEmpty(), "Password field should be cleared");
        
        logPass("Login form field clearing functionality verification passed");
    }
    
    @Test(priority = 10, description = "Verify login with special characters in username", groups = {"negative", "regression"})
    public void verifyLoginWithSpecialCharactersInUsername() {
        logInfo("Test: Verify login with special characters in username");
        
        loginPage = new LoginPage();
        
        // Test data with special characters
        String specialUsername = "user@#$%";
        String validPassword = "Test@123";
        
        // Perform login
        loginPage.login(specialUsername, validPassword);
        
        // Wait for response
        loginPage.waitForSeconds(2);
        
        // Verify error message is displayed (assuming special characters are not allowed)
        if (loginPage.isErrorMessageDisplayed()) {
            String errorMessage = loginPage.getErrorMessage();
            logInfo("Error message displayed: " + errorMessage);
            logPass("Login with special characters handled correctly");
        } else {
            logWarning("Login with special characters was accepted - verify if this is expected behavior");
        }
    }
    
    @Test(priority = 11, description = "Verify login with SQL injection attempt", groups = {"security", "regression"})
    public void verifyLoginWithSQLInjection() {
        logInfo("Test: Verify login with SQL injection attempt");
        
        loginPage = new LoginPage();
        
        // Test data with SQL injection
        String sqlInjectionUsername = "' OR '1'='1";
        String sqlInjectionPassword = "' OR '1'='1";
        
        // Perform login
        loginPage.login(sqlInjectionUsername, sqlInjectionPassword);
        
        // Wait for response
        loginPage.waitForSeconds(2);
        
        // Verify that SQL injection is prevented
        if (loginPage.isErrorMessageDisplayed()) {
            String errorMessage = loginPage.getErrorMessage();
            logInfo("Error message displayed: " + errorMessage);
            logPass("SQL injection attempt was blocked successfully");
        } else {
            logFail("SQL injection attempt was not blocked - security vulnerability detected");
            Assert.fail("SQL injection attempt was not blocked");
        }
    }
    
    @Test(priority = 12, description = "Verify login with very long username", groups = {"boundary", "regression"})
    public void verifyLoginWithVeryLongUsername() {
        logInfo("Test: Verify login with very long username");
        
        loginPage = new LoginPage();
        
        // Test data with very long username
        String longUsername = "a".repeat(1000); // 1000 characters
        String validPassword = "Test@123";
        
        // Perform login
        loginPage.login(longUsername, validPassword);
        
        // Wait for response
        loginPage.waitForSeconds(2);
        
        // Verify error message is displayed
        if (loginPage.isErrorMessageDisplayed()) {
            String errorMessage = loginPage.getErrorMessage();
            logInfo("Error message displayed: " + errorMessage);
            logPass("Long username boundary test passed");
        } else {
            logWarning("Long username was accepted - verify if this is expected behavior");
        }
    }
    
    @Test(priority = 13, description = "Verify multiple login attempts", groups = {"functional", "regression"})
    public void verifyMultipleLoginAttempts() {
        logInfo("Test: Verify multiple login attempts");
        
        loginPage = new LoginPage();
        
        // Test data
        String invalidUsername = "invaliduser";
        String invalidPassword = "invalidpass";
        
        // Perform multiple login attempts
        for (int i = 1; i <= 3; i++) {
            logInfo("Login attempt " + i);
            loginPage.login(invalidUsername, invalidPassword);
            loginPage.waitForSeconds(2);
            
            if (loginPage.isErrorMessageDisplayed()) {
                String errorMessage = loginPage.getErrorMessage();
                logInfo("Attempt " + i + " - Error message: " + errorMessage);
            }
            
            // Clear fields for next attempt
            loginPage.clearAllFields();
        }
        
        logPass("Multiple login attempts test completed");
    }
}