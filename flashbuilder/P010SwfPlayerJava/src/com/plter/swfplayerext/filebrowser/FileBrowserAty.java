package com.plter.swfplayerext.filebrowser;

import android.content.Intent;
import android.os.Bundle;

import com.adobe.fre.FREContext;
import com.plter.lib.android.java.controls.ViewControllerActivity;

public class FileBrowserAty extends ViewControllerActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(new FileBrowserView(this, this,null));
	}
	
	public static void start(final FREContext context,final OnSwfFileSelectListener fileSelectListener){
		FileBrowserAty.setOnSwfFileSelectListener(fileSelectListener);
		context.getActivity().startActivity(new Intent(context.getActivity(), FileBrowserAty.class));		
	}
	
	public static OnSwfFileSelectListener getOnSwfFileSelectListener() {
		return onSwfFileSelect;
	}
	public static void setOnSwfFileSelectListener(OnSwfFileSelectListener onSwfFileSelect) {
		FileBrowserAty.onSwfFileSelect = onSwfFileSelect;
	}


	private static OnSwfFileSelectListener onSwfFileSelect=null;
	
}
