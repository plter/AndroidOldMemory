package com.plter.androidwallpaper.views;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CellDataLocalImageFile {

	public CellDataLocalImageFile(File file) {
		setFile(file);
	}

	private File file=null;

	public File getFile() {
		return file;
	}

	private void setFile(File file) {
		this.file = file;
	}

	private Bitmap icon=null;

	public Bitmap getIcon(){
		if (icon==null) {
			Bitmap src = BitmapFactory.decodeFile(getFile().getAbsolutePath());
			if (src!=null) {
				icon = Bitmap.createScaledBitmap(src, 80, (int) (80.0*src.getHeight()/src.getWidth()), false);
				src.recycle();
				return icon;
			}
		}
		return icon;
	}
}
