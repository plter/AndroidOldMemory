package com.plter.puzzle.atys;

import com.plter.puzzle.ui.GameContainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class SourceImageAty extends Activity implements OnClickListener {

	
	private ImageView imageView;
	private FrameLayout.LayoutParams imageViewLp;
	
	public static final String IMAGE_ID_KEY="image";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageView=new ImageView(this);
		setContentView(imageView);
		
		imageViewLp=(LayoutParams) imageView.getLayoutParams();
		imageViewLp.width=-1;
		imageViewLp.height=-1;
		imageView.setLayoutParams(imageViewLp);
		
		imageView.setImageBitmap(GameContainer.getCurrentImageBitmap());
		
		imageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}
