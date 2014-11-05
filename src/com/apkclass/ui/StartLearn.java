package com.apkclass.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.apkclass.adapter.DocsListAdapter;
import com.apkclass.study.CodeBean;
import com.apkclass.study.CodeProvider;
import com.apkclass.study.LearnPage;
import com.apkclass.utils.log;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
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
		
		final ArrayList<String> titlelist = new ArrayList<String>();
        ll_contentlist =  (ListView) findViewById(R.id.lv_content);
        contentlistadapter = new DocsListAdapter(StartLearn.this);

//        CodeProvider cp = new CodeProvider(StartLearn.this);
//        FindCallback<AVObject> findCallback = new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> avObjects, AVException e) {
//                if(e == null){
//                    for(AVObject avObject : avObjects){
//                        titlelist.add(avObject.getString("codeName"));
//                    }
//                    contentlistadapter.setDatalist(titlelist);
//                    ll_contentlist.setAdapter(contentlistadapter);
//                }
//            }
//        };
//        cp.getCodeListFromServer(10, 0, findCallback);
        AVQuery<AVObject> query = new AVQuery<AVObject>("Codes");
        try {
            List<AVObject> avObjects = query.find();
            for(AVObject avObject : avObjects){
                titlelist.add(avObject.getString("codeName"));
                Log.d("CP", avObject.getString("codeName"));
            }
            contentlistadapter.setDatalist(titlelist);
            ll_contentlist.setAdapter(contentlistadapter);
        } catch (AVException e){
            e.printStackTrace();
        }
//		titlelist.add("疯狂java");
//		titlelist.add("疯狂android");
//
//		contentlistadapter.setDatalist(titlelist);
//		ll_contentlist.setAdapter(contentlistadapter);
		
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
