package com.plter.androidwallpaper.test;

import android.test.AndroidTestCase;

import com.plter.lib.android.java.net.Http;

public class TestDir extends AndroidTestCase{
	
	public void testDir(){
		System.out.println(Http.getHttpCachedDataDir().getAbsolutePath());
	}
}
