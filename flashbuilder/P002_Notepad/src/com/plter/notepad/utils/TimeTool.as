package com.plter.notepad.utils
{
	public class TimeTool
	{
		public function TimeTool()
		{
		}
		
		
		public static function getCurrentTime(dateSp:String="-",sp:String=" ",timeSp:String=":"):String{
			var date:Date=new Date;
			return date.fullYear+dateSp+timeFormat(date.month+1)+dateSp+timeFormat(date.date)+sp+timeFormat(date.hours)+timeSp+timeFormat(date.minutes)+timeSp+timeFormat(date.seconds);
		}
		
		public static function timeFormat(t:int):String{
			return (t>=10?"":"0")+t;
		}
	}
}