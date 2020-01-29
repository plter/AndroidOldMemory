package com.plter.androidwallpaper.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.plter.androidwallpaper.services.AutoChangeWallPaperService;
import com.plter.androidwallpaper.system.FileTool;

public class LocalImageViewerActivity extends ImageViewerActivity {

	private String filePath="";
	private File imgFile=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		filePath=getIntent().getStringExtra("path");
		imgFile = new File(filePath);
		
		showImage(BitmapFactory.decodeFile(filePath));
	}
	
	@Override
	public void addToListBtnClicked() {
		
		try {
			FileInputStream fis = new FileInputStream(imgFile);
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			fis.close();
			
			if (FileTool.saveWallpaperFile(bytes)) {
				Intent i =new Intent(this, ClientImageListActivity.class);
				startActivity(i);
				
				AutoChangeWallPaperService.refreshWallpaperDir();
			}else{
				Toast.makeText(this, "文件保存失败", Toast.LENGTH_SHORT).show();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			Toast.makeText(this, "该文件不存在", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
			
			Toast.makeText(this, "无法读取文件", Toast.LENGTH_SHORT).show();
		}
	}
}
