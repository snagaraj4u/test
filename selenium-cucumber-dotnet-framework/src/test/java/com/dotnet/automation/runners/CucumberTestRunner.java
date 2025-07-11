package com.dotnet.automation.runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Cucumber test runner for BDD test execution
 * Configures test execution parameters and reporting
 * 
 * @author QA Automation Team
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.dotnet.automation.stepdefinitions,com.dotnet.automation.hooks")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@smoke or @regression")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = 
    "pretty," +
    "html:target/cucumber-reports," +
    "json:target/cucumber-reports/Cucumber.json," +
    "junit:target/cucumber-reports/Cucumber.xml," +
    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
@ConfigurationParameter(key = Constants.SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME, value = "false")
public class CucumberTestRunner {
    
    /**
     * Main test runner class for Cucumber BDD tests
     * 
     * Configuration includes:
     * - Feature files location: src/test/resources/features
     * - Step definitions package: com.dotnet.automation.stepdefinitions
     * - Hooks package: com.dotnet.automation.hooks
     * - Tags filter: @smoke or @regression
     * - Multiple report formats: HTML, JSON, JUnit XML, ExtentReports
     * - Snippet style: camelCase
     * - Dry run: disabled
     */
}