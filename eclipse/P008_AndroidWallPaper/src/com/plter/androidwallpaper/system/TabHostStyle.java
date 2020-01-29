package com.plter.androidwallpaper.system;

import android.widget.RelativeLayout;
import android.widget.TabWidget;
import android.widget.TextView;

public class TabHostStyle {

	
	public static void setTabLabelTextSize(TabWidget tw,float size){
		int count = tw.getChildCount();
		RelativeLayout rl;
		TextView tv;
		for (int j = 0; j < count; j++) {
			rl = (RelativeLayout) tw.getChildAt(j);
			tv=(TextView) rl.findViewById(android.R.id.title);
			tv.setTextSize(size);
		}
	}
	
}
