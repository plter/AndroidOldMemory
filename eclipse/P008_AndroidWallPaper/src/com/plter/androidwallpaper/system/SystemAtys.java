package com.plter.androidwallpaper.system;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class SystemAtys {

	
	public static void showNetworkConfigAty(Context context){
		Intent i =new Intent(Intent.ACTION_VIEW);
		i.setComponent(new ComponentName("com.android.settings","com.android.settings.WirelessSettings"));
		context.startActivity(i);
	}
	
}
