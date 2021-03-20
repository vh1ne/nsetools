package com.vh1ne.finance.tester;

import java.io.File;
import java.io.FileOutputStream;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vh1ne.finance.globle.StockNameRepo;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;


public class Volme_shokers {
	static Stock stock;
	static int count=0;
	static Logger logger = LoggerFactory.getLogger(Bull_Cartel_BTST.class);
	public static void main(String[] args) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");
		LocalDateTime now = LocalDateTime.now();
		int writeCount = 0;
		String[] stockListall = StockNameRepo.NIFTY_BANK;
		String fileName = "E:\\Work\\Files\\YahooFinance\\VolumeChart_12_01_2021\\volume" + dtf.format(now) + ".csv";	   
		 test();
		try (FileOutputStream myWriter = new FileOutputStream(new File(fileName), false)) {
			myWriter.write(getHeader().getBytes());
			Instant start = Instant.now();
			System.out.println("Writing to file");
			for (String s : Arrays.asList(stockListall)) {
			//	myWriter.write(getDetails1(s).getBytes());
				if (writeCount % (stockListall.length / 10) == 0) {
				//	System.out.println("progress " + writeCount);
				}
				if (s != null && !s.equalsIgnoreCase(""))
					writeCount++;
			}
			Instant end = Instant.now();
			System.out.println();
			System.out.println(writeCount + " Rows written to file successfully in "
					+ Duration.between(start, end).getSeconds() + " seconds");
		} catch (Exception e) {
			System.out.println("Error writing to file " + fileName + " " + e.getMessage());
			e.printStackTrace();
		}
	
	
	
		
	}
	public static void test(){
	System.out.println(StockNameRepo.NIFTY_1500.length);
	Arrays.asList(StockNameRepo.NIFTY_1500).removeAll(Arrays.asList(StockNameRepo.NIFTY_ERRLIST));
	System.out.println(StockNameRepo.NIFTY_1500.length);
   
	}
	static String getHeader()
	{
		return  "Name,"
				+ "Symbol,"
				+ "Volume,"
				+ "Change in per,"
				+ "Price,"
				+ "Market Cap Volume,"
				+ "Avg Volume,"
				+ "Volume change percent"   + "\n";
	}
	static String getDetails1(String stockName ) throws Exception
	{
	
		if(!stockName.contains(".NS"))
		stockName= stockName+".NS";
		String str="";
		try {
			
			
			stock = YahooFinance.get(stockName);
			if(stock!=null && stock.getSymbol()!=null && !stock.getSymbol().equalsIgnoreCase("null")
					&& stock.getQuote().getAvgVolume() instanceof Number &&stock.getQuote().getAvgVolume()!=0
					&& stock.getQuote().getVolume() instanceof Number 
					&& stock.getStats().getMarketCap() instanceof Number
				//	&& stock.getQuote().getPrice().compareTo(new BigDecimal(20))>1
				) 
					
			{
		//	System.out.println(stock.getSymbol());
			if(true
					// && ((float)stock.getQuote().getVolume() /(float)stock.getQuote().getAvgVolume()>=2)
					)
			
				{
				str=  stock.getName()+ ","
							+ stock.getSymbol() + ","
							//+ stock.getQuote().getLastTradeDateStr() + ","
							+  stock.getQuote().getVolume()  + ","
							+  stock.getQuote().getChangeInPercent()  + ","
						//	+ stock.getQuote().getVolume()  + ","
							+ stock.getQuote().getPrice()  + ","
						//	+ stock.getQuote().getVolume()  + ","
							+ stock.getStats().getMarketCap() + ","
							+ stock.getQuote().getAvgVolume()  + ","
							+  (float)stock.getQuote().getVolume() /(float)stock.getQuote().getAvgVolume()  + "\n";
				
				System.out.print(str);
				
				
			
					}
				
				
			}
			
		}catch (NullPointerException e) {
			System.out.println( "Can not find symbol " + stockName + " " + e.getMessage());
		} 
		catch (Exception e) {
			System.out.println(stockName +" " + e.getMessage());
		}
		return str;
	}
	

	
}
