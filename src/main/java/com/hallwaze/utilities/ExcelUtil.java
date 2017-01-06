package com.hallwaze.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelUtil {
	public Workbook getWoorkBook(String excelFilePath);

	public Sheet getSheet(Object sheetName);

	public void closeWorkbook();
	
	public Object getCellValue(Cell cell);
	
	public String getExcelValue(String excelFilePath, Object sheetNumber, String param );

}