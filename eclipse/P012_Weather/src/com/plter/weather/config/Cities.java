package com.plter.weather.config;

import com.plter.weather.R;

public class Cities {

	public static int getCityPic(String city){
		if (city.indexOf("北京")>-1) {
			return R.drawable.beijing;
		}else if(city.indexOf("天津")>-1){
			return R.drawable.tianjing;
		}else if(city.indexOf("上海")>-1){
			return R.drawable.shanghai;
		}else if(city.indexOf("广州")>-1){
			return R.drawable.guangzhou;
		}else if (city.indexOf("深圳")>-1) {
			return R.drawable.shenzhen;
		}else if(city.indexOf("重庆")>-1){
			return R.drawable.chongqing;
		}
		
		return R.drawable.default_pic;
	}
	
	
	public static int getDefaultCityPic(){
		return R.drawable.default_pic;
	}
}
