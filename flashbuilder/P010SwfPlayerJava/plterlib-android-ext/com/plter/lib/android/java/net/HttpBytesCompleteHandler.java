package com.plter.lib.android.java.net;

import com.plter.lib.java.lang.ICallback;

public abstract class HttpBytesCompleteHandler implements ICallback<Object> {

	
	public boolean execute(Object... args) {
		onResult((byte[]) args[0]);
		return false;
	}
	
	public abstract void onResult(byte[] bytes);
	
}
