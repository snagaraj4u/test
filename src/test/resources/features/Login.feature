Feature: User Authentication
  As a user of the .NET application
  I want to be able to login with my credentials
  So that I can access the application functionality

  Background:
    Given I am on the login page

  @smoke @dotnet @critical
  Scenario: Successful login with valid credentials
    When I enter valid username "testuser" and password "Test@123"
    And I click the login button
    Then I should be successfully logged in
    And I should see the welcome message

  @smoke @dotnet
  Scenario: Login page elements validation
    Then I should see all required login form elements
    And the username field should be enabled
    And the password field should be enabled
    And the login button should be enabled

  @regression @dotnet @negative
  Scenario: Login with invalid username
    When I enter username "invaliduser" and password "Test@123"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page

  @regression @dotnet @negative
  Scenario: Login with invalid password
    When I enter username "testuser" and password "wrongpassword"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page

  @regression @dotnet @negative
  Scenario: Login with empty credentials
    When I leave both username and password fields empty
    And I click the login button
    Then I should see a validation error message
    And I should remain on the login page

  @regression @dotnet @negative
  Scenario Outline: Login with various invalid credential combinations
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see an error message containing "<error_type>"
    And I should remain on the login page

    Examples:
      | username     | password      | error_type        |
      |              | Test@123      | username required |
      | testuser     |               | password required |
      | admin        | admin         | invalid           |
      | test@123     | test123       | authentication    |

  @sanity @dotnet
  Scenario: Clear login form fields
    When I enter username "testuser" and password "Test@123"
    And I clear all form fields
    Then both username and password fields should be empty

  @regression @dotnet
  Scenario: Login with remember me option
    When I enter username "testuser" and password "Test@123"
    And I select the remember me option
    And I click the login button
    Then I should be successfully logged in
    And the remember me option should be preserved

  @security @dotnet @negative
  Scenario: SQL injection attempt in login
    When I enter username "' OR '1'='1" and password "' OR '1'='1"
    And I click the login button
    Then I should see a security error message
    And the login attempt should be blocked

  @accessibility @dotnet
  Scenario: Login form accessibility validation
    Then the login form should be accessible
    And all form fields should have proper labels
    And the form should support keyboard navigation

  @performance @dotnet
  Scenario: Login response time validation
    When I enter valid username "testuser" and password "Test@123"
    And I click the login button
    Then the login process should complete within 5 seconds