package com.plter.lockscreen;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class LockConfigAty extends LSAty implements OnClickListener {

	
	private CheckedTextView enableOpenLockCtv;
	private TextView lockScreenTip;
	
	
	public LockConfigAty() {
		__lockConfigAty=this;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_screen_config);
		
		enableOpenLockCtv=(CheckedTextView) findViewById(R.id.enableOpenLockCtv);
        enableOpenLockCtv.setOnClickListener(this);
        
        lockScreenTip=(TextView) findViewById(R.id.lockScreenTip);
        lockScreenTip.setText("");
        
        refreshEnableOpenLockCtv();
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.enableOpenLockCtv:
			if(!enableOpenLockCtv.isChecked()){
				getSystemManager().showGetPermissionActivity(this,"");
			}else{
				getSystemManager().removePermission();
			}
			
			break;
		}
	}
	
	/**
	 * 刷新enableOpenLockCtv的呈现状态
	 */
	public void refreshEnableOpenLockCtv(){
		enableOpenLockCtv.setChecked(getSystemManager().hasPermission());
		
		if (enableOpenLockCtv.isChecked()) {
			lockScreenTip.setText("你好，锁屏功能已经开启，您可以点击“锁屏”图标进行锁屏");
		}else {
			lockScreenTip.setText("请开启一键锁屏功能");
		}
	}
	
	
	private static LockConfigAty __lockConfigAty;
	
	public static LockConfigAty getLockConfigAty() {
		return __lockConfigAty;
	}
}
