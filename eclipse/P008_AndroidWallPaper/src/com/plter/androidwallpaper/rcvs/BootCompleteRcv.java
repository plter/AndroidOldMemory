package com.plter.androidwallpaper.rcvs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.plter.androidwallpaper.services.AutoChangeWallPaperService;

public class BootCompleteRcv extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {		
		AutoChangeWallPaperService.checkToStartOrStopService(context);
	}

}
