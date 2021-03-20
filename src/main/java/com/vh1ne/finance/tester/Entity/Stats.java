package com.vh1ne.finance.tester.Entity;

import java.math.BigDecimal;

//@author vh1ne
public class Stats {
	
	BigDecimal diff; // calculates difference between two consecutive interval
	BigDecimal diffPercentage;  // calculates percentage difference between two consecutive interval
	
	public BigDecimal getDiff() {
		return diff;
	}
	@Override
	public String toString() {
		return "Stats [diff=" + diff + ", diffPercentage=" + diffPercentage + "]";
	}
	public Stats() {
		super();
	}
	public Stats(BigDecimal diff, BigDecimal diffPercentage) {
		super();
		this.diff = diff;
		this.diffPercentage = diffPercentage;
	}
	public void setDiff(BigDecimal diff) {
		this.diff = diff;
	}
	public BigDecimal getDiffPercentage() {
		return diffPercentage;
	}
	public void setDiffPercentage(BigDecimal diffPercentage) {
		this.diffPercentage = diffPercentage;
	}
	
}
