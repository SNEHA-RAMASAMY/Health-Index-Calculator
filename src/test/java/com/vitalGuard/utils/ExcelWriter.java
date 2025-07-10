package com.vitalGuard.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class ExcelWriter {

	public static void writeResults(String filePath, String sheetName, List<String> results) {
	    try {
	        FileInputStream fis = new FileInputStream(filePath);
	        Workbook workbook = new XSSFWorkbook(fis);
	        Sheet sheet = workbook.getSheet(sheetName);

	        int rowIndex = 1;
	        for (String result : results) {
	            Row row = sheet.getRow(rowIndex);
	            if (row == null) row = sheet.createRow(rowIndex);

	            row.createCell(5, CellType.STRING).setCellValue(result); 

	            rowIndex++;
	        }

	        fis.close();
	        FileOutputStream fos = new FileOutputStream(filePath);
	        workbook.write(fos);
	        fos.close();
	        workbook.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}