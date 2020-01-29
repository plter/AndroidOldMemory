package com.plter.lockscreen;

import android.content.Intent;
import android.os.Bundle;

public class LockScreenAty extends LSAty {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        if (getSystemManager().hasPermission()) {
			getSystemManager().lockScreen();
			
		}else {
			Intent i=new Intent(getApplicationContext(), LockConfigAty.class);
			startActivity(i);
		}
        
        finish();
    }
}