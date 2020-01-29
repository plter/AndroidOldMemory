package com.plter.pplayer.config
{
	import flash.display.DisplayObject;
	import flash.net.SharedObject;
	import flash.net.registerClassAlias;

	public class SOManager
	{
		
		private static var __so:SharedObject=null;
		
		public static function get sharedObject():SharedObject{
			if (__so==null) 
			{
				__so=SharedObject.getLocal("pplayer");
			}
			
			return __so;
		}
		
		
		private static var _lastOpenPath:String="/";

		/**
		 * 最后一次所打开的目录
		 */
		public static function get lastOpenPath():String
		{
			
			_lastOpenPath=sharedObject.data.lastOpenPath;
			if (!_lastOpenPath) 
			{
				_lastOpenPath="/";
			}
			return _lastOpenPath;
		}

		/**
		 * @private
		 */
		public static function set lastOpenPath(value:String):void
		{
			sharedObject.data.lastOpenPath=value;
			sharedObject.flush();
		}

	}
}