package com.vitalGuard.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public static Object[][] readExcel(String filePath, String sheetName) {
        List<Object[]> data = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            boolean firstRow = true;
            for (Row row : sheet) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                Object[] rowData = new Object[5];
                rowData[0] = row.getCell(0).toString(); // Age
                rowData[1] = row.getCell(1).toString(); // Pulse
                rowData[2] = row.getCell(2).toString(); // BP
                rowData[3] = row.getCell(3).toString(); // Expected index value
                rowData[4] = row.getCell(4).toString(); // Expected feedback
                //rowData[4] = row.getCell(4) != null ? row.getCell(4).toString() : ""; 
                data.add(rowData);
                workbook.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return data.toArray(new Object[0][]);
    }
}

