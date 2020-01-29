package com.plter.everyweek.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.plter.everyweek.data.InitData;
import com.plter.everyweek.vo.DayOfWeek;
import com.plter.everyweek.vo.Event;

public class SQLConn extends SQLiteOpenHelper {

	public static final String TABLE_NAME_DAILY="daily";
	public static final String TABLE_NAME_WEEK="week";
	public static final String COLUMN_NAME_ID="_id";
	public static final String COLUMN_NAME_DAY_OF_WEEK="day";
	public static final String COLUMN_NAME_TIME="name";
	public static final String COLUMN_NAME_WEEKDAY_ID="week_day_id";
	public static final String COLUMN_NAME_EVENT="event";


	public SQLConn(Context context) {
		super(context, "everyweek", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+TABLE_NAME_WEEK+"(" +
				COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_NAME_DAY_OF_WEEK+" TEXT NOT NULL DEFAULT \"\"" +
				")");
		db.execSQL("CREATE TABLE "+TABLE_NAME_DAILY+"(" +
				COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_NAME_WEEKDAY_ID+" INTEGER NOT NULL DEFAULT 0," +
				COLUMN_NAME_TIME+" TEXT NOT NULL DEFAULT \"\"," +
				COLUMN_NAME_EVENT+" TEXT NOT NULL DEFAULT \"\"" +
				")");

		//初始化数据表
		ContentValues cv=null;
		DayOfWeek dw;
		for (int i = 0; i < InitData.DAYS_OF_WEEK.length; i++) {
			dw=InitData.DAYS_OF_WEEK[i];
			cv= new ContentValues();
			cv.put(COLUMN_NAME_ID, dw.id);
			cv.put(COLUMN_NAME_DAY_OF_WEEK, dw.day);
			db.insert(TABLE_NAME_WEEK, null, cv);
		}
		
		Event evt;
		for (int i = 0; i < InitData.INIT_EVENTS.length; i++) {
			evt=InitData.INIT_EVENTS[i];
			cv=new ContentValues();
			cv.put(COLUMN_NAME_TIME, evt.time);
			cv.put(COLUMN_NAME_WEEKDAY_ID, evt.week_day_id);
			cv.put(COLUMN_NAME_EVENT, evt.eventName);
			db.insert(TABLE_NAME_DAILY, null, cv);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
