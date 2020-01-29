package com.plter.lib.android.java.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.plter.lib.java.lang.ICallback;

public class Http {

	public static final int ERROR_CODE_CLIENT_PROTOCOL=2;
	public static final int ERROR_CODE_IO=3;
	
	
	/**
	 * 取得一个页面的二进制数据
	 * @param url				页面的地址
	 * @param postPairs			以post方式发送的数据键值对
	 * @param completeCallback	加载成功时的回调
	 * @param faultCallback		加载失败时的回调
	 * @param useCache			是否使用缓存
	 */
	public static void getBytes(String url,NameValuePairs postPairs,HttpBytesCompleteHandler completeCallback,IHttpFaultCallback faultCallback,boolean useCache){
		new HttpLoaderTask(url, postPairs, completeCallback, faultCallback,useCache).execute();
	}
	
	/**
	 * 取得一个页面的二进制数据
	 * @param url				页面的地址
	 * @param postPairs			以post方式发送的数据键值对
	 * @param completeListener	加载成功时的回调
	 * @param useCache			是否使用缓存
	 */
	public static void getBytes(String url,NameValuePairs postPairs,HttpBytesCompleteHandler completeListener,boolean useCache){
		Http.getBytes(url, postPairs, completeListener, null,useCache);
	}
	
	public static void getBytes(String url,HttpBytesCompleteHandler completeListener,boolean useCache){
		Http.getBytes(url, null, completeListener, null,useCache);
	}
	
	public static void getBytes(String url,HttpBytesCompleteHandler completeCallback,IHttpFaultCallback faultCallback,boolean useCache){
		Http.getBytes(url, null, completeCallback, faultCallback,useCache);
	}
	
	public static void getText(String url,NameValuePairs postPairs,final IHttpTextCompleteCallback completeListener,IHttpFaultCallback faultListener,boolean useCache){
		
		Http.getBytes(url, postPairs, new HttpBytesCompleteHandler() {
			
			
			public void onResult(byte[] bytes) {
				if (bytes!=null) {
					try {
						completeListener.execute(new String(bytes,Http.getCharSet()));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}					
				}
			}
		}, faultListener,useCache);
	}
	
	public static void getText(String url,NameValuePairs postPairs,IHttpTextCompleteCallback completeListener,boolean useCache){
		Http.getText(url, postPairs, completeListener, null,useCache);
	}
	
	public static void getText(String url,IHttpTextCompleteCallback completeListener,boolean useCache){
		Http.getText(url, null, completeListener, null,useCache);
	}
	
	public static void getText(String url,IHttpTextCompleteCallback completeCallback,IHttpFaultCallback faultCallback,boolean useCache){
		Http.getText(url, null, completeCallback, faultCallback,useCache);
	}
	
	
	public static String getCharSet() {
		return charSet;
	}

	public static void setCharSet(String charSet) {
		Http.charSet = charSet;
	}
	
	
	/**
	 * 清除缓存
	 */
	public static void clearCache(){
		File[] files = getHttpCachedDataDir().listFiles();
		if (files!=null&&files.length>0) {
			for (File file : files) {
				file.delete();
			}
		}
	}

	private static String charSet="utf-8";
	
	
	public static final String HTTP_CACHED_DATA_DIR="/mnt/sdcard/.plter/httpcache/";
	
	/**
	 * 取得Http数据缓存目录
	 * @return
	 */
	public static final File getHttpCachedDataDir(){
		File dir = new File(HTTP_CACHED_DATA_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
 	}
	

	////////////////////////////////////////////////
	public static interface IHttpTextCompleteCallback extends ICallback<String>{
	}
	public static interface IHttpFaultCallback extends ICallback<Integer>{
	}
	///////////////////////////////////////////////
	private static class HttpLoaderTask extends AsyncTask<Void, Integer, Object>{

		
		public HttpLoaderTask(String url,NameValuePairs postPairs,HttpBytesCompleteHandler completeListener,IHttpFaultCallback faultListener,boolean useCache) {
			this.url=url.replaceAll(" ", "%20");
			this.postPairs=postPairs;
			this.completeCallback=completeListener;
			this.faultCallback=faultListener;
			this.useCache=useCache;
		}
		
		
		
		protected Object doInBackground(Void... params) {
			if (useCache) {
				File cachedFile = new File(getHttpCachedDataDir()+"/"+URLEncoder.encode(url));
				if (cachedFile.exists()) {
					try {
						FileInputStream fis = new FileInputStream(cachedFile);
						byte[] bytes = new byte[fis.available()];
						fis.read(bytes);
						fis.close();
						
						return bytes;
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}else{
					byte[] bytes=getWebContent();
					if (bytes!=null) {
						try {
							cachedFile.createNewFile();
							FileOutputStream fos = new FileOutputStream(cachedFile);
							fos.write(bytes);
							fos.close();
						} catch (IOException e) {
							Log.e("Http", "Need permission \"android.permission.WRITE_EXTERNAL_STORAGE\"");
							e.printStackTrace();
						}
					}
					
					return bytes;
				}
			}else{
				return getWebContent();
			}
		}
		
		
		private byte[] getWebContent(){
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(url);
			try {
				if (postPairs!=null) {
					hp.setEntity(new UrlEncodedFormEntity(postPairs.httpClientNameValuePairs));
				}
				
				HttpResponse hr = hc.execute(hp);
				InputStream is = hr.getEntity().getContent();
				int b = -1;
				
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				while((b=is.read())!=-1){
					bos.write(b);
				}
				
				byte[] bytes = bos.toByteArray();
				bos.close();
				is.close();
				
				return bytes;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				
				publishProgress(ERROR_CODE_CLIENT_PROTOCOL);
			} catch (IOException e) {
				e.printStackTrace();
				
				publishProgress(ERROR_CODE_IO);
			}
			
			return null;
		}
		
		
		
		protected void onPostExecute(Object result) {
			if (completeCallback!=null) {
				completeCallback.execute(result);
			}
			super.onPostExecute(result);
		}
		
		
		protected void onProgressUpdate(Integer... values) {
			if (faultCallback!=null) {
				faultCallback.execute(values[0]);
			}
			super.onProgressUpdate(values);
		}
		
		
		private String url ="";
		private NameValuePairs postPairs=null;
		private HttpBytesCompleteHandler completeCallback=null;
		private IHttpFaultCallback faultCallback=null;
		private boolean useCache=false;
	}
}
