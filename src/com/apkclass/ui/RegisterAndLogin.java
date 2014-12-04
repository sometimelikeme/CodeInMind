package com.apkclass.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apkclass.utils.RegularTools;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerandlogin);
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		bt_register = (Button) findViewById(R.layout.registerandlogin);
		bt_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et_phone_number.getText().equals("")) {
					Toast.makeText(RegisterAndLogin.this, "请输入手机号", 1).show();
				} else if(RegularTools.isMobileNO(et_phone_number.getText().toString())){
					//手机号码输入正确
				}
			}
		});
	}


}
