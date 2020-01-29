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


/**
 * JSON编解码工具
 * @author xtiqin
 *
 */
public class JSON {

	
	private static final JSONEncoder encoder=new JSONEncoder();
	
	/**
	 * 将Java对象编码成JSON格式的字符串
	 * @param obj 将被编码的对象
	 * @return
	 */
	public static String encode(Object obj){
		return encoder.encode(obj);
	}
	
	private static final JSONDecoder decoder=new JSONDecoder();
	
	/**
	 * 解析JSON字符串，将JSON格式的字符串解析为HashMap、ArrayList或者其它基本数据类型
	 * @param jsonStr JSON格式的字符串
	 * @return
	 */
	public static Object decode(String jsonStr){
		return decoder.decode(jsonStr);
	}
}

