package com.plter.mobwinforflashandroidext;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class FuncAddToTop implements FREFunction {

	
	
	public static final String NAME="addToTop";
	
	
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		MainActivityManager.getMainActivityManager(arg0.getActivity()).addToTop();
		return null;
	}

}
