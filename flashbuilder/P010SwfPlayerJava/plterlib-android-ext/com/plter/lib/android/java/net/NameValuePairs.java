package com.plter.lib.android.java.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class NameValuePairs {
	
	public NameValuePairs(String name,String value,String... nv) {
		httpClientNameValuePairs.add(new BasicNameValuePair(name, value));
		
		if (nv.length<2) {
			return;
		}
		
		if (nv.length%2==1) {
			throw new RuntimeException("name value pair must be even");
		}
		
		for (int i = 0; i < nv.length; i+=2) {
			httpClientNameValuePairs.add(new BasicNameValuePair(nv[i], nv[i+1]));
		}
	}
	
	final List<NameValuePair> httpClientNameValuePairs =new ArrayList<NameValuePair>();
}
