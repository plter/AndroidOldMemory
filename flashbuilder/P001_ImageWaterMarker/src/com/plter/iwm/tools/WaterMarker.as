package com.plter.iwm.tools
{
	import com.plter.iwm.views.ConfigWindow;
	import com.plter.utils.zip.ZipDecoder;
	import com.plter.utils.zip.ZipEncoder;
	
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.net.FileReference;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.utils.ByteArray;
	
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.graphics.codec.IImageEncoder;
	import mx.graphics.codec.JPEGEncoder;
	import mx.graphics.codec.PNGEncoder;
	import mx.managers.CursorManager;
	
	/**
	 * 标记完成后将触发此事件 
	 */     
	[Event(name="marked",type="com.plter.iwm.tools.WaterMarkerEvent")]
	
	public class WaterMarker extends EventDispatcher
	{
		
		/**
		 * 绘图板
		 */
		private var _drawingBoard:Sprite;
		private var _imageLoader:Loader;
		
		private var __sourceImages:Array=[];
		private var _markedFiles:Array=[];
		private var _zipEn:ZipEncoder;
		private var _zipDe:ZipDecoder;
		
		
		public function WaterMarker()
		{
			_imageLoader=new Loader;
		}
		
		/**
		 * 开始对图片进行打水印操作
		 * @param arr   FileReference数组
		 */
		public function startMark(arr:Array):void{
			ImageWaterMarker.mainApp.enabled=false;
			_markedFiles.length=0;
			__sourceImages.length=0;
			_zipEn=new ZipEncoder;
			_zipEn.setComment("http://tools.plter.com/iwm");
			
			for(var i:int=0;i<arr.length;i++){
				__sourceImages[i]=arr[i];
			}
			
			markImages(__sourceImages);
		}
		
		
		/**
		 * 取得标记后的文件的二进制数据
		 * @param name  文件的名称
		 * @return              ByteArray
		 */
		public function getMarkedFile(name:String):ByteArray{
			return _zipDe.getFile(name);
		}
		
		
		/**
		 * 取得标记后的zip文件的地进制数据
		 * @return      flash.utils.ByteArray
		 */
		public function getMarkedFilesZipBytes():ByteArray{
			return _zipEn.byteArray;
		}
		
		private var _fr:FileReference;
		
		private function markImages(arr:Array):void{
			if(arr.length<=0){
				ImageWaterMarker.mainApp.enabled=true;
				_zipEn.finish();
				
				_zipDe=new ZipDecoder(_zipEn.byteArray);
				
				dispatchEvent(new WaterMarkerEvent(WaterMarkerEvent.MARKED));
				return;
			}
			
			_fr=arr.shift() as FileReference;
			_fr.addEventListener(Event.COMPLETE,fileLoadedHandler);
			_fr.addEventListener(IOErrorEvent.IO_ERROR,ioErrorHandler);
			_fr.load();
		}
		
		
		private function fileLoadedHandler(event:Event):void{
			removeFrEventListeners();
			var data:ByteArray=_fr.data;
			
			_imageLoader.unloadAndStop();
			_imageLoader.contentLoaderInfo.addEventListener(Event.COMPLETE,imageLoadedHandler);
			_imageLoader.loadBytes(data);
			data.length=0;
		}
		
		private function ioErrorHandler(event:IOErrorEvent):void{
			removeFrEventListeners();
			Alert.show("找不到文件"+_fr.name+"，已经路过此文件","提示");
			
			//处理下一个图片
			markImages(__sourceImages);
		}
		
		private function removeFrEventListeners():void{
			_fr.removeEventListener(Event.COMPLETE,fileLoadedHandler);
			_fr.removeEventListener(IOErrorEvent.IO_ERROR,ioErrorHandler);
		}
		
		private function imageLoadedHandler(event:Event):void{
			_imageLoader.contentLoaderInfo.removeEventListener(Event.COMPLETE,imageLoadedHandler);
			
			//创建新画板
			_drawingBoard=new Sprite;
			_drawingBoard.addChild(_imageLoader.content);
			
			//制作水印
			var tf:TextField=ConfigWindow.configWindow.textFiled;
			var bitmapData:BitmapData=new BitmapData(tf.width+100,tf.height+50,true,0x00FFFFFF);
			bitmapData.draw(tf);
			
			//绘制水印
			var sp:Shape=new Shape;
			sp.graphics.beginBitmapFill(bitmapData);
			sp.graphics.drawRect(0,0,_imageLoader.content.width,_imageLoader.content.height);
			sp.graphics.endFill();
			sp.alpha=SharedObjectMgr.sharedObjectMgr.markAlpha;
			_drawingBoard.addChild(sp);
			
			//生成新的图像
			bitmapData=new BitmapData(_drawingBoard.width,_drawingBoard.height,true,0x00FFFFFF);
			bitmapData.draw(_drawingBoard);
			var imageFileEn:IImageEncoder;
			
			switch(_fr.name.substr(_fr.name.lastIndexOf("."))){
				case ".jpg":
				case ".jpeg":
					imageFileEn=new JPEGEncoder(SharedObjectMgr.sharedObjectMgr.jpgQuality);
					break;
				case ".png":
					imageFileEn=new PNGEncoder;
					break;
				default:
					Alert.show("不支持的图片格式","提示");
					break;
			}
			
			//压缩进zip文件
			var bytes:ByteArray=imageFileEn.encode(bitmapData);
			_zipEn.addFile(_fr.name,bytes);
			bytes.length=0;
			bitmapData.dispose();
			
			_markedFiles.push(_fr);
			ImageWaterMarker.mainApp.buildMarkedFileList(_markedFiles);
			
			//处理下一个图片
			markImages(__sourceImages);
		}
	}
}