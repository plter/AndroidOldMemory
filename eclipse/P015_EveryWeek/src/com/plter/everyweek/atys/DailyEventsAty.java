package com.plter.everyweek.atys;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.plter.everyweek.R;
import com.plter.everyweek.data.Config;
import com.plter.everyweek.db.SQLConn;

public class DailyEventsAty extends ListActivity implements OnItemLongClickListener, OnClickListener {


	public static final String KEY_DAY_OF_WEEK="dayOfWeek"; 


	private int dayOfWeekId=-1;
	private SimpleCursorAdapter adapter;
	private TextView dayOfWeekTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_events);

		dayOfWeekId=getIntent().getIntExtra(KEY_DAY_OF_WEEK, -1);
		if (dayOfWeekId<=-1) {
			Toast.makeText(this, "参数错误，没有指定星期几", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		
		setTitle(String.format("%s安排",Config.getDayOfWeekString(dayOfWeekId)));
		dayOfWeekTv=(TextView) findViewById(R.id.dayOfWeekTv);
		findViewById(R.id.btnAddEvent).setOnClickListener(this);

		adapter=new SimpleCursorAdapter(this, R.layout.events_list_cell, null, new String[]{SQLConn.COLUMN_NAME_TIME,SQLConn.COLUMN_NAME_EVENT}, new int[]{R.id.timeTv,R.id.eventTv});
		setListAdapter(adapter);
		getListView().setOnItemLongClickListener(this);
		refreshList();
	}
	
	
	public void refreshList(){
		dayOfWeekTv.setText(Config.getDayOfWeekString(dayOfWeekId));
		SQLiteDatabase dbRead = new SQLConn(this).getReadableDatabase();
		adapter.changeCursor(dbRead.query(SQLConn.TABLE_NAME_DAILY, null, SQLConn.COLUMN_NAME_WEEKDAY_ID+" = "+dayOfWeekId, null, null, null, SQLConn.COLUMN_NAME_TIME));
		dbRead.close();
	}

	
	public void editEvent(int position){
		Cursor c = adapter.getCursor();
		c.moveToPosition(position);

		Intent i = new Intent(this,EditEventAty.class);
		i.putExtra(EditEventAty.KEY_WEEK_DAY_ID, dayOfWeekId);
		i.putExtra(EditEventAty.KEY_ID, c.getInt(c.getColumnIndex(SQLConn.COLUMN_NAME_ID)));
		i.putExtra(EditEventAty.KEY_TIME, c.getString(c.getColumnIndex(SQLConn.COLUMN_NAME_TIME)));
		i.putExtra(EditEventAty.KEY_EVENT_NAME, c.getString(c.getColumnIndex(SQLConn.COLUMN_NAME_EVENT)));
		startActivityForResult(i, 0);
	}
	
	public void deleteEvent(int position){
		Cursor c = adapter.getCursor();
		c.moveToPosition(position);
		
		int _id = c.getInt(c.getColumnIndex(SQLConn.COLUMN_NAME_ID));
		
		SQLiteDatabase dbWrite=new SQLConn(this).getWritableDatabase();
		dbWrite.delete(SQLConn.TABLE_NAME_DAILY, SQLConn.COLUMN_NAME_ID+" = "+_id, null);
		dbWrite.close();
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		editEvent(position);
		super.onListItemClick(l, v, position, id);
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
					refreshList();
					break;
				}
			}
		}).setNeutralButton("取消", null)
		.show();
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case EditEventAty.RESULT_CODE_NEED_REFRESH:
			refreshList();
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, EditEventAty.class);
		i.putExtra(EditEventAty.KEY_WEEK_DAY_ID, dayOfWeekId);
		startActivityForResult(i, 0);
	}
}
