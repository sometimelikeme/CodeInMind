package com.apkclass.ui;

import com.apkclass.ui.MyImageView.OnViewClickListener;
import com.apkclass.ui.R;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.LogUtil.log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class TestRolateAnimActivity extends Activity {
	MyImageView joke;
	MyImageView idea;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		joke = (MyImageView) findViewById(R.id.c_joke);
		joke.setOnClickIntent(new MyImageView.OnViewClickListener() {

			@Override
			public void onViewClick(MyImageView view) {
				Toast.makeText(TestRolateAnimActivity.this,
						"记代码，找工作，赚钱，生娃，学IT，记代码。。。", 1000).show();
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

		// avos = (MyImageView) findViewById(R.id.c_recommend);
		// avos.setOnClickIntent(new OnViewClickListener() {
		// @Override
		// public void onViewClick(MyImageView view) {
		// Log.d("AVOS test", "start");
		// AVObject avObject = new AVObject("TestObject");
		// avObject.put("name","test in cim");
		// avObject.saveInBackground(new SaveCallback() {
		// @Override
		// public void done(AVException e) {
		// if( e == null ){
		// Log.d("Test","avObject saved");
		// } else {
		// e.printStackTrace();
		// }
		// }
		// });

		MyImageView avos = (MyImageView) findViewById(R.id.c_recommend);
		avos.setOnClickIntent(new OnViewClickListener() {
			@Override
			public void onViewClick(MyImageView view) {
				AVQuery<AVObject> query = new AVQuery<AVObject>("apk");
				AVObject avObject;
				try {
					avObject = query.get("54485927e4b0327b4b9b84b3");
					if(avObject == null) {
						log.e("avobject == null");
					}
					AVFile avFile = avObject.getAVFile("myfile");
					if (avFile == null) {
						log.e("avfile==null");
						return;
					}
					avFile.getDataInBackground(new GetDataCallback() {
						public void done(byte[] data, AVException e) {
							// process data or exception.
							log.e("data:" + data.length);
							//log.e("data:" + new String(data));
						}
					});
				} catch (AVException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	      AVQuery<AVObject> query2 = new AVQuery<AVObject>("CodeRepository");
          query2.getFirstInBackground(new GetCallback<AVObject>() {
              @Override
              public void done(AVObject avObject, AVException e) {
                  if( e == null ){
                      if(avObject == null){
                          Log.e("Test","avObject is null");
                      } else {
                          Log.e("Test","avObject is not null");
                      }
                      Log.e("Test",avObject.getObjectId());
                      String name = avObject.getString("name");
                      Log.e("Test",name);
                      AVFile xmlFile = avObject.getAVFile("code");
                      xmlFile.getDataInBackground(new GetDataCallback() {
                          @Override
                          public void done(byte[] bytes, AVException e) {
                              if(e == null){
                                  Log.e("Test", "Got Data");
                              }
                          }
                      });
                  }
              }
          });

	}

}