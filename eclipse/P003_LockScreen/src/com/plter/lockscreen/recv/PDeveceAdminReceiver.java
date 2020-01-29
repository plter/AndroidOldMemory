package com.plter.lockscreen.recv;

import com.plter.lockscreen.LockConfigAty;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class PDeveceAdminReceiver extends DeviceAdminReceiver {

	
	
	private static ComponentName cn;
	
	
	/**
	 * 取得管理组件名称对象
	 * @param context
	 * @return
	 */
	public static ComponentName getComponentName(Context context){
		if(cn==null){
			cn=new ComponentName(context, PDeveceAdminReceiver.class);
		}
		return cn;
	}
	
	@Override
	public void onEnabled(Context context, Intent intent) {
		LockConfigAty.getLockConfigAty().refreshEnableOpenLockCtv();
		
		super.onEnabled(context, intent);
	}
	
	@Override
	public void onDisabled(Context context, Intent intent) {
		LockConfigAty.getLockConfigAty().refreshEnableOpenLockCtv();
		
		super.onDisabled(context, intent);
	}
}
