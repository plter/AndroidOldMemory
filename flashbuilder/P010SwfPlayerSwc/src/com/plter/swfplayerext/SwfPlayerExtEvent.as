package com.plter.swfplayerext
{
	import flash.events.Event;
	
	public class SwfPlayerExtEvent extends Event
	{
		public function SwfPlayerExtEvent(type:String,swfPath:String)
		{
			super(type, false, false);
			
			_swfPath=swfPath;
		}
		
		override public function clone():Event{
			return new SwfPlayerExtEvent(type,swfPath);
		}

		public function get swfPath():String
		{
			return _swfPath;
		}
		
		private var _swfPath:String=null;
		
		public static const SELECT_SWF:String="selectSwf";
	}
}