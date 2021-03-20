package com.vh1ne.finance.tester.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XLSXReaderExample {
	public static void main(String[] args) {
		try (
			FileInputStream fis = new FileInputStream(new File("E:\\Work\\Files\\temp\\fii_stats_01-Mar-2021.xls")); // obtaining bytes from the file
			HSSFWorkbook wb=new HSSFWorkbook(fis);   )
		{
			//creating a Sheet object to retrieve the object  
			HSSFSheet sheet=wb.getSheetAt(0);  
				
			Iterator<Row> itr = sheet.iterator(); // iterating over excel file
		for(int i=3;i<=6;i++)
		{
			for(int j=0; j<=6;j++)
			{System.out.print(readCell(sheet, i,j) + " ");}
			System.out.println();
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readAll(Iterator<Row> itr)
	{
		while (itr.hasNext()) {
			Row row = itr.next();
			
			Iterator<Cell> cellIterator = row.cellIterator(); // iterating over each column
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case STRING: 
					System.out.print(cell.getStringCellValue() + "\t\t\t");
					break;
				case BOOLEAN: 
					System.out.print(cell.getBooleanCellValue() + "\t\t\t");
					break;
				case FORMULA: 
					System.out.print(cell.getCellFormula() + "\t\t\t");
					break;
				case NUMERIC: // field that represents number cell type
					System.out.print(cell.getNumericCellValue() + "\t\t\t");
					break;
				default:
				}
			}
			System.out.println("");
		}
	}
	
	public static String readCell(HSSFSheet sheet, int vRow , int vColumn)
	{
		String value="";
		Row row=sheet.getRow(vRow); //returns the logical row  
		Cell cell=row.getCell(vColumn); //getting the cell representing the given column  
		value=cell.getStringCellValue();    //getting cell value  
		return value;
	}
}