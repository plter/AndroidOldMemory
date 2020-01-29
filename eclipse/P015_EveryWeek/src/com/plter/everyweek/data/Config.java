package com.plter.everyweek.data;

import java.util.Calendar;

public class Config {

	
	public static String getDayOfWeekString(){
		return getDayOfWeekString(getDayOfWeek());
	}
	
	public static String getDayOfWeekString(int dayOfWeek){
		switch (dayOfWeek) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		}
		
		return "";
	}
	
	
	public static int getDayOfWeek(){
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}
}
