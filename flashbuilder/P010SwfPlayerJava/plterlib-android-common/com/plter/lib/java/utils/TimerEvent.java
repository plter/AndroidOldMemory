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

package com.plter.lib.java.utils;

import com.plter.lib.java.dartevt.Event;


/**
 * 计时器事件
 * @author xtiqin
 *
 */
public class TimerEvent extends Event {
	
	/**
	 * 计时器执行事件
	 */
	public static final String TIMER="timer";
	
	
	/**
	 * 计时器完成事件
	 */
	public static final String TIMER_COMPLETE="timerComplete";
	
	
	public TimerEvent(String type) {
		super(type);
	}

}
