package com.panpages.bow.service.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class OfficeFileUtils {
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
}
