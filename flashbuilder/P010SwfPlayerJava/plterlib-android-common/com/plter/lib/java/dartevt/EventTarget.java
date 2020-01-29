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

package com.plter.lib.java.dartevt;

import java.util.ArrayList;

public class EventTarget implements IEventTarget{


	/**
	 * 指定是否为调试状态，如果为高度状态，则错误事件会有显式通知，否则将忽略所有错误事件
	 */
	public static boolean debugMode=true;


	/**
	 * 指定触发事件的目标对象，如果传null则本身为事件的目标对象
	 * @param target
	 */
	public EventTarget(IEventTarget target) {
		this.target=target;
	}

	public EventTarget() {}


	public IEvents on(){
		return es;
	}

	public <E extends Event> EventListenerList<E> newEventListenerList(){
		return new EventListenerList<E>();
	}


	private final IEvents es=new Events();
	private IEventTarget target=null;


	/*******************************************************/
	public class Events implements IEvents{		
		public final EventListenerList<Event> complete=new EventListenerList<Event>();
	}


	/********************************************************/

	public class EventListenerList<E extends Event> {


		/**
		 * 添加一个事件侦听器
		 * @param handler		事件的处理器
		 * @param useCapture	是否侦听捕获阶段
		 * @return
		 */
		public EventListenerList<E> add(IEventListener<E> handler){
			functions.add(new Function<E>(handler,EventTarget.this.target==null?EventTarget.this:EventTarget.this.target));
			return this;
		}


		/**
		 * 移除一个事件侦听器
		 * @param handler		事件处理器
		 * @param useCapture	是否为侦听捕获阶段的处理器
		 * @return
		 */
		public EventListenerList<E> remove(IEventListener<E> handler){
			Function<E> f;

			for (int i = 0; i < functions.size(); i++) {
				f=functions.get(i);
				if (f.handler==handler) {
					functions.remove(f);
				}
			}
			return this;
		}


		/**
		 * 派发一个事件
		 * @param event
		 * @return
		 */
		public boolean dispatch(E event){
			Function<E> f;

			for (int i = 0; i < functions.size(); i++) {
				try{
					f=functions.get(i);
					event.setTarget(f.target);
					f.handler.handle(event);
				}catch(IndexOutOfBoundsException e){
					e.printStackTrace();
				}
			}

			return !event.isDefaultPrevented();
		}

		private final ArrayList<Function<E>> functions=new ArrayList<Function<E>>();
	}

	/************************************************************************/
	private static class Function<E extends Event>{
		public Function(IEventListener<E> handler,IEventTarget target) {
			this.handler=handler;
			this.target=target;
		}

		public IEventListener<E> handler=null;
		public IEventTarget target=null;
	}
}
