package com.github.pnpninja.nsetools.domain;

import com.github.pnpninja.nsetools.NSETools;

public class TestNse {

	public static void main(String[] args) throws Exception {
		NSETools nse = new NSETools();
		System.out.println(nse.getIndexList().size());
		System.exit(0);
		
	}
}
