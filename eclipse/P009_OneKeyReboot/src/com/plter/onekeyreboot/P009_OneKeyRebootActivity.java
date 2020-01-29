package com.plter.onekeyreboot;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

public class P009_OneKeyRebootActivity extends Activity {
	
	ProgressDialog progressDialog=null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        progressDialog=ProgressDialog.show(this, "��������", "ϵͳ�������������Ժ�");
        
        try {
			Runtime.getRuntime().exec("su -c reboot");
		} catch (IOException e) {
			e.printStackTrace();
			
			progressDialog.cancel();
			
			Toast.makeText(this, "����ʧ�ܣ�������", Toast.LENGTH_SHORT).show();
			finish();
		}
    }
    
    
    @Override
    protected void onStop() {
    	finish();
    	super.onStop();
    }
}