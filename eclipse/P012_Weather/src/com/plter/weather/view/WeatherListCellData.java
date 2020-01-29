package com.plter.weather.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.plter.lib.android.java.net.Http;
import com.plter.lib.android.java.net.HttpBytesCompleteHandler;
import com.plter.lib.java.xml.XML;
import com.plter.weather.config.Config;

public class WeatherListCellData {

	public WeatherListCellData(XML forecast_conditions) {
		this.setForecast_conditions(forecast_conditions);
	}


	public XML getForecast_conditions() {
		return forecast_conditions;
	}


	private void setForecast_conditions(XML forecast_conditions) {
		this.forecast_conditions = forecast_conditions;

		setIconUrl(Config.GOOGLE_BASE_DOMAIN+forecast_conditions.getChild("icon").getAttr("data"));
		setLow(forecast_conditions.getChild("low").getAttr("data"));
		setHigh(forecast_conditions.getChild("high").getAttr("data"));
		setDay_of_week(forecast_conditions.getChild("day_of_week").getAttr("data"));
		setCondition(forecast_conditions.getChild("condition").getAttr("data"));
	}


	public String getIconUrl() {
		return iconUrl;
	}


	private void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;

		Http.getBytes(iconUrl, new HttpBytesCompleteHandler() {

			@Override
			public void onResult(byte[] bytes) {
				if (bytes!=null) {
					setBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
					
					if (getOnWeatherIconCompleteListener()!=null) {
						getOnWeatherIconCompleteListener().onComplete();
					}
				}
			}
		}, true);
	}


	public String getDay_of_week() {
		return day_of_week;
	}


	private void setDay_of_week(String day_of_week) {
		this.day_of_week = day_of_week;
	}


	public String getLow() {
		return low;
	}


	private void setLow(String low) {
		this.low = low;
	}


	public String getHigh() {
		return high;
	}


	private void setHigh(String high) {
		this.high = high;
	}


	public String getCondition() {
		return condition;
	}


	private void setCondition(String condition) {
		this.condition = condition;
	}


	public Bitmap getBitmap() {
		return bitmap;
	}


	private void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}


	public OnWeatherIconCompleteListener getOnWeatherIconCompleteListener() {
		return onWeatherIconCompleteListener;
	}


	public void setOnWeatherIconCompleteListener(
			OnWeatherIconCompleteListener onWeatherIconCompleteListener) {
		this.onWeatherIconCompleteListener = onWeatherIconCompleteListener;
	}


	private XML forecast_conditions=null;
	private String day_of_week="";
	private String low="";
	private String high="";
	private String condition="";
	private String iconUrl="";
	private Bitmap bitmap=null;
	private OnWeatherIconCompleteListener onWeatherIconCompleteListener=null;

}
