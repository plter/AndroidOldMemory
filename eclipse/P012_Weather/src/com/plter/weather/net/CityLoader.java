package com.plter.weather.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.plter.lib.android.java.net.Http;
import com.plter.lib.android.java.net.Http.IHttpFaultCallback;
import com.plter.lib.android.java.net.Http.IHttpTextCompleteCallback;

public class CityLoader {

	
	public static final void loadCurrentCityInfo(final ICityLoaderCallback callback,final IHttpFaultCallback faultCallback){
		
		Http.getText("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json", new IHttpTextCompleteCallback() {
			
			@Override
			public boolean execute(String... result) {
				
				try {
					JSONObject obj = new JSONObject(result[0]);
					City c = new City();
					c.name=obj.getString("city");
					c.country=obj.getString("country");
					c.district=obj.getString("district");
					c.end=obj.getString("end");
					c.isp=obj.getString("isp");
					c.province=obj.getString("province");
					c.ret=obj.getString("ret");
					c.start=obj.getString("start");
					
					callback.execute(c);
				} catch (JSONException e) {
					e.printStackTrace();
					faultCallback.execute(-1);
				}
				
				return true;
			}
		}, faultCallback, false);
		
	}
	
}
