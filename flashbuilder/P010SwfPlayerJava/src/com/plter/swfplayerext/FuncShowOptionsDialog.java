package com.plter.swfplayerext;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.plter.swfplayerext.filebrowser.FileBrowserAty;
import com.plter.swfplayerext.filebrowser.OnSwfFileSelectListener;

public class FuncShowOptionsDialog implements FREFunction {

	@Override
	public FREObject call(final FREContext arg0, FREObject[] arg1) {

		if (currentAlert==null) {
			currentAlert=new AlertDialog.Builder(arg0.getActivity())
			.setTitle("操作选项")
			.setItems(new CharSequence[]{"打开SWF文件","软件信息"}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						FileBrowserAty.start(arg0, new OnSwfFileSelectListener() {

							@Override
							public void onSelected(String swfPath) {
								FuncGetSelectedSwfPath.selectedSwfPath=swfPath;
								arg0.dispatchStatusEventAsync(SwfPlayerExtCode.SELECT_SWF, "");
							}
						});
						break;
					case 1:
						new AlertDialog.Builder(arg0.getActivity()).setTitle("软件信息").setMessage("本软件用于播放swf文件\n作者博客：plter.com").setPositiveButton("确定", null).show();
						break;
					default:
						break;
					}
					currentAlert=null;
				}
			})
			.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					currentAlert=null;
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					currentAlert=null;
				}
			}).show();
		}		

		return null;
	}

	public static void hideOptionsDialog(){
		if (currentAlert!=null) {
			currentAlert.cancel();
		}
	}

	private static AlertDialog currentAlert=null;
}
