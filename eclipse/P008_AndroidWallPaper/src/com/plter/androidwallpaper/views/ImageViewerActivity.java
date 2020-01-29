package com.plter.androidwallpaper.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.plter.androidwallpaper.R;
import com.plter.androidwallpaper.config.Config;
import com.plter.androidwallpaper.services.AutoChangeWallPaperService;
import com.plter.androidwallpaper.system.WallPaperTool;

public abstract class ImageViewerActivity extends Activity implements OnClickListener {
	
	private ImageView imageView=null;
	private Button setAsWPBtn,addToListBtn,showListBtn;
	private Bitmap imageBitmap=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_viewer);
		
		//init ui
		imageView = (ImageView) findViewById(R.id.photo);
		setAsWPBtn = (Button) findViewById(R.id.setAsWPBtn);
		addToListBtn = (Button) findViewById(R.id.addToListBtn);
		showListBtn=(Button) findViewById(R.id.showListBtn);
		
		//set listeners
		setAsWPBtn.setOnClickListener(this);
		addToListBtn.setOnClickListener(this);
		showListBtn.setOnClickListener(this);
	}
	
	public void showImage(Bitmap b){
		
		if (b==null) {
			new AlertDialog.Builder(this)
			.setTitle("亲爱的")
			.setMessage("该图片无法显示，可能由于服务器已经删除了该图片")
			.setPositiveButton("返回", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					setResult(NEED_REFRESH);
					finish();
				}
			})
			.show();
			
			return;
		}
		
		setImageBitmap(b);
		imageView.setImageBitmap(b);
	}
	
	public static final int NEED_REFRESH=1;
	
	
	public abstract void addToListBtnClicked();
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setAsWPBtn:
			if (!WallPaperTool.setWallpaper(this, getImageBitmap())) {
				Toast.makeText(this, "设置壁纸失败", Toast.LENGTH_SHORT).show();
			}
			
			Config.setAutoChangeWallPaper(this, false);
			AutoChangeWallPaperService.checkToStartOrStopService(this);
			
			break;
		case R.id.addToListBtn:
			addToListBtnClicked();
			break;
		case R.id.showListBtn:
			Intent i =new Intent(this, ClientImageListActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}

	public Bitmap getImageBitmap() {
		return imageBitmap;
	}

	private void setImageBitmap(Bitmap imageBitmap) {
		this.imageBitmap = imageBitmap;
	}
}
