package com.vh1ne.finance.tester;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class WriteCSV {

	private static final String[] CSV_HEADER = { "id", "name", "address", "age" };
	
	String [] apacheHeader = { "StockName",
			"CurrDate",
			"NextDate",
			"Currvol",
			"Nextvol",
			"VolumeDifference",
			"VolumeChange_inPer",
			"PriceCurrClose",
			"PriceNextClose",
			"PriceDifference_curr_next",
			"PriceDiff_inPer",
			"VolumePercentChange_Indicator",
			"PricePercentChange_Indicator",
			"StockStrength",
			"MarketCap",	
			"EarningsAnnouncement",
			"Daily_Avg_volume",
			"Volume_shoker"};

	public static void main(String[] args) {

		List<Customer> customers = Arrays.asList(
				new Customer("1", "Jack Smith", "Massachusetts", 23),
				new Customer("2", "Adam Johnson", "New York", 27),
				new Customer("3", "Katherin Carter", "Washington DC", 26),
				new Customer("4", "Jack London", "Nevada", 33), 
				new Customer("5", "Jason Bourne", "California", 36));

		apacheWriter("",CSV_HEADER,customers);
System.out.println("exit..");

	}
	
	static void apacheWriter(String fileName,String[] header, Collection<?> list)
	{
		FileWriter fileWriter = null;
		CSVPrinter csvPrinter = null;
		try {
			fileWriter = new FileWriter("E:\\Work\\Files\\temp\\customer.csv");
			csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(header));
		
			
			  for (Object customer : list) {
			  
				List data = Arrays.asList(((Customer) customer).getId(), 
						((Customer) customer).getName(),
						((Customer) customer).getAddress(),
						String.valueOf(((Customer) customer).getAge())
						);

			  csvPrinter.printRecord(data); }
			 
			
			System.out.println("Write CSV successfully!");
		} catch (Exception e) {
			System.out.println("Writing CSV error!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvPrinter.close();
			} catch (IOException e) {
				System.out.println("Flushing/closing error!");
				e.printStackTrace();
			}
		}
	}
}