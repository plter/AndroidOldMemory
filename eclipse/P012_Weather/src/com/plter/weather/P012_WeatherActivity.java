package com.plter.weather;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.plter.lib.android.java.controls.Alert;
import com.plter.lib.android.java.controls.AlertCloseHandler;
import com.plter.lib.android.java.controls.AsyncImageView;
import com.plter.lib.android.java.controls.ProgressDialog;
import com.plter.lib.android.java.net.Http.IHttpFaultCallback;
import com.plter.lib.java.xml.XML;
import com.plter.lib.java.xml.XMLList;
import com.plter.weather.config.Cities;
import com.plter.weather.config.Config;
import com.plter.weather.net.City;
import com.plter.weather.net.CityLoader;
import com.plter.weather.net.GoogleWeatherLoader;
import com.plter.weather.net.ICityLoaderCallback;
import com.plter.weather.net.IGoogleWeatherLoaderCallback;
import com.plter.weather.srvs.WeatherService;
import com.plter.weather.view.DropDownMenuItemCellData;
import com.plter.weather.view.WeatherListAdapter;

public class P012_WeatherActivity extends ListActivity implements OnClickListener {


	private AsyncImageView currentWeatherIv;
	private TextView weatherInfoTv;
	private TextView cityNameBtn;
	private WeatherListAdapter adapter=null;
	private FrameLayout mainLayout;
	private View settingsButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//init ui
		mainLayout=(FrameLayout) findViewById(R.id.mainLayout);
		mainLayout.setOnClickListener(this);
		currentWeatherIv=(AsyncImageView) findViewById(R.id.currentWeatherIv);
		cityNameBtn=(TextView) findViewById(R.id.cityNameTv);
		cityNameBtn.setOnClickListener(this);
		weatherInfoTv=(TextView) findViewById(R.id.weatherInfoTv);
		settingsButton=findViewById(R.id.settingsBtn);
		settingsButton.setOnClickListener(this);

		//init listview
		adapter=new WeatherListAdapter(this);
		setListAdapter(adapter);

		String currentCity=Config.getCachedCity(this);
		if (currentCity!=null) {
			loadWeatherInfo(currentCity,true);
		}else{
			loadCityInfo();
		}

		startService(new Intent(this, WeatherService.class));
	}

	public void loadCityInfo(){
		ProgressDialog.show(this, "请稍候","正在获取城市信息");

		CityLoader.loadCurrentCityInfo(new ICityLoaderCallback() {

			@Override
			public boolean execute(City... args) {
				loadWeatherInfo(args[0].name,true);
				return false;
			}
		}, new IHttpFaultCallback() {

			@Override
			public boolean execute(Integer... args) {
				ProgressDialog.hide();

				Alert.yesLabel="是";
				Alert.noLabel="退出";
				Alert.show(P012_WeatherActivity.this, "是否重新加载？", "获取城市信息失败", Alert.YES|Alert.NO, new AlertCloseHandler() {

					@Override
					public void onAlertClose(int flag) {
						switch (flag) {
						case Alert.YES:
							loadCityInfo();
							break;
						default:
							System.exit(0);
							break;
						}
					}
				});
				return false;
			}
		});
	}


	public void showWeatherInfo(XML weather){
		try{
			//获取城市信息
			XML forecast_information = weather.getChild("forecast_information");

			String city=forecast_information.getChild("postal_code").getAttr("data");

			//设置城市信息
			cityNameBtn.setText(city);
			mainLayout.setBackgroundResource(Cities.getCityPic(city));

			//获取天气情况
			XML current_conditions = weather.getChild("current_conditions");
			currentWeatherIv.loadImage(Config.GOOGLE_BASE_DOMAIN+current_conditions.getChild("icon").getAttr("data"));

			weatherInfoTv.setText("天气："+current_conditions.getChild("condition").getAttr("data")+"\n" +
					"温度："+current_conditions.getChild("temp_c").getAttr("data")+"\n" +
					current_conditions.getChild("humidity").getAttr("data")+"\n" +
					current_conditions.getChild("wind_condition").getAttr("data"));

			adapter.set_forecast_conditions((XMLList) weather.getChild("forecast_conditions"));
		}catch(Exception e){
			e.printStackTrace();

			Alert.show(P012_WeatherActivity.this, "对不起，无法获得当前城市的天气情况", "无法获得该城市天气");
		}
	}


	public void loadWeatherInfo(final String city,final boolean useCache){
		ProgressDialog.show(this, "请稍候","正在获取天气情况");

		GoogleWeatherLoader.loadWeather(this,city, new IGoogleWeatherLoaderCallback() {

			@Override
			public boolean execute(XML... args) {
				ProgressDialog.hide();
				showWeatherInfo(args[0]);
				return false;
			}
		}, new IHttpFaultCallback() {
			@Override
			public boolean execute(Integer... args) {

				ProgressDialog.hide();

				Alert.yesLabel="是";
				Alert.noLabel="否";
				Alert.show(P012_WeatherActivity.this, "是否重新加载？", "获取天气信息失败", Alert.YES|Alert.NO, new AlertCloseHandler() {

					@Override
					public void onAlertClose(int flag) {
						switch (flag) {
						case Alert.YES:
							loadWeatherInfo(city,useCache);
							break;
						default:
//							System.exit(0);
							break;
						}
					}
				});
				return false;
			}
		},useCache);
	}


	public void showChangeCityInputBox(){
		final EditText inputText = new EditText(this);
		new AlertDialog.Builder(this)
		.setTitle("请输入城市名称")
		.setView(inputText)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (TextUtils.isEmpty( inputText.getText())) {
					return;
				}
				
				String cachedCity = Config.getCachedCity(P012_WeatherActivity.this);

				if (cachedCity!=null&&inputText.getText().toString().equals(cachedCity)) {
					return;
				}

				loadWeatherInfo(inputText.getText().toString(),true);
			}
		})
		.setNeutralButton("取消", null)
		.show();

		FrameLayout.LayoutParams lp = (LayoutParams) inputText.getLayoutParams();
		lp.leftMargin=20;
		lp.rightMargin=20;
		lp.gravity=Gravity.CENTER_HORIZONTAL;
		inputText.setLayoutParams(lp);
	}


	private PopupWindow pw=null;
	private static final DropDownMenuItemCellData[] dropDownItems=new DropDownMenuItemCellData[]{
		new DropDownMenuItemCellData(DropDownMenuItemCellData.ACTION_REFRESH, "刷新"),
		new DropDownMenuItemCellData(DropDownMenuItemCellData.ACTION_CHANGE_CITY, "更换城市"),
		new DropDownMenuItemCellData(DropDownMenuItemCellData.ACTION_ABOUT, "软件信息"),
		new DropDownMenuItemCellData(DropDownMenuItemCellData.ACTION_CANCEL, "关闭菜单"),
	};
	
	public void showDropDownMenu(){
		if (pw==null) {
			ListView lv = new ListView(this);
			lv.setBackgroundColor(0xAA000000);
			lv.setCacheColorHint(0xAA000000);
			final ArrayAdapter<DropDownMenuItemCellData> adapter = new ArrayAdapter<DropDownMenuItemCellData>(this, R.layout.drop_down_item_cell, dropDownItems);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					DropDownMenuItemCellData data= adapter.getItem(arg2);
					
					switch (data.action) {
					case DropDownMenuItemCellData.ACTION_CHANGE_CITY:
						showChangeCityInputBox();
						break;
					case DropDownMenuItemCellData.ACTION_ABOUT:
						new AlertDialog.Builder(P012_WeatherActivity.this).setTitle("关于软件").setMessage("该软件由梦宇开发\nwww.plter.com").setNegativeButton("关闭", null).show();
						break;
					case DropDownMenuItemCellData.ACTION_REFRESH:
						String city = Config.getCachedCity(P012_WeatherActivity.this);
						if (city!=null) {
							loadWeatherInfo(Config.getCachedCity(P012_WeatherActivity.this),false);
						}
						break;
					default:
						break;
					}
					
					pw.dismiss();
					pw=null;
				}
			});
			pw=new PopupWindow(lv, 200, 250,true);
			pw.showAsDropDown(settingsButton, -160, 0);
		}else{
			pw.dismiss();
			pw=null;
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cityNameTv:
			showChangeCityInputBox();
			break;
		case R.id.settingsBtn:
			showDropDownMenu();
			break;
		case R.id.mainLayout:
			//TODO handler main layout click event
			break;
		default:
			break;
		}
	}
}