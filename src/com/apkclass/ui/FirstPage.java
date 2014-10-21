package com.apkclass.ui;

import java.io.File;  
import java.io.IOException;

import com.apkclass.specialview.RoundProgressView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;



public class FirstPage extends Activity{

	private static final String TAG = "FirstPage";
	LinearLayout linearLayout;
	RoundProgressView roundProgressView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.firstpagelayout);
		
		linearLayout = (LinearLayout) findViewById(R.id.ll_firstpage);
		
		roundProgressView = (RoundProgressView) findViewById(R.id.roundpv);
		
		roundProgressView.setProgress((float) 0.1); //此处设置显示学习进度
		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
			Log.e(TAG, "mediamounted");
		}
		
		
		File sdcarddir = Environment.getExternalStorageDirectory();
		try {
			sdcarddir.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
