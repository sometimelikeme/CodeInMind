package com.apkclass.ui;

import com.apkclass.study.AnswerBean;
import com.apkclass.study.CodeProvider;
import com.apkclass.ui.MyImageView.OnViewClickListener;
import com.apkclass.ui.R;
import com.apkclass.utils.log;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import junit.framework.Test;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TestRolateAnimActivity extends Activity
{
	MyImageView joke;
	MyImageView idea;
    MyImageView recommend;

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

		
		recommend = (MyImageView) findViewById(R.id.c_recommend);
        recommend.setOnClickIntent(new OnViewClickListener() {
            @Override
            public void onViewClick(MyImageView view) {
                CodeProvider cp = new CodeProvider(TestRolateAnimActivity.this);

                //getCodeListFromServer
                ArrayList<String> codeList = cp.getCodeListFromServer(10,0);
                for(String code:codeList) {
                    Log.d("codeList", "CodeList : " + code);
                }

                //getCodeFromServer
                byte[] code = cp.getCodeFromServer(codeList.get(0));
                //Log.d("codeList", new String(code));

                ArrayList<AnswerBean> answerBeanArrayList = new ArrayList<AnswerBean>();

                XmlParser.codeParse(new StringReader(new String(code)), answerBeanArrayList);

                for(AnswerBean answerBean:answerBeanArrayList){
                    Log.d("codeList","subject :" + answerBean.getSubject());
                    Log.d("codeList","id :" + answerBean.getID());
                    for(String answer: answerBean.getAnswerList()){
                        Log.d("codeList", "answer :" + answer);
                    }
                }

            }
        });
	}

}