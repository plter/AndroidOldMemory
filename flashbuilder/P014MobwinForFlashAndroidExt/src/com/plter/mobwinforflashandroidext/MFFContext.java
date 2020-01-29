package com.plter.mobwinforflashandroidext;

import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class MFFContext extends FREContext {

	@Override
	public void dispose() {
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		if (funcs==null) {
			funcs=new HashMap<String, FREFunction>();
			funcs.put(FuncAddToBottom.NAME, new FuncAddToBottom());
			funcs.put(FuncAddToTop.NAME, new FuncAddToTop());
		}
		
		return funcs;
	}
	
	private Map<String, FREFunction> funcs=null;

}
