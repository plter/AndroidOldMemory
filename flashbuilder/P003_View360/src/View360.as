package
{
	import flash.display.Bitmap;
	import flash.display.FrameLabel;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageOrientation;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.events.TouchEvent;
	import flash.text.TextField;
	import flash.ui.Multitouch;
	import flash.ui.MultitouchInputMode;
	
	public class View360 extends Sprite
	{
		public function View360()
		{
			super();
			
			stage.scaleMode=StageScaleMode.NO_SCALE;
			stage.align=StageAlign.TOP_LEFT;
			
			
			box=createBox();
			addChild(box);
			
			box.x=stage.stageWidth/2;
			box.y=stage.stageHeight/2;
			
			Multitouch.inputMode=MultitouchInputMode.TOUCH_POINT;
			
			stage.addEventListener(TouchEvent.TOUCH_MOVE,stage_touchMoveHandler);
		}
		
		
		
		private var lastTouchX:Number=-1;
		private var currentX:Number=0;
		private var lastTouchY:Number=-1;
		private var currentY:Number=0;
		protected function stage_touchMoveHandler(event:TouchEvent):void
		{
			
			//move hor
			currentX=event.stageX;
						
			if (lastTouchX>=0) 
			{
				if (currentX-lastTouchX>2) 
				{
					box.rotationY+=5;
					lastTouchX=currentX;
				}else if (currentX-lastTouchX<-2) 
				{
					box.rotationY-=5;
					lastTouchX=currentX;
				}
			}else{
				lastTouchX=currentX;
			}
			
			//move ver
//			currentY=event.stageY;
//			if (lastTouchY>=0) 
//			{
//				if (currentY-lastTouchY>2) 
//				{
//					box.rotationX-=5;
//					lastTouchY=currentY;
//				}else if (currentY-lastTouchY<-2) 
//				{
//					box.rotationX+=5;
//					lastTouchY=currentY;
//				}
//			}else{
//				lastTouchY=currentY;
//			}
		}		
		
		public function createBox():Sprite{
			//create container
			var container:Sprite=new Sprite;
			
			//create bitmaps
			backBitmap=new BackImgClass;
			frontBitmap=new FrontImgClass;
			topBitmap=new TopImgClass;
			downBitmap=new DownImgClass;
			leftBitmap=new LeftImgClass;
			rightBitmap=new RightImgClass;
			
			//add bitmaps
			with(container){
				addChild(backBitmap);
				addChild(leftBitmap);
				addChild(rightBitmap);
				addChild(topBitmap);
				addChild(downBitmap);
				addChild(frontBitmap);
			}
			
			//gen box
			with(backBitmap){
				x=-500;
				y=-500;
				z=500;
			}
			with(leftBitmap){
				rotationY=90;
				x=500;
				y=-500;
				z=500;
			}
			with(rightBitmap){
				rotationY=-90;
				x=-500;
				y=-500;
				z=-500;
			}
			with(frontBitmap){
				rotationY=180;
				x=500;
				y=-500;
				z=-500;
			}
			with(topBitmap){
				rotationX=-90;
				rotationZ=180;
				x=500;
				y=-500;
				z=500;
			}
			with(downBitmap){
				rotationX=90;
				rotationZ=180;
				x=500;
				y=500;
				z=-500;
			}
			
			return container;
		}
		
		
		private var box:Sprite=null;
		private var leftBitmap:Bitmap,rightBitmap:Bitmap,topBitmap:Bitmap,downBitmap:Bitmap,frontBitmap:Bitmap,backBitmap:Bitmap;
		
		
		
		[Embed(source="assets/back.jpg")]
		private static const BackImgClass:Class;
		[Embed(source="assets/top.jpg")]
		private static const TopImgClass:Class;
		[Embed(source="assets/down.jpg")]
		private static const DownImgClass:Class;
		[Embed(source="assets/front.jpg")]
		private static const FrontImgClass:Class;
		[Embed(source="assets/left.jpg")]
		private static const LeftImgClass:Class;
		[Embed(source="assets/right.jpg")]
		private static const RightImgClass:Class;
	}
}