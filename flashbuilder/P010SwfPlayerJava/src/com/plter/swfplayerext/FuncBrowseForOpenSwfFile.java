package com.plter.swfplayerext;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.plter.swfplayerext.filebrowser.FileBrowserAty;
import com.plter.swfplayerext.filebrowser.OnSwfFileSelectListener;

public class FuncBrowseForOpenSwfFile implements FREFunction {

	@Override
	public FREObject call(final FREContext arg0, final FREObject[] arg1) {
		
		FileBrowserAty.start(arg0, new OnSwfFileSelectListener() {
			
			@Override
			public void onSelected(String swfPath) {
				FuncGetSelectedSwfPath.selectedSwfPath=swfPath;
				arg0.dispatchStatusEventAsync(SwfPlayerExtCode.SELECT_SWF, "");
			}
		});	
		return null;
	}




}
