package com.plter.matcha.pgplugins;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class MatchaPlugin extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		
		
		if ("quit".equals(action)) {
			System.exit(0);
		}
		
		return super.execute(action, args, callbackContext);
	}
}
