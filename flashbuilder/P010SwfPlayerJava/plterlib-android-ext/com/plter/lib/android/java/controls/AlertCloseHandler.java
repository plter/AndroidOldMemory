package com.plter.lib.android.java.controls;

import com.plter.lib.java.lang.ICallback;

public abstract class AlertCloseHandler implements ICallback<Integer> {

	
	public boolean execute(Integer... args) {
		onAlertClose(args[0]);
		return true;
	}
	
	
	public abstract void onAlertClose(int flag);
	
}
