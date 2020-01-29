package com.plter.lib.java.evt;

import java.util.ArrayList;
import java.util.List;

import com.plter.lib.java.lang.ICallback;

public class EventListenerList<CALLBACK_ARGS_TYPE> {


	public EventListenerList(Object target) {
		setTarget(target);
	}

	public EventListenerList() {
	}


	private final List<ICallback<CALLBACK_ARGS_TYPE>> EVENT_LIST = new ArrayList<ICallback<CALLBACK_ARGS_TYPE>>();

	public List<ICallback<CALLBACK_ARGS_TYPE>> getEventList() {
		return EVENT_LIST;
	}

	public void add(ICallback<CALLBACK_ARGS_TYPE> callback){
		EVENT_LIST.add(callback);
	}

	public void remove(ICallback<CALLBACK_ARGS_TYPE> callback){
		EVENT_LIST.remove(callback);
	}

	public boolean has(ICallback<CALLBACK_ARGS_TYPE> callback){
		return EVENT_LIST.contains(callback);
	}


	private ICallback<CALLBACK_ARGS_TYPE> callback=null;
	
	public boolean dispatch(CALLBACK_ARGS_TYPE... arg){
		boolean success = true;

		for (int i = 0; i < EVENT_LIST.size(); i++) {
			try{
				callback = EVENT_LIST.get(i);
				
				if(!callback.execute(arg)){
					success=false;
				}
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}

		return success;
	}

	public Object getTarget() {
		return target;
	}

	private void setTarget(Object target) {
		this.target = target;
	}

	private Object target=null;
}
