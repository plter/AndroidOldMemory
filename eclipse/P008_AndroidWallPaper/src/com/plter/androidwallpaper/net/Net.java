package com.plter.androidwallpaper.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.plter.androidwallpaper.config.Config;
import com.plter.androidwallpaper.views.ProgressDialog;
import com.plter.androidwallpaper.views.ReloadDialog;
import com.plter.lib.android.java.net.Http;
import com.plter.lib.android.java.net.NameValuePairs;
import com.plter.lib.java.lang.ICallback;

public class Net {


	public static void loadCategory(final Context context,final ICallback<Category[]> callback){
		ProgressDialog.show(context, "加载分类", "精彩内容马上呈现");

		Http.getText(Config.URL, new NameValuePairs("type", "category", "all","0"), new Http.IHttpTextCompleteCallback() {

			@Override
			public boolean execute(String... args) {
				ProgressDialog.hide();

				if (args[0]==null) {
					return true;
				}

				try {
					JSONArray resultJsonArr = new JSONArray(args[0]);
					Category[] cats = new Category[resultJsonArr.length()];
					JSONObject jsonObj=null;

					for (int i = 0; i < cats.length; i++) {
						jsonObj=resultJsonArr.getJSONObject(i);
						cats[i]=new Category(jsonObj.getString("ID"), jsonObj.getString("name"), jsonObj.getString("visible"));
					}

					callback.execute(cats);
				} catch (JSONException e) {
					e.printStackTrace();

					ReloadDialog.show(context, new ICallback<Void>() {

						@Override
						public boolean execute(Void... args) {
							loadCategory(context, callback);
							
							return true;
						}
					});
				}
				
				return true;
			}
		}, new Http.IHttpFaultCallback() {

			@Override
			public boolean execute(Integer... args) {
				ProgressDialog.hide();

				ReloadDialog.show(context, new ICallback<Void>() {
					@Override
					public boolean execute(Void... args) {
						loadCategory(context, callback);
						return true;
					}
				});
				
				return true;
			}
		}, false);
	}


	public static void loadPhotos(final Context context,final String categoryID,final String pageIndex,final ICallback<PagedImages> callback){
		ProgressDialog.show(context, "加载图片", "精彩内容马上呈现");

		Http.getText(Config.URL, new NameValuePairs("type", "photos","cid",categoryID,"page",pageIndex), new Http.IHttpTextCompleteCallback() {

			@Override
			public boolean execute(String... args) {
				ProgressDialog.hide();

				try {
					
					if (args[0]==null) {
						callback.execute(new PagedImages[]{null});
						return false;
					}
					
					JSONObject jsonObj = new JSONObject(args[0]);
					JSONArray photosJson = null;
					ArrayList<Image> photos=null;
					Object imgData = jsonObj.get("imgData");

					if (imgData!=null&&imgData instanceof JSONArray) {
						photosJson = (JSONArray) imgData;
						photos = new ArrayList<Image>();
						JSONObject photoJsonObj =null;
						Image photo=null;
						for (int i = 0; i < photosJson.length(); i++) {
							photoJsonObj = photosJson.getJSONObject(i);
							photo=new Image(photoJsonObj.getString("ID"), 
									photoJsonObj.getString("name"), 
									photoJsonObj.getString("description"), 
									photoJsonObj.getString("category_id"), 
									photoJsonObj.getString("min_url"), 
									photoJsonObj.getString("url"), 
									photoJsonObj.getString("create_date"));
							photos.add(photo);
						}
					}

					PagedImages pagedPhotos = new PagedImages(photos, 
							new Integer(jsonObj.getString("count")), 
							new Integer(jsonObj.getString("perPageCount")), 
							new Integer(jsonObj.getString("page")));
					
					callback.execute(pagedPhotos);

				} catch (JSONException e) {
					e.printStackTrace();

					ReloadDialog.show(context, new ICallback<Void>() {

						@Override
						public boolean execute(Void... args) {
							loadPhotos(context, categoryID, pageIndex, callback);
							return true;
						}
					});
				}
				
				return true;
			}
		}, new Http.IHttpFaultCallback() {

			@Override
			public boolean execute(Integer... args) {
				ProgressDialog.hide();

				ReloadDialog.show(context, new ICallback<Void>() {

					@Override
					public boolean execute(Void... args) {
						loadPhotos(context, categoryID, pageIndex, callback);
						return true;
					}
				});
				
				return true;
			}
		}, false);
	}
}
