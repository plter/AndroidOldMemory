/**
   Copyright [plter] [xtiqin]
   http://plter.sinaapp.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   This is a part of PlterAndroidLib on 
   http://plter.sinaapp.com/?cat=14
 */

package com.plter.lib.android.java.json;

import android.os.AsyncTask;

import com.plter.lib.java.dartevt.EventTarget;
import com.plter.lib.java.json.JSONDecoder;
import com.plter.lib.java.json.JSONEncoder;
import com.plter.lib.java.json.JSONException;
import com.plter.lib.java.json.JSONProgressListener;

/**
 * 异步JSON解码工具
 * 派发的事件列表如下：<br>
 * AsyncJSONEvent.COMPLETE<br>
 * AsyncJSONEvent.ON_PROGRESS<br>
 * AsyncJSONEvent.SYNTAX_ERROR<br>
 * @author xtiqin
 *
 */
public class AsyncJSON extends EventTarget {



	/**
	 * 开始编码操作
	 * @param obj
	 */
	public void encode(Object obj){
		new JSONTask().execute(ENCODE_OPT,obj);
	}


	/**
	 * 开始解码操作
	 * @param jsonStr
	 */
	public void decode(String jsonStr){
		new JSONTask().execute(DECODE_OPT,jsonStr);
	}



	private static final int ENCODE_OPT=1;
	private static final int DECODE_OPT=2;


	private class JSONTask extends AsyncTask<Object, Float, Object>{


		/**
		 * 语法错误
		 */
		private static final int SYNTAX_ERROR = -1;


		
		protected Object doInBackground(Object... params) {
			try{
				switch ((Integer)(params[0])) {
				case ENCODE_OPT:
					if (encoder==null) {
						encoder=new JSONEncoder();
						encoder.setJsonProgressListener(progressListener);
					}

					return encoder.encode(params[1]);
				case DECODE_OPT:
					if (decoder==null) {
						decoder=new JSONDecoder();
						decoder.setJsonProgressListener(progressListener);
					}

					return decoder.decode((String) params[1]);
				}
			}catch(JSONException e){
				publishProgress((float)SYNTAX_ERROR);
			}

			return null;
		}


		
		protected void onPostExecute(Object result) {
			on().complete.dispatch(new AsyncJSONEvent(AsyncJSONEvent.COMPLETE, result));
			super.onPostExecute(result);
		}


		
		protected void onProgressUpdate(Float... values) {
			float progress=values[0];

			if (progress==SYNTAX_ERROR) {
				on().syntaxError.dispatch(new AsyncJSONEvent(AsyncJSONEvent.SYNTAX_ERROR));
			}else{
				asyncProgressJSONEvent.setProgress(progress);
				on().progress.dispatch(asyncProgressJSONEvent);
			}

			super.onProgressUpdate(values);
		}



		private final AsyncJSONEvent asyncProgressJSONEvent=new AsyncJSONEvent(AsyncJSONEvent.ON_PROGRESS);
		private JSONDecoder decoder=null;
		private JSONEncoder encoder=null;

		private final JSONProgressListener progressListener=new JSONProgressListener() {

			
			public void onProgress(float progress) {
				publishProgress(progress);
			}

			
			public void onComplete(Object result) {
			}
		};

	}
	
	
	
	public AsyncJSONEvents on() {
		return evts;
	}
	
	
	private final AsyncJSONEvents evts=new AsyncJSONEvents();
	
	public class AsyncJSONEvents extends Events{
		public final EventListenerList<AsyncJSONEvent> complete=new EventListenerList<AsyncJSONEvent>();
		public final EventListenerList<AsyncJSONEvent> progress=new EventListenerList<AsyncJSONEvent>();
		public final EventListenerList<AsyncJSONEvent> syntaxError=new EventListenerList<AsyncJSONEvent>();

	}

}
