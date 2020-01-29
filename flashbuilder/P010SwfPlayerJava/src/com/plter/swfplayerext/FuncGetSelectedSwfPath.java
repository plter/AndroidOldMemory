package com.plter.swfplayerext;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class FuncGetSelectedSwfPath implements FREFunction {
	
	
	public static String selectedSwfPath="";

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		
		try {
			return FREObject.newObject(selectedSwfPath);
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}		
		return null;
	}

}
