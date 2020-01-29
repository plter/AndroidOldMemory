package com.plter.lib.android.java.controls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.plter.lib.android.java.net.Http;
import com.plter.lib.android.java.net.HttpBytesCompleteHandler;



/**
 * 异步ImageView对象
 * 派发的事件列表<br>
 * AsyncImageViewEvent.COMPLETE<br>
 * AsyncImageViewEvent.IO_ERROR<br>
 * AsyncImageViewEvent.MALFORMED_URL_ERROR<br>
 * 
 * @author xtiqin
 *
 */
public class AsyncImageView extends ImageView{

	public AsyncImageView(Context context) {
		super(context);
	}

	public AsyncImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AsyncImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	/**
	 * 异步加载图片
	 * @param url	图片的网络地址
	 */
	public void loadImage(String url,final AsyncImageViewCompleteHandler handler){
		Http.getBytes(url, new HttpBytesCompleteHandler() {
			
			public void onResult(byte[] bytes) {
				if (bytes==null) {
					return;
				}
				
				if (currentBitmap!=null) {
					currentBitmap.recycle();
				}
				currentBitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
				setImageBitmap(currentBitmap);

				if (handler!=null) {
					handler.execute(currentBitmap);
				}
			}
		}, true);
	}


	/**
	 * 异步加载图片
	 * @param url
	 */
	public void loadImage(String url){
		loadImage(url, null);
	}


	private Bitmap currentBitmap=null;
}
