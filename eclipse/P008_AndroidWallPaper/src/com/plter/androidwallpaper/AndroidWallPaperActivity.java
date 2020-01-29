package com.plter.androidwallpaper;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.plter.androidwallpaper.net.Category;
import com.plter.androidwallpaper.net.Net;
import com.plter.androidwallpaper.services.AutoChangeWallPaperService;
import com.plter.androidwallpaper.views.ClientImageListActivity;
import com.plter.androidwallpaper.views.LocalImageViewerActivity;
import com.plter.androidwallpaper.views.ServerImageListActivity;
import com.plter.androidwallpaper.views.SettingsActivity;
import com.plter.androidwallpaper.views.WaitUserConfigNetworkDialog;
import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.lib.java.lang.ICallback;

public class AndroidWallPaperActivity extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
				
		connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=connectivityManager.getActiveNetworkInfo();
		if (info!=null&&info.isAvailable()) {
			Net.loadCategory(this, categoryDataCallback);
		}else{
			WaitUserConfigNetworkDialog.show(this, categoryDataCallback);
		}
		
		categoryAdapter=new CategoryAdapter(this);
		setListAdapter(categoryAdapter);
		
		AutoChangeWallPaperService.checkToStartOrStopService(this);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		Category category = categoryAdapter.getItem(position);
		if (category.ID.equals(Category.ID_LOCAL_WALLPAPER_CATETORY)) {
			Intent i =new Intent(this, ClientImageListActivity.class);
			startActivity(i);
		}else if(category.ID.equals(Category.ID_LOCAL_IMAGE_FILES_CATEGORY)){
			startChooseImages();
		}else if(category.ID.equals(Category.ID_SETTINGS_CATEGORY)){
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
		}else{
			Category c = categoryAdapter.getItem(position);
			Intent i =new Intent(this,ServerImageListActivity.class);
			i.putExtra("cid", c.ID);
			i.putExtra("label", c.name);
			startActivity(i);
		}
				
		super.onListItemClick(l, v, position, id);
	}
	
	
	private File currentChoosingFileTmp=null;
	/**
	 * 开始选择本地图库图片
	 */
	public void startChooseImages(){
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);

		currentChoosingFileTmp=new File(Environment.getExternalStorageDirectory()+"/.tmp");
		try {
			currentChoosingFileTmp.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();

			Toast.makeText(this, "无法向SD卡写入数据", Toast.LENGTH_SHORT).show();
			return;
		}

		i.setType("image/*");
		i.putExtra("output", Uri.fromFile(currentChoosingFileTmp));
		i.putExtra("outputFormat", "JPEG");
		i.putExtra("crop", "true");
		i.putExtra("aspectX", 4);
		i.putExtra("aspectY", 3);

		startActivityForResult(i, REQUEST_CHOOSE_IMAGE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CHOOSE_IMAGE:
				if (currentChoosingFileTmp!=null&&currentChoosingFileTmp.exists()) {
					Intent i = new Intent(this, LocalImageViewerActivity.class);
					i.putExtra("path", currentChoosingFileTmp.getAbsolutePath());
					startActivity(i);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	private final ICallback<Category[]> categoryDataCallback=new ICallback<Category[]>() {
		@Override
		public boolean execute(Category[]... args) {
			categoryAdapter.setCategoryArray(args[0]);
			return true;
		}
	};
	
	private CategoryAdapter categoryAdapter;
	private ConnectivityManager connectivityManager=null;
	public static final int REQUEST_CHOOSE_IMAGE=2;
	private LinearLayout mainLayout;
	
	///////////////////////////////////////////////////////////////////////////
	public static class CategoryAdapter extends ArrayAdapter<Category> {
		
		public CategoryAdapter(Context context) {
			super(context, R.layout.category_list_cell);
		}

		@Override
		public void initListCell(int position, View listCell, ViewGroup parent) {
			categoryNameTv = (TextView) listCell.findViewById(R.id.categoryNameTv);
			categoryNameTv.setText(getItem(position).name);
		}
		
		
		public void setCategoryArray(Category[] arr){
			clear();
			
			for (Category category : arr) {
				add(category);
			}
			
			add(new Category(Category.ID_LOCAL_WALLPAPER_CATETORY, "已存壁纸", "1"));
			add(new Category(Category.ID_LOCAL_IMAGE_FILES_CATEGORY, "本机图片", "1"));
			add(new Category(Category.ID_SETTINGS_CATEGORY, "设置", "1"));
		}
		
		private TextView categoryNameTv=null;
	}
}