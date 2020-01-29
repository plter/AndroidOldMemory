package com.plter.cardgame;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.plter.cardgame.sound.SoundPlayer;
import com.plter.lib.android.game2d.java.display.Sprite;
import com.plter.lib.android.game2d.java.display.StageView;
import com.plter.lib.android.game2d.java.events.TouchEvent;
import com.plter.lib.java.events.IEventListener;
import com.plter.lib.java.geom.Point;
import com.tencent.mobwin.AdView;

public class CardGame extends Activity {


	private StageView stageView;
	private FrameLayout mainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


		//广告代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		mainLayout=(FrameLayout) findViewById(R.id.mainLayout);

		//腾讯广告
		AdView adView = new AdView(this); 
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -2);
		mainLayout.addView(adView, params);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


		//初始化声音
		SoundPlayer.init(this);

		stageView=(StageView) findViewById(R.id.stageView);

		stageView.getStage().setFps(60);

		//    	TextField textOutput=new TextField();
		//		textOutput.setColor(0xFF666666);
		//		textOutput.setSize(25);
		//		textOutput.setText(	"作者博客http://plter.com\n" +
		//							"此游戏基于plter-android-lib\n" +
		//							"库中的Game2D游戏引擎：\n" +
		//							"http://plter.sinaapp.com/?p=224");
		//		addChild(textOutput);

		gameContainer=new Sprite();
		stageView.getStage().addChild(gameContainer);

		showStartGameDialog();

		super.onCreate(savedInstanceState);
	}

	/**
	 * 重新开始游戏
	 */
	public void restartGame() {
		currentLevel=1;
		startGame();
	}

	public void startGame() {
		gameContainer.setWidth(stageView.getStage().getStageWidth());
		gameContainer.setHeight(stageView.getStage().getStageHeight()-80);
		gameContainer.setY(80);
		
		gameContainer.removeAllChildren();
		cardList.clear();
		reGenPositions();
		currentClickNum=1;

		Card card;
		Point point;

		for (int i = 1; i <= currentLevel+2; i++) {
			card=new Card(i);
			point=positionsList.remove((int)Math.floor(Math.random()*positionsList.size()));
			card.setX(point.x);
			card.setY(point.y);
			cardList.add(card);
			card.on().touchDown.add(cardTouchDownHandler);
			gameContainer.addChild(card);
		}

		stageView.getStage().update();
	}

	private final IEventListener<TouchEvent> cardTouchDownHandler=new IEventListener<TouchEvent>() {

		@Override
		public void handle(TouchEvent event) {

			SoundPlayer.playClickSound();

			Card card;
			AlertDialog.Builder builder;

			if (event.getTarget() instanceof Card) {
				card=(Card) event.getTarget();

				if (card.getIndex()==currentClickNum) {
					card.getParent().removeChild(card);
					cardList.remove(card);
					if (currentClickNum==1) {
						for (Card c : cardList) {
							c.turnToVerso();
						}
					}

					currentClickNum++;

					if (positionsList.size()>0) {
						if (cardList.size()<=0) {
							currentLevel++;

							builder=new AlertDialog.Builder(CardGame.this);
							builder.setMessage("游戏将进入第"+currentLevel+"关");
							builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									startGame();
								}
							});
							builder.show();
						}
					}else {
						builder=new AlertDialog.Builder(CardGame.this);
						builder.setMessage("恭喜你通关了，是否重新开始");
						builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								restartGame();
							}
						});
						builder.show();
					}

				}else {
					builder=new AlertDialog.Builder(CardGame.this);
					builder.setMessage("游戏失败");
					builder.setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							restartGame();
						}
					});
					builder.show();

					for (Card c : cardList) {
						c.turnToRecto();
					}
				}
			}
		}
	};


	///////////////////////private functions//////////////////////////

	private void showStartGameDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage("点击“开始”按钮开始玩游戏");
		builder.setPositiveButton("开始", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				startGame();
			}
		});
		builder.show();
	}


	/**
	 * 重新生成可用点阵
	 */
	private void reGenPositions(){
		positionsList.clear();

		float x=0;
		float y=0;

		while (true) {
			positionsList.add(new Point(x, y));

			x+=Card.WIDTH+10;
			if (x>gameContainer.getWidth()-Card.WIDTH) {
				y+=Card.HEIGHT+10;
				x=0;

				if (y>gameContainer.getHeight()-Card.HEIGHT) {
					break;
				}
			}
		}
	}

	///////////////////////////private values//////////////////////////////

	private final List<Card> cardList=new ArrayList<Card>();

	/**
	 * 可用的点
	 */
	private final List<Point> positionsList=new ArrayList<Point>();

	/**
	 * 当前级别
	 */
	private int currentLevel=1;

	/**
	 * 当前点击的数字
	 */
	private int currentClickNum=1;

	private Sprite gameContainer;


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, NEW_GAME_ITEM_ID, 0, "新游戏").setIcon(android.R.drawable.ic_input_add);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		restartGame();

		return super.onOptionsItemSelected(item);
	}

	private static final int NEW_GAME_ITEM_ID=1;

}
