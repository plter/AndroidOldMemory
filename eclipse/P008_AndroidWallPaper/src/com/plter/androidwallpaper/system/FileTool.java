package com.plter.androidwallpaper.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.plter.lib.java.utils.TimeUtil;

public class FileTool {

	
	public static boolean saveFile(byte[] bytes,String path){
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			
			//write file
			fos.write(bytes);
			fos.close();
			
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public static boolean saveWallpaperFile(byte[] bytes){
		if (!androidWallpaperDir().exists()) {
			androidWallpaperDir().mkdirs();
		}
		
		return FileTool.saveFile(bytes, SAVED_WALLPAPER_DIR_PATH+TimeUtil.getCurrentTimeString("","","")+".jpg");
	}
	
	public static final String SAVED_WALLPAPER_DIR_PATH = "/mnt/sdcard/AndroidWallpaper/";
	
	public static File androidWallpaperDir(){
		File f= new File(SAVED_WALLPAPER_DIR_PATH);
		if (!f.exists()) {
			f.mkdirs();
		}
		return f;
	}
	
}
