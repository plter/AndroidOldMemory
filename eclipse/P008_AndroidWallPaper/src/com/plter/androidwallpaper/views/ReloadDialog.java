package com.plter.androidwallpaper.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.plter.lib.java.lang.ICallback;

public class ReloadDialog {

	public static void show(final Context context,final ICallback<Void> callBack){
		new AlertDialog.Builder(context).setTitle("亲爱的")
		.setMessage("加载失败，请重新加载")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				callBack.execute();
			}
		}).show();
	}
	
}
