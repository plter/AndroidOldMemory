package com.plter.lockscreen.recv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.plter.lockscreen.mgrs.SystemManager;
import com.plter.lockscreen.services.ProximityService;


/**
 * 启动完成广播接收器
 * @author xtiqin
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		SystemManager sm=SystemManager.createSystemManager(arg0);
		
		if (!ProximityService.running) {
			sm.startProximityService();
		}
	}

}
