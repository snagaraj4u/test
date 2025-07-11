package com.hybridframework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybridframework.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for JSON operations
 */
public class JsonUtils {
    
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Read JSON file and return as JsonNode
     * @param filePath JSON file path
     * @return JsonNode
     */
    public static JsonNode readJsonFile(String filePath) {
        try {
            File file = new File(filePath);
            JsonNode jsonNode = objectMapper.readTree(file);
            logger.info("JSON file read successfully: " + filePath);
            return jsonNode;
        } catch (IOException e) {
            logger.error("Failed to read JSON file: " + filePath, e);
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
    
    /**
     * Get test data from JSON file
     * @param filePath JSON file path
     * @param dataKey Data key in JSON
     * @return List of test data maps
     */
    public static List<Map<String, String>> getTestDataFromJson(String filePath, String dataKey) {
        List<Map<String, String>> testDataList = new ArrayList<>();
        
        try {
            JsonNode rootNode = readJsonFile(filePath);
            JsonNode dataNode = rootNode.get(dataKey);
            
            if (dataNode == null || !dataNode.isArray()) {
                logger.error("Data key '" + dataKey + "' not found or not an array in JSON file: " + filePath);
                return testDataList;
            }
            
            for (JsonNode node : dataNode) {
                Map<String, String> testData = new HashMap<>();
                
                // Convert JsonNode to Map
                node.fields().forEachRemaining(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue().asText();
                    testData.put(key, value);
                });
                
                testDataList.add(testData);
            }
            
            logger.info("Retrieved " + testDataList.size() + " test data records from JSON file");
            
        } catch (Exception e) {
            logger.error("Error getting test data from JSON file: " + filePath, e);
        }
        
        return testDataList;
    }
    
    /**
     * Get specific test data by test case name
     * @param filePath JSON file path
     * @param dataKey Data key in JSON
     * @param testCaseName Test case name
     * @return Test data map
     */
    public static Map<String, String> getTestDataByTestCase(String filePath, String dataKey, String testCaseName) {
        List<Map<String, String>> allTestData = getTestDataFromJson(filePath, dataKey);
        
        for (Map<String, String> testData : allTestData) {
            if (testData.containsKey("testCase") && testData.get("testCase").equals(testCaseName)) {
                logger.info("Test data found for test case: " + testCaseName);
                return testData;
            }
        }
        
        logger.warn("No test data found for test case: " + testCaseName);
        return new HashMap<>();
    }
    
    /**
     * Get login test data from JSON file
     * @return List of login test data
     */
    public static List<Map<String, String>> getLoginTestDataFromJson() {
        String filePath = ConfigReader.getTestDataPath() + FrameworkConstants.JSON_TEST_DATA;
        return getTestDataFromJson(filePath, "loginTestData");
    }
    
    /**
     * Get specific login test data by test case name
     * @param testCaseName Test case name
     * @return Login test data map
     */
    public static Map<String, String> getLoginTestDataByTestCase(String testCaseName) {
        String filePath = ConfigReader.getTestDataPath() + FrameworkConstants.JSON_TEST_DATA;
        return getTestDataByTestCase(filePath, "loginTestData", testCaseName);
    }
    
    /**
     * Write test data to JSON file
     * @param filePath JSON file path
     * @param data Data to write
     */
    public static void writeJsonFile(String filePath, Object data) {
        try {
            File file = new File(filePath);
            
            // Create parent directories if they don't exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
            logger.info("JSON file written successfully: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to write JSON file: " + filePath, e);
            throw new RuntimeException("Failed to write JSON file: " + filePath, e);
        }
    }
    
    /**
     * Convert JSON string to Map
     * @param jsonString JSON string
     * @return Map representation of JSON
     */
    public static Map<String, Object> jsonStringToMap(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Map.class);
        } catch (IOException e) {
            logger.error("Failed to convert JSON string to Map: " + jsonString, e);
            throw new RuntimeException("Failed to convert JSON string to Map", e);
        }
    }
    
    /**
     * Convert Map to JSON string
     * @param map Map to convert
     * @return JSON string representation
     */
    public static String mapToJsonString(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (IOException e) {
            logger.error("Failed to convert Map to JSON string", e);
            throw new RuntimeException("Failed to convert Map to JSON string", e);
        }
    }
    
    /**
     * Validate JSON structure
     * @param filePath JSON file path
     * @return True if JSON is valid
     */
    public static boolean isValidJson(String filePath) {
        try {
            readJsonFile(filePath);
            logger.info("JSON file is valid: " + filePath);
            return true;
        } catch (Exception e) {
            logger.error("JSON file is invalid: " + filePath, e);
            return false;
        }
    }
    
    /**
     * Get all test case names from JSON file
     * @param filePath JSON file path
     * @param dataKey Data key in JSON
     * @return List of test case names
     */
    public static List<String> getTestCaseNames(String filePath, String dataKey) {
        List<String> testCaseNames = new ArrayList<>();
        List<Map<String, String>> testDataList = getTestDataFromJson(filePath, dataKey);
        
        for (Map<String, String> testData : testDataList) {
            if (testData.containsKey("testCase")) {
                testCaseNames.add(testData.get("testCase"));
            }
        }
        
        logger.info("Retrieved " + testCaseNames.size() + " test case names from JSON file");
        return testCaseNames;
    }
}