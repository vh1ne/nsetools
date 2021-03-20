package com.vh1ne.zerodha.postprocess.entities;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

public class Order {

	//Time,Type,Instrument,Product,Qty.,Avg. price,Status
	
	LocalDateTime time;
	String type;
	String instrument;
	String product;
	double quantity;
	double price;
	String status;
	boolean isCnc;
	public boolean isCnc() {
		return isCnc;
	}
	public void setCnc(boolean isCnc) {
		this.isCnc = isCnc;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(@NonNull LocalDateTime time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(@NonNull String type) {
		this.type = type;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument( @NonNull String instrument) {
		this.instrument = instrument;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(@NonNull String product) {
		this.product = product;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(@NonNull double quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice( @NonNull double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(@NonNull String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Order [time=" + time + ", type=" + type + ", instrument=" + instrument + ", product=" + product
				+ ", quantity=" + quantity + ", price=" + price + ", status=" + status + ", isCnc=" + isCnc + "]";
	}
	public Order(LocalDateTime time, String type, String instrument, String product, double quantity, double price,
			String status) {
		super();
		this.time = time;
		this.type = type;
		this.instrument = instrument;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
	}
	public Order() {
		super();
	}
	
	public boolean isOrder(Order o)
	{
		return 
		o!=null && o.getInstrument()!=null && !o.getInstrument().equals("") && 
		o.getProduct()!=null && !o.getProduct().equals("")	&&
		o.getType()!=null && !o.getType().equals("") &&
		  o.getQuantity()!=0  && o.getTime()!=null && !o.getTime().equals("")
		
		;
	}
	
}
