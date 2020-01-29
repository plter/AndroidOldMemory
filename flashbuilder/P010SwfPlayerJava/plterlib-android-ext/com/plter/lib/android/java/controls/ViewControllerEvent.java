package com.plter.lib.android.java.controls;

import com.plter.lib.java.dartevt.Event;

/**
 * ViewController相关的事件
 * @author xtiqin
 *
 */
public class ViewControllerEvent extends Event {

	
	/**
	 * 构建一个ViewControllerEvent事件
	 * @param type			事件的类型
	 * @param cancelable	事件是否可以取消
	 */
	public ViewControllerEvent(String type, boolean cancelable) {
		super(type, cancelable);
	}
	
	
	/**
	 * 构建一个ViewControllerEvent事件
	 * @param type	事件的类型
	 */
	public ViewControllerEvent(String type) {
		super(type);
	}

	/**
	 * 将要后退事件，该事件可以取消
	 */
	public static final String ON_BACKING="onBacking";
	
	/**
	 * 后退完成事件
	 */
	public static final String ON_BACK="onBack";
}
