package com.plter.iwm.tools
{
	import flash.events.Event;
	
	public class WaterMarkerEvent extends Event
	{
		/**
		 * 标记完成
		 */
		public static const MARKED:String="marked";
		
		public function WaterMarkerEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event{
			return new WaterMarkerEvent(type,bubbles,cancelable);
		} 
	}
}