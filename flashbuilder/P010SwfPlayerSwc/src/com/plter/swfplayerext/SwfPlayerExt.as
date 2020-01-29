package com.plter.swfplayerext
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	
	
	
	[Event(name="selectSwf", type="com.plter.swfplayerext.SwfPlayerExtEvent")]
	public class SwfPlayerExt extends EventDispatcher
	{
		public function SwfPlayerExt(target:IEventDispatcher=null)
		{
			super(target);
			
			extension=ExtensionContext.createExtensionContext("com.plter.air.extension.android.swfplayerext",null);
			extension.addEventListener(StatusEvent.STATUS,extension_statusHandler);
		}
		
		protected function extension_statusHandler(event:StatusEvent):void
		{
			switch(event.code)
			{
				case SwfPlayerExtCode.SELECT_SWF:
				{
					dispatchEvent(new SwfPlayerExtEvent(SwfPlayerExtEvent.SELECT_SWF,extension.call(SwfPlayerExtFuncName.GET_SELECTED_SWF_PATH) as String));
					break;
				}
					
				default:
				{
					break;
				}
			}
		}		
		
		public function browseForOpenSwfFile():void{
			extension.call(SwfPlayerExtFuncName.BROWSE_FOR_OPEN_SWF_FILE);
		}
		
		public function showAlert(msg:String,title:String="",closeButtonLabel:String="OK"):void{
			extension.call(SwfPlayerExtFuncName.SHOW_ALERT,msg,title,closeButtonLabel);
		}
		
		public function showToast(msg:String,willLong:Boolean=false):void{
			extension.call(SwfPlayerExtFuncName.SHOW_TOAST,msg,willLong?1:0);
		}
		
		public function showOptionsDialog():void{
			extension.call(SwfPlayerExtFuncName.SHOW_OPTIONS_DIALOG);
		}
		
		public function hideOptionsDialog():void{
			extension.call(SwfPlayerExtFuncName.HIDE_OPTIONS_DIALOG);
		}
		
		public function dispose():void{
			_disposed=true;
			extension.removeEventListener(StatusEvent.STATUS,extension_statusHandler);
			extension.dispose();
		}

		public function get disposed():Boolean
		{
			return _disposed;
		}
		
		
		private var _disposed:Boolean=false;
		private var extension:ExtensionContext=null;
	}
}