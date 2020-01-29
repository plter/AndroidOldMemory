package com.plter.pplayer.utils
{
	public class TimeUtil
	{
		public static function timeFormat(value:int):String{
			return value>=10?value+"":"0"+value;
		}
	}
}