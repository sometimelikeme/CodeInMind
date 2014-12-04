package com.apkclass.ui;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apkclass.utils.AvosHelper;
import com.apkclass.utils.RegularTools;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.LogUtil.log;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 注册、登陆模块
 * 
 * @author 姜坤
 * 
 */

public class RegisterAndLogin extends Activity {

	private Button bt_register;
	private EditText et_phone_number;
	private AvosHelper avosHelper;
	private EditText et_nickname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerandlogin);
		
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		et_nickname 	= (EditText) findViewById(R.id.et_nickname);
		bt_register 	= (Button) findViewById(R.id.bt_register);
		
		avosHelper 		= new AvosHelper(RegisterAndLogin.this);
		
		bt_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (et_phone_number.getText().equals("")) {
					Toast.makeText(RegisterAndLogin.this, "请输入手机号", 1).show();
				} else if(RegularTools.isMobileNO(et_phone_number.getText().toString())){
					//手机号码匹配正确，验证手机号码是否注册过。
					avosHelper.SearchFromCloud(et_phone_number.getText().toString(), new FindCallback<AVObject>() {
						@Override
						public void done(List<AVObject> arg0, AVException arg1) {
							// TODO Auto-generated method stub
							if(arg1 == null) {
								log.d("查询到"+arg0.size()+" 条符合条件的数据");
								if(arg0.size() == 0) {
									//查询成功，但是未查到对应数据
									avosHelper.SaveUserAccountInBackground(et_phone_number.getText().toString(), et_nickname.getText().toString(), "2014.12.4", new SaveCallback() {
										@Override
										public void done(AVException arg0) {
											// TODO Auto-generated method stub
											if(arg0 == null) {
												log.d("保存成功");
												Toast.makeText(RegisterAndLogin.this, "注册成功", 1).show();
											}else {
												log.d("保存失败"+arg0.getMessage());
												Toast.makeText(RegisterAndLogin.this, "注册失败", 1).show();
											}
										}
									});
								}else {
									Toast.makeText(RegisterAndLogin.this, "本号码已经注册过", 1).show();
								}
							}else {
								log.d("查询错误"+arg1.getMessage());
							}
						}
					});
				}
			}
		});
	}


}
