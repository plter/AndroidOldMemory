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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * JSON数据解码工具
 * @author xtiqin
 *
 */
public class JSONDecoder {

	
	/**
	 * 解析JSON字符串，将JSON格式的字符串解析为HashMap、ArrayList或者其它 
	 * @param jsonStr
	 * @return
	 */
	public Object decode(String jsonStr){
		offset=0;
		this.jsonStr=jsonStr;
		jsonStrLen=jsonStr.length();
		
		dispatchProgress();
		Object result=decode();
		dispatchComplete(result);
		return result;
	}

	private Object decode(){
		
		while(offset<jsonStrLen){
			currentChar=jsonStr.charAt(offset);

			switch (currentChar) {
			case '{':
				final HashMap<String, Object> map=new HashMap<String, Object>();
				
				GetKeyAndValue:
					while(offset<jsonStrLen){
						//read key start
						keyStartIndex=jsonStr.indexOf('\"', offset)+1;
						keyEndIndex=jsonStr.indexOf('\"', keyStartIndex);
						String key=jsonStr.substring(keyStartIndex, keyEndIndex);
						if (key.equals("")||!isRightKey(key)) {
							throw new JSONException("JSON Syntax error near index "+keyEndIndex);
						}
						//read key end

						//read value start
						valueStartIndex=jsonStr.indexOf(':',keyEndIndex);
						offset=valueStartIndex+1;
						dispatchProgress();
						String value=null;

						while(offset<jsonStrLen){
							currentChar=jsonStr.charAt(offset);
							
							switch (currentChar) {
							case '\"':
								//string value start
								valueStartIndex=offset+1;
								valueEndIndex=jsonStr.indexOf('\"', valueStartIndex);
								value=jsonStr.substring(valueStartIndex, valueEndIndex);
								if (!isRightValue(value)) {
									throw new JSONException("JSON Syntax error near index "+offset);
								}
								map.put(key, value);

								offset=valueEndIndex;
								dispatchProgress();
								
								while(offset<jsonStrLen){
									currentChar=jsonStr.charAt(offset);
									switch (currentChar) {
									case ',':
										offset++;
										dispatchProgress();
										continue GetKeyAndValue;
									case '}':
										break GetKeyAndValue;
									}
									
									offset++;
								}
							case ',':
								//int or float value end
								valueStartIndex++;
								valueEndIndex=offset;
								value=jsonStr.substring(valueStartIndex, valueEndIndex);
								if (isRightValue(value)) {
									map.put(key, convertToNumber(value));
								}
								
								offset++;
								dispatchProgress();
								continue GetKeyAndValue;
							case '}':
								//int or float value end
								valueStartIndex++;
								valueEndIndex=offset;
								value=jsonStr.substring(valueStartIndex, valueEndIndex);
								
								if (isRightValue(value)) {
									map.put(key, convertToNumber(value));
								}
								break GetKeyAndValue;
							case '{':
								//object value start
							case '[':
								//array value start
								map.put(key, decode());
								break;
							case ']':
								throw new JSONException("JSON Syntax error near index "+offset);
							}
							//read value end 
							
							offset++;
						}
					}
				return map;
			case '[':
				final ArrayList<Object> arr=new ArrayList<Object>();
				offset++;
				valueStartIndex=offset;
				String value=null;
				
				dispatchProgress();
				
				GetArrayValue:
				while(offset<jsonStrLen){
					currentChar=jsonStr.charAt(offset);
					
					switch (currentChar) {
					case '\"':
						//string value start
						valueStartIndex=offset+1;
						valueEndIndex=jsonStr.indexOf('\"', valueStartIndex);
						value=jsonStr.substring(valueStartIndex, valueEndIndex);
						if (!isRightValue(value)) {
							throw new JSONException("JSON Syntax error near index "+offset);
						}
						
						arr.add(value);
						
						offset=valueEndIndex;
						dispatchProgress();
						
						while(offset<jsonStrLen){
							currentChar=jsonStr.charAt(offset);
							switch (currentChar) {
							case ',':
								offset++;
								valueStartIndex=offset;
								dispatchProgress(); 
								continue GetArrayValue;
							case ']':
								break GetArrayValue;
							}
							offset++;
						}
						break;
					case ',':
						//int or float value end
						valueEndIndex=offset;
						value=jsonStr.substring(valueStartIndex, valueEndIndex);
						if (isRightValue(value)) {
							arr.add(convertToNumber(value));
						}
						
						offset++;
						dispatchProgress();
						valueStartIndex=offset;
						continue GetArrayValue;
					case ']':
						valueEndIndex=offset;
						value=jsonStr.substring(valueStartIndex, valueEndIndex);
						if (isRightValue(value)) {
							arr.add(convertToNumber(value));
						}
						break GetArrayValue;
					case '[':
					case '{':
						arr.add(decode());
						break;
					case '}':
						throw new JSONException("JSON Syntax error near index "+offset);
					}
					
					offset++;
					dispatchProgress();
				}
				
				return arr;
			default:
				offset++;
				dispatchProgress();
				break;
			}
		}

		return null;
	}

	private static Object convertToNumber(String value){
		try{
			if (value.indexOf('.')<=-1) {
				try{
					return new Integer(value);
				}catch(Exception e){
					return new Long(value); 
				}
			}else{
				try{
					return new Float(value);
				}catch(Exception e){
					return new Double(value);
				}
			}
		}catch(Exception e){
			return 0;
		}
	}

	/**
	 * 判断一个key的格式是否为正确的
	 * @param key
	 * @return
	 */
	private boolean isRightKey(String key){
		return key.indexOf('{')==-1&&
				key.indexOf('}')==-1&&
				key.indexOf('[')==-1&&
				key.indexOf(']')==-1&&
				key.indexOf(',')==-1&&
				key.indexOf(':')==-1&&
				key.indexOf('.')==-1;
	}

	/**
	 * 判断一个值的格式是否为正确的
	 * @param value
	 * @return
	 */
	private boolean isRightValue(String value){
		return value.indexOf('{')==-1&&
				value.indexOf('}')==-1&&
				value.indexOf('[')==-1&&
				value.indexOf(']')==-1&&
				value.indexOf(',')==-1&&
				value.indexOf(':')==-1;
	}
	
	public JSONProgressListener getJsonProgressListener() {
		return jsonProgressListener;
	}


	/**
	 * 设置解码时的进度侦听器
	 * @param jsonProgressListener
	 */
	public void setJsonProgressListener(JSONProgressListener jsonProgressListener) {
		this.jsonProgressListener = jsonProgressListener;
	}
	
	private void dispatchProgress(){
		if (getJsonProgressListener()!=null) {
			currentNum=offset;
			allNum=jsonStrLen;
			getJsonProgressListener().onProgress(currentNum/allNum);
		}
	}
	
	private void dispatchComplete(Object result){
		if (getJsonProgressListener()!=null) {
			getJsonProgressListener().onComplete(result);
		}
	}
	
	private int offset=0;
	private String jsonStr="";
	private int jsonStrLen=0;
	private char currentChar;
	private int keyStartIndex=0;
	private int keyEndIndex=0;
	private int valueStartIndex=0;
	private int valueEndIndex=0;
	private JSONProgressListener jsonProgressListener=null;
	private float allNum=1;
	private float currentNum=0;
	
}
