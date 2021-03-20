package com.vh1ne.finance.tester.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSSFExcelWriter {

	
	public static void main(String[] args) {
		
		createExcel("INFY");
	}
	
	public static void createExcel(String name)
	{
		  // Blank workbook 
        XSSFWorkbook workbook = new XSSFWorkbook(); 
  
        // Create a blank sheet 
        XSSFSheet sheet = workbook.createSheet("INFY"); 
        
       
        Map<String, Object[]> data = new TreeMap<String, Object[]>(); 
        data.put("1", new Object[]{ "ID", "NAME", "LASTNAME" }); 
        data.put("2", new Object[]{ 1, "Pankaj", "Kumar" }); 
        data.put("3", new Object[]{ 2, "Prakashni", "Yadav" }); 
        data.put("4", new Object[]{ 3, "Ayan", "Mondal" }); 
        data.put("5", new Object[]{ 4, "Virat", "kohli" }); 
  
        // Iterate over data and write to sheet 
        Set<String> keyset = data.keySet(); 
        int rownum = 0; 
        for (String key : keyset) { 
            // this creates a new row in the sheet 
            Row row = sheet.createRow(rownum++); 
            Object[] objArr = data.get(key); 
            int cellnum = 0; 
            for (Object obj : objArr) { 
                // this line creates a cell in the next column of that row 
                Cell cell = row.createCell(cellnum++); 
               
                    cell.setCellValue(""); 
              
            } 
        } 
        try { 
            // this Writes the workbook gfgcontribute 
            FileOutputStream out = new FileOutputStream(new File("E:\\Work\\Files\\"+name+".xlsx")); 
            workbook.write(out); 
            out.close(); 
            System.out.println(name+".xlsx written successfully to location" + "E:\\Work\\Files\\"+name+".xlsx"); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 

  
	}
	

	public static void writer(String fileNameToWrite , String header ,ArrayList<String> listToWrite)
	{
		try (FileOutputStream myWriter = new FileOutputStream(new File(fileNameToWrite),false)){
		

			//logger.info("Writing to file. " + fileNameToWrite);
			myWriter.write(header.getBytes());
	
			for(String str: listToWrite)
			{
				myWriter.write(str.getBytes());
			}	
		} catch (Exception e) {
			//logger.error("Error writing to file " + fileName + " "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
