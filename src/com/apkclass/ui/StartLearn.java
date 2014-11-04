package com.apkclass.ui;

import java.util.ArrayList;
import java.util.Map;

import com.apkclass.adapter.DocsListAdapter;
import com.apkclass.study.CodeBean;
import com.apkclass.study.CodeProvider;
import com.apkclass.study.LearnPage;
import com.apkclass.utils.log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class StartLearn extends Activity {
	
	
	ListView ll_contentlist = null;
	DocsListAdapter contentlistadapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startlearn);
		
		ArrayList<String> titlelist = new ArrayList<String>();
		titlelist.add("疯狂java");
		titlelist.add("疯狂android");
		ll_contentlist =  (ListView) findViewById(R.id.lv_content);
		contentlistadapter = new DocsListAdapter(StartLearn.this);
		contentlistadapter.setDatalist(titlelist);
		ll_contentlist.setAdapter(contentlistadapter);
		
		ll_contentlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(StartLearn.this, LearnPage.class);
				Bundle bundle = new Bundle();
				log.e("position:"+ position);
				bundle.putInt("number", position);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		

		

		
		
		
	}
	
	

}
