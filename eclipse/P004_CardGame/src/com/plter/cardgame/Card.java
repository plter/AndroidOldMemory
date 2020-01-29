/**
 * Copyright [http://game2d.sinaapp.com] [xtiqin]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.plter.cardgame;

import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;

import com.plter.lib.android.game2d.java.display.Card2D;
import com.plter.lib.android.game2d.java.display.Shape;
import com.plter.lib.android.game2d.java.display.TextLine;

public class Card extends Card2D {
	
	
	public Card(int index) {
		super(WIDTH/2);
		
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		setIndex(index);
		setFrames(7);
		
		//构建正面
		Shape s=new Shape();
		s.graphics.setColor(0xFF999999);
		s.graphics.addRect(0, 0, WIDTH, HEIGHT, Direction.CW);
		getRecto().addChild(s);
		s=new Shape();
		s.graphics.setStyle(Style.STROKE);
		s.graphics.setColor(0xFF333333);
		s.graphics.setStrokeWidth(3);
		s.graphics.moveTo(0, 0);
		s.graphics.lineTo(WIDTH, 0);
		s.graphics.lineTo(WIDTH, HEIGHT);
		s.graphics.lineTo(0, HEIGHT);
		s.graphics.lineTo(0, 0);
		getRecto().addChild(s);
		
		TextLine tl=new TextLine();
		tl.setSize(60);
		tl.setText(new Integer(index).toString());
		tl.setX((WIDTH-tl.getTextWidth())/2);
		tl.setY(15);
		tl.setColor(Color.BLACK);
		getRecto().addChild(tl);
		
		
		//构建反面
		s=new Shape();
		s.graphics.setColor(0xFF333333);
		s.graphics.addRect(0, 0, WIDTH, HEIGHT, Direction.CW);
		getVerso().addChild(s);
	}
	
	private int index=0;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	
	////////////////////////private static values////////////////////////////////
	
	public static final int WIDTH=80;
	public static final int HEIGHT=80;
}
