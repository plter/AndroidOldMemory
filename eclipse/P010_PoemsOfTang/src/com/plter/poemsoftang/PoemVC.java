package com.plter.poemsoftang;

import android.content.Context;
import android.widget.TextView;

import com.plter.lib.android.java.anim.AnimationStyle;
import com.plter.lib.android.java.controls.ViewController;


public class PoemVC extends ViewController {

	public PoemVC(Context context,String name,String content) {
		super(context, R.layout.poem);
		
		nameTv=(TextView) findViewByID(R.id.nameTv);
		contentTv=(TextView) findViewByID(R.id.contentTv);
				
		nameTv.setText(name);
		contentTv.setText(content.replaceAll("enter", "\n"));
		
		setAnimationStyle(AnimationStyle.FLIP_HORIZONTAL);
	}
	
	private TextView nameTv=null;
	private TextView contentTv=null;
	
}
