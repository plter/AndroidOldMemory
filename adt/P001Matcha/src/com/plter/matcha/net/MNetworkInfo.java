package com.plter.matcha.net;

import com.plter.matcha.Globals;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MNetworkInfo {

	
	private static ConnectivityManager __cm=null;
	public static boolean isNetworkOK(){
		if(__cm==null){
			__cm = (ConnectivityManager) Globals.getMainContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		
		NetworkInfo info = __cm.getActiveNetworkInfo();
		return info!=null?info.isAvailable():false;
	}
	
}
