package com.plter.androidwallpaper.services;

import java.io.File;
import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.plter.androidwallpaper.config.Config;
import com.plter.androidwallpaper.system.FileTool;
import com.plter.androidwallpaper.system.WallPaperTool;

public class AutoChangeWallPaperService extends Service {


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public void onCreate() {
		Log.i(TAG, "Create");

		startChangeWallpaperHandler();
		super.onCreate();
	}

	@Override
	public void onDestroy() {		
		Log.i(TAG, "Destory");

		stopChangeWallpaperHandler();
		super.onDestroy();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "StartCommand");

		refreshWallpaperDir();
		return super.onStartCommand(intent, flags, startId);
	}

	private File imgFile=null;
	private void changeWallpaper(){
		if (imgFiles==null||imgFiles.size()<=0) {
			refreshWallpaperDir();
		}
		if (imgFiles==null||imgFiles.size()<=0) {
			return;
		}

		int index = Config.getCurrentImageIndex(AutoChangeWallPaperService.this);
		index++;
		if (index>=imgFiles.size()) {
			index=0;
		}
		Config.setCurrentImageIndex(AutoChangeWallPaperService.this, index);

		imgFile = imgFiles.get(index);
		if (imgFile.exists()) {
			WallPaperTool.setWallpaper(AutoChangeWallPaperService.this, imgFile.getAbsolutePath());
		}
	}


	/**
	 * 启动改变壁纸的功能
	 */
	public void startChangeWallpaperHandler(){
		changeWallpaperHandler.sendEmptyMessage(0);
	}

	public void stopChangeWallpaperHandler(){
		changeWallpaperHandler.removeMessages(0);
	}


	private final Handler changeWallpaperHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			changeWallpaper();

			if (Config.isAutoChangeWallPaper(AutoChangeWallPaperService.this)) {
				changeWallpaperHandler.sendEmptyMessageDelayed(0, Config.getPeriod(AutoChangeWallPaperService.this));
			}
		};
	};

	//////////////////////////////static/////////////////////

	public static void checkToStartOrStopService(Context context){		
		Intent i =new Intent(context, AutoChangeWallPaperService.class);

		if (Config.isAutoChangeWallPaper(context)) {			
			context.startService(i);
		}else{
			context.stopService(i);
		}
	}

	public static void refreshWallpaperDir(){
		imgFiles.clear();

		String type=null;
		String name=null;
		File[] files = FileTool.androidWallpaperDir().listFiles();

		if (files!=null&&files.length>0) {
			for (File f : files) {
				name=f.getName();
				type=name.substring(name.lastIndexOf("."));
				if (type.equals(".jpg")) {
					imgFiles.add(f);
				}
			}
		}
	}

	private static final ArrayList<File> imgFiles=new ArrayList<File>();
	private static final String TAG="AutoChangeWallpaperService";

	//////////////////////////////////////////////////////////

}
