package com.plter.mobwinforflashandroidext;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class FuncAddToBottom implements FREFunction{
	
	
	public static final String NAME="addToBottom";
	

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		MainActivityManager.getMainActivityManager(arg0.getActivity()).addToBottom();
		return null;
	}

}
