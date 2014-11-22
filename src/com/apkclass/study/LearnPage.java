package com.apkclass.study;


import java.util.ArrayList;

import com.apkclass.adapter.CodeListAdapter;
import com.apkclass.database.AnswerDBHelper;
import com.apkclass.ui.R;
import com.apkclass.ui.StartLearn;
import com.apkclass.utils.log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.widget.ListView;

public class LearnPage extends Activity {
	
	private ArrayList<CodeBean> codeBeanList = null;
	private ListView lv_codelist;
	private AnswerDBHelper asdb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learnpage);
		
		asdb = new AnswerDBHelper(this);
		
		lv_codelist = (ListView) findViewById(R.id.lv_codelist);
		
		CodeProvider codeProvider = new CodeProvider(LearnPage.this);
		//codeBeanList = codeProvider.getCodeListFromFile(Integer.parseInt(getIntent().getStringExtra("number")));
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		int num = bundle.getInt("number");
		log.e("number:"+num);
		
		codeBeanList = codeProvider.getCodeListFromFile(num);
		
		CodeListAdapter codeListAdapter = new CodeListAdapter(LearnPage.this, asdb);
		lv_codelist.setAdapter(codeListAdapter);
		codeListAdapter.setCodebeanlist(codeBeanList);
		codeListAdapter.notifyDataSetChanged();
		
		
		if(codeBeanList != null){
			for(int i=0; i<codeBeanList.size(); i++) {
				log.e("codeBeanListitem:"+codeBeanList.get(i).toString());
			}
		} else {
            log.e("codeBeanList is null");
        }
	}
	

}
