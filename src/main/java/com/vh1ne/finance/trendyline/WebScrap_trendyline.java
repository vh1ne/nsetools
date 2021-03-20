package com.vh1ne.finance.trendyline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebScrap_trendyline {

	public static void main(String[] args) {
		
		String url ="https://trendlyne.com/equity/630";
		try {
			readFromWeb(url);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	

	  public static void readFromWeb(String webURL) throws IOException {
	        URL url = new URL(webURL);
	        InputStream is =  url.openStream();
	        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                System.out.println(line);
	            }
	        }
	        catch (MalformedURLException e) {
	            e.printStackTrace();
	            throw new MalformedURLException("URL is malformed!!");
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	            throw new IOException();
	        }
	  }
}
