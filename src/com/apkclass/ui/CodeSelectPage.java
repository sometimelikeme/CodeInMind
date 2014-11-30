package com.apkclass.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.apkclass.code.CodeManager;
import com.apkclass.code.CodeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by macrov on 2014/11/28.
 */
public class CodeSelectPage extends Activity {
    ListView codeListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codeselect);
        CodeManager codeManager = new CodeManager(this);

        ArrayList<CodeNode> codeNodeArrayList = codeManager.getCodeNodeListFromServer(10, 0);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0; i < codeNodeArrayList.size(); i++){
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("codeName", codeNodeArrayList.get(i).getCodeName());
            Drawable icon = new BitmapDrawable(getResources(), codeNodeArrayList.get(i).getIcon());
            listItem.put("codeIcon", icon);
            list.add(listItem);
        }

        SimpleAdapter codeNodeAdapter = new SimpleAdapter(this, list,
                R.layout.code_list_layout,
                new String[]{"codeName", "codeIcon"},
                new int[]{ R.id.codeName, R.id.codeIcon});
        codeListView = (ListView)findViewById(R.id.codeList);
        codeNodeAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view,Object data,String textRepresentation){
                if(view instanceof ImageView && data instanceof Drawable){
                    ImageView iv=(ImageView)view;
                    iv.setImageDrawable((Drawable)data);
                    return true;
                }
                else return false;
            }
        });
        codeListView.setAdapter(codeNodeAdapter);
        codeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Object> item = (HashMap<String, Object>) adapterView.getItemAtPosition(i);
                String codeName = (String)item.get("codeName");
                Intent intent = new Intent(CodeSelectPage.this, LearnPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("codeName", codeName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
}
