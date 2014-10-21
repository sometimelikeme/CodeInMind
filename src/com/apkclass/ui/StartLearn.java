package com.apkclass.ui;

import java.util.ArrayList;
import java.util.Map;

import com.apkclass.adapter.DocsListAdapter;
import com.apkclass.study.CodeBean;
import com.apkclass.study.CodeProvider;
import com.apkclass.utils.log;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class StartLearn extends Activity {
	
	ArrayList<CodeBean> codeBeanList = null;
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
		

		
		CodeProvider codeProvider = new CodeProvider(StartLearn.this);
		codeBeanList = codeProvider.getCodeListFromFile(0);
		if(codeBeanList != null){
			for(int i=0; i<codeBeanList.size(); i++) {
				log.e("codeBeanListitem:"+codeBeanList.get(i).toString());
			}
		} else {
            log.e("codeBeanList is null");
        }
		
		
		
	}
	
	

}
