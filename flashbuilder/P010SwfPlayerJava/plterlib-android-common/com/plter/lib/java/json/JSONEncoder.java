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

package com.plter.lib.java.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


/**
 * JSON编码工具类
 * @author xtiqin
 *
 */
public class JSONEncoder {

	/**
	 * 将Java对象编码成JSON格式的字符串
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String encode(Object obj){
		if (obj == null) {
			return null;
		}

		if (isBaseType(obj)) {
			return obj+"";
		}else if (obj instanceof Map) {
			
			//progress
			allNum=((Map) obj).size();
			currentNum=0;
			
			String jsonStr="{";
			String key="";
			Object value;

			for (Object k : ((Map) obj).keySet()) {

				key=(String) k;
				if (key!=null) {
					value=((Map) obj).get(key);
					if (value!=null) {
						jsonStr+="\""+key+"\":";

						if (value instanceof String) {
							jsonStr+="\""+value+"\"";
						}else{
							jsonStr+=encode(value);
						}

						jsonStr+=",";
					}
				}
				
				currentNum++;
				dispatchProgress();
			}
			jsonStr=jsonStr.substring(0, jsonStr.length()-1);
			jsonStr+="}";

			dispatchComplete(jsonStr);
			return jsonStr;
		}else if (obj instanceof Collection) {
			String jsonStr="[";
			Iterator it=((Collection) obj).iterator();
			Object value;
			
			//progress
			allNum=((Collection) obj).size();
			currentNum=0;
			
			while(it.hasNext()){
				value = it.next();

				if (value==null) {
					continue;
				}

				if (value instanceof String) {
					jsonStr+="\""+value+"\"";
				}else{
					jsonStr+=encode(value);
				}

				jsonStr+=",";
				
				currentNum++;
				dispatchProgress();
			}
			jsonStr=jsonStr.substring(0, jsonStr.length()-1);
			jsonStr+="]";

			dispatchComplete(jsonStr);
			return jsonStr;
		}else if(obj.getClass().isArray()){
			String jsonStr="[";
			int len=Array.getLength(obj);
			Object value;
			
			//progress
			allNum=len;
			currentNum=0;
			
			for (int i = 0; i < len; i++) {
				value = Array.get(obj, i);

				if (value ==null) {
					continue;
				}

				if (value instanceof String) {
					jsonStr+="\""+value+"\"";
				}else{
					jsonStr+=encode(value);
				}

				jsonStr+=",";
				currentNum++;
				dispatchProgress();
			}

			jsonStr=jsonStr.substring(0, jsonStr.length()-1);
			jsonStr+="]";

			dispatchComplete(jsonStr);
			return jsonStr;
		}else{

			String jsonStr="{";
			Object value;
			String key;
			Field[] fields=obj.getClass().getFields();
			Method[] methods=obj.getClass().getMethods();
			
			//progress
			allNum=fields.length+methods.length;
			currentNum=0;

			for (Field f : fields) {
				try {
					value=f.get(obj);

					if (value==null) {
						continue;
					}

					key=f.getName();

					jsonStr+="\""+key+"\":";

					if (value instanceof String) {
						jsonStr+="\""+value+"\"";
					}else{
						jsonStr+=encode(value);
					}

					jsonStr+=",";
				} catch (Exception e) {
					value=null;
				}
				
				currentNum++;
				dispatchProgress();
			}


			for (Method m : methods) {
				if (m.getParameterTypes().length>0) {
					continue;
				}

				key=m.getName();

				if (key.length()>3&&
						key.substring(0, 3).equals("get")&&
						!key.equals("getClass")) {

					key=key.substring(3);
					key=key.substring(0, 1).toLowerCase()+key.substring(1);

					try {
						value=m.invoke(obj);

						if (value!=null) {
							jsonStr+="\""+key+"\":";

							if (value instanceof String) {
								jsonStr+="\""+value+"\"";
							}else{
								jsonStr+=encode(value);
							}

							jsonStr+=",";
						}
					} catch (Exception e) {
						value=null;
					}
				}
				
				currentNum++;
				dispatchProgress();
			}

			jsonStr=jsonStr.substring(0, jsonStr.length()-1);
			jsonStr+="}";
			
			dispatchComplete(jsonStr);
			return jsonStr;
		}
	}

	/**
	 * 判断一个对象是否为基本类型
	 * @param obj
	 * @return
	 */
	private boolean isBaseType(Object obj){
		return obj instanceof Integer||
				obj instanceof Long||
				obj instanceof String||
				obj instanceof Float||
				obj instanceof Double||
				obj instanceof Short||
				obj instanceof Byte||
				obj==null;
	}


	public JSONProgressListener getJsonProgressListener() {
		return jsonProgressListener;
	}


	/**
	 * 设置编码时的进度侦听器
	 * @param jsonProgressListener
	 */
	public void setJsonProgressListener(JSONProgressListener jsonProgressListener) {
		this.jsonProgressListener = jsonProgressListener;
	}

	private void dispatchProgress(){
		if (getJsonProgressListener()!=null) {
			getJsonProgressListener().onProgress(currentNum/allNum);
		}
	}
	
	private void dispatchComplete(Object result){
		if (getJsonProgressListener()!=null) {
			getJsonProgressListener().onComplete(result);
		}
	}


	private JSONProgressListener jsonProgressListener=null;
	private float allNum=1;
	private float currentNum=0;

}
