package com.plter.androidwallpaper.views;

import java.io.File;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.plter.androidwallpaper.R;
import com.plter.androidwallpaper.config.Config;
import com.plter.androidwallpaper.services.AutoChangeWallPaperService;
import com.plter.androidwallpaper.system.FileTool;
import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.lib.java.lang.ICallback;

public class ClientImageListActivity extends ListActivity {

	private ICallback<Boolean> autoChangeWallpaperSettingsChangedHandler=new ICallback<Boolean>() {

		@Override
		public boolean execute(Boolean... args) {
			refreshAutoChangeWPCb();
			return false;
		}
	};
	private OnClickListener autoChangeWallpaperCbClickedHandler=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Config.setAutoChangeWallPaper(ClientImageListActivity.this, autoChangeWallpaperCb.isChecked());

			if (autoChangeWallpaperCb.isChecked()) {
				SetPeriodDialog.showSetPeriodDialog(ClientImageListActivity.this, setPeriodDialogCallback,setPeriodcancelCallback);
			}else{
				AutoChangeWallPaperService.checkToStartOrStopService(ClientImageListActivity.this);
			}
		}
	};
	private int currentClickIndex=0;
	private OnItemLongClickListener onItemLongClickHandler=new AdapterView.OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			currentClickIndex=position;
			return false;
		}
	};
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_image_list);

		autoChangeWallpaperCb=(CheckBox) findViewById(R.id.autoChangeWallpaperCb);
		autoChangeWallpaperCb.setOnClickListener(autoChangeWallpaperCbClickedHandler);

		imgFiles= FileTool.androidWallpaperDir().listFiles();
		
		if (imgFiles==null||imgFiles.length<=0) {
			
			new AlertDialog.Builder(this)
			.setTitle("没有文件")
			.setMessage("当前文件夹还没有图片")
			.setPositiveButton("返回", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).show();
			
			return;
		}
		
		adapter = new ClientImageListAdapter(this);
		String type=null;
		String name=null;
		
		for (File f : imgFiles) {
			name=f.getName();
			type=name.substring(name.lastIndexOf("."));
			if (type.equals(".jpg")) {
				adapter.add(new CellDataLocalImageFile(f));
			}
		}
		setListAdapter(adapter);

		Config.autoChangeWallpaperSettingsChange.add(autoChangeWallpaperSettingsChangedHandler);

		refreshAutoChangeWPCb();
		
		registerForContextMenu(getListView());
		getListView().setOnItemLongClickListener(onItemLongClickHandler);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("操作选项");
		menu.add(0, DELETE_ITEM_ID, 0, "删除");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ITEM_ID:
			adapter.getItem(currentClickIndex).getFile().delete();
			adapter.remove(currentClickIndex);
			AutoChangeWallPaperService.checkToStartOrStopService(this);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, ClientImageViewerActivity.class);
		i.putExtra("path", adapter.getItem(position).getFile().getAbsolutePath());
		startActivity(i);
		super.onListItemClick(l, v, position, id);
	}


	public void refreshAutoChangeWPCb(){
		autoChangeWallpaperCb.setChecked(Config.isAutoChangeWallPaper(this));
	}


	private static final int DELETE_ITEM_ID=1;
	private File[] imgFiles=null;
	private ClientImageListAdapter adapter=null;	
	private CheckBox autoChangeWallpaperCb=null;
	private ICallback<Integer> setPeriodDialogCallback=new ICallback<Integer>() {

		@Override
		public boolean execute(Integer... args) {
			Config.setPeriod(ClientImageListActivity.this, args[0]);
			
			AutoChangeWallPaperService.checkToStartOrStopService(ClientImageListActivity.this);
			return false;
		}
	};
	private ICallback<Void> setPeriodcancelCallback=new ICallback<Void>() {

		@Override
		public boolean execute(Void... args) {
			Config.setAutoChangeWallPaper(ClientImageListActivity.this, false);
			return false;
		}
	};


	///////////////////////////////////////////////////////////////

	public static class ClientImageListAdapter extends ArrayAdapter<CellDataLocalImageFile> {

		public ClientImageListAdapter(Context context) {
			super(context, R.layout.local_image_list_cell);
		}

		@Override
		public void initListCell(int position, View listCell, ViewGroup parent) {
			iconIv = (ImageView) listCell.findViewById(R.id.iconIv);
			nameTv = (TextView) listCell.findViewById(R.id.nameTv);

			CellDataLocalImageFile f = getItem(position);
			nameTv.setText(f.getFile().getName());
			iconIv.setImageBitmap(f.getIcon());
		}


		private ImageView iconIv=null;
		private TextView nameTv=null;

	}
	
	////////////////////////////////////////////////////////////////////////////
}

