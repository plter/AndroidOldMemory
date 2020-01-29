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

import com.plter.lib.java.dartevt.Event;

/**
 * 异步JSON编码解码工具事件
 * @author xtiqin
 *
 */
public class AsyncJSONEvent extends Event {
	
	
	/**
	 * 完成事件
	 */
	public static final String COMPLETE="complete";
	
	
	/**
	 * 进度事件
	 */
	public static final String ON_PROGRESS="onProgress";
	
	
	/**
	 * 语法错误
	 */
	public static final String SYNTAX_ERROR="syntaxError";
	

	public AsyncJSONEvent(String type) {
		super(type);
	}
	
	
	public AsyncJSONEvent(String type,float progress) {
		super(type);
		this.progress=progress;
	}
	
	public AsyncJSONEvent(String type,Object result){
		super(type);
		
		this.result=result;
	}
	
	public float getProgress() {
		return progress;
	}

	void setProgress(float progress) {
		this.progress = progress;
	}


	/**
	 * 得到异步JSON编码或者解码之后的结果
	 * @return
	 */
	public Object getResult() {
		return result;
	}

	private float progress=0;
	
	private Object result=null;

}
