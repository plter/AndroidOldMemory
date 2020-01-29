package com.plter.everyweek.atys;

import com.plter.everyweek.db.SQLConn;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DaysOfWeekListAty extends ListActivity {

	
	private SimpleCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("星期列表");
		
		adapter=new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{SQLConn.COLUMN_NAME_DAY_OF_WEEK}, new int[]{android.R.id.text1});
		setListAdapter(adapter);
		
		refreshList();
	}
	
	public void refreshList(){
		SQLiteDatabase dbRead = new SQLConn(this).getReadableDatabase();
		adapter.changeCursor(dbRead.query(SQLConn.TABLE_NAME_WEEK, null, null, null, null, null, null));
		dbRead.close();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		Cursor c = adapter.getCursor();
		c.moveToPosition(position);
		
		int _id = c.getInt(c.getColumnIndex(SQLConn.COLUMN_NAME_ID));
		
		Intent i = new Intent(this, DailyEventsAty.class);
		i.putExtra(DailyEventsAty.KEY_DAY_OF_WEEK, _id);
		startActivity(i);
		
		super.onListItemClick(l, v, position, id);
	}
	
}
