package com.panpages.bow.service.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.panpages.bow.configuration.ConfigConstant;
import com.panpages.bow.model.Report;

public class OfficeFileUtils {
	
	@Autowired ApplicationContext ctx;
	
	public byte[] readOds(File file) {
		Sheet sheet;
		String result = "";
		try {
			// Getting the 0th sheet for manipulation| pass sheet name as string
			sheet = SpreadSheet.createFromFile(file).getSheet(0);

			// Get row count and column count
			int nColCount = sheet.getColumnCount();
			int nRowCount = sheet.getRowCount();

			MutableCell cell = null;
			for (int nRowIndex = 0; nRowIndex < nRowCount; nRowIndex++) {
				// Iterating through each column
				String rowValue = "";
				int nColIndex = 0;
				for (; nColIndex < nColCount; nColIndex++) {
					
					try {
						cell = sheet.getCellAt(nColIndex, nRowIndex);
						if (cell != null && !cell.getTextValue().isEmpty()) {
							if (rowValue.equals("")) {
								rowValue = cell.getTextValue().trim();
							} else {
								rowValue += "," + cell.getTextValue().trim();
							}
						}
					} catch (Exception e) {
						
					}
					
				}
				if (!rowValue.isEmpty()) {
					result += (rowValue.trim() + "\n");
				}
			}
			return result.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] readXlsx(File file) {
		FileInputStream fis = null;
		String result = "";
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println(fis.toString());
		XSSFWorkbook wb;
		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		XSSFSheet sh = wb.getSheetAt(0);
		int rowNum = sh.getLastRowNum() + 1;
		int colNum = sh.getRow(0).getLastCellNum();

		for (int i = 0; i < rowNum; i++) {
			XSSFRow row = sh.getRow(i);
			String rowValue = "";
			for (int j = 0; j < colNum; j++) {
				XSSFCell col = row.getCell(j);
				String value = celltoString(col);
				if (!value.trim().isEmpty()) {
					if (rowValue.isEmpty()) {
						rowValue = value;
					} else {
						rowValue += ("," + value);
					}
				}

			}
			if (!rowValue.trim().isEmpty()) {
				result += rowValue.trim() + "\n";
			}
		}

		return result.getBytes();
	}

	private static String celltoString(XSSFCell col) {
		int type;
		Object result;
		type = col.getCellType();
		switch (type) {
		case 0:
			result = col.getNumericCellValue();
			break;
		case 1:
			result = col.getStringCellValue();
			break;
		default:
			System.out.println(type);
			throw new RuntimeException("Runtime Exception");
		}
		return result.toString();
	}
	
	public String createExcelFile(String excelOutputPath, String monthYear, List<Report> lstReport){
		try {
			
			String fileExcelName =  String.format("Excel_%1$s_%2$s.%3$s", monthYear, new Random().nextLong(),"xls");
			String fileName = String.format("%1$s%2$s%3$s", excelOutputPath, File.separator, fileExcelName);
			 HSSFWorkbook workbook = new HSSFWorkbook();
	         HSSFSheet sheet = workbook.createSheet(monthYear);
	         
	         HSSFRow rowhead = sheet.createRow((short)0);
	         rowhead.createCell(0).setCellValue("User Email");
	         rowhead.createCell(1).setCellValue("User Name");
	         rowhead.createCell(2).setCellValue("Time Of Access");
	         rowhead.createCell(3).setCellValue("Time Of Report Received");
	         rowhead.createCell(4).setCellValue("Type Of Report");
	         
	         for(int i = 0 ; i < lstReport.size() ; i++) {
	        	 Report report = lstReport.get(i);
	        	 HSSFRow row = sheet.createRow((short)i+1);
	             row.createCell(0).setCellValue(report.getEmail());
	             row.createCell(1).setCellValue(report.getUserName());
	             row.createCell(2).setCellValue(report.getTimeAccess());
	             row.createCell(3).setCellValue(report.getTimeReceived());
	             row.createCell(4).setCellValue(report.getType());
	         }
	         
	         FileOutputStream fileOut = new FileOutputStream(fileName);
	         workbook.write(fileOut);
	         fileOut.close();
	         return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
