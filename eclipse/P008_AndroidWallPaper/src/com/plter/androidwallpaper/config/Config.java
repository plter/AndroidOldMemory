package com.plter.androidwallpaper.config;

import com.plter.lib.java.evt.EventListenerList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Config{

	public static final String URL="http://androidwallpaper.sinaapp.com/server.php";
	
	
	public static Boolean isAutoChangeWallPaper(Context context){
		return getSharedPreferences(context).getBoolean("autoChangeWallPaper", false);
	}
	
	public static void setAutoChangeWallPaper(Context context,Boolean willAutoChange){
		Editor e = getEditor(context);
		
		e.putBoolean("autoChangeWallPaper", willAutoChange);
		e.commit();
		
		autoChangeWallpaperSettingsChange.dispatch(willAutoChange);
	}
	
	
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences("com.plter.androidwallpaper.AndroidWallPaper", Context.MODE_WORLD_WRITEABLE);
	}
	
	public static Editor getEditor(Context context){
		return getSharedPreferences(context).edit();
	}
	
	
	/**
	 * 取得切换图片时的间隔
	 * @return
	 */
	public static int getPeriod(Context context) {
		return getSharedPreferences(context).getInt("period", 60000);
	}

	public static void setPeriod(Context context,int period) {
		Editor e = getEditor(context);
		
		e.putInt("period", period);
		e.commit();
	}
	
	
	public static int getCurrentImageIndex(Context context) {
		return getSharedPreferences(context).getInt("currentImageIndex", -1);
	}

	public static void setCurrentImageIndex(Context context,int currentImageIndex) {
		Editor e = getEditor(context);
		e.putInt("currentImageIndex", currentImageIndex);
		e.commit();
	}

	
	public static final EventListenerList<Boolean> autoChangeWallpaperSettingsChange=new EventListenerList<Boolean>(); 
}
