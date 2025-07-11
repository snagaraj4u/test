# Selenium TestNG Hybrid Framework

A comprehensive, robust, and flexible Selenium TestNG hybrid framework designed for testing .NET applications. This framework follows industry best practices and provides a solid foundation for scalable test automation.

## 🚀 Framework Features

### Core Features
- **Hybrid Framework**: Combines Data-Driven, Keyword-Driven, and Page Object Model approaches
- **Cross-Browser Support**: Chrome, Firefox, Edge, Safari
- **Parallel Execution**: TestNG parallel execution support
- **Comprehensive Reporting**: ExtentReports with screenshots and detailed logging
- **Data-Driven Testing**: Excel and JSON data support
- **Configuration Management**: Centralized configuration with properties files
- **Robust Logging**: Log4j2 integration with multiple appenders
- **Screenshot Handling**: Automatic screenshot capture on test failures
- **Exception Handling**: Comprehensive error handling and recovery

### Framework Components
- **Base Classes**: BaseTest, BasePage for common functionality
- **Page Object Model**: Clean separation of page elements and actions
- **Utility Classes**: WebDriver utilities, Excel/JSON utilities, Configuration readers
- **Test Listeners**: TestNG listeners for test execution events
- **Driver Management**: Thread-safe WebDriver management
- **Reporting**: ExtentReports with customizable themes and detailed logs

## 📁 Project Structure

```
selenium-testng-hybrid-framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hybridframework/
│   │   │           ├── base/
│   │   │           │   ├── BaseTest.java
│   │   │           │   └── BasePage.java
│   │   │           ├── constants/
│   │   │           │   └── FrameworkConstants.java
│   │   │           ├── drivers/
│   │   │           │   └── DriverManager.java
│   │   │           ├── listeners/
│   │   │           │   ├── TestListener.java
│   │   │           │   └── ExtentReportListener.java
│   │   │           ├── pages/
│   │   │           │   └── LoginPage.java
│   │   │           └── utils/
│   │   │               ├── ConfigReader.java
│   │   │               ├── WebDriverUtils.java
│   │   │               ├── ExtentReportManager.java
│   │   │               ├── ExcelUtils.java
│   │   │               └── JsonUtils.java
│   │   └── resources/
│   │       ├── config/
│   │       │   └── config.properties
│   │       ├── log4j2/
│   │       │   └── log4j2.xml
│   │       └── testdata/
│   │           └── TestData.json
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── hybridframework/
│       │           └── tests/
│       │               └── LoginTest.java
│       └── resources/
│           └── testng/
├── test-output/
├── screenshots/
├── logs/
├── pom.xml
├── testng.xml
└── README.md
```

## 🛠️ Prerequisites

- **Java 11+**: Required for running the framework
- **Maven 3.6+**: For dependency management and build
- **Chrome/Firefox/Edge**: Browsers for test execution
- **Eclipse/IntelliJ**: IDE for development (Eclipse recommended as per requirements)

## 🔧 Setup Instructions

### 1. Clone/Download the Framework
```bash
git clone <repository-url>
cd selenium-testng-hybrid-framework
```

### 2. Import into Eclipse
1. Open Eclipse IDE
2. Go to File > Import > Existing Maven Projects
3. Browse to the framework directory
4. Select the project and click Finish
5. Wait for Maven to download dependencies

### 3. Configure the Framework
Edit `src/main/resources/config/config.properties` to set your preferences:
```properties
# Browser Configuration
browser=chrome
headless=false
maximize=true

# Application Configuration
app.url=https://demoqa.com/login
environment=qa

# Test Data Configuration
test.data.path=src/main/resources/testdata/

# Reporting Configuration
extent.report.path=test-output/ExtentReport.html
```

### 4. Install Dependencies
```bash
mvn clean install
```

## 🎯 Running Tests

### Running Tests from Eclipse
1. Right-click on `testng.xml`
2. Select "Run As" > "TestNG Suite"
3. View results in Eclipse TestNG plugin

### Running Tests from Command Line
```bash
# Run all tests
mvn test

# Run specific test suite
mvn test -Dtest=LoginTest

# Run with specific browser
mvn test -Dbrowser=chrome

# Run in headless mode
mvn test -Dheadless=true

# Run with specific environment
mvn test -Denvironment=qa
```

### Running Specific Test Groups
```bash
# Run smoke tests
mvn test -Dgroups=smoke

# Run regression tests
mvn test -Dgroups=regression

# Run negative tests
mvn test -Dgroups=negative
```

## 📊 Test Reporting

### ExtentReports
- **Location**: `test-output/ExtentReport.html`
- **Features**: 
  - Detailed test execution results
  - Screenshots on failures
  - System information
  - Test categorization
  - Interactive charts and graphs

### Logs
- **Location**: `logs/`
- **Files**:
  - `application.log`: General application logs
  - `test-execution.log`: Test execution logs with rotation

### Screenshots
- **Location**: `screenshots/`
- **Naming**: `Screenshot_TestName_Timestamp.png`
- **Trigger**: Automatic on test failures (configurable)

## 🧪 Sample Test Cases

The framework includes comprehensive login test scenarios:

1. **Positive Tests**:
   - Valid login with correct credentials
   - Login page element verification

2. **Negative Tests**:
   - Invalid username/password
   - Empty field validations
   - Special character handling

3. **Security Tests**:
   - SQL injection prevention
   - XSS attack prevention

4. **Boundary Tests**:
   - Long input values
   - Character limits

## 🔧 Configuration Options

### Browser Configuration
```properties
# Supported browsers: chrome, firefox, edge, safari
browser=chrome
headless=false
maximize=true
```

### Timeout Configuration
```properties
implicit.wait=10
explicit.wait=20
page.load.timeout=30
```

### Screenshot Configuration
```properties
screenshot.on.pass=false
screenshot.on.fail=true
screenshot.path=screenshots/
```

### Parallel Execution
```properties
parallel.execution=false
thread.count=2
```

## 📈 Data-Driven Testing

### JSON Data Format
```json
{
  "loginTestData": [
    {
      "testCase": "ValidLogin",
      "username": "testuser",
      "password": "Test@123",
      "expectedResult": "Success",
      "description": "Valid login test"
    }
  ]
}
```

### Excel Data Format
- **Sheet Name**: Login
- **Columns**: TestCase, Username, Password, ExpectedResult, Description

### Usage in Tests
```java
// Get test data from JSON
Map<String, String> testData = JsonUtils.getLoginTestDataByTestCase("ValidLogin");
String username = testData.get("username");
String password = testData.get("password");
```

## 🎨 Customization

### Adding New Page Objects
1. Create new page class extending `BasePage`
2. Define page elements using `@FindBy` annotations
3. Implement page-specific methods
4. Add verification methods

### Adding New Test Classes
1. Create new test class extending `BaseTest`
2. Use `@Test` annotations with groups and descriptions
3. Implement test methods with proper assertions
4. Add test data if needed

### Custom Utilities
1. Create utility classes in `utils` package
2. Use static methods for common operations
3. Add proper logging and error handling

## 🔒 Best Practices

### Code Quality
- Follow Page Object Model pattern
- Use meaningful method and variable names
- Add proper comments and documentation
- Implement proper exception handling

### Test Design
- Keep tests independent and atomic
- Use data-driven approach for test data
- Implement proper assertions
- Add descriptive test names and descriptions

### Maintenance
- Regular dependency updates
- Code review and refactoring
- Test data management
- Continuous integration setup

## 🐛 Troubleshooting

### Common Issues

1. **Browser Driver Issues**:
   - WebDriverManager handles driver downloads automatically
   - Ensure browser is installed and up-to-date

2. **Element Not Found**:
   - Check element locators
   - Verify page load timing
   - Use explicit waits

3. **Test Data Issues**:
   - Verify test data file paths
   - Check data format and structure
   - Ensure proper encoding

4. **Reporting Issues**:
   - Check file permissions
   - Verify output directory exists
   - Check disk space

### Debug Mode
Enable debug logging by updating `log4j2.xml`:
```xml
<Logger name="com.hybridframework" level="DEBUG" additivity="false">
    <AppenderRef ref="Console"/>
    <AppenderRef ref="RollingFileAppender"/>
</Logger>
```

## 📚 Dependencies

### Core Dependencies
- **Selenium WebDriver**: 4.15.0
- **TestNG**: 7.8.0
- **WebDriverManager**: 5.6.2
- **ExtentReports**: 5.1.1

### Utility Dependencies
- **Apache POI**: 5.2.4 (Excel handling)
- **Jackson**: 2.15.2 (JSON handling)
- **Log4j2**: 2.20.0 (Logging)
- **Commons IO**: 2.11.0 (File operations)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new features
5. Ensure all tests pass
6. Submit a pull request

## 📜 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For issues and questions:
1. Check the troubleshooting section
2. Review existing issues
3. Create a new issue with detailed description
4. Provide logs and screenshots

## 🔄 Version History

- **v1.0.0**: Initial release with core framework features
- **v1.1.0**: Added data-driven testing support
- **v1.2.0**: Enhanced reporting and logging
- **v1.3.0**: Added parallel execution support

---

**Happy Testing! 🚀**