package com.plter.matcha.media;

import java.io.IOException;

import com.plter.matcha.Globals;
import com.plter.matcha.R;

import android.media.MediaPlayer;


public class SoundPlayer {

	
	private static MediaPlayer welcomePlayer=null;
	public static void playWelcomeSound(){
		if(welcomePlayer==null){
			welcomePlayer=MediaPlayer.create(Globals.getMainContext(), R.raw.welcome);
			try {
				welcomePlayer.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		welcomePlayer.start();
	}
}
