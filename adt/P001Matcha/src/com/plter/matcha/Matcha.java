/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.plter.matcha;

import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.DroidGap;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.plter.matcha.media.SoundPlayer;
import com.plter.matcha.net.MNetworkInfo;

public class Matcha extends DroidGap
{
	private String main_page;

	public Matcha() {
		Globals.setMainContext(this);
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//init appView
		appView = new CordovaWebView(this);
		init(appView, new CordovaWebViewClient(this, appView){
			@Override
			public void onPageFinished(WebView arg0, String arg1) {
				hideWelcomeFace();
				super.onPageFinished(arg0, arg1);
			}
		}, new CordovaChromeClient(this, appView));
		
		//add fg 
		addContentView(LayoutInflater.from(this).inflate(R.layout.main, null), new FrameLayout.LayoutParams(-1, -1));

		//read main page path
		main_page=getString(R.string.main_page);
		
		//webcome face >>>>>>>>>>>>>>>>>>>
		welcomeFace=findViewById(R.id.webcomeFace);
		initAnimations();
		//<<<<<<<<<<<<<<<<<<<<<webcome face

		//network not ok face >>>>>>>>>>>>>
		setupNetworkNotOKFace();
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	}


	@Override
	protected void onResume() {
		SoundPlayer.playWelcomeSound();

		boolean networkOk = MNetworkInfo.isNetworkOK();
		networkNotOKFace.setVisibility(networkOk?View.GONE:View.VISIBLE);
		loadUrl(main_page);
		super.onResume();
	}


	public boolean isWelcomeFaceVisible() {
		return welcomeFaceVisible;
	}

	public void setWelcomeFaceVisible(boolean fgVisible) {
		this.welcomeFaceVisible = fgVisible;
		welcomeFace.setVisibility(fgVisible?View.VISIBLE:View.GONE);
	}
	private View welcomeFace=null;
	private boolean welcomeFaceVisible=true;
	private final Animation.AnimationListener hideFgAnimListener=new Animation.AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			setWelcomeFaceVisible(false);
		}
	};

	public void showWelcomeFace(){
		if (!isWelcomeFaceVisible()) {

			setWelcomeFaceVisible(true);
			welcomeFace.startAnimation(showFgAnim);
		}
	}

	public void hideWelcomeFace(){
		if (isWelcomeFaceVisible()) {
			welcomeFace.startAnimation(hideFgAnim);
		}
	}

	private void initAnimations(){
		hideFgAnim.setAnimationListener(hideFgAnimListener);

		showFgAnim.setDuration(300);
		hideFgAnim.setDuration(300);
	}
	private final AlphaAnimation showFgAnim=new AlphaAnimation(0, 1),hideFgAnim=new AlphaAnimation(1, 0);

	public boolean isNetworkNotOKFaceVisible() {
		return networkNotOKFaceVisible;
	}

	public void setNetworkNotOKFaceVisible(boolean networkNotOKFaceVisible) {
		this.networkNotOKFaceVisible = networkNotOKFaceVisible;

		networkNotOKFace.setVisibility(networkNotOKFaceVisible?View.VISIBLE:View.GONE);
	}

	private void setupNetworkNotOKFace(){
		networkNotOKFace=findViewById(R.id.networkNotOKFace);

		findViewById(R.id.quitBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.exit(0);
			}
		});
		findViewById(R.id.networkSettingsBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				//				i.setComponent(new ComponentName("com.android.settings","com.android.settings.WirelessSettings"));
				startActivity(i);
			}
		});
	}

	private boolean networkNotOKFaceVisible=false;
	private View networkNotOKFace=null;
}

