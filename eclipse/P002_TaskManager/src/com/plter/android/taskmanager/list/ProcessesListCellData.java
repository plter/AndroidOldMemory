package com.plter.android.taskmanager.list;

import com.plter.android.taskmanager.TaskManagerActivity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;

public class ProcessesListCellData implements OnClickListener {
	
	public ProcessesListCellData(Context context,RunningAppProcessInfo info) {
		runningAppProcessInfo=info;
		processName=info.processName;
		this.context=context;
		pm=this.context.getPackageManager();
		am=(ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE);
		
		try {
			appInfo=pm.getApplicationInfo(processName, PackageManager.GET_META_DATA);
			appName=pm.getApplicationLabel(appInfo).toString();
			appIcon=pm.getApplicationIcon(appInfo);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	public RunningAppProcessInfo getRunningAppProcessInfo() {
		return runningAppProcessInfo;
	}
	
	public void killProcess() {
		am.killBackgroundProcesses(processName);
	}


	private RunningAppProcessInfo runningAppProcessInfo=null;
	
	public String processName="";
	
	public String appName="";
	
	public boolean selected=true;
	
	public Drawable appIcon=null;
	
	private Context context;
	private PackageManager pm;
	private ApplicationInfo appInfo;
	private ActivityManager am;
	
	
	
	@Override
	public void onClick(View arg0) {
		killProcess();
		
		((TaskManagerActivity)context).showProcesses();
	}
}
