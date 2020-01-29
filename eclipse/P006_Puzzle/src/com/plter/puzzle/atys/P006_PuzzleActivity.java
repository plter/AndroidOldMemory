package com.plter.puzzle.atys;

import java.io.File;
import java.io.IOException;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.plter.lib.android.game2d.java.display.StageView;
import com.plter.lib.android.game2d.java.display.TextLine;
import com.plter.lib.android.game2d.java.events.StageEvent;
import com.plter.lib.java.events.IEventListener;
import com.plter.puzzle.R;
import com.plter.puzzle.data.Config;
import com.plter.puzzle.ui.Dialogs;
import com.plter.puzzle.ui.GameContainer;
import com.plter.puzzle.ui.GameContainerListener;

public class P006_PuzzleActivity extends Activity implements OnClickListener, GameContainerListener {


	private StageView stageView;
	private GameContainer gameContainer;
	private Button break_or_stopBreak_btn,resetBtn,changeImageBtn,showSourceImageBtn;
	private TextView stepsCountTv;
	private static final int ABOUT_ITEM_ID=1;
	private static P006_PuzzleActivity __instance=null;
	private FrameLayout adContainer;

	public static final int REQUEST_CAPTURE_IMAGE=1;
	public static final int REQUEST_CHOOSE_IMAGE=2;

	public P006_PuzzleActivity() {
		__instance=this;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//广告代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		adContainer=(FrameLayout) findViewById(R.id.adContainer);

		//有米广告
		AdManager.init(this, "b945a30478bceac2", "0e2d3c374134a774", 30, false);
		AdView adView = new AdView(this);
		adContainer.addView(adView, -1, -2);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		stageView=(StageView) findViewById(R.id.stageView);
		stageView.getStage().addEventListener(StageEvent.ON_CREATE, stageCreateHandler);

		//init titile
		TextLine titleTl=new TextLine();
		titleTl.setX(10);
		titleTl.setY(10);
		titleTl.setSize(36);
		titleTl.setText("拼图游戏");
		stageView.getStage().addChild(titleTl);

		//Create and init game container
		gameContainer=GameContainer.getInstance();
		stageView.getStage().addChild(gameContainer);
		gameContainer.setGameContainerListener(this);

		break_or_stopBreak_btn=(Button) findViewById(R.id.break_OrStopBreak_PicsBtn);
		break_or_stopBreak_btn.setOnClickListener(this);
		resetBtn=(Button) findViewById(R.id.resetPicsBtn);
		resetBtn.setOnClickListener(this);
		changeImageBtn=(Button) findViewById(R.id.changeImageBtn);
		changeImageBtn.setOnClickListener(this);
		showSourceImageBtn=(Button) findViewById(R.id.showSourceImageBtn);
		showSourceImageBtn.setOnClickListener(this);

		stepsCountTv=(TextView) findViewById(R.id.stepsCountTv);
		stepsCountTv.setText("0步");

		refreshBtnState();
	}

	private IEventListener<StageEvent> stageCreateHandler=new IEventListener<StageEvent>() {

		@Override
		public void handle(StageEvent event) {
			gameContainer.initGameProperties();
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.break_OrStopBreak_PicsBtn:
			if (gameContainer.isBreakingPics()) {
				gameContainer.stopBreakPics();
			}else{
				gameContainer.breakPics();
			}
			refreshBtnState();
			break;
		case R.id.resetPicsBtn:
			gameContainer.resetPics();
			break;
		case R.id.changeImageBtn:
			Dialogs.showChangeImageDialog();
			break;
		case R.id.showSourceImageBtn:
			gameContainer.showSourceImage();
			break;
		}
	}


	private File currentChoosingFileTmp=null;

	/**
	 * 开始选择本地图库图片
	 */
	public void startChooseImages(){
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);

		currentChoosingFileTmp=new File(Environment.getExternalStorageDirectory()+"/.tmp");
		try {
			currentChoosingFileTmp.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();

			Dialogs.showSimpleMessageDialog("亲爱的主人，我已经尽了最大的努力，可是还是没法选择文件，对不起！！！", "不好意思");
			return;
		}

		i.setType("image/*");
		i.putExtra("output", Uri.fromFile(currentChoosingFileTmp));
		i.putExtra("outputFormat", "JPEG");
		i.putExtra("crop", "true");
		i.putExtra("aspectX", 2);
		i.putExtra("aspectY", 3);

		startActivityForResult(i, REQUEST_CHOOSE_IMAGE);
	}


	public void startCaptureCamera(){
		Intent i =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

		File appDataDir= Config.getAppCapPicsDir();
		if (!appDataDir.exists()) {
			appDataDir.mkdirs();
		}

		currentImageFile=new File(appDataDir.getPath()+"/"+Config.getNewPictureName());
		try {
			currentImageFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();

			Dialogs.showSimpleMessageDialog("亲爱的主人，我已经尽了最大的努力，可是还是没法启动摄像头，对不起！！！", "不好意思");
			return;
		}

		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
		startActivityForResult(i, REQUEST_CAPTURE_IMAGE);
	}

	private File currentImageFile=null;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CAPTURE_IMAGE:
				gameContainer.changeImage(BitmapFactory.decodeFile(currentImageFile.getAbsolutePath()));
				break;
			case REQUEST_CHOOSE_IMAGE:

				if (currentChoosingFileTmp!=null&&currentChoosingFileTmp.exists()) {
					try{
						gameContainer.changeImage(BitmapFactory.decodeFile(currentChoosingFileTmp.getAbsolutePath()));
					}catch(Exception e){

					}
				}

				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}



	private void refreshBtnState(){
		if (gameContainer.isBreakingPics()) {
			break_or_stopBreak_btn.setText(R.string.stop_break_pics_btn_label);

			resetBtn.setEnabled(false);
			changeImageBtn.setEnabled(false);
			showSourceImageBtn.setEnabled(false);
		}else {
			break_or_stopBreak_btn.setText(R.string.break_pics_btn_label);

			resetBtn.setEnabled(true);
			changeImageBtn.setEnabled(true);
			showSourceImageBtn.setEnabled(true);
		}
	}


	@Override
	public void onStep(int stepsCount) {
		stepsCountTv.setText(stepsCount+"步");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, ABOUT_ITEM_ID, 0, "关于此游戏");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ABOUT_ITEM_ID:
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("关于");
			builder.setMessage("此游戏基于plter-android-lib库中的game2d游戏引擎，该库的开源地址：\nhttp://plter.sinaapp.com/?p=224");
			builder.setPositiveButton("OK", null);
			builder.show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	public static P006_PuzzleActivity getInstance() {
		return __instance;
	}
}