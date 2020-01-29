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
 * JSON编解码时的进度侦听器
 * @author xtiqin
 *
 */
public interface JSONProgressListener {

	
	/**
	 * 进度为0到1之间的float类型的值
	 * @param progress
	 */
	void onProgress(float progress);
	
	
	/**
	 * 编码或者解码完成
	 * @param result 编码或者解码完成后的结果
	 */
	void onComplete(Object result);
	
}
