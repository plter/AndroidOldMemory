package com.plter.androidwallpaper.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.AsyncTask;

import com.plter.lib.java.lang.ICallback;

public class GetStreamBytesTask extends AsyncTask<InputStream, Void, Object> {

	
	private ICallback<Object> callback=null;
	
	public GetStreamBytesTask(ICallback<Object> callback) {
		this.callback=callback;
	}
	
	
	@Override
	protected Object doInBackground(InputStream... params) {
		InputStream is = params[0];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		int b=-1;
		try {
			while((b=is.read())>-1){
				bos.write(b);
			}
			
			byte[] bytes = bos.toByteArray();
			
			bos.close();
			is.close();
			
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@Override
	protected void onPostExecute(Object result) {
		callback.execute(result);
		super.onPostExecute(result);
	}

}
