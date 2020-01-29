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

package com.plter.lib.java.utils;

import android.graphics.RectF;

import com.plter.lib.java.geom.Point;

/**
 * 测试碰撞工具
 * @author xtiqin
 * @see <a href="http://weibo.com/plter">plter</a>
 * @see <a href="http://game2d.sinaapp.com">Game2D</a>
 *
 */
public final class HitTestUtil {

	
	/**
	 * 点与矩形做碰撞测试
	 * @param pointX	点的x坐标
	 * @param pointY	点的y坐标
	 * @param rectLeft	矩形的左边
	 * @param top		矩形的上边
	 * @param rectRight	矩形的右边
	 * @param bottom	矩形的下边
	 * @return	碰撞测试是否成功
	 */
	public static boolean pointHitTestRect(float pointX,float pointY,float rectLeft,float rectTop,float rectRight,float rectBottom) {
		return pointX>=rectLeft&&pointX<=rectRight&&pointY>=rectTop&&pointY<=rectBottom;
	}
	
	/**
	 * 点与矩形做碰撞测试
	 * @param p	点
	 * @param r	矩形
	 * @return 碰撞测试是否成功
	 */
	public static boolean pointHitTestRect(Point p,RectF r) {
		return pointHitTestRect(p.x, p.y, r.left, r.top, r.right, r.bottom);
	}
	
	/**
	 * 点与矩形做测试碰撞
	 * @param pointX	点的x坐标
	 * @param pointY	点的y坐标
	 * @param rect		矩形
	 * @return			碰撞测试是否成功
	 */
	public static boolean pointHitTestRect(float pointX,float pointY,RectF rect){
		return pointHitTestRect(pointX, pointY, rect.left, rect.top, rect.right, rect.bottom);
	}
	
	
	/**
	 * 两个矩形做测试碰撞
	 * @param rect1 第一个矩形 
	 * @param rect2 第二个矩形 
	 * @return 是否碰撞
	 */
	public static boolean rectHitTestRect(RectF rect1,RectF rect2) {
		return pointHitTestRect(rect1.left, rect1.top, rect2)||
				pointHitTestRect(rect1.right, rect1.top, rect2)||
				pointHitTestRect(rect1.left, rect1.bottom, rect2)||
				pointHitTestRect(rect1.right, rect1.bottom, rect2)||
				pointHitTestRect(rect2.left, rect2.top, rect1)||
				pointHitTestRect(rect2.left, rect2.bottom, rect1)||
				pointHitTestRect(rect2.right, rect2.top, rect1)||
				pointHitTestRect(rect2.right, rect2.bottom, rect1);
	}
}
