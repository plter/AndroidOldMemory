package com.plter.iwm.tools
{
	import flash.net.SharedObject;
	
	public class SharedObjectMgr
	{
		
		private static var __thisClass:SharedObjectMgr;
		
		
		private var _sharedObject:SharedObject;
		
		public function SharedObjectMgr()
		{
			_sharedObject=SharedObject.getLocal("iwm");
		}
		
		public static function get sharedObjectMgr():SharedObjectMgr{
			if(__thisClass==null){
				__thisClass=new SharedObjectMgr;
			}
			return __thisClass;
		}
		
		public function get jpgQuality():int
		{
			var value:int=sharedData.jpgQyality;
			if(value<=0){
				value=60;
				sharedData.jpgQyality=60;
			}
			
			return value;
		}
		
		public function set jpgQuality(value:int):void
		{
			sharedData.jpgQyality = value;
			flush();
		}
		
		public function get textFont():String
		{
			var font:String=sharedData.textFont;
			if(font==null){
				font="Arial";
			}
			return font;
		}
		
		public function get textSize():int
		{
			var size:int=sharedData.textSize;
			if(size==0){
				size=12;
			}
			
			return size;
		}
		
		public function set textSize(value:int):void
		{
			sharedData.textSize = value;
		}
		
		
		public function set textFont(value:String):void
		{
			sharedData.textFont = value;
		}
		
		
		public function get textColor():uint
		{
			var color:uint=sharedData.textColor;
			return color;
		}
		
		public function set textColor(value:uint):void
		{
			sharedData.textColor = value;
		}
		
		
		/**
		 *水印透明值 
		 */
		public function get markAlpha():Number
		{
			var value:Number=sharedData.markAlpha;
			
			if(value<=0||isNaN(value)){
				value=1;
			}
			return value;
		}
		
		/**
		 * @private
		 */
		public function set markAlpha(value:Number):void
		{
			sharedData.markAlpha = value;
		}
		
		
		/**
		 * 取得本地共享对象的数据
		 */
		private function get sharedData():Object{
			return _sharedObject.data;
		}
		
		private function flush():void{
			_sharedObject.flush();
		}
	}
}