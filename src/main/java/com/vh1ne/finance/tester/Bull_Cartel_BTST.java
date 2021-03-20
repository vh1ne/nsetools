package com.vh1ne.finance.tester;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vh1ne.finance.globle.StockNameRepo;

import yahoofinance.*;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/* @author vh1nelabs */
public class Bull_Cartel_BTST {
	static Stock stock;
	static Logger logger = LoggerFactory.getLogger(Bull_Cartel_BTST.class);
	
	/* schedule jobs*/
	static void scheduleTicker()
	{
		TimerTask task = new TimerTask() {

			@Override
			public void run() {

				try {
					getDetails("");
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		};
	Timer timer = new Timer();
	timer.schedule(task, new Date(), 300);
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
	
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");
		 LocalDateTime now = LocalDateTime.now();
		
		String fileName ="E:\\Work\\Files\\Finance\\Strategy\\BullCartel\\Data_"		+	dtf.format(now)	+ 	".csv";
		  
		Instant start = Instant.now();
		ArrayList<String> list =new ArrayList<String>();
		ArrayList<String> errorList = new ArrayList<String>();
	String [] stockListTest = StockNameRepo.NIFTY_TEST
	
		;
		for(String s :	stockListTest	
				)
		{
			try {
				 for(String str:(ArrayList<String>) getDetailsVolumeChange(s)[0]) 
				 { list.add(str); }
				 
				
				//getDetailsVolumeChange(s,list);
		
				
			}
	    catch (IOException e) {
	    	logger.error("Error in fetching details for "+ s + " -> " +e.getMessage());
	    	errorList.add(s);
		}
		catch(NullPointerException e)
		{
			logger.error("Could not find symbol or returned null " + s  +"  -> " +e.getMessage() );
			errorList.add(s);
		}
		catch (Exception e) {
				logger.error("Error in fetching details for "+ s + " -> " +e.getMessage());
				errorList.add(s);
			}

			
		}
		
	writer(fileName, list);
	Instant end = Instant.now();
	logger.info("Errored stock size - > " + errorList.size()+ " List ->  "+errorList.toString());
	logger.info("Program executed in "+(float)Duration.between(start, end).toMillis()/1000 + " seconds " ); 
		System.exit(0);
	}
	static String getDetailsHeader()
	{
		return "Stock Name," +
				"next vol," +
				"currvol," +
				"volume per," +
				"next date," +
				"next close," +
				"curr date," +
				"curr close," +
				"difference between two,"+
				"diff in %" + "\n";
	}
	static String getDetailsVolumeChangeHeader()
	{
		return  "StockName," +
				"CurrDate," +
				"NextDate," +
				"Currvol," +
				"Nextvol," +
				"VolumeDifference," +
				"VolumeChange_inPer," +
				
				"PriceCurrClose," +
				"PriceNextClose," +
				"PriceDifference_curr_next,"+
				"PriceDiff_inPer," +
				
				
				"VolumePercentChange_Indicator," +
				"PricePercentChange_Indicator," +
				"StockStrength," +
				"MarketCap," 
				+	"EarningsAnnouncement"
				
				+ "\n";
	} 

	
	static void writer(String fileName ,ArrayList<String> list)
	{
		try (FileOutputStream myWriter = new FileOutputStream(new File(fileName),false)){
		

			logger.info("Writing to file. " + fileName);
			myWriter.write(getDetailsVolumeChangeHeader().getBytes());
	
			for(String str: list)
			{
				myWriter.write(str.getBytes());
			}	
		} catch (Exception e) {
			logger.error("Error writing to file " + fileName + " "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	/* this method returns data of given security between specified period difference between volume and difference 
	 * between price and same in percentage*/ 
	@Deprecated
	static ArrayList<String>  getDetails1(String stockName) throws Exception
	{
		ArrayList<String> list = new ArrayList<String>();
		if(!stockName.endsWith(".NS"))
			stockName=stockName+".NS";
		String str="";
		String str1="";
		try {
			stock = YahooFinance.get(stockName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			System.out.println("could not find symbol " + stockName );
		}

		Calendar startDate = Calendar.getInstance();
		startDate.set(2021, Calendar.JANUARY, 29);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2021, Calendar.FEBRUARY, 7);
		
		List<HistoricalQuote> history = stock.getHistory(startDate, endDate, Interval.DAILY);
		// stock.getQuote().getVolume();
		//StockVol stockVol =null;
		//Map<String, Float> map = new TreeMap<String, Float>();
		for (int i = 0; i < history.size(); i++) {

			if (i == history.size() -1) {
				
			} else if(history.get(i)!=null) {
				HistoricalQuote current = history.get(i);
				HistoricalQuote next = history.get(i + 1);
				BigDecimal per = BigDecimal.ZERO;
				long one = 1;
				float nextv = (next.getVolume() != null && (next.getVolume() instanceof Long)) ? next.getVolume() : one;
				float currv = (current.getVolume() != null && (current.getVolume() instanceof Long))
						? current.getVolume()
						: one;
				BigDecimal nextVol = BigDecimal.valueOf(nextv);
				BigDecimal currVol = BigDecimal.valueOf(currv);
			//	BigDecimal volPer = nextVol.divide(currVol, RoundingMode.HALF_UP);
				float voluPerFloat = nextv / currv;
				boolean voluPerFloatChange = (nextv - currv)>0?true:false;
				if (current != null && next != null)// && voluPerFloat >=2)

				{
					BigDecimal currentClose = (current.getClose() != null && (current.getClose() instanceof BigDecimal))
							? current.getClose()
							: BigDecimal.ZERO;
					BigDecimal nextClose = (next.getClose() != null && next.getClose() instanceof BigDecimal)
							? next.getClose()
							: BigDecimal.ZERO;

					BigDecimal diff = nextClose.subtract(currentClose);
					boolean perChange = nextClose.subtract(currentClose).compareTo(BigDecimal.ZERO) >=0 ?true : false;
					if (!diff.equals(BigDecimal.ZERO) && diff instanceof BigDecimal && diff != null
							&& currentClose != null && currentClose != BigDecimal.ZERO && nextClose != null
							&& nextClose != BigDecimal.ZERO 
							//&& voluPerFloat>=5
							) {
						per = diff.divide(currentClose, RoundingMode.HALF_UP);
						per = per.multiply(new BigDecimal("100"));
						
						str=(history.get(i).getSymbol() 
								+ "," + nextVol 
								+ "," + currVol 
								+ "," + voluPerFloat
								+ "," + next.getDate().getTime()
								+ "," + nextClose 
								+ "," + current.getDate().getTime() 
								+ "," + currentClose
								+ "," + diff
								+ "," + per
								+ "\n");
					list.add(str);
					}
				}

			}

		}
 return list;
	}
	
	
	/* Current date minus 8  day change in volume price and same in percentage also strategy of increasing price supported by volume 
	 * this method filters out data from getDetails1
	  */
	
	//to-do ---------> back-test this strategy for last 5 years with all nifty securities
	static Object[]  getDetailsVolumeChange(String stockName) throws IOException,NullPointerException, Exception
	{
		
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<StockVolumeIndicator> stockVolumeIndicatorList = new ArrayList<StockVolumeIndicator>();
		if(!stockName.endsWith(".NS"))
			stockName=stockName+".NS";
		String str="";
		stock = YahooFinance.get(stockName);
		
		Calendar endDate = Calendar.getInstance();
		
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH, -8);
		
		int stockStrength=0;
		List<HistoricalQuote> history = stock.getHistory(startDate, endDate, Interval.DAILY);
		for (int i = 0; i < history.size(); i++) {
				if (history!=null && i == history.size() -1) {
				
			} else if(history!=null && history.get(i + 1)!=null && history.get(i)!=null) {
				HistoricalQuote current = history.get(i);
				HistoricalQuote next = history.get(i + 1);
				BigDecimal per = BigDecimal.ZERO;
				long one = 1;
				float nextv = (next.getVolume() != null && (next.getVolume() instanceof Long)) ? next.getVolume() : one;
				float currv = (current.getVolume() != null && (current.getVolume() instanceof Long))
						? current.getVolume(): one;
				BigDecimal nextVol = BigDecimal.valueOf(nextv);
				BigDecimal currVol = BigDecimal.valueOf(currv);
			//	BigDecimal volPer = nextVol.divide(currVol, RoundingMode.HALF_UP);
				float voluPerFloat = nextv / currv;
				BigDecimal volumeDifference =new BigDecimal(nextv-currv);
				boolean isVolumePerFloatChange = (nextv > currv)?true:false;
				if (current != null && next != null)// && voluPerFloat >=2)

				{
					BigDecimal currentClose = (current.getClose() != null && (current.getClose() instanceof BigDecimal))
							? current.getClose()
							: BigDecimal.ZERO;
					BigDecimal nextClose = (next.getClose() != null && next.getClose() instanceof BigDecimal)
							? next.getClose()
							: BigDecimal.ZERO;

					BigDecimal diff = nextClose.subtract(currentClose);
					boolean isPerChange = nextClose.compareTo(currentClose)>=0 ?true : false;
					if (!diff.equals(BigDecimal.ZERO) && diff instanceof BigDecimal && diff != null
							&& currentClose != null && currentClose != BigDecimal.ZERO && nextClose != null
							&& nextClose != BigDecimal.ZERO 
							//&& voluPerFloat>=5
							) {
						per = diff.divide(currentClose, RoundingMode.HALF_UP);
						per = per.multiply(new BigDecimal("100"));
					if(isVolumePerFloatChange &&  isPerChange)
					{
						stockStrength++;
					}
					else
						stockStrength=0;
						str=(
								history.get(i).getSymbol() 
								+ "," + current.getDate().getTime() 
								+ "," + next.getDate().getTime()
								+ "," + currVol 
								+ "," + nextVol 
								+ "," + volumeDifference
								+ "," + voluPerFloat
								
								+ "," + currentClose
								+ "," + nextClose 
								+ "," + diff
								+ "," + per
								
								+ "," + isVolumePerFloatChange
								+ "," + isPerChange
								+ "," + stockStrength
								+ "," + stock.getStats().getMarketCap()
								+ "," + (stock.getStats()!=null && stock.getStats().getEarningsAnnouncement()!=null && stock.getStats().getEarningsAnnouncement().getTime()!=null  ?  stock.getStats().getEarningsAnnouncement().getTime().toString() :"")
								+ "\n");
					//	logger.debug(str);
						StockVolumeIndicator svi= new StockVolumeIndicator();
						svi.setSymbol(history.get(i).getSymbol() );
						svi.setCurrDate(current.getDate().getTime() );
						svi.setNextDate(next.getDate().getTime());
						svi.setCurrvol(currVol);
						svi.setNextvol(nextVol);
						svi.setVolumeDifference(volumeDifference);
						svi.setVolumeChange_inPer(voluPerFloat);
						
						svi.setPriceCurrClose(currentClose);
						svi.setPriceNextClose(nextClose);
						svi.setPriceDifference_curr_next(diff);
						svi.setPriceDiff_inPer(per);
						svi.setVolumePercentChange_Indicator(isVolumePerFloatChange);
						svi.setPricePercentChange_Indicator(isPerChange);
						
						stockVolumeIndicatorList.add(svi);
					list.add(str);
					}
				}

			}

		}
		Object [] arr =new Object[2];
		arr[0]=list;
		arr[1]=stockVolumeIndicatorList;
 return arr;
	}
	@Deprecated
	static StockVolumeIndicator getDetails(String stockName) throws IOException, Exception {
		try {
			stock = YahooFinance.get(stockName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stock.getSymbol();
		//BigDecimal price = stock.getQuote().getPrice();
		// BigDecimal change = stock.getQuote().getChangeInPercent();
		// BigDecimal peg = stock.getStats().getPeg();
		// BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
		// stock.getHistory(new GregorianCalendar(2015, 01, 01), new
		// GregorianCalendar(2015, 01, 01));
		// stock.print();
		// System.out.println(stock.getName() + " -------- > " +price );
		Calendar startDate = Calendar.getInstance();
		startDate.set(2021, Calendar.JANUARY, 7);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2021, Calendar.JANUARY, 24);
		List<HistoricalQuote> history = stock.getHistory(startDate, endDate, Interval.DAILY);
		// stock.getQuote().getVolume();
		StockVolumeIndicator stockVol =null;
		//Map<String, Float> map = new TreeMap<String, Float>();
		for (int i = 0; i < history.size(); i++) {

			if (i == history.size() -1) {
				
			} else if(history.get(i)!=null) {
				HistoricalQuote current = history.get(i);
				HistoricalQuote next = history.get(i + 1);
				BigDecimal per = BigDecimal.ZERO;
				long one = 1;
				float nextv = (next.getVolume() != null && (next.getVolume() instanceof Long)) ? next.getVolume() : one;
				float currv = (current.getVolume() != null && (current.getVolume() instanceof Long))
						? current.getVolume()
						: one;
				BigDecimal nextVol = BigDecimal.valueOf(nextv);
				BigDecimal currVol = BigDecimal.valueOf(currv);
			//	BigDecimal volPer = nextVol.divide(currVol, RoundingMode.HALF_UP);
				float voluPerFloat = nextv / currv;
				if (current != null && next != null)// && voluPerFloat >=2)

				{
					BigDecimal currentClose = (current.getClose() != null && (current.getClose() instanceof BigDecimal))
							? current.getClose()
							: BigDecimal.ZERO;
					BigDecimal nextClose = (next.getClose() != null && next.getClose() instanceof BigDecimal)
							? next.getClose()
							: BigDecimal.ZERO;

					BigDecimal diff = nextClose.subtract(currentClose);
					
					if (!diff.equals(BigDecimal.ZERO) && diff instanceof BigDecimal && diff != null
							&& currentClose != null && currentClose != BigDecimal.ZERO && nextClose != null
							&& nextClose != BigDecimal.ZERO 
							//&& voluPerFloat>=5
							) {
						per = diff.divide(currentClose, RoundingMode.HALF_UP);
						per = per.multiply(new BigDecimal("100"));
						
						System.out.println("Stock Name -- " +history.get(i).getSymbol()  +"     " + nextVol + " " + currVol + "  -----   " + voluPerFloat
								+ "  -- " + next.getDate().getTime() + " ---> " + nextClose + " --->  "
								+ current.getDate().getTime() + " ---> " + currentClose
								+ "  ---difference between two---> " + diff + " ---in %--->  " + per);
					// stockVol =new StockVol(history.get(i).getSymbol(), voluPerFloat);
					
					}
				}

			}

		}
 return stockVol;
	}


@Deprecated
static Object[]  getDetailsVolumeChange(String stockName, ArrayList<String> list) throws IOException,NullPointerException, Exception
{
	
	//ArrayList<String> list = new ArrayList<String>();
	ArrayList<StockVolumeIndicator> stockVolumeIndicatorList = new ArrayList<StockVolumeIndicator>();
	if(!stockName.endsWith(".NS"))
		stockName=stockName+".NS";
	String str="";
	stock = YahooFinance.get(stockName);
	Calendar startDate = Calendar.getInstance();
	startDate.set(2021, Calendar.JANUARY, 31);
	Calendar endDate = Calendar.getInstance();
	endDate.set(2021, Calendar.FEBRUARY, 8);
	
	int stockStrength=0;
	List<HistoricalQuote> history = stock.getHistory(startDate, endDate, Interval.DAILY);
	for (int i = 0; i < history.size(); i++) {
			if (history!=null && i == history.size() -1) {
			
		} else if(history!=null && history.get(i + 1)!=null && history.get(i)!=null) {
			HistoricalQuote current = history.get(i);
			HistoricalQuote next = history.get(i + 1);
			BigDecimal per = BigDecimal.ZERO;
			long one = 1;
			float nextv = (next.getVolume() != null && (next.getVolume() instanceof Long)) ? next.getVolume() : one;
			float currv = (current.getVolume() != null && (current.getVolume() instanceof Long))
					? current.getVolume(): one;
			BigDecimal nextVol = BigDecimal.valueOf(nextv);
			BigDecimal currVol = BigDecimal.valueOf(currv);
		//	BigDecimal volPer = nextVol.divide(currVol, RoundingMode.HALF_UP);
			float voluPerFloat = nextv / currv;
			BigDecimal volumeDifference =new BigDecimal(nextv-currv);
			boolean isVolumePerFloatChange = (nextv > currv)?true:false;
			if (current != null && next != null)// && voluPerFloat >=2)

			{
				BigDecimal currentClose = (current.getClose() != null && (current.getClose() instanceof BigDecimal))
						? current.getClose()
						: BigDecimal.ZERO;
				BigDecimal nextClose = (next.getClose() != null && next.getClose() instanceof BigDecimal)
						? next.getClose()
						: BigDecimal.ZERO;

				BigDecimal diff = nextClose.subtract(currentClose);
				boolean isPerChange = nextClose.compareTo(currentClose)>=0 ?true : false;
				if (!diff.equals(BigDecimal.ZERO) && diff instanceof BigDecimal && diff != null
						&& currentClose != null && currentClose != BigDecimal.ZERO && nextClose != null
						&& nextClose != BigDecimal.ZERO 
						//&& voluPerFloat>=5
						) {
					per = diff.divide(currentClose, RoundingMode.HALF_UP);
					per = per.multiply(new BigDecimal("100"));
				if(isVolumePerFloatChange &&  isPerChange)
				{
					stockStrength++;
				}
				else
					stockStrength=0;
					str=(
							history.get(i).getSymbol() 
							+ "," + current.getDate().getTime() 
							+ "," + next.getDate().getTime()
							+ "," + currVol 
							+ "," + nextVol 
							+ "," + volumeDifference
							+ "," + voluPerFloat
							
							+ "," + currentClose
							+ "," + nextClose 
							+ "," + diff
							+ "," + per
							
							+ "," + isVolumePerFloatChange
							+ "," + isPerChange
							+ "," + stockStrength
							+ "\n");
				//	logger.debug(str);
					StockVolumeIndicator svi= new StockVolumeIndicator();
					svi.setSymbol(history.get(i).getSymbol() );
					svi.setCurrDate(current.getDate().getTime() );
					svi.setNextDate(next.getDate().getTime());
					svi.setCurrvol(currVol);
					svi.setNextvol(nextVol);
					svi.setVolumeDifference(volumeDifference);
					svi.setVolumeChange_inPer(voluPerFloat);
					
					svi.setPriceCurrClose(currentClose);
					svi.setPriceNextClose(nextClose);
					svi.setPriceDifference_curr_next(diff);
					svi.setPriceDiff_inPer(per);
					svi.setVolumePercentChange_Indicator(isVolumePerFloatChange);
					svi.setPricePercentChange_Indicator(isPerChange);
					
					stockVolumeIndicatorList.add(svi);
				list.add(str);
				}
			}

		}

	}
	Object [] arr =new Object[2];
	arr[0]=list;
	arr[1]=stockVolumeIndicatorList;
return arr;
}


public void bullCartel(String filename ,String[] stockListall ){
	
	String header = "StockName," +
			"CurrDate," +
			"NextDate," +
			"Currvol," +
			"Nextvol," +
			"VolumeDifference," +
			"VolumeChange_inPer," +
			"PriceCurrClose," +
			"PriceNextClose," +
			"PriceDifference_curr_next,"+
			"PriceDiff_inPer," +
			"VolumePercentChange_Indicator," +
			"PricePercentChange_Indicator," +
			"StockStrength," +
			"MarketCap," +	
			"EarningsAnnouncement"		+ "\n";
	Calendar endDate = Calendar.getInstance();
	Calendar startDate = Calendar.getInstance();
	startDate.add(Calendar.DAY_OF_MONTH, -8);
	List<String> list = new ArrayList<String>();
	Set<String> errList = new HashSet<String>();
	int count =0;
	int length =stockListall.length;
	int tenper=length/10;

	
	for(String stockName :stockListall)
	{
		count++;
		if (count % (tenper) == 0)
		logger.info("" + (count / tenper * 10) + " % processed");
		
	try {
	if(!stockName.endsWith(".NS"))	stockName=stockName+".NS";
	
	String str="";
	stock = YahooFinance.get(stockName);
	int stockStrength=0;
	List<HistoricalQuote> history = stock.getHistory(startDate, endDate, Interval.DAILY);
	for (int i = 0; i < history.size(); i++) {
			if (history!=null && i == history.size() -1) {
			
		} else if(history!=null && history.get(i + 1)!=null && history.get(i)!=null) {
			HistoricalQuote current = history.get(i);
			HistoricalQuote next = history.get(i + 1);
			BigDecimal per = BigDecimal.ZERO;
			long one = 1;
			float nextv = (next.getVolume() != null && (next.getVolume() instanceof Long)) ? next.getVolume() : one;
			float currv = (current.getVolume() != null && (current.getVolume() instanceof Long))
					? current.getVolume(): one;
			BigDecimal nextVol = BigDecimal.valueOf(nextv);
			BigDecimal currVol = BigDecimal.valueOf(currv);
		
			float voluPerFloat = nextv / currv;
			BigDecimal volumeDifference =new BigDecimal(nextv-currv);
			boolean isVolumePerFloatChange = (nextv > currv)?true:false;
			if (current != null && next != null)

			{
				BigDecimal currentClose = (current.getClose() != null && (current.getClose() instanceof BigDecimal))
						? current.getClose()
						: BigDecimal.ZERO;
				BigDecimal nextClose = (next.getClose() != null && next.getClose() instanceof BigDecimal)
						? next.getClose()
						: BigDecimal.ZERO;

				BigDecimal diff = nextClose.subtract(currentClose);
				boolean isPerChange = nextClose.compareTo(currentClose)>=0 ?true : false;
				if (!diff.equals(BigDecimal.ZERO) && diff instanceof BigDecimal && diff != null
						&& currentClose != null && currentClose != BigDecimal.ZERO && nextClose != null
						&& nextClose != BigDecimal.ZERO 
						//&& voluPerFloat>=5
						) {
					per = diff.divide(currentClose, RoundingMode.HALF_UP);
					per = per.multiply(new BigDecimal("100"));
				if(isVolumePerFloatChange &&  isPerChange)
				{stockStrength++; }
				else	stockStrength=0;
				
					str=(
							history.get(i).getSymbol() 
							+ "," + current.getDate().getTime() 
							+ "," + next.getDate().getTime()
							+ "," + currVol 
							+ "," + nextVol 
							+ "," + volumeDifference
							+ "," + voluPerFloat
							+ "," + currentClose
							+ "," + nextClose 
							+ "," + diff
							+ "," + per
							+ "," + isVolumePerFloatChange
							+ "," + isPerChange
							+ "," + stockStrength
							+ "," + stock.getStats().getMarketCap()
							+ "," + (stock.getStats()!=null && stock.getStats().getEarningsAnnouncement()!=null && stock.getStats().getEarningsAnnouncement().getTime()!=null  ?  stock.getStats().getEarningsAnnouncement().getTime().toString() :"")
							+ "\n");
					list.add(str);
				}
			}
		}
	}
	}
	catch(IOException e)
	{
		logger.error("File not found  for stock - " +stockName + " " + e.getMessage() );
		errList.add(stockName);
	}
	catch (NullPointerException e)
	{
		logger.error("could not find symbol or null for stock - " +stockName + " " + e.getMessage()  );
		errList.add(stockName);
		
	}
	catch (Exception e) {
		logger.error("error occurred for stock - " +stockName + " "  + e.getMessage() );
		errList.add(stockName);
	}
	}
	
	
	
	
	
	
};
}

class StockVolumeIndicator extends HistoricalQuote
{
	String StockName;
	Date CurrDate;
	Date NextDate;
	BigDecimal Currvol;
	BigDecimal Nextvol;
	BigDecimal VolumeDifference;
	float VolumeChange_inPer;
	BigDecimal PriceCurrClose;
	BigDecimal PriceNextClose;
	BigDecimal PriceDifference_curr_next;
	BigDecimal PriceDiff_inPer;
	boolean VolumePercentChange_Indicator;
	boolean PricePercentChange_Indicator;
	
	public StockVolumeIndicator() {
		super();
	}
	public StockVolumeIndicator(String stockName, Date currDate, Date nextDate, BigDecimal currvol, BigDecimal nextvol,
			BigDecimal volumeDifference, float volumeChange_inPer, BigDecimal priceCurrClose, BigDecimal priceNextClose,
			BigDecimal priceDifference_curr_next, BigDecimal priceDiff_inPer, boolean volumePercentChange_Indicator,
			boolean pricePercentChange_Indicator) {
		super();
		StockName = stockName;
		CurrDate = currDate;
		NextDate = nextDate;
		Currvol = currvol;
		Nextvol = nextvol;
		VolumeDifference = volumeDifference;
		VolumeChange_inPer = volumeChange_inPer;
		PriceCurrClose = priceCurrClose;
		PriceNextClose = priceNextClose;
		PriceDifference_curr_next = priceDifference_curr_next;
		PriceDiff_inPer = priceDiff_inPer;
		VolumePercentChange_Indicator = volumePercentChange_Indicator;
		PricePercentChange_Indicator = pricePercentChange_Indicator;
	}
	
	public String getStockName() {
		return StockName;
	}
	public void setStockName(String stockName) {
		StockName = stockName;
	}
	public Date getCurrDate() {
		return CurrDate;
	}
	public void setCurrDate(Date currDate) {
		CurrDate = currDate;
	}
	public Date getNextDate() {
		return NextDate;
	}
	public void setNextDate(Date nextDate) {
		NextDate = nextDate;
	}
	public BigDecimal getCurrvol() {
		return Currvol;
	}
	public void setCurrvol(BigDecimal currvol) {
		Currvol = currvol;
	}
	public BigDecimal getNextvol() {
		return Nextvol;
	}
	public void setNextvol(BigDecimal nextvol) {
		Nextvol = nextvol;
	}
	public BigDecimal getVolumeDifference() {
		return VolumeDifference;
	}
	public void setVolumeDifference(BigDecimal volumeDifference) {
		VolumeDifference = volumeDifference;
	}
	public float getVolumeChange_inPer() {
		return VolumeChange_inPer;
	}
	public void setVolumeChange_inPer(float volumeChange_inPer) {
		VolumeChange_inPer = volumeChange_inPer;
	}
	public BigDecimal getPriceCurrClose() {
		return PriceCurrClose;
	}
	public void setPriceCurrClose(BigDecimal priceCurrClose) {
		PriceCurrClose = priceCurrClose;
	}
	public BigDecimal getPriceNextClose() {
		return PriceNextClose;
	}
	public void setPriceNextClose(BigDecimal priceNextClose) {
		PriceNextClose = priceNextClose;
	}
	public BigDecimal getPriceDifference_curr_next() {
		return PriceDifference_curr_next;
	}
	public void setPriceDifference_curr_next(BigDecimal priceDifference_curr_next) {
		PriceDifference_curr_next = priceDifference_curr_next;
	}
	public BigDecimal getPriceDiff_inPer() {
		return PriceDiff_inPer;
	}
	public void setPriceDiff_inPer(BigDecimal priceDiff_inPer) {
		PriceDiff_inPer = priceDiff_inPer;
	}
	public boolean isVolumePercentChange_Indicator() {
		return VolumePercentChange_Indicator;
	}
	public void setVolumePercentChange_Indicator(boolean volumePercentChange_Indicator) {
		VolumePercentChange_Indicator = volumePercentChange_Indicator;
	}
	public boolean isPricePercentChange_Indicator() {
		return PricePercentChange_Indicator;
	}
	public void setPricePercentChange_Indicator(boolean pricePercentChange_Indicator) {
		PricePercentChange_Indicator = pricePercentChange_Indicator;
	}
	
	}
