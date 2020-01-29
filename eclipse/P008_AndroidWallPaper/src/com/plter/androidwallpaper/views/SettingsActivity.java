package com.plter.androidwallpaper.views;

import com.plter.androidwallpaper.R;
import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.lib.android.java.net.Http;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends ListActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter=new SettingsActivityAdapter(this);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (adapter.getItem(position).action) {
		case SettingsListCellData.ACTION_CLEAR_CACHE:
			Http.clearCache();
			Toast.makeText(this, "缓存已清理", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case SettingsListCellData.ACTION_ABOUT:
			new AlertDialog.Builder(this)
			.setTitle("关于")
			.setMessage("产品名称：精美壁纸\n" +
					"作者博客：\n" +
					"http://plter.sinaapp.com\n" +
					"http://plter.com\n" +
					"产品网站：\n" +
					"http://androidwallpaper.sinaapp.com\n" +
					"作者微博：\n" +
					"http://weibo.com/plter")
			.setIcon(R.drawable.ic_launcher)
			.setPositiveButton("关闭", null)
			.show();
			break;
		default:
			break;
		}
		
		super.onListItemClick(l, v, position, id);
	}
	
	
	
	private SettingsActivityAdapter adapter=null;
	
	static final class SettingsListCellData{
		
		
		public SettingsListCellData(String label,int action) {
			this.label=label;
			this.action=action;
		}
		
		
		/**
		 * 清除缓存
		 */
		public static final int ACTION_CLEAR_CACHE=1;
		public static final int ACTION_ABOUT=2;
		
		
		public String label="";
		public int action=0;
	}
	
	
	static class SettingsActivityAdapter extends ArrayAdapter<SettingsListCellData>{

		public SettingsActivityAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_1);
			
			add(new SettingsListCellData("清除缓存图片", SettingsListCellData.ACTION_CLEAR_CACHE));
			add(new SettingsListCellData("关于", SettingsListCellData.ACTION_ABOUT));
		}

		@Override
		public void initListCell(int position, View listCell, ViewGroup parent) {
			TextView tv =(TextView) listCell;
			tv.setText(getItem(position).label);
			tv.setBackgroundColor(0x66000000);
		}
		
	}
	
}
