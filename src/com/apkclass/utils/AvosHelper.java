package com.apkclass.utils;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

public class AvosHelper {
	private Context context;
	private AVObject useraccount;

	public AvosHelper(Context context) {
		super();
		this.context = context;
		//创建UserAccount数据表
		useraccount = new AVObject("UserAccount");
	}
	
	
	public void SaveUserAccountInBackground(String phone, String nickname, String date) {
		useraccount.put("phone", phone);
		useraccount.put("nickname", nickname);
		useraccount.put("data", date);
		useraccount.saveInBackground(new SaveCallback() {
			@Override
			public void done(AVException arg0) {
				if(arg0 == null) {
					//保存成功
					log.e("SaveUserAccountInBackground done");
				}else {
					//保存失败
					log.e("SaveUserAccountInBackground error");
				}
			}
		});
		
	}
	
	

	
	

}
