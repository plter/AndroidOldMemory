package com.plter.swfplayerext.filebrowser;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.plter.lib.android.java.anim.AnimationStyle;
import com.plter.lib.android.java.controls.IViewController;
import com.plter.lib.android.java.controls.ViewController;
import com.plter.swfplayerext.data.Config;
import com.plter.swfplayerext.data.Images;
import com.tencent.mobwin.AdView;

@SuppressLint("ViewConstructor")
public class FileBrowserView extends LinearLayout implements OnItemClickListener {

	public FileBrowserView(Context context,IViewController viewController,String dirPath) {
		super(context);
		setViewController(viewController);

		setOrientation(LinearLayout.VERTICAL);
		
		adView=new AdView(getContext());
		addView(adView, -1, -2);

		fileListView=new ListView(context);
		addView(fileListView, -1, -2);
		LinearLayout.LayoutParams fileListViewLp = (LinearLayout.LayoutParams) fileListView.getLayoutParams();
		fileListViewLp.weight=1;
		fileListView.setLayoutParams(fileListViewLp);

		if (dirPath==null) {
			dirPath=Config.getCurrentDir(getContext());
		}
		if (dirPath==null||dirPath.length()<Environment.getExternalStorageDirectory().getAbsolutePath().length()) {
			dirPath=Environment.getExternalStorageDirectory().getAbsolutePath();
		}


		File dir = new File(dirPath);
		File[] children=null;
		if (dir.exists()&&(children=dir.listFiles())!=null) {
			adapter=new FileListAdapter(context);

			ArrayList<FileListCellData> data = new ArrayList<FileListCellData>();
			
			if (!dir.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
				data.add(new FileListCellData(dir.getParentFile(),"..",Images.getBackIconBitmap(),true));
			}
			
			for (File file : children) {
				if (file.getName().charAt(0)!='.') {
					data.add(new FileListCellData(file));
				}
			}

			adapter.addAll(data);
			
			fileListView.setAdapter(adapter);
		}

		fileListView.setOnItemClickListener(this);

	}

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		FileListCellData data = adapter.getItem(arg2);
		
		if (data.getFile().isDirectory()) {
			Config.setCurrentDir(data.getFile().getAbsolutePath(), getContext());
			
			FileBrowserView view = new FileBrowserView(getContext(), null, data.getFile().getAbsolutePath());
			ViewController vc = new ViewController(view);
			view.setViewController(vc);
			
			if (!data.isParentDir()) {
				vc.setAnimationStyle(AnimationStyle.PUSH_RIGHT_TO_LEFT);
			}else{
				vc.setAnimationStyle(AnimationStyle.PUSH_LEFT_TO_RIGHT);
			}
			
			getViewController().pushViewController(vc, true);
		}else{
			if (data.getFileType().equals("swf")) {
				if (FileBrowserAty.getOnSwfFileSelectListener()!=null) {
					FileBrowserAty.getOnSwfFileSelectListener().onSelected(data.getFile().getAbsolutePath());
				}
				
				((Activity)getContext()).finish();
			}
		}
	}

	public IViewController getViewController() {
		return viewController;
	}
	public void setViewController(IViewController viewController) {
		this.viewController = viewController;
	}

	private ListView fileListView=null;
	private IViewController viewController=null;
	private FileListAdapter adapter=null;
	private AdView adView=null;

}
