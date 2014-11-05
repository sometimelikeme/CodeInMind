package com.apkclass.ui;

import com.apkclass.study.AnswerBean;
import com.apkclass.study.CodeProvider;
import com.apkclass.ui.MyImageView.OnViewClickListener;
import com.apkclass.ui.R;
import com.avos.avoscloud.AVException;
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

		
		recommend = (MyImageView) findViewById(R.id.c_recommend);
        recommend.setOnClickIntent(new OnViewClickListener() {
            @Override
            public void onViewClick(MyImageView view) {

                CodeProvider cp = new CodeProvider(TestRolateAnimActivity.this);
//                FindCallback<AVObject> findCallback = new FindCallback<AVObject>() {
//                    @Override
//                    public void done(List<AVObject> avObjects, AVException e) {
//                        if(e == null){
//                            Log.d("CP", "Codes num : " + String.valueOf(avObjects.size()));
//                            for( AVObject avObject : avObjects){
//                                Log.d("CP", "Code name : " + avObject.getString("codeName"));
//                            }
//                        } else {
//                            Log.d("Error","can't find code");
//                        }
//                    }
//                };
//                cp.getCodeListFromServer(5, 0, findCallback);
//                GetDataCallback getDataCallback = new GetDataCallback() {
//                    @Override
//                    public void done(byte[] bytes, AVException e) {
//                        if(e == null){
//                            Log.d("CP", "Get Data.");
//                            Log.d("CP", new String(bytes));
//                        }
//                    }
//                };
//                cp.getCodeFromServer("android-base", getDataCallback);

//                AVQuery<AVObject> query = new AVQuery<AVObject>("Codes"); //get code list by synchroniztion way
//                query.whereEqualTo("codeName","android-base");
//                try {
//                    AVObject avObject = query.getFirst();
//                    Log.d("CP", avObject.getString("codeName"));
//                } catch (AVException e){
//                    e.printStackTrace();
//                }


//                AVUser user = new AVUser(); //sign up a new user
//                user.setUsername("macrov");
//                user.setEmail("backfire.ghw@gmail.com");
//                user.setPassword("6666");
//                try {
//                    user.signUp();
//                } catch (AVException e){
//                    e.printStackTrace();
//                }

                GetDataCallback getDataCallback = new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, AVException e) {
                        Log.d("xml",new String(bytes));
                        ArrayList<AnswerBean> answerBeanList = new ArrayList<AnswerBean>();
//                        XmlParser xp = new XmlParser();
                        XmlPullParser xpp = Xml.newPullParser();

                        try {
                            xpp.setInput(new StringReader(new String(bytes)));
                            int eventType = xpp.getEventType();
                            String name = null;
                            AnswerBean answerBean = new AnswerBean();
                            while(eventType != XmlPullParser.END_DOCUMENT){
                                if(eventType == XmlPullParser.START_DOCUMENT){
                                    Log.d("codeParse","START_DOCUMENT");
                                }else if(eventType == XmlPullParser.START_TAG){
                                    if(xpp.getName() == "subject") {
                                        name = "subject";
                                    }else if(xpp.getName() == "answer"){
                                        name = "answer";
                                    }
                                }else if(eventType == XmlPullParser.END_TAG){
                                    if(xpp.getName() == "node"){
                                        answerBeanList.add(answerBean);
                                        answerBean.clean();
                                    }
                                }else if(eventType == XmlPullParser.TEXT) {
                                    if(name == "subject"){
                                        answerBean.setSubject(xpp.getText());
                                        name = null;
                                    }else if(name == "answer"){
                                        answerBean.addAnswer(xpp.getText());
                                        name = null;
                                    }
                                }
                                eventType = xpp.next();
                            }
                            answerBeanList.add(answerBean);

                        }catch(XmlPullParserException xmlExc){
                            xmlExc.printStackTrace();
                        }catch(IOException ioExc){
                            ioExc.printStackTrace();
                        }


//                        xp.codeParse(new StringReader(new String(bytes)), answerBeanArrayList);
                        for(AnswerBean answerBean : answerBeanList){
                            Log.d("Test", "subject : " + answerBean.getSubject());
                            for(String answer : answerBean.getAnswerList()){
                                Log.d("Test", "answer : " + answer);
                            }
                        }
//                        XmlPullParser xpp = Xml.newPullParser();
//                        try {
//                            xpp.setInput(new StringReader(new String(bytes)));
//
//
//                            int eventType = xpp.getEventType();
//                            String name = null;
//                            while(eventType != XmlPullParser.END_DOCUMENT){
//                                if(eventType == XmlPullParser.START_DOCUMENT){
//                                    Log.d("parser","DOC START");
//                                } else if(eventType == XmlPullParser.START_TAG){
//                                    Log.d("parser","TAG START : " + xpp.getName());
//                                    if(xpp.getName() == "answer"){
//                                        name = "answer";
//                                    }else if(xpp.getName() == "subject"){
//                                        name = "subject";
//                                    }
//                                } else if(eventType == XmlPullParser.END_TAG){
//                                    Log.d("parser","TAG END : " + xpp.getName());
//                                } else if(eventType == XmlPullParser.TEXT){
//
//                                    if(name == "answer") {
//                                        Log.d("parser", "answer : " + xpp.getText());
//                                        name = null;
//                                    } else if(name == "subject"){
//                                        Log.d("parser", "subject : " + xpp.getText());
//                                        name = null;
//                                    }
//                                }
//                                eventType = xpp.next();
//                            }
//                        }catch (XmlPullParserException xmle){
//                                xmle.printStackTrace();
//                        }catch (IOException ioe){
//                            ioe.printStackTrace();
//                        }

                    }
                };
                cp.getCodeFromServer("android-base", getDataCallback);
            }
        });
	}

}