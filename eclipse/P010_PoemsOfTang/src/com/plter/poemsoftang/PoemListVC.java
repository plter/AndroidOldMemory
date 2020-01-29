package com.plter.poemsoftang;

import java.util.List;

import org.w3c.dom.Node;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.lib.android.java.controls.ListViewController;
import com.plter.poemsoftang.utils.PoemsResTool;

public class PoemListVC extends ListViewController implements OnItemClickListener {
	
	
	public PoemListVC(Context context,List<Node> nodes) {
		super(context, R.layout.poem_list);
		
		adapter=new PoemListAdapter(context);
		adapter.addAll(nodes);
		setAdapter(adapter);
		setOnItemClickListener(this);		
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Node node = adapter.getItem(arg2);		
		pushViewController(new PoemVC(getContext(), PoemsResTool.getAttr("name", node),PoemsResTool.getAttr("content", node)), true);
	}
	
	
	private PoemListAdapter adapter;	
	
	/////////////////////////////////////////////////////////////////
	static final class PoemListAdapter extends ArrayAdapter<Node>{

		public PoemListAdapter(Context context) {
			super(context, R.layout.poem_list_cell);
		}

		@Override
		public void initListCell(int position, View listCell, ViewGroup parent) {
			poemNameTv=(TextView) listCell.findViewById(R.id.poemNameTv);
			poemNameTv.setText(PoemsResTool.getAttr("name", getItem(position)));
		}
		
		private TextView poemNameTv=null;
		
	}

	
}
