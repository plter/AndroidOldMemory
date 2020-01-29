package com.plter.androidwallpaper.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;

public class WallPaperTool {

	
	private static WallpaperManager wallpaperManager=null;
	
	public static boolean setWallpaper(Context context,Bitmap bitmap){
		
		if (bitmap==null) {
			return false;
		}
		
		wallpaperManager = getWallpaperManager(context);
		try {
			wallpaperManager.setBitmap(bitmap);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean setWallpaper(Context context,String imgFilePath){
		wallpaperManager=getWallpaperManager(context);
		
		try {
			FileInputStream fis = new FileInputStream(new File(imgFilePath));
			boolean value = setWallpaper(context, fis);
			fis.close();
			
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean setWallpaper(Context context,InputStream in){
		wallpaperManager=getWallpaperManager(context);
		try {
			wallpaperManager.setStream(in);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static WallpaperManager getWallpaperManager(Context context){
		return (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);
	}
	
}
