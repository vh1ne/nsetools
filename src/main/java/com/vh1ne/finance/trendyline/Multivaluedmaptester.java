package com.vh1ne.finance.trendyline;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public class Multivaluedmaptester {

	public static void main(String[] args) {
		
		 
		 
		
		MultiValuedMap<String, Object> map = new ArrayListValuedHashMap<String, Object>();
		
		map.put("fruits", "apple");
		map.put("fruits", new BigDecimal("1000000000000000000000"));
		map.put("fruits", LocalDate.now());
		
		map.put("motor", "tata");
		map.put("f", "tesla");
		
		
		//Collection<Map.Entry<String, String>> entries = map.entries();
		map.asMap().forEach((k,v) -> System.out.println(k+ " " +v.toString()));
		//System.out.println(entries);
	}

}
