package com.plter.lockscreen.services;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.SystemClock;

public class ProximityService extends LSService {

	
	
	/**
	 * 该服务所在的地址
	 */
	public static final String INTENT_ACTION_NAME="com.plter.lockscreen.intent.action.services.ProximityService";
	
	
	/**
	 * 取得服务地址对象
	 */
	public static final Intent  PROXIMITY_SERVICE_INTENT=new Intent("com.plter.lockscreen.intent.action.services.ProximityService");
	
	public ProximityService() {
		super(Sensor.TYPE_PROXIMITY);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onCreate() {
		running=true;
		super.onCreate();
	}
	
	
	@Override
	public void onDestroy() {
		running=false;
		super.onDestroy();
	}
	
	
	private long lastCurrentTime=0;

	@Override
	public void onSensorChanged(SensorEvent event) {
		float value=event.values[0];
		
//		System.out.println(value);
		
		if (value<=0) {
			lastCurrentTime=SystemClock.elapsedRealtime();
		}
		if (value>=1) {
			if (SystemClock.elapsedRealtime()-lastCurrentTime<500) {
				getSystemManager().turnOnScreen();
			}
		}
	}
	
	
	public static boolean running=false;

}
