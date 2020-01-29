package com.plter.mobwinforflashandroidext;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class MFFExtension implements FREExtension {

	@Override
	public FREContext createContext(String arg0) {
		if (context==null) {
			context=new MFFContext();
		}
		
		return context;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void initialize() {
	}
	
	private MFFContext context=null;

}
