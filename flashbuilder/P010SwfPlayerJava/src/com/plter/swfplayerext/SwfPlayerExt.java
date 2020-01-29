package com.plter.swfplayerext;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class SwfPlayerExt implements FREExtension {

	@Override
	public FREContext createContext(String arg0) {
		if (freContext==null) {
			freContext=new SwfPlayerContext();
		}
		
		return freContext;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void initialize() {
	}
	
	private SwfPlayerContext freContext=null;

}
