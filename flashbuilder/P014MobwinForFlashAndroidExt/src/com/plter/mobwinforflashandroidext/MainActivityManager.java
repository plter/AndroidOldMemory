package com.plter.mobwinforflashandroidext;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.tencent.mobwin.AdView;


public class MainActivityManager {

	private MainActivityManager(Activity aty) {
		activity=aty;
		
		adParent=new RelativeLayout(getActivity());
		getActivity().addContentView(adParent, new RelativeLayout.LayoutParams(-1, -1));
		
		ad=new AdView(getActivity());
		adParent.addView(ad, -1, -2);
	}
	
	
	private static MainActivityManager mgr=null;
	public static MainActivityManager getMainActivityManager(Activity aty){
		if (mgr==null) {
			mgr=new MainActivityManager(aty);
		}
		
		return mgr;
	}
	
	
	public void addToTop(){
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -2);
		lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		ad.setLayoutParams(lp);
	}
	
	public void addToBottom(){
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -2);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		ad.setLayoutParams(lp);
	}
	
	
	public Activity getActivity(){
		return activity;
	}
	
	
	private Activity activity=null;
	private AdView ad=null;
	private RelativeLayout adParent=null;
	
}
