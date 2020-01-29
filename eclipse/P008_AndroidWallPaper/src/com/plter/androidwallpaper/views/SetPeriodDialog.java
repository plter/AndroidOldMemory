package com.plter.androidwallpaper.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.plter.androidwallpaper.config.Config;
import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.lib.java.lang.ICallback;

public class SetPeriodDialog {

	public static void showSetPeriodDialog(Context context,final ICallback<Integer> callback,final ICallback<Void> cancel){
		
		final PeriodDialogAdapter adapter = new PeriodDialogAdapter(context);
		
		new AlertDialog.Builder(context)
		.setTitle("设置间隔")
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cancel.execute();
			}
		})
		.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				callback.execute(adapter.getItem(which).period);
			}
		})
		.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				cancel.execute();
			}
		})
		.show();
	}


	public static class PeriodDialogCellData{

		public PeriodDialogCellData(int period,String label,boolean selected) {
			this.period=period;
			this.label=label;
			this.selected=selected;
		}

		@Override
		public String toString() {
			return label;
		}

		public int period=60*1000;
		public String label="1分钟";
		public boolean selected=false;
	}


	public static class PeriodDialogAdapter extends ArrayAdapter<PeriodDialogCellData>{

		public static final String[] SELECTIONS = new String[]{
			"10秒",
			"30秒",
			"1分钟",
			"2分钟",
			"5分钟",
			"10分钟",
			"20分钟",
			"30分钟",
			"60分钟"
		};

		public static final int[] PERIODS = new int[]{
			10*1000,
			30*1000,
			60*1000,
			2*60*1000,
			5*60*1000,
			10*60*1000,
			20*60*1000,
			30*60*1000,
			60*60*1000
		};

		public PeriodDialogAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_single_choice);

			int selectedIndex=getSelectedIndex(context);

			for (int i = 0; i < PERIODS.length; i++) {
				if (selectedIndex!=i) {
					add(new PeriodDialogCellData(PERIODS[i], SELECTIONS[i], false));
				}else{
					add(new PeriodDialogCellData(PERIODS[i], SELECTIONS[i], true));
				}
			}
		}

		@Override
		public void initListCell(int position, View listCell, ViewGroup parent) {
			PeriodDialogCellData data = getItem(position);
			
			CheckedTextView rb = (CheckedTextView) listCell;
			rb.setText(data.label);
			rb.setTextColor(0xFF333333);
			rb.setChecked(data.selected);
		}


		public static int getSelectedIndex(Context context){
			int configedPeriod=Config.getPeriod(context);
			
			for (int i = 0; i < PERIODS.length; i++) {
				if (PERIODS[i]==configedPeriod) {
					return i;
				}
			}
			
			return 0;
		}
	}

}
