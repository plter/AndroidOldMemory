package com.plter.androidwallpaper.views;

import android.content.Context;

public class ProgressDialog {

	
	private static android.app.ProgressDialog currentProgressDialog=null;
	
	public static void show(Context context,String title,String msg){
		if (currentProgressDialog==null) {
			currentProgressDialog = android.app.ProgressDialog.show(context, title, msg);			
		}else{
			currentProgressDialog.setTitle(title);
			currentProgressDialog.setMessage(msg);
		}
	}
	
	public static void hide(){
		if (currentProgressDialog!=null) {
			currentProgressDialog.dismiss();
			currentProgressDialog=null;
		}
	}
	
}
