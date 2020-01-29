package com.plter.android.taskmanager.list;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plter.android.taskmanager.R;

public class ProcessesListAdapter extends BaseAdapter {

	
	
	public ProcessesListAdapter(Context context) {
		this.context=context;
	}
	
	private Context context=null;
	
	/**
	 * 列表数据源
	 */
	private final Vector<ProcessesListCellData> LIST_DATA_VEC=new Vector<ProcessesListCellData>();
	
	public Vector<ProcessesListCellData> getListCellDataVec() {
		return LIST_DATA_VEC;
	}
	
	
	public void addCellData(ProcessesListCellData data) {
		getListCellDataVec().add(data);
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		return getListCellDataVec().size();
	}

	@Override
	public Object getItem(int position) {
		return getListCellDataVec().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout ll=(convertView instanceof LinearLayout)?(LinearLayout)convertView:null;
		LayoutInflater inflater=null;
		
		if (convertView==null) {
			inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ll=(LinearLayout) inflater.inflate(R.layout.list_cell, null);
		}
		
		//Get cell data
		ProcessesListCellData data=getListCellDataVec().get(position);
		
		
		//Config process name textview
		TextView processNameTv=(TextView) ll.findViewById(R.id.processNameTv);
		if (data.appName!=null&&!data.appName.equals("")) {
			processNameTv.setText(data.appName);
		}else {
			processNameTv.setText(data.processName);
		}
		
		//Config checkbox
		CheckBox killableCb=(CheckBox) ll.findViewById(R.id.killableCb);
		killableCb.setClickable(false);
		killableCb.setChecked(getListCellDataVec().get(position).selected);
		
		//Config application icon
		ImageView appIconIv=(ImageView) ll.findViewById(R.id.appIconIv);
		if (data.appIcon!=null) {
			appIconIv.setImageDrawable(data.appIcon);
		}else {
			appIconIv.setImageResource(R.drawable.default_android_app_icon);
		}
		
		//Config kill process button
		ImageView killProcessBtn =(ImageView) ll.findViewById(R.id.killProcessBtn);
		killProcessBtn.setOnClickListener(data);
		
		return ll;
	}

	public Context getContext() {
		return context;
	}
	
	public void clear() {
		getListCellDataVec().clear();
	}

	public void killAllBackgroundProcess() {
		
		boolean canKillSelf=false;
		
		for (ProcessesListCellData data : getListCellDataVec()) {
			if (data.selected) {
				if (!data.processName.equals("com.plter.android.taskmanager")) {
					data.killProcess();
				}else {
					canKillSelf=true;
				}
			}
		}
		
		if (canKillSelf) {
			System.exit(0);
		}
	}
}
