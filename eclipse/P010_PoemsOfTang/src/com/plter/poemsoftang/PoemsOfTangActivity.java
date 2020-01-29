package com.plter.poemsoftang;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plter.lib.android.java.controls.ArrayAdapter;
import com.plter.lib.android.java.controls.ListViewControllerActivity;
import com.plter.poemsoftang.utils.PoemsResTool;

public class PoemsOfTangActivity extends ListViewControllerActivity implements OnItemClickListener {


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		doc = PoemsResTool.getPoemsXMLDocument(this);

		adapter=new AuthorListAdapter(this);
		adapter.setNodeList(doc.getElementsByTagName("author"));
		setAdapter(adapter);

		setOnItemClickListener(this);

		//有米广告>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		AdManager.init(this, "849cb327fa09e6fb", "8fbcb8b9b0ac5b6c", 30, false);
		mainLayout=(LinearLayout) findViewById(R.id.mainLayout);
		AdView ad=new AdView(this);
		mainLayout.addView(ad, -1, -2);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		pushViewController(new PoemListVC(this, PoemsResTool.getChildrenByTagName("poem", adapter.getItem(arg2))), true);
	}


	private Document doc;
	private AuthorListAdapter adapter=null;
	private LinearLayout mainLayout=null;


	///////////////////////////////////////////////////////////////////
	static class AuthorListAdapter extends ArrayAdapter<Node>{

		public AuthorListAdapter(Context context) {
			super(context, R.layout.author_list_cell);
		}

		@Override
		public void initListCell(int position, View listCell, ViewGroup parent) {
			nameTv=(TextView) listCell.findViewById(R.id.nameTv);
			desTv=(TextView) listCell.findViewById(R.id.desTv);
			node=getItem(position);

			nameTv.setText(PoemsResTool.getAttr("name", node));
			desTv.setText(PoemsResTool.getAttr("des", node));
		}
		private TextView nameTv=null;
		private TextView desTv=null;
		private Node node=null;

		public void setNodeList(NodeList nodes){
			for (int i = 0; i < nodes.getLength(); i++) {
				add(nodes.item(i));
			}
		}

	}
}