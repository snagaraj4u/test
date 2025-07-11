package com.dotnet.automation.hooks;

import com.dotnet.automation.drivers.DriverManager;
import com.dotnet.automation.utils.ConfigReader;
import com.dotnet.automation.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cucumber hooks for setup and teardown operations
 */
public class CucumberHooks {
    
    private static final Logger logger = LogManager.getLogger(CucumberHooks.class);
    
    /**
     * Setup before each scenario
     * @param scenario Cucumber scenario
     */
    @Before
    public void setUp(Scenario scenario) {
        logger.info("Starting scenario: " + scenario.getName());
        logger.info("Scenario tags: " + scenario.getSourceTagNames());
        
        try {
            // Initialize WebDriver
            String browser = ConfigReader.getBrowser();
            DriverManager.initializeDriver(browser);
            
            // Navigate to application URL
            DriverManager.navigateToApplication();
            
            logger.info("Setup completed for scenario: " + scenario.getName());
            
        } catch (Exception e) {
            logger.error("Setup failed for scenario: " + scenario.getName(), e);
            throw new RuntimeException("Failed to setup scenario", e);
        }
    }
    
    /**
     * Teardown after each scenario
     * @param scenario Cucumber scenario
     */
    @After
    public void tearDown(Scenario scenario) {
        logger.info("Finishing scenario: " + scenario.getName());
        logger.info("Scenario status: " + scenario.getStatus());
        
        try {
            // Handle scenario result
            handleScenarioResult(scenario);
            
        } catch (Exception e) {
            logger.error("Error during scenario teardown", e);
        } finally {
            // Always quit driver
            DriverManager.quitDriver();
            logger.info("Teardown completed for scenario: " + scenario.getName());
        }
    }
    
    /**
     * Setup before scenarios with @smoke tag
     * @param scenario Cucumber scenario
     */
    @Before("@smoke")
    public void setUpSmoke(Scenario scenario) {
        logger.info("Setting up smoke test scenario: " + scenario.getName());
        // Additional setup for smoke tests if needed
    }
    
    /**
     * Setup before scenarios with @regression tag
     * @param scenario Cucumber scenario
     */
    @Before("@regression")
    public void setUpRegression(Scenario scenario) {
        logger.info("Setting up regression test scenario: " + scenario.getName());
        // Additional setup for regression tests if needed
    }
    
    /**
     * Setup before scenarios with @dotnet tag
     * @param scenario Cucumber scenario
     */
    @Before("@dotnet")
    public void setUpDotNet(Scenario scenario) {
        logger.info("Setting up .NET application test scenario: " + scenario.getName());
        
        // .NET specific setup
        if (ConfigReader.isWindowsAuthentication()) {
            logger.info("Windows authentication is enabled for .NET application");
            // Handle Windows authentication if needed
        }
        
        if (!ConfigReader.isSslCertificateValidation()) {
            logger.info("SSL certificate validation is disabled for .NET application");
        }
    }
    
    /**
     * Teardown after scenarios with @dotnet tag
     * @param scenario Cucumber scenario
     */
    @After("@dotnet")
    public void tearDownDotNet(Scenario scenario) {
        logger.info("Tearing down .NET application test scenario: " + scenario.getName());
        // .NET specific teardown if needed
    }
    
    /**
     * Handle scenario result based on status
     * @param scenario Cucumber scenario
     */
    private void handleScenarioResult(Scenario scenario) {
        switch (scenario.getStatus()) {
            case PASSED:
                logger.info("Scenario PASSED: " + scenario.getName());
                
                // Take screenshot on pass if configured
                if (ConfigReader.isScreenshotOnPass()) {
                    takeScreenshotAndAttach(scenario, "PASSED");
                }
                break;
                
            case FAILED:
                logger.error("Scenario FAILED: " + scenario.getName());
                
                // Take screenshot on failure if configured
                if (ConfigReader.isScreenshotOnFail()) {
                    takeScreenshotAndAttach(scenario, "FAILED");
                }
                break;
                
            case SKIPPED:
                logger.warn("Scenario SKIPPED: " + scenario.getName());
                break;
                
            default:
                logger.warn("Scenario status unknown: " + scenario.getName());
                break;
        }
    }
    
    /**
     * Take screenshot and attach to scenario
     * @param scenario Cucumber scenario
     * @param status Scenario status
     */
    private void takeScreenshotAndAttach(Scenario scenario, String status) {
        try {
            String screenshotName = status + "_" + scenario.getName().replaceAll(" ", "_");
            byte[] screenshot = ScreenshotUtils.takeScreenshotAsBytes();
            
            if (screenshot != null && screenshot.length > 0) {
                scenario.attach(screenshot, "image/png", screenshotName);
                logger.info("Screenshot attached to scenario: " + screenshotName);
            } else {
                logger.warn("Failed to capture screenshot for scenario: " + scenario.getName());
            }
            
        } catch (Exception e) {
            logger.error("Error taking screenshot for scenario: " + scenario.getName(), e);
        }
    }
}