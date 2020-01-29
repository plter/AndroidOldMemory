package com.plter.swfplayerext;

import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class SwfPlayerContext extends FREContext {

	@Override
	public void dispose() {
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		if (funcs==null) {
			funcs=new HashMap<String, FREFunction>();
			funcs.put(SwfPlayerExtFuncName.BROWSE_FOR_OPEN_SWF_FILE, new FuncBrowseForOpenSwfFile());
			funcs.put(SwfPlayerExtFuncName.GET_SELECTED_SWF_PATH, new FuncGetSelectedSwfPath());
			funcs.put(SwfPlayerExtFuncName.SHOW_OPTIONS_DIALOG, new FuncShowOptionsDialog());
			funcs.put(SwfPlayerExtFuncName.HIDE_OPTIONS_DIALOG, new FuncHideOptionsDialog());
			funcs.put(SwfPlayerExtFuncName.SHOW_ALERT, new FuncShowAlert());
			funcs.put(SwfPlayerExtFuncName.SHOW_TOAST, new FuncShowToast());
		}
		
		return funcs;
	}
	
	private Map<String, FREFunction> funcs=null;

}
