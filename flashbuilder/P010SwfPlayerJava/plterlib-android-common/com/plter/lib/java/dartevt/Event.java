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


public class Event{
	
	
	public static final String COMPLETE="complete";
	

	public Event(String type,boolean cancelable) {
		setCancelable(cancelable);
		setType(type);
	}
	
	
	public Event(String type) {
		setType(type);
	}
	
	
	public void preventDefault(){
		if (cancelable) {
			setDefaultPrevented(true);
		}
	}
	
	
	
	public String toString() {
		return "["+getClass().getName()+"(type="+getType()+",cancelable="+isCancelable()+")]";
	}
	
	private String type=null;public String getType() {return type;}private void setType(String type) {this.type = type;}
	private boolean cancelable=false;public boolean isCancelable() {return cancelable;}private void setCancelable(boolean cancelable) {this.cancelable = cancelable;}
	private boolean defaultPrevented=false;public boolean isDefaultPrevented() {return defaultPrevented;}private void setDefaultPrevented(boolean defaultPrevented) {this.defaultPrevented = defaultPrevented;}
	private IEventTarget target=null;public IEventTarget getTarget() {return target;}void setTarget(IEventTarget target) {this.target = target;}
}
