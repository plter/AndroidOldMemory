package com.plter.everyweek.vo;

public class Event {
	
	public Event(String eventName,String time,int week_day_id) {
		this.eventName=eventName;
		this.time=time;
		this.week_day_id=week_day_id;
	}
	
	public String eventName="";
	public String time="";
	public int week_day_id=0;
}
