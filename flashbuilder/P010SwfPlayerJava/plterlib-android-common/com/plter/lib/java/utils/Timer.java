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

import java.util.TimerTask;

import com.plter.lib.java.dartevt.EventTarget;

public class Timer extends EventTarget{

	
	public Timer(int delay,int repeatCount) {
		setDelay(delay);
		setRepeatCount(repeatCount);
	}
	
	public Timer(int delay) {
		setDelay(delay);
	}
	
	/**
	 * 启动计时器
	 */
	public void start(){
		if (running) {
			return;
		}
		
		timer=new java.util.Timer();
		timerTask=new TimerTask() {
			
			
			public void run() {
				
				currentCount++;
				
				on().timer.dispatch(timerEvent);
				
				if (getRepeatCount()>0&&
					currentCount>=repeatCount) {
					
					on().timerComplete.dispatch(timerCompleteEvent);
					
					reset();
				}
			}
		};
		timer.schedule(timerTask, delay, delay);
		running=true;
	}
	
	
	/**
	 * 停止计时器
	 */
	public void stop(){
		if (running) {
			timer.cancel();
			timerTask.cancel();
			
			running=false;
		}
	}
	
	
	/**
	 * 停止并重置计时器
	 */
	public void reset(){
		stop();
		currentCount=0;
	}
	
	
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}


	/**
	 * 取得计时器计数
	 * @return
	 */
	public int getRepeatCount() {
		return repeatCount;
	}


	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}


	public int getCurrentCount() {
		return currentCount;
	}


	/**
	 * 计时器是否正在运行
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}
	
	
	
	public TimerEvents on() {
		return evts;
	}
	
	private final TimerEvents evts=new TimerEvents();


	//private values
	private int delay=1000;
	private int repeatCount=0;
	private java.util.Timer timer;
	private TimerTask timerTask;
	private int currentCount=0;
	private boolean running=false;
	private final TimerEvent timerEvent=new TimerEvent(TimerEvent.TIMER);
	private final TimerEvent timerCompleteEvent=new TimerEvent(TimerEvent.TIMER_COMPLETE);
	
	
	
	public class TimerEvents extends Events{
		
		public final EventListenerList<TimerEvent> timer=new EventListenerList<TimerEvent>();
		
		public final EventListenerList<TimerEvent> timerComplete=new EventListenerList<TimerEvent>();
	}
}
