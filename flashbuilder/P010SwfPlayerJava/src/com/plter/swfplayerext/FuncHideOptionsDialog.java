package com.plter.swfplayerext;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class FuncHideOptionsDialog implements FREFunction{

	@Override
	public FREObject call(FREContext paramFREContext,
			FREObject[] paramArrayOfFREObject) {
		FuncShowOptionsDialog.hideOptionsDialog();
		return null;
	}

}
