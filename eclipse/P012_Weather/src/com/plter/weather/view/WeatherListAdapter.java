package com.plter.weather.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.lib.java.xml.XML;
import com.plter.lib.java.xml.XMLList;
import com.plter.weather.R;

public class WeatherListAdapter extends ArrayAdapter<WeatherListCellData> implements OnWeatherIconCompleteListener {

	public WeatherListAdapter(Context context) {
		super(context, R.layout.weather_list_cell);
	}

	public void set_forecast_conditions(XMLList forecast_conditions){
		clear();
		
		for (XML x : forecast_conditions.getChildren()) {
			add(new WeatherListCellData(x));
		}
		
		notifyDataSetChanged();
	}


	@Override
	public void initListCell(int position, View listCell, ViewGroup parent) {
		WeatherListCellData data = getItem(position);

		ImageView iconIv = (ImageView) listCell.findViewById(R.id.iconIv);
		TextView day_of_week_tv = (TextView) listCell.findViewById(R.id.day_of_weekTv);
		TextView contentTv = (TextView) listCell.findViewById(R.id.contentTv);

		if (data.getBitmap()==null) {
			data.setOnWeatherIconCompleteListener(this);
		}else{
			iconIv.setImageBitmap(data.getBitmap());
		}
		day_of_week_tv.setText(data.getDay_of_week());
		contentTv.setText("天气情况："+data.getCondition()+"\n" +
				"最高气温："+data.getHigh()+"  " +
				"最低气温："+data.getLow());
	}

	@Override
	public void onComplete() {
		notifyDataSetChanged();
	}


}
