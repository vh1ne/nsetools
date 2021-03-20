package com.vh1ne.zerodha.postprocess.Tester;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vh1ne.zerodha.postprocess.entities.Order;
import com.vh1ne.zerodha.postprocess.util.ReadDailyOrderBook;

public class ReadDailyOrderBookTester {


	static Logger logger = LoggerFactory.getLogger(ReadDailyOrderBookTester.class);
	public static void main(String[] args) {
		logger.info("Main metod - ReadDailyOrderBookTester");
		ReadDailyOrderBook ob= new ReadDailyOrderBook();
		
		Instant start = Instant.now();
	        Path pathInput = Paths.get( "E:\\Downloads\\Chrome Downloads\\orders.csv" );
	        System.out.println("--------------");
	       
	       
	        List < Order > list = ob.read( pathInput );
	      //  System.out.println(ob.calculateBrokrage(list));
	        
	      //  ob.createTransaction(list).forEach((k,v) -> System.out.println(k +"  " + v[0] + v[1]) );
	      System.out.println();
	        logger.info("txn size " + ob.createTransaction(list).getKey().size());
	        ob.createTransaction(list).getKey().forEach((k,v) -> System.out.println(k +"  " + v[0] + v[1]) );
	        System.out.println();
	        logger.info("cnc size " +ob.createTransaction(list).getValue().size());
	        ob.createTransaction(list).getValue().forEach((k,v) -> System.out.println(k +"  " + v[0] + v[1]) );
	     //  ob.calculateBrokrage(list);
	      //for(HashMap<String, Order[] > l :ob.calculateBrokrage(list))
	      {
	    //	  l.forEach((k,v) -> System.out.println(k +"  " + v[0] + v[1]) );
	      }
	        Instant end = Instant.now();
	        /*
		(list.stream().filter(i -> i.getStatus().equalsIgnoreCase("COMPLETE")
		// && (i.getProduct().equalsIgnoreCase("MIS")
		// ||i.getProduct().equalsIgnoreCase("CNC"))
		).collect(Collectors.toList())).stream()
				.sorted(Comparator.comparing(Order::getTime)
				 .thenComparing(Order::getInstrument))
				.forEach(System.out::println);
		
	       // list.forEach(System.out::println);
	        
	         * 
	         */
	        
	        logger.info(Duration.between(start, end).toString()); 
	    
	}
}
