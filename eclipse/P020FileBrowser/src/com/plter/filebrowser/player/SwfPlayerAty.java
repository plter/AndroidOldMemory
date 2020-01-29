package com.plter.filebrowser.player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.plter.filebrowser.R;

public class SwfPlayerAty extends Activity {


	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swf_player_aty);

		webView=(WebView) findViewById(R.id.webView);
		WebSettings ws = webView.getSettings();
		ws.setPluginsEnabled(true);
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(true);
		ws.setPluginState(PluginState.ON);
		ws.setAllowFileAccess(true);

		String swfPath = getIntent().getStringExtra(EXTRA_SWF_PATH);
		if (swfPath!=null) {
			
			ApplicationInfo info=null;
			try {
				 info = getPackageManager().getApplicationInfo("com.adobe.flashplayer", PackageManager.GET_META_DATA);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			
			if (info!=null) {
				webView.loadUrl(swfPath);
			}else{
				new AlertDialog.Builder(this)
				.setTitle("提醒")
				.setMessage("你好，检查到您的设备上没有安装FlashPlayer，是否到Adobe官方网站下载")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.adobe.com/go/EN_US-H-GET-FLASH")));
						finish();
					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						finish();
					}
				}).show();
			}
		}else{
			new AlertDialog.Builder(this)
			.setTitle("你好")
			.setMessage("没有指定要播放的文件")
			.setPositiveButton("返回", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			})
			.show();
		}
	}


	private void callHiddenWebViewMethod(String name)
	{
		if (webView != null)
		{
			try
			{
				Method method = WebView.class.getMethod(name);
				method.invoke(webView);
			}
			catch (NoSuchMethodException e)
			{
				Log.i("No such method: " + name, e.toString());
			}
			catch (IllegalAccessException e)
			{
				Log.i("Illegal Access: " + name, e.toString());
			}
			catch (InvocationTargetException e)
			{
				Log.d("Invocation Target Exception: " + name, e.toString());
			}
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		webView.pauseTimers();

		if (isFinishing())
		{
			webView.loadUrl("about:blank");
			setContentView(new FrameLayout(this));
		}
		callHiddenWebViewMethod("onPause");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		callHiddenWebViewMethod("onResume");
	}

	private WebView webView=null;
	public static final String EXTRA_SWF_PATH="swfPath";
}
