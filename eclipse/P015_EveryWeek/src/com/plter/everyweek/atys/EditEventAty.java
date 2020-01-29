package com.plter.everyweek.atys;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.plter.everyweek.R;
import com.plter.everyweek.db.SQLConn;
import com.plter.lib.java.utils.TimeUtil;

public class EditEventAty extends Activity implements OnClickListener {


	public static final String KEY_TIME="time";
	public static final String KEY_EVENT_NAME="event";
	public static final String KEY_ID="_id";
	public static final String KEY_WEEK_DAY_ID="weekDayId";
	
	/**
	 * 数据库中有数据更新
	 */
	public static final int RESULT_CODE_NEED_REFRESH=100;


	private int _id=-1,weekDayId=-1;
	private String eventName=null,timeStr=null,timeStartStr=null,timeEndStr=null;
	private int startHour,endHour,startMin,endMin;
	private TextView btnSetStartTime=null,btnSetEndTime=null,eventNameTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_event);
		
		_id=getIntent().getIntExtra(KEY_ID, -1);
		weekDayId=getIntent().getIntExtra(KEY_WEEK_DAY_ID, -1);
		if (weekDayId<-1) {
			
			Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();
			
			finish();
			return;
		}
		
		
		eventName=getIntent().getStringExtra(KEY_EVENT_NAME);
		timeStr=getIntent().getStringExtra(KEY_TIME);

		btnSetEndTime=(Button) findViewById(R.id.btnSetEndTime);
		btnSetStartTime=(Button) findViewById(R.id.btnSetStartTime);
		btnSetEndTime.setOnClickListener(this);
		btnSetStartTime.setOnClickListener(this);
		findViewById(R.id.btnSave).setOnClickListener(this);
		findViewById(R.id.btnCancel).setOnClickListener(this);
		eventNameTv=(TextView) findViewById(R.id.eventNameTv);


		if (_id>-1) {
			String[] times=timeStr.split("-");
			timeStartStr=times[0];
			timeEndStr=times[1];
			
			String[] startTimes=timeStartStr.split(":");
			startHour=new Integer(startTimes[0]);
			startMin=new Integer(startTimes[1]);
			
			String[] endTimes=timeEndStr.split(":");
			endHour=new Integer(endTimes[0]);
			endMin=new Integer(endTimes[1]);
			
			btnSetStartTime.setText(timeStartStr);
			btnSetEndTime.setText(timeEndStr);
			eventNameTv.setText(eventName);
		}else{
			btnSetEndTime.setText("00:00");
			btnSetStartTime.setText("00:00");
		}
	}

	@Override
	public void onClick(View v) {

		if (v instanceof Button) {
			switch (v.getId()) {
			case R.id.btnCancel:
				finish();
				break;
			case R.id.btnSave:
				String startTimeStr=btnSetStartTime.getText().toString();
				if (startTimeStr==null||startTimeStr.equals("00:00")) {
					Toast.makeText(this, "起始时间不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				String endTimeStr=btnSetEndTime.getText().toString();
				if (endTimeStr==null||endTimeStr.equals("00:00")) {
					Toast.makeText(this, "结束时间不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				String eventName=eventNameTv.getText().toString();
				if (eventName==null||eventName.equals("")) {
					Toast.makeText(this, "事件内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				ContentValues cv = new ContentValues();
				cv.put(SQLConn.COLUMN_NAME_WEEKDAY_ID, weekDayId);
				cv.put(SQLConn.COLUMN_NAME_EVENT, eventName);
				cv.put(SQLConn.COLUMN_NAME_TIME, String.format("%s-%s", startTimeStr,endTimeStr));
				
				SQLiteDatabase dbWrite=new SQLConn(this).getWritableDatabase();
				if (_id>-1) {
					dbWrite.update(SQLConn.TABLE_NAME_DAILY, cv, SQLConn.COLUMN_NAME_ID+"="+_id, null);
				}else{
					dbWrite.insert(SQLConn.TABLE_NAME_DAILY, null, cv);
				}
				dbWrite.close();
				setResult(RESULT_CODE_NEED_REFRESH);
				finish();
				break;
			case R.id.btnSetEndTime:
				new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						endHour=hourOfDay;
						endMin=minute;
						
						btnSetEndTime.setText(getTimeStr(hourOfDay, minute));
					}
				}, endHour, endMin, true).show();
				break;
			case R.id.btnSetStartTime:
				new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						startHour=hourOfDay;
						startMin=minute;
						
						btnSetStartTime.setText(getTimeStr(hourOfDay, minute));
					}
				}, startHour, startMin, true).show();
				break;
			}
		}
	}
	
	private String getTimeStr(int hour,int min){
		return String.format("%s:%s", TimeUtil.timeFormat(hour),TimeUtil.timeFormat(min));
	}
}
