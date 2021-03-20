package com.vh1ne.finance.tester.Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class DownlodFileApache {

	public static void main(String[] args)  {
		
		System.out.println("----------------");
		try {
			//nioDownload();
		//	printDate();
			//printDate1();
			
			setProxy();
			System.out.println("end try before fis--------");
			new FileInputStream("");
	} catch (FileNotFoundException e) {
		System.out.println("Could not find file  " + e.getMessage());
	}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void nioDownload() throws IOException 
	{
		FileOutputStream fos =null;
		try 
		{
		URL website = new URL("https://www1.nseindia.com/content/fo/fii_stats_19-Mar-2021.xls");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		 fos = new FileOutputStream("E:\\Work\\Files\\temp\\fii_stats_19-Mar-2021.xls");
		 
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		System.out.println(fos.toString());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			fos.close();
		}
	}
	
	public static void setProxy() throws IOException
	{
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "3128");
		
		URL website = new URL("https://www1.nseindia.com/");
		Proxy webProxy 
		  = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
		HttpURLConnection webProxyConnection 
		  = (HttpURLConnection) website.openConnection(webProxy);
	webProxyConnection.connect();
	
	System.out.println(" using proxy ?" +webProxyConnection.usingProxy());
	
	}
	
	
	public static void  printDate()
	{
	        LocalDate date = LocalDate.of(Integer.parseInt("2021"), Integer.parseInt("3"), 01);
	        Calendar c = Calendar.getInstance();
	        for (int i = 0; i < date.lengthOfMonth(); i++) {
	        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-YYYY"); 
	        	
	        	if(date.getDayOfWeek()==DayOfWeek.SUNDAY || date.getDayOfWeek()==DayOfWeek.SATURDAY)
	            	//	System.out.println("Day of week " + date.getDayOfWeek());
	        	System.out.println(formatter.format(date)); 
	            date = date.plusDays(1);
	        }

	}
	public static void  printDate1()
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		for (int i = 0; i <= 20; i++) {
			if(LocalDateTime.ofInstant(c.toInstant(), ZoneId.systemDefault()).getDayOfWeek()!=DayOfWeek.SATURDAY && LocalDateTime.ofInstant(c.toInstant(), ZoneId.systemDefault()).getDayOfWeek()!=DayOfWeek.SUNDAY)
		    System.out.println(df.format(c.getTime()));
		    c.add(Calendar.DATE, 1);
		}
	}
}
