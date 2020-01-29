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

package com.plter.lib.java.geom;


/**
 * 点
 * @author xtiqin
 * @see <a href="http://game2d.sinaapp.com">Game2D</a>
 *
 */
public class Point {
	
	/**
	 * 构建一个点对象
	 */
	public Point() {
	}
	

	/**
	 * 构建一个点对象
	 * @param x 点的x坐标
	 * @param y 点的y坐标
	 */
	public Point(float x,float y) {
		this.x=x;
		this.y=y;
	}
	
	
	/**
	 * 求两点之间的距离
	 * @param a 第一个点
	 * @param b 第二个点
	 * @return 两点之间的距离
	 */
	public static float distance(Point a,Point b) {
		return distance(a.x, a.y, b.x, b.y);
	}
	
	/**
	 * 求两点之间的距离
	 * @param a_x 第一个点的x坐标
	 * @param a_y 第一个点的y坐标
	 * @param b_x 第二个点的x坐标
	 * @param b_y 第二个点的y坐标
	 * @return 两个点之间的距离
	 */
	public static float distance(float a_x,float a_y,float b_x,float b_y) {
		float d_x=a_x-b_x;
		float d_y=a_y-b_y;
		return (float)Math.sqrt((d_x*d_x+d_y*d_y));
	}
	
	/**
	 * 创建一个当前点的副本
	 */
	public Point clone() {
		
		return new Point(x, y);
	}
	
	
	public String toString() {
		return "[Point(x="+x+",y="+y+")]";
	}
	
	/**
	 * x坐标
	 */
	public float x=0;
	
	/**
	 * y坐标
	 */
	public float y=0;
}
