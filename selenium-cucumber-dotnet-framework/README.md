# Selenium Cucumber Automation Framework for .NET Applications

A robust and scalable BDD (Behavior-Driven Development) automation framework built with Selenium WebDriver and Cucumber for testing .NET applications. This framework follows industry best practices and provides comprehensive test automation capabilities.

## 🚀 Framework Architecture

This framework implements a hybrid approach combining:
- **BDD with Cucumber** - Natural language test scenarios
- **Page Object Model** - Maintainable and reusable page components
- **Data-Driven Testing** - External test data management
- **Parallel Execution** - Faster test execution
- **Comprehensive Reporting** - Multiple reporting formats

## 📋 Features

### Core Capabilities
- ✅ Cross-browser testing (Chrome, Firefox, Edge, Safari)
- ✅ Headless execution support
- ✅ Parallel test execution
- ✅ BDD test scenarios with Cucumber
- ✅ Page Object Model implementation
- ✅ Comprehensive logging with Log4j2
- ✅ Screenshot capture on failures
- ✅ Multiple reporting formats (ExtentReports, Allure, HTML)
- ✅ Data-driven testing with JSON/Excel
- ✅ Configuration management
- ✅ CI/CD pipeline support

### .NET Application Specific
- ✅ Windows Authentication support
- ✅ SSL certificate handling
- ✅ .NET Framework and .NET Core compatibility
- ✅ Security testing capabilities
- ✅ Performance validation
- ✅ Accessibility testing support

## 🏗️ Project Structure

```
selenium-cucumber-dotnet-framework/
├── src/
│   ├── main/java/com/dotnet/automation/
│   │   ├── constants/          # Framework constants
│   │   ├── drivers/           # WebDriver management
│   │   ├── hooks/             # Cucumber hooks
│   │   ├── pages/             # Page Object classes
│   │   └── utils/             # Utility classes
│   └── test/
│       ├── java/com/dotnet/automation/
│       │   ├── runners/       # Test runners
│       │   └── stepdefinitions/ # Cucumber step definitions
│       └── resources/
│           ├── features/      # Cucumber feature files
│           ├── testdata/      # Test data files
│           └── config/        # Configuration files
├── test-output/               # Test reports
├── screenshots/              # Test screenshots
├── logs/                     # Application logs
├── pom.xml                   # Maven dependencies
└── README.md                 # Project documentation
```

## 🛠️ Prerequisites

- **Java 11+** - Required for framework execution
- **Maven 3.6+** - For dependency management and build
- **Chrome/Firefox/Edge** - Browsers for test execution
- **IDE** - IntelliJ IDEA or Eclipse for development

## ⚙️ Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd selenium-cucumber-dotnet-framework
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configure Framework
Edit `src/test/resources/config/config.properties`:
```properties
# Browser Configuration
browser=chrome
headless=true
maximize=true

# Application Configuration
app.url=https://your-dotnet-app.com
environment=qa

# .NET Specific Configuration
dotnet.framework.version=4.8
windows.authentication=false
ssl.certificate.validation=false
```

### 4. Verify Setup
```bash
mvn clean compile
```

## 🎯 Running Tests

### Command Line Execution

```bash
# Run all smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Run regression tests
mvn test -Dcucumber.filter.tags="@regression"

# Run specific browser
mvn test -Dbrowser=chrome

# Run in headless mode
mvn test -Dheadless=true

# Run with specific environment
mvn test -Denvironment=staging

# Run parallel tests
mvn test -Dparallel.execution=true -Dthread.count=3

# Run specific feature
mvn test -Dcucumber.features="src/test/resources/features/Login.feature"
```

### IDE Execution
1. Right-click on `CucumberTestRunner.java`
2. Select "Run as JUnit Test"
3. View results in IDE test runner

### Tag-based Execution
```bash
# Smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Critical tests
mvn test -Dcucumber.filter.tags="@critical"

# .NET specific tests
mvn test -Dcucumber.filter.tags="@dotnet"

# Negative test scenarios
mvn test -Dcucumber.filter.tags="@negative"

# Security tests
mvn test -Dcucumber.filter.tags="@security"
```

## 📊 Test Reporting

### Available Report Types

1. **ExtentReports** - Interactive HTML reports
   - Location: `test-output/ExtentReport.html`
   - Features: Screenshots, logs, charts, test categorization

2. **Cucumber HTML Reports** - Standard Cucumber reports
   - Location: `target/cucumber-reports/`
   - Features: Scenario details, step execution status

3. **Allure Reports** - Advanced reporting with trends
   ```bash
   mvn allure:serve
   ```

4. **JUnit XML Reports** - CI/CD integration
   - Location: `target/cucumber-reports/Cucumber.xml`

### Report Features
- 📸 Automatic screenshot capture on failures
- 📝 Detailed step-by-step execution logs
- 📈 Test execution trends and statistics
- 🏷️ Test categorization by tags
- ⏱️ Performance metrics and timing
- 📊 Pass/Fail ratios and success rates

## 🧪 Test Development

### Creating Feature Files
```gherkin
Feature: User Authentication
  As a user of the .NET application
  I want to be able to login with my credentials
  So that I can access the application functionality

  @smoke @dotnet @critical
  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter valid username "testuser" and password "Test@123"
    And I click the login button
    Then I should be successfully logged in
```

### Implementing Step Definitions
```java
@When("I enter valid username {string} and password {string}")
public void i_enter_valid_username_and_password(String username, String password) {
    logger.info("Entering valid credentials for user: {}", username);
    loginPage.enterUsername(username);
    loginPage.enterPassword(password);
}
```

### Creating Page Objects
```java
public class LoginPage extends BasePage {
    @FindBy(id = "userName")
    private WebElement usernameField;
    
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        enterText(usernameLocator, username);
    }
}
```

## 🔧 Configuration Options

### Browser Configuration
```properties
# Supported: chrome, firefox, edge, safari
browser=chrome
headless=true
maximize=true
```

### Timeout Settings
```properties
implicit.wait=10
explicit.wait=20
page.load.timeout=30
```

### .NET Application Settings
```properties
dotnet.framework.version=4.8
dotnet.core.version=6.0
application.type=web
windows.authentication=false
ssl.certificate.validation=false
```

### Parallel Execution
```properties
parallel.execution=true
thread.count=3
cucumber.execution.parallel.enabled=true
```

## � Test Data Management

### JSON Test Data
```json
{
  "loginTestData": [
    {
      "testCase": "ValidLogin",
      "username": "testuser",
      "password": "Test@123",
      "expectedResult": "Success"
    }
  ]
}
```

### Excel Test Data
- Supported formats: .xlsx, .xls
- Multiple sheets support
- Dynamic data reading capabilities

## 🔒 Security Testing

The framework includes built-in security testing capabilities:

- ✅ SQL Injection prevention testing
- ✅ XSS attack prevention
- ✅ Authentication bypass attempts
- ✅ Input validation testing
- ✅ Session management validation

## 🚀 CI/CD Integration

### Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test -Dcucumber.filter.tags="@smoke"'
            }
            post {
                always {
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'test-output',
                        reportFiles: 'ExtentReport.html',
                        reportName: 'Test Report'
                    ])
                }
            }
        }
    }
}
```

### GitHub Actions
```yaml
name: Automated Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          java-version: '11'
      - name: Run tests
        run: mvn clean test -Dheadless=true
```

## 🐛 Troubleshooting

### Common Issues

1. **Browser Driver Issues**
   - WebDriverManager handles automatic driver downloads
   - Ensure browser is installed and up-to-date

2. **Element Not Found**
   - Check element locators in page objects
   - Verify wait conditions and timeouts
   - Use explicit waits for dynamic elements

3. **Test Data Issues**
   - Verify JSON format validity
   - Check file paths in configuration
   - Ensure proper encoding (UTF-8)

4. **Headless Mode Issues**
   - Some elements may behave differently in headless mode
   - Increase wait times if needed
   - Use window size settings for consistent rendering

### Debug Mode
Enable debug logging in `log4j2.xml`:
```xml
<Logger name="com.dotnet.automation" level="DEBUG">
    <AppenderRef ref="Console"/>
    <AppenderRef ref="RollingFileAppender"/>
</Logger>
```

## 📚 Dependencies

### Core Testing Framework
- **Selenium WebDriver** 4.15.0 - Web automation
- **Cucumber** 7.14.0 - BDD testing framework
- **JUnit 5** 5.10.0 - Test execution engine

### Reporting & Utilities
- **ExtentReports** 5.1.1 - Advanced reporting
- **Allure** 2.24.0 - Test reporting with trends
- **AssertJ** 3.24.2 - Fluent assertions
- **Log4j2** 2.20.0 - Logging framework

### Data Management
- **Apache POI** 5.2.4 - Excel file handling
- **Jackson** 2.15.2 - JSON processing
- **WebDriverManager** 5.6.2 - Driver management

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

### Code Standards
- Follow Java naming conventions
- Add proper JavaDoc documentation
- Include unit tests for utilities
- Update feature files for new functionality
- Maintain logging consistency

## � License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For issues and questions:
1. Check the troubleshooting section
2. Review existing GitHub issues
3. Create a new issue with detailed description
4. Include logs and screenshots when applicable

## 🔄 Version History

- **v1.0.0** - Initial framework release
- **v1.1.0** - Added parallel execution support
- **v1.2.0** - Enhanced .NET application support
- **v1.3.0** - Integrated security testing capabilities

---

**Happy Testing! 🎯**

*Framework developed by QA Automation Team for enterprise .NET application testing*