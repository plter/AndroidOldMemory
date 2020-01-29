package com.plter.swfplayerext;

import android.app.AlertDialog;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class FuncShowAlert implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

		if (arg1.length==3) {
			try {
				new AlertDialog.Builder(arg0.getActivity()).setTitle(arg1[1].getAsString()).setMessage(arg1[0].getAsString()).setPositiveButton(arg1[2].getAsString(), null).show();
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
