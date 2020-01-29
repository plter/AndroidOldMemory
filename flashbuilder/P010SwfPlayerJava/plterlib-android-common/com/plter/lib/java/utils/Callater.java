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

import com.plter.lib.java.dartevt.IEventListener;


/**
 * 延时执行代码
 * @author xtiqin
 *
 */
public abstract class Callater {

	
	
	private Timer timer=null;
	
	/**
	 * 创建一个Callater对象
	 * @param delay	延时时间，单位是毫秒
	 */
	public Callater(int delay) {
		timer=new Timer(delay,1);
		timer.on().timerComplete.add(timerCompleteHandler);
		timer.start();
	}
	
	private final IEventListener<TimerEvent> timerCompleteHandler=new IEventListener<TimerEvent>() {
		
		
		public void handle(TimerEvent event) {
			timer.on().timerComplete.remove(timerCompleteHandler);
			
			execute();
		}
	};
	
	
	/**
	 * 延时执行的代码
	 */
	protected abstract void execute();
	
}
