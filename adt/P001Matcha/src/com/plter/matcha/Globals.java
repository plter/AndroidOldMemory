package com.plter.matcha;

import android.content.Context;

public class Globals {
	
	private static Context mainContext=null;

	public static Context getMainContext() {
		return mainContext;
	}

	static void setMainContext(Context mainContext) {
		Globals.mainContext = mainContext;
	}
	
	public static Matcha getMatcha(){
		return (Matcha) getMainContext();
	}
}
