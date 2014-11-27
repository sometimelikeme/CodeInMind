package com.apkclass.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.apkclass.code.AnswerNode;
import com.apkclass.code.CodeManager;
import com.apkclass.code.CodeNode;
import com.apkclass.database.CodeDBHelper;
import com.apkclass.ui.MyImageView.OnViewClickListener;

import java.util.ArrayList;

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

//                CodeManager cm = new CodeManager(TestRolateAnimActivity.this);//当前context初始化
//                ArrayList<String> codeList = cm.getCodeListFromServer(10,0);//10获取数量，0是skip值， 10,0表示取前十个， 10，2表示取第21-30个
//                for(String code:codeList){
//                    Log.d("test","codeName :" + code);
//                }
//                CodeNode codeNode = cm.getCodeNode("android-base");//题库名字是android-base
//                AnswerNode answerNode = codeNode.getOneAnswerNode();//拿到一个记忆等级最低的题
//                Log.d("test", answerNode.getSubject());//题的subject
//                for(String answer:answerNode.getRandomAnswerArrayList()){//各个答案，顺序已经打乱
//                    Log.d("test","answer :" + answer);
//                }
//                String answerSelected = answerNode.getAnswerArrayList().get(0);//用户选择的答案
//                codeNode.saveOneAnswerNode(answerNode,answerSelected);//判断并保存结果
//                Log.d("test", String.valueOf(codeNode.getOneAnswerMemLevel(answerNode)));
                Intent intent = new Intent();
                intent.setClass(TestRolateAnimActivity.this, LearnPage.class);
                TestRolateAnimActivity.this.startActivity(intent);


			}
		});


	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CodeDBHelper codeDBHelper = new CodeDBHelper(TestRolateAnimActivity.this);
        codeDBHelper.close();
    }
}