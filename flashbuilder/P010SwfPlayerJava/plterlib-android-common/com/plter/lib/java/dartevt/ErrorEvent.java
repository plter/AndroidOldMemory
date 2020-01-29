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

import com.plter.lib.java.lang.Error;


public class ErrorEvent extends Event{

	public ErrorEvent(String type,String errorMsg) {
		super(type);
		setErrorMsg(errorMsg);
	}
	
	public ErrorEvent(String type) {
		super(type);
	}
	
	
	public ErrorEvent(String type,int errorID) {
		super(type);
		setErrorID(errorID);
	}
	
	
	public ErrorEvent(String type,String errorMsg,int errorID) {
		super(type);
		setErrorMsg(errorMsg);
		setErrorID(errorID);
	}
	
	void throwError(){
		throw new Error(errorMsg, errorID);
	}
	


	private String errorMsg="";public String getErrorMsg() {return errorMsg;}private void setErrorMsg(String errorMsg) {this.errorMsg = errorMsg;}
	private int errorID=0;public int getErrorID() {return errorID;}private void setErrorID(int errorID) {this.errorID = errorID;}
	
	
}
