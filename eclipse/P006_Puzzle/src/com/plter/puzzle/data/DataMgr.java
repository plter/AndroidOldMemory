package com.plter.puzzle.data;

import com.plter.puzzle.R;

public class DataMgr {

	
	public static final int[] IMAGE_ARR=new int[]{
		R.drawable.img1,
		R.drawable.img2,
		R.drawable.img3,
		R.drawable.img4,
		R.drawable.img5,
		R.drawable.img6,
		R.drawable.img7,
		R.drawable.img8,
		R.drawable.img9
	};
	
	
	private static int imageIndex=-1;
	
	/**
	 * 取得一个图片的ID
	 * @return
	 */
	public static int changeImage(){
		imageIndex++;
		if (imageIndex>IMAGE_ARR.length-1) {
			imageIndex=0;
		}
		return IMAGE_ARR[imageIndex];
	}
}
