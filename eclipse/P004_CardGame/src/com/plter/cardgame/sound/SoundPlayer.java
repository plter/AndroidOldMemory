package com.plter.cardgame.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.plter.cardgame.R;


public class SoundPlayer {
	
	
	private static MediaPlayer clickSoundMp=null;
	
	public static void init(Context context){
		SoundPlayer.context=context;
	}
	
	private static Context context;
	
	public static void playClickSound(){
		clickSoundMp=MediaPlayer.create(SoundPlayer.context, R.raw.click);
		clickSoundMp.start();
	}
	
}
