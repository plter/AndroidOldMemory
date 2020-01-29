package com.plter.lib.android.java.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Alert类，对sdk中所提供的AlertDialog进行封装，简化弹出提示框的操作
 * @author xtiqin
 */
public class Alert {

	
	/**
	 * 呈现一个对话框
	 * @param context	
	 * @param msg		对话框中要呈现的消息
	 * @param title		对话框的标题
	 * @param flag		指示对话框中要呈现的按钮的标签
	 * @param handler	对话框关闭时的处理器
	 * @return	被启动的对话框对象
	 */
	public static final AlertDialog show(final Context context,final String msg,final String title,final int flag,final AlertCloseHandler handler){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		if((flag&YES)>0){
			builder.setPositiveButton(yesLabel, handler==null?null:new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					handler.execute(YES);
				}
			});
		}
		if((flag&NO)>0){
			builder.setNegativeButton(noLabel, handler==null?null:new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					handler.execute(NO);
				}
			});
		}
		if((flag&CANCEL)>0){
			builder.setNeutralButton(cancelLabel, handler==null?null:new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					handler.execute(CANCEL);
				}
			});
		}
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			
			public void onCancel(DialogInterface dialog) {
				handler.execute(CANCEL);
			}
		});
		return builder.show();
	}
	
	
	/**
	 * 呈现一个提示框
	 * @param context
	 * @param msg		要呈现的消息
	 * @param title		对话框的标题
	 * @param handler	对话框消失后的回调对象
	 * @return
	 */
	public static final AlertDialog show(final Context context,final String msg,final String title,AlertCloseHandler handler){
		return show(context, msg, title, YES, handler);
	}
	
	/**
	 * 呈现一个提示框
	 * @param context
	 * @param msg	要呈现的消息
	 * @param title	对话框的标题
	 * @return
	 */
	public static final AlertDialog show(final Context context,final String msg,final String title){
		return show(context, msg, title, YES, null);
	}
	
	
	public static final int YES=1;
	public static final int NO=YES<<1;
	public static final int CANCEL=YES<<2;
	public static String yesLabel="OK";
	public static String noLabel="No";
	public static String cancelLabel="Cancel";
	
}
