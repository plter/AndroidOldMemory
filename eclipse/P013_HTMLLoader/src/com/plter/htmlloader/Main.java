package com.plter.htmlloader;

import android.os.Bundle;

import com.phonegap.DroidGap;

public class Main extends DroidGap {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        loadUrl("file:///android_asset/www/index.html");
    }
}