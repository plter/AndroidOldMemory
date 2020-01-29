package com.plter.androidwallpaper.views;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.plter.androidwallpaper.R;
import com.plter.androidwallpaper.net.CachedPhotos;
import com.plter.androidwallpaper.net.Image;
import com.plter.androidwallpaper.net.Net;
import com.plter.androidwallpaper.net.PagedImages;
import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.lib.java.lang.ICallback;

public class ServerImageListActivity extends ListActivity {


	private String cid="";
	private PagedImages pagedPhotos=null;
	private ICallback<PagedImages> loadPhotosCallback=new ICallback<PagedImages>() {
		@Override
		public boolean execute(PagedImages... args) {
			pagedPhotos=args[0];

			if (pagedPhotos==null) {
				new AlertDialog.Builder(ServerImageListActivity.this)
				.setTitle("加载失败")
				.setMessage("加载失败，请确认网络连接")
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).show();
				return false;
			}else{
				PagedImages cachedPhotos = CachedPhotos.get(cid);

				if (cachedPhotos!=null) {
					cachedPhotos.contact(pagedPhotos);

					setPagedPhotos(cachedPhotos);
				}else{
					CachedPhotos.put(cid, pagedPhotos);

					setPagedPhotos(pagedPhotos);
				}
				return true;
			}


		}
	};
	private ServerPhotoListAdapter adapter=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new ServerPhotoListAdapter(this);
		setListAdapter(adapter);

		cid = getIntent().getStringExtra("cid");
		pagedPhotos = CachedPhotos.get(cid);

		setTitle(getIntent().getStringExtra("label"));

		if (pagedPhotos==null) {
			Net.loadPhotos(this, cid, "1", loadPhotosCallback);
		}else{
			setPagedPhotos(pagedPhotos);
		}
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Image photo = adapter.getItem(position);
		if (!photo.ID.equals("loadMore")) {

			Intent i =new Intent(this, ServerImageViewerActivity.class);
			i.putExtra("url", photo.url);
			startActivityForResult(i, 0);

		}else{
			System.out.println("Load More Photo");

			PagedImages pp = CachedPhotos.get(cid);
			Net.loadPhotos(this, cid, (pp.getPage()+1)+"", loadPhotosCallback);
		}

		super.onListItemClick(l, v, position, id);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case ImageViewerActivity.NEED_REFRESH:			
			Net.loadPhotos(this, cid, "1", new ICallback<PagedImages>() {

				@Override
				public boolean execute(PagedImages... args) {
					pagedPhotos=args[0];
					CachedPhotos.put(cid, pagedPhotos);
					setPagedPhotos(pagedPhotos);
					return false;
				}
			});
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	private void setPagedPhotos(PagedImages photos){
		adapter.setPagedPhotos(photos);
	}


	////////////////////////////////////////////////////////////////////////////

	public static class ServerPhotoListAdapter extends ArrayAdapter<Image> implements ICallback<Void> {

		public ServerPhotoListAdapter(Context context) {
			super(context, R.layout.photo_list_cell);
		}

		@Override
		public void initListCell(int position, View listCell, ViewGroup parent) {

			tmp = getItem(position);

			iconIv= (ImageView) listCell.findViewById(R.id.iconIv);
			titleTv=(TextView) listCell.findViewById(R.id.titleTv);
			desTv=(TextView) listCell.findViewById(R.id.desTv);

			if (tmp.getIcon()!=null) {
				iconIv.setImageBitmap(tmp.getIcon());
			}else{
				if (!tmp.ID.equals("loadMore")) {
					iconIv.setImageResource(R.drawable.loading);
					tmp.loadIconOnce(this);
				}else{
					iconIv.setImageBitmap(null);
				}
			}
			titleTv.setText(tmp.name);
			desTv.setText(tmp.description);
		}

		public void addPagedPhotos(PagedImages pagedPhotos){
			if (pagedPhotos.getImgData()==null) {
				return;
			}

			removeLast();
			addAll(pagedPhotos.getImgData());

			if (pagedPhotos.getPage()< ((float)pagedPhotos.getCount())/((float)pagedPhotos.getPerPageCount())) {
				add(new Image("loadMore", "点击加载更多图片...", "", "", "", "", ""));
			}
		}

		public void setPagedPhotos(PagedImages pagedPhotos){
			clear();
			addPagedPhotos(pagedPhotos);
		}

		private ImageView iconIv=null;
		private TextView titleTv=null;
		private TextView desTv=null;
		private Image tmp=null;

		@Override
		public boolean execute(Void... args) {
			notifyDataSetChanged();

			return true;
		}
	}


}
