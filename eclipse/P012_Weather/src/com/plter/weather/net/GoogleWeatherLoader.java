package com.plter.weather.net;

import java.net.URLEncoder;

import android.content.Context;
import android.os.SystemClock;

import com.plter.lib.android.java.net.Http;
import com.plter.lib.android.java.net.Http.IHttpFaultCallback;
import com.plter.lib.android.java.net.Http.IHttpTextCompleteCallback;
import com.plter.lib.java.xml.XML;
import com.plter.lib.java.xml.XMLRoot;
import com.plter.weather.config.Config;

public class GoogleWeatherLoader {


	public static void loadWeather(final Context context,final String cityName,final IGoogleWeatherLoaderCallback callback,final IHttpFaultCallback faultCallback,boolean useCache){
		
		if (useCache) {
			//检查缓存数据
			long cacheTime = Config.getLastCacheWeatherTime(context);
			String cachedCity = Config.getCachedCity(context);
			String cachedWeather = Config.getCachedWeatherStr(context);
			
			if (cacheTime>0&&
					Math.abs(SystemClock.elapsedRealtime()-cacheTime)<3600000&&
					cachedCity!=null&&
					cachedCity.equals(cityName)&&
					callback!=null&&
					cachedWeather!=null) {
				
				try{
					XMLRoot xml = XMLRoot.parse(cachedWeather);
					XML node = xml.getChild("weather");
					if (node!=null) {
						callback.execute(node);
					}else {
						if (faultCallback!=null) {							
							faultCallback.execute(0);
						}
					}				
				}catch(Exception e){
					e.printStackTrace();
					
					if (faultCallback!=null) {
						faultCallback.execute(0);
					}
				}
				
				return;
			}
		}
		


		//重新加载新数据
		Http.setCharSet("gbk");
		Http.getText("http://www.google.com/ig/api?hl=zh-cn&weather="+URLEncoder.encode(cityName), new IHttpTextCompleteCallback() {

			@Override
			public boolean execute(String... args) {
				String xmlStr = args[0];
				if (xmlStr.indexOf("problem_cause")>-1) {
					if (faultCallback!=null) {
						faultCallback.execute(0);
					}
					return false;
				}

				
				Config.setCachedWeatherStr(context, xmlStr);

				if (callback!=null) {
					try{
						XMLRoot xml = XMLRoot.parse(xmlStr);
						XML node = xml.getChild("weather");
						
						if (node!=null) {
							
							Config.setCachedCity(context, cityName);
							
							callback.execute(node);
						}else {
							if (faultCallback!=null) {							
								faultCallback.execute(0);
							}
						}
					}catch(Exception e){
						e.printStackTrace();

						if (faultCallback!=null) {							
							faultCallback.execute(0);
						}
					}
				}

				return false;
			}
		}, faultCallback, false);
	}
}
