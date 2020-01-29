package com.plter.androidwallpaper.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.plter.lib.android.java.net.Http;
import com.plter.lib.android.java.net.HttpBytesCompleteHandler;
import com.plter.lib.java.lang.ICallback;

public class Image {
	
	
	public Image(String ID,
			String name,
			String description,
			String category_id,
			String min_url,
			String url,
			String create_date) {
		
		this.ID=ID;
		this.name=name;
		this.description=description;
		this.category_id=category_id;
		this.min_url=min_url;
		this.url=url;
		this.create_date=create_date;
	}
	
	public Image() {
	}
	
	
	private boolean loaded=false;
	
	public void loadIconOnce(final ICallback<Void> completeCallback){
		if (!loaded) {
			loaded=true;
			
			loadIcon(completeCallback);
		}
	}
	
	public void loadIcon(final ICallback<Void> completeCallback){
		
		if (min_url==null||min_url.equals("")) {
			return;
		}
				
		Http.getBytes(min_url, new HttpBytesCompleteHandler() {
			
			@Override
			public void onResult(byte[] bytes) {
				setIcon(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
				
				completeCallback.execute();
			}
		}, new Http.IHttpFaultCallback() {
			@Override
			public boolean execute(Integer... args) {
				return true;
			}
		},true);
	}

	public Bitmap getIcon() {
		return icon;
	}

	private void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String ID="";
	public String name="";
	public String description="";
	public String category_id="";
	public String min_url="";
	public String url="";
	public String create_date="";
	private Bitmap icon=null;
}
