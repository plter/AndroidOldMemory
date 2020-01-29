package com.plter.weather.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;

public class Config {

	
	public static final String GOOGLE_BASE_DOMAIN="http://www.google.com";
	
	
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences("PlterGoogleWeather", Context.MODE_PRIVATE);
	}
	
	public static Editor getEditor(Context context){
		return getSharedPreferences(context).edit();
	}
	
	public static String getCachedCity(Context context){
		return getSharedPreferences(context).getString("currentCity", null);
	}
	
	public static void setCachedCity(Context context,String city){
		Editor editor = getEditor(context);
		editor.putString("currentCity", city);
		editor.commit();
	}
	
	public static String getCachedWeatherStr(Context context){
		return getSharedPreferences(context).getString("cachedWeatherStr", null);
	}
	
	public static void setCachedWeatherStr(Context context,String weatherStr){
		Editor editor = getEditor(context);
		editor.putString("cachedWeatherStr", weatherStr);
		editor.putLong("lastCacheWeatherTime", SystemClock.elapsedRealtime());
		editor.commit();
	}
	
	public static long getLastCacheWeatherTime(Context context){
		return getSharedPreferences(context).getLong("lastCacheWeatherTime", 0);
	}
}
