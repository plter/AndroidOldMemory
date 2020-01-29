package com.plter.androidwallpaper.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.plter.androidwallpaper.services.AutoChangeWallPaperService;
import com.plter.androidwallpaper.system.FileTool;
import com.plter.lib.android.java.net.Http;
import com.plter.lib.android.java.net.HttpBytesCompleteHandler;

public class ServerImageViewerActivity extends ImageViewerActivity implements OnClickListener {


	private String imageUrl = "";
	private byte[] imgBytes=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		imageUrl = getIntent().getStringExtra("url");
		loadImage();
	}

	private void loadImage(){
		ProgressDialog.show(this, "正在加载图片", "精美图片马上呈现");

		Http.getBytes(imageUrl,new HttpBytesCompleteHandler() {
			
			@Override
			public void onResult(byte[] bytes) {
				ProgressDialog.hide();
				
				imgBytes=bytes;
				if (imgBytes==null) {
					return;
				}
				showImage(BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length));
			}
		}, new Http.IHttpFaultCallback() {

			@Override
			public boolean execute(Integer... args) {

				ProgressDialog.hide();

				new AlertDialog.Builder(ServerImageViewerActivity.this)
				.setTitle("亲爱的")
				.setMessage("图片加载失败，是否重新加载？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						loadImage();
					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).show();

				return true;
			}
		},true);
	}


	@Override
	public void addToListBtnClicked() {

		if (getImageBitmap()==null) {
			Toast.makeText(this, "无法读取该图片", Toast.LENGTH_SHORT).show();
			return;
		}

		if (FileTool.saveWallpaperFile(imgBytes)) {
			Intent i =new Intent(this, ClientImageListActivity.class);
			startActivity(i);

			AutoChangeWallPaperService.refreshWallpaperDir();
		}else{
			Toast.makeText(this, "文件保存失败", Toast.LENGTH_SHORT).show();
		}
	}

}
