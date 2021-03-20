package com.vh1ne.finance.tester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class YahooFinanceTest_historical_data {
	static Stock stock;
	public static void main(String[] args) {
		List<HistoricalQuote> history = new ArrayList<HistoricalQuote>();
		try {
			history=	getHistoricalData("INFY");
			for(HistoricalQuote stock: history)
			{
				String str=  stock.getSymbol()+ " ,"
					+stock.getDate().getTime() + " ,"
						+  stock.getVolume()  + " ,"
						+ stock.getClose()  + " "
				
						
					 + "\n";
			
			System.out.print(str);

			}
			
		} catch (Exception e) {
		
			System.out.println(e.getMessage());
		}
	}
	
	static List<HistoricalQuote> getHistoricalData(String stockName) throws IOException,Exception
	{
		if(!stockName.endsWith(".NS"))
			stockName=stockName+".NS";
		
		try {
			stock = YahooFinance.get(stockName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stock.getSymbol();
	
		Calendar startDate = Calendar.getInstance();
		startDate.set(2021, Calendar.FEBRUARY, 1);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2021, Calendar.FEBRUARY, 7);
		List<HistoricalQuote> history = stock.getHistory(startDate, endDate, Interval.DAILY);
		//System.out.println(history);
		return history;
	}
}
