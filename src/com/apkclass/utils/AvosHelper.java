package com.apkclass.utils;

import android.content.Context; 

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

public class AvosHelper {
	private final String CLOUD_TABLE = "UserAccount";
	private Context context;
	private AVObject useraccount;
	private SaveCallback savecallback;
	private FindCallback<AVObject> findcallback;

	public AvosHelper(Context context) {
		super();
		this.context = context;
		//创建UserAccount数据表
		useraccount = new AVObject(CLOUD_TABLE);
	}

	public void SaveUserAccountInBackground(String phone, String nickname, String date, SaveCallback savecallback) {
		useraccount.put("phone", phone);
		useraccount.put("nickname", nickname);
		useraccount.put("data", date);
		useraccount.saveInBackground(savecallback);
	}
	
	public void SearchFromCloud(String phone, FindCallback<AVObject> findcallback) {
		AVQuery<AVObject> query = new AVQuery<AVObject>(CLOUD_TABLE);
		query.whereEqualTo("phone", phone);
		query.findInBackground(findcallback);
	}
	
}
