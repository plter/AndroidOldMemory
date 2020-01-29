package com.plter.puzzle.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.Toast;

import com.plter.lib.android.game2d.java.display.Sprite;
import com.plter.lib.android.game2d.java.display.Stage;
import com.plter.lib.android.game2d.java.events.TouchEvent;
import com.plter.lib.android.game2d.java.events.TweenEvent;
import com.plter.lib.android.game2d.java.tween.TranslateTween;
import com.plter.lib.java.events.IEventListener;
import com.plter.puzzle.atys.SourceImageAty;
import com.plter.puzzle.data.DataMgr;

public class GameContainer extends Sprite {


	private static GameContainer __instance=null;
	public static GameContainer getInstance(){
		if (__instance==null) {
			__instance=new GameContainer();
		}
		return __instance;
	}

	private GameContainer() {
		tt.setFrames(5);
		tt.addEventListener(TweenEvent.TWEEN_STOP, tweenStopHandler);
	}


	/**
	 * 呈现原图
	 */
	public void showSourceImage(){
		Intent i=new Intent(Stage.getContext(), SourceImageAty.class);
		Stage.getContext().startActivity(i);
	}

	private boolean propertiesInited=false;
	
	public void initGameProperties(){
		if (propertiesInited) {
			return;
		}


		setWidth(getStage().getStageWidth()-20);
		setHeight(getStage().getStageHeight()-80);
		setX(10);
		setY(70);

		cutImage(DataMgr.changeImage());
		propertiesInited=true;
	}

	public void changeImage(){
		changeImage(((BitmapDrawable)Stage.getContext().getResources().getDrawable(DataMgr.changeImage())).getBitmap());
	}

	public void changeImage(Bitmap img){
		if (breakingPics) {
			Toast.makeText(Stage.getContext(), "请先 停止打乱", Toast.LENGTH_LONG).show();
			return ;
		}
		if (tt.isRunning()) {
			Toast.makeText(Stage.getContext(), "请先 停止打乱", Toast.LENGTH_LONG).show();
			return;
		}

		cutImage(img);
	}

	/**
	 * 重置卡片的位置
	 */
	public void resetPics(){

		if (breakingPics) {
			Toast.makeText(Stage.getContext(), "请先 停止打乱", Toast.LENGTH_LONG).show();
			return ;
		}
		if (tt.isRunning()) {
			Toast.makeText(Stage.getContext(), "请先 停止打乱", Toast.LENGTH_LONG).show();
			return;
		}

		steps=0;
		if (getGameContainerListener()!=null) {
			getGameContainerListener().onStep(steps);
		}

		ArrayList<Pic> pics=new ArrayList<Pic>();

		for (int i = 0; i < WIDTH_NUM; i++) {
			for (int j = 0; j < HEIGHT_NUM; j++) {
				pic=picMat[i][j];
				if (pic!=null) {
					pics.add(pic);
				}
			}
		}

		for (Pic p : pics) {
			p.putInRightPlace();
			picMat[p.getRightI()][p.getRightJ()]=p;
		}

		currentNullI=WIDTH_NUM-1;
		currentNullJ=HEIGHT_NUM-1;
		picMat[currentNullI][currentNullJ]=null;

		pics.clear();
	}


	/**
	 * 打乱卡片的顺序，此方法将一一打乱卡片的顺序
	 */
	public void breakPics(){

		setBreakingPics(true);

		validDirs.clear();

		if (currentNullI!=0) {
			validDirs.add(DIR_LEFT);
		}
		if (currentNullI!=WIDTH_NUM-1) {
			validDirs.add(DIR_RIGHT);
		}
		if (currentNullJ!=0) {
			validDirs.add(DIR_UP);
		}
		if (currentNullJ!=HEIGHT_NUM-1) {
			validDirs.add(DIR_DOWN);
		}

		int dir=validDirs.get((int) (Math.random()*validDirs.size()));
		switch (dir) {
		case DIR_LEFT:
			pic=picMat[currentNullI-1][currentNullJ];
			if (pic==lastBreakedPic) {
				breakPics();
				return;
			}

			tt.setTarget(pic);
			tt.setStartX(pic.getX());
			tt.setStartY(pic.getY());
			tt.setEndX(pic.getX()+pic_width);
			tt.setEndY(pic.getY());
			tt.start();

			picMat[currentNullI][currentNullJ]=pic;
			pic.setI(currentNullI);
			currentNullI=currentNullI-1;
			picMat[currentNullI][currentNullJ]=null;

			lastBreakedPic=pic;
			break;
		case DIR_RIGHT:
			pic=picMat[currentNullI+1][currentNullJ];
			if (pic==lastBreakedPic) {
				breakPics();
				return;
			}

			tt.setTarget(pic);
			tt.setStartX(pic.getX());
			tt.setStartY(pic.getY());
			tt.setEndX(pic.getX()-pic_width);
			tt.setEndY(pic.getY());
			tt.start();

			picMat[currentNullI][currentNullJ]=pic;
			pic.setI(currentNullI);
			currentNullI=currentNullI+1;
			picMat[currentNullI][currentNullJ]=null;

			lastBreakedPic=pic;
			break;
		case DIR_UP:
			pic=picMat[currentNullI][currentNullJ-1];
			if (pic==lastBreakedPic) {
				breakPics();
				return;
			}

			tt.setTarget(pic);
			tt.setStartX(pic.getX());
			tt.setStartY(pic.getY());
			tt.setEndX(pic.getX());
			tt.setEndY(pic.getY()+pic_height);
			tt.start();

			picMat[currentNullI][currentNullJ]=pic;
			pic.setJ(currentNullJ);
			currentNullJ=currentNullJ-1;
			picMat[currentNullI][currentNullJ]=null;

			lastBreakedPic=pic;
			break;
		case DIR_DOWN:
			pic=picMat[currentNullI][currentNullJ+1];
			if (pic==lastBreakedPic) {
				breakPics();
				return;
			}

			tt.setTarget(pic);
			tt.setStartX(pic.getX());
			tt.setStartY(pic.getY());
			tt.setEndX(pic.getX());
			tt.setEndY(pic.getY()-pic_height);
			tt.start();

			picMat[currentNullI][currentNullJ]=pic;
			pic.setJ(currentNullJ);
			currentNullJ=currentNullJ+1;
			picMat[currentNullI][currentNullJ]=null;

			lastBreakedPic=pic;
			break;
		}
	}


	/**
	 * 停止打乱卡片顺序的动画
	 */
	public void stopBreakPics(){

		setBreakingPics(false);

		steps=0;

		if (getGameContainerListener()!=null) {
			getGameContainerListener().onStep(steps);
		}
	}


	private Bitmap convertToGameBitmap(Bitmap b){

		Matrix mat = new Matrix();
		mat.setScale(480.0f/b.getWidth(), 720.0f/b.getHeight());

		Bitmap n_b=Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), mat, true);
		
		b.recycle();
		System.gc();
		
		return n_b;
	}


	private void cutImage(Bitmap src){

		src=convertToGameBitmap(src);

		if (src==null) {
			return;
		}

		setCurrentImageBitmap(src);

		removeAllChildren();

		pic_width=(int) (getWidth()/WIDTH_NUM);
		pic_height=(int) (getHeight()/HEIGHT_NUM);

		bitmapSrc=src;
		pic_original_width=bitmapSrc.getWidth()/WIDTH_NUM;
		pic_original_height=bitmapSrc.getHeight()/HEIGHT_NUM;

		int i=0,j=0;

		for (i = 0; i < WIDTH_NUM; i++) {
			for (j = 0; j < HEIGHT_NUM; j++) {
				if (i<WIDTH_NUM-1||j<HEIGHT_NUM-1) {
					bitmap= Bitmap.createBitmap(bitmapSrc, i*pic_original_width, j*pic_original_height, pic_original_width,pic_original_height);
					pic=new Pic(bitmap);
					pic.setWidth(pic_width);
					pic.setHeight(pic_height);

					pic.setRightX(i*pic_width);
					pic.setRightY(j*pic_height);

					addChild(pic);
					picMat[i][j]=pic;
					pic.setRightI(i);
					pic.setRightJ(j);

					//init
					pic.putInRightPlace();

					pic.addEventListener(TouchEvent.TOUCH_DOWN, touchEventHandler);
				}
			}
		}

		//init current null i and current null j
		currentNullI=WIDTH_NUM-1;
		currentNullJ=HEIGHT_NUM-1;
		picMat[currentNullI][currentNullJ]=null;
		steps=0;

		if (getGameContainerListener()!=null) {
			getGameContainerListener().onStep(steps);
		}
	}


	public void cutImage(int imageId){
		drawableSrc=(BitmapDrawable) Stage.getContext().getResources().getDrawable(imageId);
		cutImage(drawableSrc.getBitmap());
	}


	public boolean isBreakingPics() {
		return breakingPics;
	}


	private void setBreakingPics(boolean breakingPics) {
		this.breakingPics = breakingPics;
	}


	public GameContainerListener getGameContainerListener() {
		return gameContainerListener;
	}


	public void setGameContainerListener(GameContainerListener gameContainerListener) {
		this.gameContainerListener = gameContainerListener;
	}

	private int getDir(int i,int j){

		if (i>0&&picMat[i-1][j]==null) {
			return DIR_LEFT;
		}
		if (i<WIDTH_NUM-1&&picMat[i+1][j]==null) {
			return DIR_RIGHT;
		}
		if (j>0&&picMat[i][j-1]==null) {
			return DIR_UP;
		}
		if (j<HEIGHT_NUM-1&&picMat[i][j+1]==null) {
			return DIR_DOWN;
		}

		return 0;
	}

	public static Bitmap getCurrentImageBitmap() {
		return GameContainer.currentImageBitmap;
	}


	private static void setCurrentImageBitmap(Bitmap currentImageBitmap) {
		if (GameContainer.currentImageBitmap!=null) {
			GameContainer.currentImageBitmap.recycle();
			System.gc();
		}
		GameContainer.currentImageBitmap = currentImageBitmap;
	}

	private final IEventListener<TouchEvent> touchEventHandler=new IEventListener<TouchEvent>() {

		@Override
		public void handle(TouchEvent event) {

			if (!breakingPics&&!tt.isRunning()) {
				Pic pic=(Pic) event.getTarget();

				switch (getDir(pic.getI(), pic.getJ())) {
				case DIR_LEFT:
					tt.setTarget(pic);
					tt.setStartX(pic.getX());
					tt.setStartY(pic.getY());
					tt.setEndX(pic.getX()-pic_width);
					tt.setEndY(pic.getY());
					tt.start();

					picMat[currentNullI][currentNullJ]=pic;
					currentNullI=pic.getI();
					pic.setI(currentNullI-1);
					picMat[currentNullI][currentNullJ]=null;

					steps++;
					if (getGameContainerListener()!=null) {
						getGameContainerListener().onStep(steps);
					}
					break;
				case DIR_UP:
					tt.setTarget(pic);
					tt.setStartX(pic.getX());
					tt.setStartY(pic.getY());
					tt.setEndX(pic.getX());
					tt.setEndY(pic.getY()-pic_height);
					tt.start();

					picMat[currentNullI][currentNullJ]=pic;
					currentNullJ=pic.getJ();
					pic.setJ(currentNullJ-1);
					picMat[currentNullI][currentNullJ]=null;

					steps++;
					if (getGameContainerListener()!=null) {
						getGameContainerListener().onStep(steps);
					}
					break;
				case DIR_RIGHT:
					tt.setTarget(pic);
					tt.setStartX(pic.getX());
					tt.setStartY(pic.getY());
					tt.setEndX(pic.getX()+pic_width);
					tt.setEndY(pic.getY());
					tt.start();

					picMat[currentNullI][currentNullJ]=pic;
					currentNullI=pic.getI();
					pic.setI(currentNullI+1);
					picMat[currentNullI][currentNullJ]=null;

					steps++;
					if (getGameContainerListener()!=null) {
						getGameContainerListener().onStep(steps);
					}
					break;
				case DIR_DOWN:
					tt.setTarget(pic);
					tt.setStartX(pic.getX());
					tt.setStartY(pic.getY());
					tt.setEndX(pic.getX());
					tt.setEndY(pic.getY()+pic_height);
					tt.start();

					picMat[currentNullI][currentNullJ]=pic;
					currentNullJ=pic.getJ();
					pic.setJ(currentNullJ+1);
					picMat[currentNullI][currentNullJ]=null;

					steps++;
					if (getGameContainerListener()!=null) {
						getGameContainerListener().onStep(steps);
					}
					break;
				}
			}
		}
	};

	private final IEventListener<TweenEvent> tweenStopHandler=new IEventListener<TweenEvent>() {

		@Override
		public void handle(TweenEvent event) {
			if (breakingPics) {
				breakPics();
			}else{
				boolean allInRightPlace=true;

				AllInRightPlace:
					for (int i = 0; i < WIDTH_NUM; i++) {
						for (int j = 0; j < HEIGHT_NUM; j++) {
							pic=picMat[i][j];
							if (pic!=null) {
								if (!pic.isInRightPlace()) {
									allInRightPlace=false;
									break AllInRightPlace;
								}
							}
						}
					}
				if (allInRightPlace) {
					completeTaskHandler.sendEmptyMessage(0);
				}
			}
		}
	};

	private final Handler completeTaskHandler=new Handler(){

		public void handleMessage(android.os.Message msg) {
			AlertDialog.Builder builder=new AlertDialog.Builder(Stage.getContext());
			builder.setTitle("恭喜");
			builder.setMessage("成功完成任务，共用"+steps+"步");
			builder.setPositiveButton("OK", null);
			builder.show();
		};

	};


	private BitmapDrawable drawableSrc;
	private Bitmap bitmapSrc;
	private final int WIDTH_NUM=4;
	private final int HEIGHT_NUM=6;
	private final Pic[][] picMat =new Pic[WIDTH_NUM][HEIGHT_NUM];
	private int pic_original_width=0;
	private int pic_original_height=0;
	private int pic_width=0;
	private int pic_height=0;
	private Bitmap bitmap;
	private Pic pic;
	private TranslateTween tt=new TranslateTween(null);
	private final ArrayList<Integer> validDirs=new ArrayList<Integer>();
	private Pic lastBreakedPic=null;//最后一次打乱顺序的方块对象

	//移动方向
	private static final int DIR_LEFT=1;
	private static final int DIR_UP=2;
	private static final int DIR_RIGHT=3;
	private static final int DIR_DOWN=4;

	private int currentNullI=-1;
	private int currentNullJ=-1;

	/**
	 * 是否正在打乱卡片的顺序
	 */
	private boolean breakingPics=false;

	private GameContainerListener gameContainerListener=null;
	private int steps=0;
	private static Bitmap currentImageBitmap=null;

}
