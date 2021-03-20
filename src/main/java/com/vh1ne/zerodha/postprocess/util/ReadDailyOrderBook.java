package com.vh1ne.zerodha.postprocess.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vh1ne.zerodha.postprocess.entities.Order;

public class ReadDailyOrderBook {
	
	Logger logger = LoggerFactory.getLogger(ReadDailyOrderBook.class);
	 void readOrderBook()
	{
		 logger.info("readOrderBook");
		}
	 
	 public List<Order> read ( Path path )
	    {
	        

	        List < Order > list = new ArrayList<Order>();  // Default to empty list.
	        try
	        {
	            // Prepare list.
	            int initialCapacity = ( int ) Files.lines( path ).count();
	            list = new ArrayList <>( initialCapacity );

	            // Read CSV file. For each row, instantiate and collect `DailyProduct`.
	            BufferedReader reader = Files.newBufferedReader( path );
	            Iterable < CSVRecord > records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse( reader );
	            for ( CSVRecord record : records )
	            {
	            	//Time,Type,Instrument,Product,Qty.,Avg. price,Status
	            	LocalDateTime time= formatDateTime(record.get("Time"), "yyyy-MM-dd HH:mm:ss");
	            	String type = record.get( "Type" );
	                String instrument = record.get("Instrument");
	                String product =record.get("Product");
	                String qty=record.get("Qty.").split("/")[0];
	                
	                double quantity =Double.parseDouble(qty);
	                double price = Double.parseDouble(record.get("Avg. price"));
	                String status = record.get("Status");
	            	//Order  = new Order();
	            	Order order= new Order(time, type, instrument, product, quantity, price, status);
	            
	                list.add( order );
	            }
	        } catch ( IOException e )
	        {
	        	logger.error("IOException");
	            e.printStackTrace();
	        }
	        catch ( Exception e )
	        {
	            e.printStackTrace();
	        }
	        return list;
	    }
	 
	 @SuppressWarnings(value = { "unused" })
	 	private LocalDateTime formatDateTime(String str,String inputFormat,String outputFormat)
	 	{
	 	LocalDateTime dateTime =LocalDateTime.of(0001, 01, 01,00,00);
 		try {
 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(inputFormat);
 		dateTime = LocalDateTime.parse(str, formatter);
 		
 		}
 		catch (Exception e) {
			logger.error("Error in formatting datetime please check input string = " + str + ", input format  = " +inputFormat +"and output format ="+outputFormat + " are correct " );
			e.printStackTrace();
		}
 		return dateTime;
	 	}
	 
	 	private LocalDateTime formatDateTime(String str,String inputFormat)
	 	{
	 		LocalDateTime dateTime =LocalDateTime.of(0001, 01, 01,00,00);
	 		try {
	 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(inputFormat);
	 		dateTime = LocalDateTime.parse(str, formatter);
	 		
	 		}
	 		catch (Exception e) {
				logger.error("Error in formatting datetime please check input string = " + str + " and input format  =" +inputFormat + " are correct " );
				e.printStackTrace();
			}
	 		return dateTime;
	 	}
	 
	 	void calcBrokrage(List<Order> list)
	 	{
	 		
	 		list =list.stream().
					filter(i -> i.getStatus().equalsIgnoreCase("COMPLETE")).
					collect(Collectors.toList());
	 		
	 	}
	
	 	public Pair<HashMap<String, Order[]>, HashMap<String, Order[]>> createTransaction(List<Order> list)
	 	{
	 		 		
		/*
		 * double brokrage=0; double stt=0; double txnCharge=0; double gst=0; double
		 * sebiCharges=0; double stampcharges=0;
		 */
	 		
	 		List<Order> misList =list.stream().
					filter(i -> i.getStatus().equalsIgnoreCase("COMPLETE") && (i.getProduct().equalsIgnoreCase("MIS") || i.getProduct().equalsIgnoreCase("CNC") && !i.getInstrument().contains("NIFTY")  )).
					collect(Collectors.toList());
	 		//System.out.println(misList);
		
			HashMap<String, Order[]> map = new HashMap<String, Order[]>();
	 		for(Order o: misList)
	 		{ 
	 		if(o.isOrder(o) )
	 		{
	 			//logger.debug(o.toString());
	 			
	 			String key = o.getInstrument()+o.getProduct();
	 			Order [] arr= new Order[2];
	 			
	 			if(map.containsKey(key))
	 			{
	 				if(o.getType().equalsIgnoreCase("BUY"))
	 				{
	 				if(  map.get(key)[0]!=null)
	 				{ Order b=map.get(key)[0];
	 				 double old_qty=	b.getQuantity() ;
	 				 double old_price = b.getPrice();
	 				 double new_qty= o.getQuantity();
	 				 double new_price =o.getPrice();
	 				 
	 				 double qty= old_qty+ new_qty;
	 				 
	 				 double price= ((old_price * old_qty)+(new_qty* new_price )) / qty;
	 				 
	 				b.setQuantity(qty);
	 				b.setPrice(price);
	 				qty=0;
	 				price=0;
	 				}
	 				else
	 				{
	 					  map.get(key)[0]=o;
	 				}
	 				}
	 				else if(o.getType().equalsIgnoreCase("SELL"))
	 				{
	 					if(  map.get(key)[1]!=null)
		 				{ 	
	 					Order b=map.get(key)[1];
	 				 double old_qty=	b.getQuantity() ;
	 				 double old_price = b.getPrice();
	 				 double new_qty= o.getQuantity();
	 				 double new_price =o.getPrice();
	 				 
	 				 double qty= old_qty+ new_qty;
	 				 
	 				 double price= ((old_price * old_qty)+(new_qty* new_price )) / qty;
	 				 
	 				b.setQuantity(qty);
	 				b.setPrice(price);
	 				qty=0;
	 				price=0;
		 				}
		 				else
		 				{
		 					  map.get(key)[1]=o;
		 				}
	 				 
	 				}
	 					
	 			}
	 			else
	 			{
	 				if(o.getType().equalsIgnoreCase("BUY"))
	 				{
	 					arr[0]=o;
	 				}
	 				else if(o.getType().equalsIgnoreCase("SELL"))
	 				{
	 					arr[1]=o;
	 				}
	 				map.put(key, arr);
	 				
	 			}
	 		  }
	 		}
	 		map.forEach((k,v)-> System.out.println(k + " " + v[0] + " " +v[1] )) ; 
	 	
	 		Pair<HashMap<String, Order[]>, HashMap<String, Order[]>> p=
	 				Pair.create(
	 						//key = valid txn
	 						(HashMap<String, Order[]>)map.entrySet().stream().filter(x -> (x.getValue()[0]!=null && x.getValue()[1]!= null &&x.getValue()[0].getQuantity()==x.getValue()[1].getQuantity())).collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()))
	 						, 
	 						//val = key cnc orders 
	 						(HashMap<String, Order[]>)map.entrySet().stream().filter(x -> (x.getValue()[0]==null || x.getValue()[1]== null || x.getValue()[0].getQuantity()!=x.getValue()[1].getQuantity())).collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()))
	 						);
	 
	 		return p;
	 				//(HashMap<String, Order[]>)map.entrySet().stream().filter(x -> (x.getValue()[0]!=null && x.getValue()[1]!= null &&x.getValue()[0].getQuantity()==x.getValue()[1].getQuantity())).collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
	 	}
	 
	
}
