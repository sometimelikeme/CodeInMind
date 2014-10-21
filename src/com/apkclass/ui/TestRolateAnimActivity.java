package com.apkclass.ui;

import com.apkclass.ui.MyImageView.OnViewClickListener;
import com.apkclass.ui.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class TestRolateAnimActivity extends Activity
{
	MyImageView joke;
	MyImageView idea;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		
		joke = (MyImageView) findViewById(R.id.c_joke);
		joke.setOnClickIntent(new MyImageView.OnViewClickListener()
		{

			@Override
			public void onViewClick(MyImageView view)
			{
				Toast.makeText(TestRolateAnimActivity.this, "记代码，找工作，赚钱，生娃，学IT，记代码。。。", 1000).show();
				Intent intent = new Intent();
				intent.setClass(TestRolateAnimActivity.this, FirstPage.class);
				TestRolateAnimActivity.this.startActivity(intent);
			}
		});
		
		idea = (MyImageView) findViewById(R.id.c_idea);
		idea.setOnClickIntent(new OnViewClickListener() {
			
			@Override
			public void onViewClick(MyImageView view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(TestRolateAnimActivity.this, StartLearn.class);
				TestRolateAnimActivity.this.startActivity(intent);
			}
		});
		
		
		
		
	}
	
	
}