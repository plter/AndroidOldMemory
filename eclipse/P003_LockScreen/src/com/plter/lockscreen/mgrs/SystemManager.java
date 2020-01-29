package com.plter.lockscreen.mgrs;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.plter.lockscreen.recv.PDeveceAdminReceiver;
import com.plter.lockscreen.services.ProximityService;

public class SystemManager {
	
	public SystemManager(Context context) {
		
		
		this.context=context;
	}
	
	
	private static SystemManager __systemManager=null;
	
	public static SystemManager createSystemManager(Context context) {
		if (__systemManager==null) {
			__systemManager=new SystemManager(context.getApplicationContext());
		}
		return __systemManager;
	}
	
	
	private Context context=null;
	
	public Context getContext() {
		return context;
	}


	public static final int REQUEST_CODE_ENABLE_ADMIN=1;
	
	
	private DevicePolicyManager dpm=null;
	
	/**
	 * 取得设备管理器
	 * @param context 
	 * @return
	 */
	public DevicePolicyManager getDevicePolicyManager(){
		if(dpm==null){
			dpm=(DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		}
		return dpm;
	}

	/**
	 * 是否有权限使用设备管理器
	 * @param context
	 * @return
	 */
	public boolean hasPermission(){
		return getDevicePolicyManager().isAdminActive(PDeveceAdminReceiver.getComponentName(context));
	}
	
	/**
	 * 移除使用设备管理器权限
	 * @param context
	 */
	public void removePermission(){
		getDevicePolicyManager().removeActiveAdmin(PDeveceAdminReceiver.getComponentName(context));
	}
	
	
	/**
	 * 取得使用设备管理器权限
	 * @param aty 调用呈现设备管理器激活界面的Activity
	 * @param msg 呈现给用户的提示信息
	 */
	public void showGetPermissionActivity(Activity aty,String msg){
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                PDeveceAdminReceiver.getComponentName(aty));
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                msg);
        
        aty.startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
	}
	
	/**
	 * 锁定屏幕
	 */
	public void lockScreen(){
		getDevicePolicyManager().lockNow();
		System.exit(0);
	}
	
	private PowerManager.WakeLock wakeLockWL=null;
	public PowerManager.WakeLock getWakeLockWL() {
		if(wakeLockWL==null){
			wakeLockWL=getPowerManager().newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.PARTIAL_WAKE_LOCK, "WakeLock");
		}
		
		return wakeLockWL;
	}

	private PowerManager.WakeLock turnOnScreenWL=null;
	
	public PowerManager.WakeLock getTurnOnScreenWL() {
		turnOnScreenWL=getPowerManager().newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "TurnOnScreen");
		return turnOnScreenWL;
	}
	
	
	private PowerManager pm=null;
	/**
	 * 取得电源管理器
	 * @param context
	 * @return
	 */
	public PowerManager getPowerManager(){
		if(pm==null){
			pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
		}
		return pm;
	}
	
	/**
	 * 将屏幕点亮
	 */
	public void turnOnScreen(){
		
		if(!getPowerManager().isScreenOn()){
			getTurnOnScreenWL().acquire(3000);
		}
	}
	
	
	/**
	 * 启用距离感应器服务
	 */
	public void startProximityService() {
		getContext().startService(ProximityService.PROXIMITY_SERVICE_INTENT);
	}
	
	/**
	 * 停止距离感应器服务
	 */
	public void stopProxymityService() {
		getContext().stopService(ProximityService.PROXIMITY_SERVICE_INTENT);
	}
}
