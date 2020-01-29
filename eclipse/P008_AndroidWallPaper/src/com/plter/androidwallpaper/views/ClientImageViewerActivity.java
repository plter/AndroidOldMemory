package com.plter.androidwallpaper.views;

import java.io.File;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.plter.androidwallpaper.R;
import com.plter.androidwallpaper.config.Config;
import com.plter.androidwallpaper.services.AutoChangeWallPaperService;
import com.plter.androidwallpaper.system.WallPaperTool;

public class ClientImageViewerActivity extends Activity{

	private ImageView imageView=null;
	private String filePath=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_image_viewer);
		
		filePath=getIntent().getStringExtra("path");
		
		imageView=(ImageView) findViewById(R.id.imageView);
		findViewById(R.id.setAsWallpaperBtn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WallPaperTool.setWallpaper(ClientImageViewerActivity.this, filePath);
				
				Config.setAutoChangeWallPaper(ClientImageViewerActivity.this, false);
				AutoChangeWallPaperService.checkToStartOrStopService(ClientImageViewerActivity.this);
			}
		});
		
		if (filePath!=null) {
			File f = new File(filePath);
			if (f.exists()) {
				imageView.setImageURI(Uri.fromFile(f));
			}
		}
	}
	
	
	
}
