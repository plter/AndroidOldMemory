/**
   Copyright [plter] [xtiqin]
   http://plter.sinaapp.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
   This is a part of PlterAndroidLib on 
   http://plter.sinaapp.com/?cat=14
*/

package com.plter.puzzle.ui;

import android.graphics.Bitmap;

import com.plter.lib.android.game2d.java.display.Image;

public class Pic extends Image {

	public Pic(Bitmap bitmap) {
		super(bitmap);
	}
	
	
	public float getRightX() {
		return rightX;
	}

	public void setRightX(float rightX) {
		this.rightX = rightX;
	}


	public float getRightY() {
		return rightY;
	}


	public void setRightY(float rightY) {
		this.rightY = rightY;
	}
	
	private int rightI=-1;
	
	private int rightJ=-1;
	
	
	/**
	 * 放在正确的位置
	 */
	public void putInRightPlace(){
		setI(rightI);
		setJ(rightJ);
		setX(rightX);
		setY(rightY);
	}
	
	private int I=-1;
	
	private int J=-1;
	
	
	/**
	 * 该卡片是否处于正确的位置
	 * @return
	 */
	public boolean isInRightPlace(){
		return rightX==getX()&&rightY==getY();
	}


	public int getI() {
		return I;
	}


	public void setI(int currentI) {
		this.I = currentI;
	}

	public int getJ() {
		return J;
	}


	public void setJ(int currentJ) {
		this.J = currentJ;
	}

	public int getRightJ() {
		return rightJ;
	}


	public void setRightJ(int rightJ) {
		this.rightJ = rightJ;
	}

	public int getRightI() {
		return rightI;
	}


	public void setRightI(int rightI) {
		this.rightI = rightI;
	}

	private float rightX=0;
	
	private float rightY=0;

}
