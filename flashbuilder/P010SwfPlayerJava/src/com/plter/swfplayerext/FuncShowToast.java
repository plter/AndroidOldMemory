package com.plter.swfplayerext;

import android.widget.Toast;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class FuncShowToast implements FREFunction{

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		
		if (arg1.length==2) {
			try {
				Toast.makeText(arg0.getActivity(), arg1[0].getAsString(), arg1[1].getAsInt()).show();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (FRETypeMismatchException e) {
				e.printStackTrace();
			} catch (FREInvalidObjectException e) {
				e.printStackTrace();
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

}
