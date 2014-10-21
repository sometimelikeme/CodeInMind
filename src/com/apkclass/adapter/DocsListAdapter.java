package com.apkclass.adapter;

import java.util.ArrayList;

import com.apkclass.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DocsListAdapter extends BaseAdapter {
	
	Context context;
	ArrayList<String> datalist;
	
	public DocsListAdapter(Context context) {
		super();
		this.context = context;
	}
	
	

	public ArrayList<String> getDatalist() {
		return datalist;
	}



	public void setDatalist(ArrayList<String> datalist) {
		this.datalist = datalist;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.docslistitem, null);
		TextView tv = (TextView) view.findViewById(R.id.tv_title);
		tv.setText(datalist.get(position));
		
		return view;
	}

}
