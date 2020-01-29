package com.plter.lockscreen.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.plter.lockscreen.mgrs.SystemManager;

public abstract class LSService extends Service implements SensorEventListener{
	
	
	public LSService(int sensorType) {
		this.sensorType=sensorType;
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onCreate() {
		
		//取得传感器服务
		sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//取得传感器
		s=sm.getDefaultSensor(sensorType);
		
		//注册传感器侦听器
		sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
		
		getSystemManager().getWakeLockWL().acquire();
		
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		sm.unregisterListener(this);
		getSystemManager().getWakeLockWL().release();
		super.onDestroy();
	}
	
	
	/**
	 * 取得系统管理类
	 * @return
	 */
	protected SystemManager getSystemManager() {
		return SystemManager.createSystemManager(this);
	}
	
	protected int getSensorType() {
		return sensorType;
	}
	
	
	//////////////////////////private values/////////////////////////////////
	
	private int sensorType=0;
	private SensorManager sm;
	private Sensor s;
}
