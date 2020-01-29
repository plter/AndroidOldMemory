package com.plter.lib.java.evt;

import com.plter.lib.java.lang.ICallback;

public abstract class EventCallback<T> implements ICallback<T> {

	
	
	private Object target=null;

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
}
