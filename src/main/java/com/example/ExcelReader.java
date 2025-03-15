package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    /**
     * Reads test data from an Excel file and returns it as a 2D array.
     *
     * @param filePath The path to the Excel file.
     * @return A 2D array of test data.
     */
    @SuppressWarnings("resource")
    public static Object[][] readTestData(String filePath) {
        List<Object[]> testData = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            // Load the Excel workbook
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

            // Check if the sheet is empty
            if (sheet.getPhysicalNumberOfRows() <= 1) {
                throw new IllegalArgumentException("The Excel sheet is empty or has no data.");
            }

            // Iterate through rows (skip the header row)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // Check if the row is null or empty
                if (row == null || row.getPhysicalNumberOfCells() < 2) {
                    continue; // Skip empty or incomplete rows
                }

                // Read data from the row
                String departureCity = row.getCell(0).getStringCellValue(); // Column 1: DepartureCity
                String destinationCity = row.getCell(1).getStringCellValue(); // Column 2: DestinationCity

                // Add the data to the list
                testData.add(new Object[]{departureCity, destinationCity});
            }

            // Close the workbook to release resources
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read the Excel file: " + e.getMessage());
        }

        // Convert the list to a 2D array
        return testData.toArray(new Object[0][0]);
    }

    /**
     * Reads Excel data from a file. This method is a wrapper for readTestData.
     *
     * @param filePath The path to the Excel file.
     * @return A 2D array of test data.
     */
    public Object[][] readExcel(String filePath) {
        return readTestData(filePath);
    }
}