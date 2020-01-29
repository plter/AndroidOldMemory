package com.plter.weather.srvs;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.plter.weather.config.Config;
import com.plter.weather.net.GoogleWeatherLoader;


public class WeatherService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onCreate() {
		updateWeatherHandler.sendEmptyMessageDelayed(0, DELAY);
		super.onCreate();
	}
	
	
	private final Handler updateWeatherHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			String city = Config.getCachedCity(WeatherService.this);
			if (city!=null) {
				GoogleWeatherLoader.loadWeather(WeatherService.this,city, null, null,true);
			}
			
			updateWeatherHandler.sendEmptyMessageDelayed(0, DELAY);
		};
	};
	
	
	@Override
	public void onDestroy() {
		updateWeatherHandler.removeMessages(0);
		super.onDestroy();
	}

	private static final int DELAY = 7200000;
}
