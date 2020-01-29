package com.plter.mobwinforflash
{
	import flash.events.EventDispatcher;
	import flash.external.ExtensionContext;
	
	public class MFF extends EventDispatcher
	{
		public function MFF()
		{
			if (__locked) 
			{
				throw new Error("该类不允许从外部实例化，请调用 MFF.mff 以取得该类的单例");
			}
			
			context=ExtensionContext.createExtensionContext(ID,null);
			if (context==null) 
			{
				throw new Error("加载本机模块失败：\n1.请确认您是将本库用在Android平台\n2.可能该库已经损坏，请从联系作者以得到完整的库，作者博客：plter.com，作者邮箱：xtiqin@163.com");
			}
		}
		
		
		public function addToTop():void{
			context.call(FUNC_ADD_TO_TOP);
		}
		
		
		public function addToBottom():void{
			context.call(FUNC_ADD_TO_BOTTOM);
		}
		
		
		public static function get mff():MFF{
			if (__mff==null) 
			{
				__locked=false;
				__mff=new MFF();
				__locked=true;
			}
			return __mff;
		}
		
		
		public static const FUNC_ADD_TO_TOP:String="addToTop";
		public static const FUNC_ADD_TO_BOTTOM:String="addToBottom";
		private var context:ExtensionContext=null;
		private static var __mff:MFF=null;
		private static var __locked:Boolean=true;
		public static const ID:String="com.plter.air.extension.android.mobwinforflash";
	}
}