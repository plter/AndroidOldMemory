package com.plter.iwm.views
{
	import flash.events.Event;
	
	public class ImageBrowerEvent extends Event
	{
		public static const IMAGE_LOADED:String="imageLoaded";
		
		
		public function ImageBrowerEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event{
			return new ImageBrowerEvent(type,bubbles,cancelable);
		}
	}
}