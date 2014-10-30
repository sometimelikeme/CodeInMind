package com.apkclass.app;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by 28852028 on 10/22/2014.
 */
public class CIMApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //initial AVOSCloud;
        Log.d("CIMApp", "initialize AVOSCloud");
        AVOSCloud.initialize(CIMApp.this, "losyo515l1ozem6d3052v8t3jqvubl1o8cwrqf0hc1atnod2", "zn62b5f04c9079lqyxq4tfrpcvtu56eis7fdi8g3l3ee1k2x");
    }
}
