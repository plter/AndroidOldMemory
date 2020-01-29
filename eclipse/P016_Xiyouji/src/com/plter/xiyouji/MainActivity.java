package com.plter.xiyouji;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;

import android.os.Bundle;

public class MainActivity extends DroidGap{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        webView=(CordovaWebView) findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(true);
        webView.setVerticalScrollbarOverlay(true);
        webView.loadUrl("file:///android_asset/www/apps/html/xiyouji/index.html");
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }

    private CordovaWebView webView;
}
