package com.plter.swfplayerext.filebrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FileListCell extends LinearLayout {

	public FileListCell(Context context) {
		super(context);
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		
		ivIcon=new ImageView(context);
		addView(ivIcon, 72, 72);
		
		tvFileName=new TextView(context);
		tvFileName.setTextSize(24);
		addView(tvFileName, -1, -2);
	}
	
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
		tvFileName.setText(label);
	}


	public Bitmap getIcon() {
		return icon;
	}
	public void setIcon(Bitmap icon) {
		this.icon = icon;
		ivIcon.setImageBitmap(icon);
	}


	private Bitmap icon=null;
	private String label=null;
	private ImageView ivIcon=null;
	private TextView tvFileName=null;

}
