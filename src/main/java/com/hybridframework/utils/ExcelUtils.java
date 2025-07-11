package com.hybridframework.utils;

import com.hybridframework.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for Excel operations
 */
public class ExcelUtils {
    
    private static final Logger logger = LogManager.getLogger(ExcelUtils.class);
    private static Workbook workbook;
    private static Sheet worksheet;
    
    /**
     * Open Excel file
     * @param filePath Excel file path
     * @param sheetName Sheet name
     */
    public static void openExcelFile(String filePath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            worksheet = workbook.getSheet(sheetName);
            logger.info("Excel file opened successfully: " + filePath + " - Sheet: " + sheetName);
        } catch (IOException e) {
            logger.error("Failed to open Excel file: " + filePath, e);
            throw new RuntimeException("Failed to open Excel file: " + filePath, e);
        }
    }
    
    /**
     * Close Excel file
     */
    public static void closeExcelFile() {
        try {
            if (workbook != null) {
                workbook.close();
                logger.info("Excel file closed successfully");
            }
        } catch (IOException e) {
            logger.error("Failed to close Excel file", e);
        }
    }
    
    /**
     * Get total number of rows in the sheet
     * @return Total number of rows
     */
    public static int getRowCount() {
        if (worksheet == null) {
            logger.error("Worksheet is null. Please open Excel file first.");
            return 0;
        }
        int rowCount = worksheet.getLastRowNum() + 1;
        logger.info("Total rows in sheet: " + rowCount);
        return rowCount;
    }
    
    /**
     * Get total number of columns in the sheet
     * @return Total number of columns
     */
    public static int getColumnCount() {
        if (worksheet == null) {
            logger.error("Worksheet is null. Please open Excel file first.");
            return 0;
        }
        
        if (worksheet.getRow(0) == null) {
            logger.error("First row is null.");
            return 0;
        }
        
        int columnCount = worksheet.getRow(0).getLastCellNum();
        logger.info("Total columns in sheet: " + columnCount);
        return columnCount;
    }
    
    /**
     * Get cell data as string
     * @param rowNum Row number (0-based)
     * @param colNum Column number (0-based)
     * @return Cell data as string
     */
    public static String getCellData(int rowNum, int colNum) {
        if (worksheet == null) {
            logger.error("Worksheet is null. Please open Excel file first.");
            return "";
        }
        
        try {
            Row row = worksheet.getRow(rowNum);
            if (row == null) {
                logger.warn("Row " + rowNum + " is null");
                return "";
            }
            
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                logger.warn("Cell at row " + rowNum + ", column " + colNum + " is null");
                return "";
            }
            
            String cellData = getCellValueAsString(cell);
            logger.debug("Cell data at row " + rowNum + ", column " + colNum + ": " + cellData);
            return cellData;
        } catch (Exception e) {
            logger.error("Error getting cell data at row " + rowNum + ", column " + colNum, e);
            return "";
        }
    }
    
    /**
     * Get cell data by column name
     * @param rowNum Row number (0-based)
     * @param columnName Column name
     * @return Cell data as string
     */
    public static String getCellData(int rowNum, String columnName) {
        if (worksheet == null) {
            logger.error("Worksheet is null. Please open Excel file first.");
            return "";
        }
        
        int columnIndex = getColumnIndex(columnName);
        if (columnIndex == -1) {
            logger.error("Column '" + columnName + "' not found");
            return "";
        }
        
        return getCellData(rowNum, columnIndex);
    }
    
    /**
     * Set cell data
     * @param rowNum Row number (0-based)
     * @param colNum Column number (0-based)
     * @param data Data to set
     */
    public static void setCellData(int rowNum, int colNum, String data) {
        if (worksheet == null) {
            logger.error("Worksheet is null. Please open Excel file first.");
            return;
        }
        
        try {
            Row row = worksheet.getRow(rowNum);
            if (row == null) {
                row = worksheet.createRow(rowNum);
            }
            
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            
            cell.setCellValue(data);
            logger.debug("Cell data set at row " + rowNum + ", column " + colNum + ": " + data);
        } catch (Exception e) {
            logger.error("Error setting cell data at row " + rowNum + ", column " + colNum, e);
        }
    }
    
    /**
     * Save Excel file
     * @param filePath File path to save
     */
    public static void saveExcelFile(String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            logger.info("Excel file saved successfully: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to save Excel file: " + filePath, e);
            throw new RuntimeException("Failed to save Excel file: " + filePath, e);
        }
    }
    
    /**
     * Get column index by column name
     * @param columnName Column name
     * @return Column index (-1 if not found)
     */
    private static int getColumnIndex(String columnName) {
        if (worksheet == null) {
            logger.error("Worksheet is null. Please open Excel file first.");
            return -1;
        }
        
        Row headerRow = worksheet.getRow(0);
        if (headerRow == null) {
            logger.error("Header row is null");
            return -1;
        }
        
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && getCellValueAsString(cell).equals(columnName)) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Get cell value as string
     * @param cell Cell object
     * @return Cell value as string
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
    
    /**
     * Get all data from sheet as List of Maps
     * @param filePath Excel file path
     * @param sheetName Sheet name
     * @return List of Maps containing all data
     */
    public static List<Map<String, String>> getAllData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        
        try {
            openExcelFile(filePath, sheetName);
            
            int rowCount = getRowCount();
            int colCount = getColumnCount();
            
            if (rowCount <= 1) {
                logger.warn("No data rows found in sheet: " + sheetName);
                return data;
            }
            
            // Get header row
            List<String> headers = new ArrayList<>();
            for (int col = 0; col < colCount; col++) {
                headers.add(getCellData(0, col));
            }
            
            // Get data rows
            for (int row = 1; row < rowCount; row++) {
                Map<String, String> rowData = new HashMap<>();
                for (int col = 0; col < colCount; col++) {
                    String header = headers.get(col);
                    String value = getCellData(row, col);
                    rowData.put(header, value);
                }
                data.add(rowData);
            }
            
            logger.info("Retrieved " + data.size() + " data rows from sheet: " + sheetName);
            
        } catch (Exception e) {
            logger.error("Error getting all data from Excel", e);
        } finally {
            closeExcelFile();
        }
        
        return data;
    }
    
    /**
     * Get test data by test case name
     * @param filePath Excel file path
     * @param sheetName Sheet name
     * @param testCaseName Test case name
     * @return Test data as Map
     */
    public static Map<String, String> getTestData(String filePath, String sheetName, String testCaseName) {
        Map<String, String> testData = new HashMap<>();
        
        try {
            List<Map<String, String>> allData = getAllData(filePath, sheetName);
            
            for (Map<String, String> rowData : allData) {
                if (rowData.containsKey("TestCase") && rowData.get("TestCase").equals(testCaseName)) {
                    testData = rowData;
                    break;
                }
            }
            
            if (testData.isEmpty()) {
                logger.warn("No test data found for test case: " + testCaseName);
            } else {
                logger.info("Test data retrieved for test case: " + testCaseName);
            }
            
        } catch (Exception e) {
            logger.error("Error getting test data for test case: " + testCaseName, e);
        }
        
        return testData;
    }
    
    /**
     * Get login test data
     * @return List of login test data
     */
    public static List<Map<String, String>> getLoginTestData() {
        String filePath = ConfigReader.getTestDataPath() + ConfigReader.getExcelTestData();
        return getAllData(filePath, FrameworkConstants.LOGIN_SHEET);
    }
}