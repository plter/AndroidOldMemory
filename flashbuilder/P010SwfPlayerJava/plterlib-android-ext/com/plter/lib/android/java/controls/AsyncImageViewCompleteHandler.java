package com.plter.lib.android.java.controls;

import android.graphics.Bitmap;

import com.plter.lib.java.lang.ICallback;

public abstract class AsyncImageViewCompleteHandler implements ICallback<Bitmap> {

	
	public boolean execute(Bitmap... args) {
		onComplete(args[0]);
		return false;
	}
	
	
	public abstract boolean onComplete(Bitmap bitmap);
}
