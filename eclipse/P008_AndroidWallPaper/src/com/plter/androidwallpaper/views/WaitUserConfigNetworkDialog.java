package com.plter.androidwallpaper.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.plter.androidwallpaper.net.Category;
import com.plter.androidwallpaper.net.Net;
import com.plter.androidwallpaper.system.SystemAtys;
import com.plter.lib.java.lang.ICallback;

public class WaitUserConfigNetworkDialog {

	
	public static void show(final Context context,final ICallback<Category[]> callback){
		new AlertDialog.Builder(context)
		.setTitle("亲爱的")
		.setMessage("请先连接网络")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				showWaitConnectNetworkDialog(context, callback);
			}
		})
		.show();
	}
	
	
	private static void showWaitConnectNetworkDialog(final Context context,final ICallback<Category[]> callback){
		new AlertDialog.Builder(context)
		.setTitle("亲爱的")
		.setMessage("已经连接网络了吗？")
		.setPositiveButton("是的", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Net.loadCategory(context, callback);
			}
		})
		.setNegativeButton("还没", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				WaitUserConfigNetworkDialog.show(context, callback);
			}
		}).show();
		
		SystemAtys.showNetworkConfigAty(context);
	}
	
}
