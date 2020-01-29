package com.plter.puzzle.data;

import java.io.File;
import java.util.Calendar;

import android.os.Environment;

public class Config {

	
	public static String getAppCapPicsDirPath(){
		return Environment.getExternalStorageDirectory().getPath()+"/Puzzle/Camera";
	}
	
	public static File getAppCapPicsDir(){
		return new File(getAppCapPicsDirPath());
	}
	
	public static String getNewPictureName(){
		Calendar c =Calendar.getInstance();
		return c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH)+"-"+
				c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.SECOND)+"-"+c.get(Calendar.MILLISECOND)+".jpg";
	}
}
