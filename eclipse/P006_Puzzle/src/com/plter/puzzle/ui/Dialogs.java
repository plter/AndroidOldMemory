package com.plter.puzzle.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plter.lib.android.game2d.java.display.Stage;
import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.puzzle.R;
import com.plter.puzzle.atys.P006_PuzzleActivity;

public class Dialogs {


	/**
	 * 呈现切换图片的选择对话框
	 */
	public static void showChangeImageDialog(){

		if (cidiAdapter==null) {
			cidiAdapter=new ArrayAdapter<CIDIListCellData>(Stage.getContext(),R.layout.change_image_dialog_list_cell) {

				@Override
				public void initListCell(int arg0, View arg1, ViewGroup arg2) {
					TextView labelTv = (TextView) arg1.findViewById(R.id.labelTv);
					ImageView iconIv = (ImageView) arg1.findViewById(R.id.iconIv);
					
					CIDIListCellData data= getItem(arg0);
					
					labelTv.setText(data.getLabel());
					iconIv.setImageResource(data.getIconRes());
				}
			};

			
			cidiAdapter.add(new CIDIListCellData("内置图片", R.drawable.icon));//0
			cidiAdapter.add(new CIDIListCellData("本机图片", R.drawable.gallery_icon));//1
			cidiAdapter.add(new CIDIListCellData("拍照", R.drawable.camera_icon));//2
		}

		new AlertDialog.Builder(Stage.getContext())
		.setTitle("切换图片")
		.setAdapter(cidiAdapter, dialogItemClickListener)
		.setNegativeButton("取消", null)
		.show();
	}

	private static final DialogInterface.OnClickListener dialogItemClickListener=new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case 0:
				GameContainer.getInstance().changeImage();
				break;
			case 1:
				P006_PuzzleActivity.getInstance().startChooseImages();
				break;
			case 2:
				P006_PuzzleActivity.getInstance().startCaptureCamera();//拍照
				break;
			}
		}
	};

	private static ArrayAdapter<CIDIListCellData> cidiAdapter=null;
	
	
	public static void showSimpleMessageDialog(String msg,String title){
		new AlertDialog.Builder(Stage.getContext())
		.setTitle(title)
		.setMessage(msg)
		.setPositiveButton("OK", null)
		.show();
	}
}
