package com.plter.swfplayerext.filebrowser;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FileListAdapter extends BaseAdapter {


	public FileListAdapter(Context context) {
		setContext(context);
	}


	@Override
	public int getCount() {
		return al.size();
	}

	@Override
	public FileListCellData getItem(int position) {
		return al.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	public void add(FileListCellData object){
		al.add(object);
		notifyDataSetChanged();
	}

	public void add(int index,FileListCellData object){
		al.add(index, object);
		notifyDataSetChanged();
	}

	public void addAll(Collection<? extends FileListCellData> collection){
		al.addAll(collection);
		notifyDataSetChanged();
	}


	public void addAll(FileListCellData[] objects){
		for (int i = 0; i < objects.length; i++) {
			add(objects[i]);
		}
		notifyDataSetChanged();
	}


	public void remove(FileListCellData object){
		al.remove(object);
		notifyDataSetChanged();
	}


	public void remove(int index){
		al.remove(index);
		notifyDataSetChanged();
	}


	public void removeAll(){
		al.clear();
		notifyDataSetChanged();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		FileListCell cell=null;

		if (convertView!=null) {
			cell=(FileListCell) convertView;
		}else{
			cell=new FileListCell(getContext());
		}

		FileListCellData data = getItem(position);
		cell.setIcon(data.getIcon());
		cell.setLabel(data.getLabel());

		return cell;
	}	

	public Context getContext() {
		return context;
	}


	private void setContext(Context context) {
		this.context = context;
	}


	private Context context=null;
	private final ArrayList<FileListCellData> al = new ArrayList<FileListCellData>();

}
