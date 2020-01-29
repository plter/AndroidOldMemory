package com.plter.everyweek;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.plter.everyweek.atys.DaysOfWeekListAty;
import com.plter.everyweek.atys.EditEventAty;
import com.plter.everyweek.data.Config;
import com.plter.everyweek.db.SQLConn;
import com.plter.lib.java.utils.TimeUtil;

public class TodayEventsAty extends ListActivity implements OnClickListener, OnItemLongClickListener{

	private SimpleCursorAdapter dailyLifeListCursorAdapter;
	private SQLiteDatabase dbRead;
	private TextView timeTv;
	private int currentWeekDayId=0;


	/**
	 * 最后一次刷新列表的时间
	 */
	private int lastRefreshTime=0;

	/**
	 * 当前时间
	 */
	private int currentTime=0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.today_events);

		setTitle("今日安排");

		timeTv=(TextView) findViewById(R.id.dayOfWeekTv);
		findViewById(R.id.btnAddEvent).setOnClickListener(this);
		findViewById(R.id.btnShowWeekLifeList).setOnClickListener(this);

		dailyLifeListCursorAdapter=new SimpleCursorAdapter(
				this, 
				R.layout.events_list_cell, null,
				new String[]{SQLConn.COLUMN_NAME_EVENT,SQLConn.COLUMN_NAME_TIME}, 
				new int[]{R.id.eventTv,R.id.timeTv}){
			
			@Override
			public View getView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				Cursor c = getCursor();
				c.moveToPosition(position);
				String time=c.getString(c.getColumnIndex(SQLConn.COLUMN_NAME_TIME));
				String[] times = time.split("-");
				String[] startTimes = times[0].split(":");
				String[] endTimes = times[1].split(":");
				int start=new Integer(startTimes[0])*60+new Integer(startTimes[1]);
				int end=new Integer(endTimes[0])*60+new Integer(endTimes[1]);
				if (currentTime>start&&currentTime<end) {
					v.setBackgroundColor(0xFF5555FF);
				}else{
					v.setBackgroundColor(0x00000000);
				}
				return v;
			}
		};
		setListAdapter(dailyLifeListCursorAdapter);
		getListView().setOnItemLongClickListener(this);

		refreshDailyLifeList();
	}


	public void refreshDailyLifeList(){
		Calendar c = Calendar.getInstance();
		int dayOfWeek=c.get(Calendar.DAY_OF_WEEK);
		currentWeekDayId=dayOfWeek;

		dbRead=new SQLConn(this).getReadableDatabase();
		dailyLifeListCursorAdapter.changeCursor(dbRead.query(SQLConn.TABLE_NAME_DAILY, null, SQLConn.COLUMN_NAME_WEEKDAY_ID+" = "+dayOfWeek, null, null, null, SQLConn.COLUMN_NAME_TIME));
		dbRead.close();
		
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				getListView().smoothScrollToPosition(getCurrentEventPosition(dailyLifeListCursorAdapter.getCursor()));
				super.handleMessage(msg);
			}
		};
		handler.sendEmptyMessageDelayed(0, 1000);
	}
	
	
	/**
	 * 取得当前事件的位置
	 * @return
	 */
	public int getCurrentEventPosition(Cursor c){
		Calendar cal = Calendar.getInstance();
		currentTime=cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);
		
		for (int i = 0; i < c.getCount(); i++) {
			c.moveToPosition(i);
			String time=c.getString(c.getColumnIndex(SQLConn.COLUMN_NAME_TIME));
			String[] times = time.split("-");
			String[] startTimes = times[0].split(":");
			String[] endTimes = times[1].split(":");
			int start=new Integer(startTimes[0])*60+new Integer(startTimes[1]);
			int end=new Integer(endTimes[0])*60+new Integer(endTimes[1]);
			if (currentTime>start&&currentTime<end) {
				return i;
			}
		}
		return 0;
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		editEvent(position);
		
		super.onListItemClick(l, v, position, id);
	}
	
	
	public void editEvent(int position){
		Cursor c = dailyLifeListCursorAdapter.getCursor();
		c.moveToPosition(position);

		Intent i = new Intent(this,EditEventAty.class);
		i.putExtra(EditEventAty.KEY_WEEK_DAY_ID, currentWeekDayId);
		i.putExtra(EditEventAty.KEY_ID, c.getInt(c.getColumnIndex(SQLConn.COLUMN_NAME_ID)));
		i.putExtra(EditEventAty.KEY_TIME, c.getString(c.getColumnIndex(SQLConn.COLUMN_NAME_TIME)));
		i.putExtra(EditEventAty.KEY_EVENT_NAME, c.getString(c.getColumnIndex(SQLConn.COLUMN_NAME_EVENT)));
		startActivityForResult(i, 0);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case EditEventAty.RESULT_CODE_NEED_REFRESH:
			refreshDailyLifeList();
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public void showTime(){
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		timeTv.setText(String.format("%s %s:%s:%s", 
				Config.getDayOfWeekString(dayOfWeek),
				TimeUtil.timeFormat(c.get(Calendar.HOUR_OF_DAY)),
				TimeUtil.timeFormat(c.get(Calendar.MINUTE)),
				TimeUtil.timeFormat(c.get(Calendar.SECOND))));

		currentTime=c.get(Calendar.HOUR_OF_DAY)*60+c.get(Calendar.MINUTE);
		
		if (currentTime-lastRefreshTime>60) {
			dailyLifeListCursorAdapter.notifyDataSetChanged();
			lastRefreshTime=currentTime;
		}
	}


	@Override
	protected void onResume() {
		timeHandler.sendEmptyMessage(0);
		super.onResume();
	}

	@Override
	protected void onPause() {
		timeHandler.removeMessages(0);
		super.onPause();
	}


	private final Handler timeHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			showTime();
			timeHandler.sendEmptyMessageDelayed(0, 1000);
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddEvent:
			Intent i = new Intent(this, EditEventAty.class);
			i.putExtra(EditEventAty.KEY_WEEK_DAY_ID, currentWeekDayId);
			startActivityForResult(i, 0);
			break;
		case R.id.btnShowWeekLifeList:
			startActivity(new Intent(this, DaysOfWeekListAty.class));
			break;
		default:
			break;
		}
	}
	
	
	public void deleteEvent(int position){
		Cursor c = dailyLifeListCursorAdapter.getCursor();
		c.moveToPosition(position);
		
		int _id = c.getInt(c.getColumnIndex(SQLConn.COLUMN_NAME_ID));
		
		SQLiteDatabase dbWrite=new SQLConn(this).getWritableDatabase();
		dbWrite.delete(SQLConn.TABLE_NAME_DAILY, SQLConn.COLUMN_NAME_ID+" = "+_id, null);
		dbWrite.close();
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {
		new AlertDialog.Builder(this)
		.setTitle("操作选项")
		.setItems(new String[]{"编辑","删除"}, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					editEvent(position);
					break;
				case 1:
					deleteEvent(position);
					refreshDailyLifeList();
					break;
				}
			}
		}).setNeutralButton("取消", null)
		.show();
		return true;
	}
}