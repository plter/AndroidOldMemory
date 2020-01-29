package com.plter.lockscreen;

import com.plter.lockscreen.mgrs.SystemManager;
import com.plter.lockscreen.services.ProximityService;

import android.app.Activity;
import android.os.Bundle;

public class LSAty extends Activity{

	
	protected SystemManager getSystemManager(){
		return SystemManager.createSystemManager(this);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!ProximityService.running) {
			getSystemManager().startProximityService();
		}
		super.onCreate(savedInstanceState);
	}
}
