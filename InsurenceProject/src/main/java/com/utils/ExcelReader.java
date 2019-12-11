package com.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	FileInputStream filein;
	FileOutputStream fileout;
	XSSFWorkbook book;
	XSSFSheet sheet = null;
	XSSFRow row = null;
	XSSFCell cell = null;
	String filePath = null;

	public ExcelReader(String sourceFilePath/*,String sheetName*/) {
		try {
			filein = new FileInputStream(sourceFilePath);
			book = new XSSFWorkbook(filein);
			/*sheet=book.getSheet(sheetName);*/

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getrowcount(String sheetName) {
		sheet = book.getSheet(sheetName);
		return sheet.getLastRowNum();
	}

	public int getcolumncount(String sheetName) {
		sheet = book.getSheet(sheetName);
		row = sheet.getRow(0);
		return row.getLastCellNum()+1;
	}

	public int getcolumncount(String sheetName, int rownumber) {
		sheet = book.getSheet(sheetName);
		row = sheet.getRow(rownumber);
		return row.getLastCellNum();
	}

	public String getcelldata(String sheetName, int colnum, int rownum) {

		sheet = book.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);

		if (cell.getCellTypeEnum() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
			String value = String.valueOf(cell.getNumericCellValue());
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				value = String.valueOf(cell.getDateCellValue());
			}
			return value;
		} else if (cell.getCellTypeEnum() == CellType.BLANK) {
			return "";
		} else if (cell.getCellTypeEnum() == CellType._NONE) {
			return "";
		} else {
			return String.valueOf(cell.getBooleanCellValue());
		}

	}

	public String getcelldata(String sheetName, String colname, int rownum) {

		sheet = book.getSheet(sheetName);
		row=sheet.getRow(0);
		int cellnum=-1;
		
		for (int i = 0; i <row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colname)) {
				cellnum=i;
			}
		}		
		row = sheet.getRow(rownum);
		cell = row.getCell(cellnum);

		if (cell.getCellTypeEnum() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
			String value = String.valueOf(cell.getNumericCellValue());
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				value = String.valueOf(cell.getDateCellValue());
			}
			return value;
		} else if (cell.getCellTypeEnum() == CellType.BLANK) {
			return "";
		} else if (cell.getCellTypeEnum() == CellType._NONE) {
			return "";
		} else {
			return String.valueOf(cell.getBooleanCellValue());
		}
	}
	
	public void setCellData(String sheetName,int colnum,int rownum,String EnterCellValue){
		sheet=book.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		cell.setCellValue(EnterCellValue);
		
		try {
			fileout=new FileOutputStream(filePath);
			book.write(fileout);
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCellData(String sheetName,String colname,int rownum,String EnterCellValue){
		sheet=book.getSheet(sheetName);
		int colnum=-1;
		for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
			if (sheet.getRow(0).getCell(i).getStringCellValue().trim().equalsIgnoreCase(colname)) {
				colnum=i;
			}
		}
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		cell.setCellValue(EnterCellValue);
		
		try {
			fileout=new FileOutputStream(filePath);
			book.write(fileout);
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCellData(String sheetName,String colname,String rowname,String EnterCellValue){
		sheet=book.getSheet(sheetName);
		int rownum=-1;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			if (sheet.getRow(i).getCell(0).getStringCellValue().trim().equalsIgnoreCase(rowname)) {
				rownum=i;
			}
		}
		int colnum=-1;
		for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
			if (sheet.getRow(0).getCell(i).getStringCellValue().trim().equalsIgnoreCase(colname)) {
				colnum=i;
			}
		}
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		cell.setCellValue(EnterCellValue);
		
		try {
			fileout=new FileOutputStream(filePath);
			book.write(fileout);
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object[][] getTestData(String sheetName){
		sheet =book.getSheet(sheetName);
		
		int testStartRownum=0;
		while (!getcelldata(sheetName, 0, testStartRownum).equals("")) {
			testStartRownum++;
		}
		int colStartrownum=testStartRownum+1;
		int dataStartrownum=testStartRownum+2;
		
		int rows=0;
		while (!getcelldata(sheetName, 0, dataStartrownum+rows).equals("")) {
			rows++;
		}
		
		int colums=0;
		while (!getcelldata(sheetName, colums, colStartrownum).equals("")) {
			colums++;
		}
		
		Object[][] data=new Object[rows][1];
		int rowcount=0;
		Hashtable<String, String> table;
		
		for (int rnum = dataStartrownum; rnum < dataStartrownum+rows; rnum++) {
			table=new Hashtable<String, String>();
			for (int cnum = 0; cnum < colums; cnum++) {
				String key=getcelldata(sheetName, cnum, colStartrownum);
				String value=getcelldata(sheetName, cnum, dataStartrownum);
				table.put(key, value);
			}
			data[rowcount][0]=table;
			rowcount++;			
		}
		return data;
	}
}
