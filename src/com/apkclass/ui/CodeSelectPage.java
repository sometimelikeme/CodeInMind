package com.apkclass.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apkclass.code.CodeManager;
import com.apkclass.code.CodeNode;

import java.util.ArrayList;

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
        ArrayList<String> codeArrayList = codeManager.getCodeListFromServer(10,0);
        String[] codeArray = new String[codeArrayList.size()];
        codeArray = codeArrayList.toArray(codeArray);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.array_list, codeArray);
        codeListView = (ListView)findViewById(R.id.codeList);
        if(codeListView == null){
            Log.d("test", "codeListView is null");
        }
        codeListView.setAdapter(arrayAdapter);
        codeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String codeName = (String) codeListView.getItemAtPosition(i);
                Intent intent = new Intent(CodeSelectPage.this, LearnPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("codeName", codeName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
