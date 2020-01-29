package com.plter.menglihualuozhiduoshao;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;

import android.os.Bundle;

public class MainActivity extends DroidGap {

    private CordovaWebView webView=null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        webView=(CordovaWebView) findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(true);
        webView.setVerticalScrollbarOverlay(true);
        webView.loadUrl("file:///android_asset/www/apps/html/menglihualuozhiduoshao/index.html");
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }

    
}
