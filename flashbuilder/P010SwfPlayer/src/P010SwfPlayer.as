package
{
	import flash.desktop.NativeApplication;
	import flash.desktop.SystemIdleMode;
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.system.LoaderContext;
	
	public class P010SwfPlayer extends Sprite
	{
		
		public function P010SwfPlayer()
		{
			super();
			
			trace(File.applicationDirectory.nativePath);
			
			stage.scaleMode=StageScaleMode.NO_SCALE;
			stage.align=StageAlign.TOP_LEFT;
			
			NativeApplication.nativeApplication.systemIdleMode=SystemIdleMode.KEEP_AWAKE;
			NativeApplication.nativeApplication.addEventListener(Event.ACTIVATE,nativeApp_activateHandler);
			NativeApplication.nativeApplication.addEventListener(Event.DEACTIVATE,nativeApp_deactivateHandler);
			
			loader=new Loader;
			addChild(loader);
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE,function(event:Event):void{
				configPropertisByLoaderInfo(loader.contentLoaderInfo);
			},false,0,true);
			
			context= new LoaderContext();
			context.allowCodeImport = true;
		}
		
		protected function nativeApp_activateHandler(event:Event):void
		{
			loader.loadBytes(new MediaBytesClass,context);
		}
		
		protected function nativeApp_deactivateHandler(event:Event):void
		{
			loader.unloadAndStop(true);
		}		
		
		private function configPropertisByLoaderInfo(info:LoaderInfo):void{
			stage.frameRate=info.frameRate;
			
			loader.scaleX=stage.stageWidth/info.width;
			loader.scaleY=loader.scaleX;
			
			var loaderInfoWidth:Number=info.width*loader.scaleX;
			var loaderInfoHeight:Number=info.height*loader.scaleX;
			
			if (loaderInfoHeight>stage.stageHeight) 
			{
				loader.scaleY=stage.stageHeight/info.height;
				loader.scaleX=loader.scaleY;
			}
			
			loaderInfoWidth=info.width*loader.scaleX;
			loaderInfoHeight=info.height*loader.scaleX;
			
			loader.x=(stage.stageWidth-loaderInfoWidth)/2;
			loader.y=(stage.stageHeight-loaderInfoHeight)/2;
		}
		
		
		private	var context:LoaderContext;
		private var loader:Loader;
		
		[Embed(source="../assets/Media.swf",mimeType="application/octet-stream")]
		private static var MediaBytesClass:Class;
	}
}