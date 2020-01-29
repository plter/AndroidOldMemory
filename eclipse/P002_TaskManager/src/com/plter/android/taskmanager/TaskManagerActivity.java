package com.plter.android.taskmanager;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.plter.android.taskmanager.list.ProcessesListAdapter;
import com.plter.android.taskmanager.list.ProcessesListCellData;

public class TaskManagerActivity extends Activity implements OnClickListener, OnItemClickListener {
	
	private ListView processesLv;
	private Button killProcessesBtn;
	private ActivityManager am;
	private ProcessesListAdapter adapter;
	
	public TaskManagerActivity() {
		adapter=new ProcessesListAdapter(this);
	}
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        am=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
        
        processesLv=(ListView) findViewById(R.id.processesList);
        processesLv.setOnItemClickListener(this);
        processesLv.setAdapter(adapter);
        
        killProcessesBtn=(Button) findViewById(R.id.killProcessesBtn);
        killProcessesBtn.setOnClickListener(this);
        
        showProcesses();
    }
    
    
    /**
     * Show all current running processes
     */
    public void showProcesses() {
		List<RunningAppProcessInfo> infos=am.getRunningAppProcesses();
		adapter.clear();
		
		
		ProcessesListCellData data;
		
		for (RunningAppProcessInfo info : infos) {
			
			data=new ProcessesListCellData(this,info);
			if (data.appName!=null&&!data.appName.equals("")) {
				adapter.addCellData(data);
			}
		}
		
		
		MemoryInfo memInfo=new MemoryInfo();
		am.getMemoryInfo(memInfo);
		setTitle("进程管理器，可用内存："+memInfo.availMem/1024/1024+"M");
	}


	@Override
	public void onClick(View v) {
		adapter.killAllBackgroundProcess();
		showProcesses();
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		LinearLayout ll =(LinearLayout) arg1;
		
		//get components and config data
		CheckBox killableCb=(CheckBox) ll.findViewById(R.id.killableCb);
		killableCb.setChecked(!killableCb.isChecked());
		adapter.getListCellDataVec().get(arg2).selected=killableCb.isChecked();
		
	}
}