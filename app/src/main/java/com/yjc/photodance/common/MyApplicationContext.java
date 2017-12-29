package com.yjc.photodance.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/12/29/029.
 * 获取全局Context
 * 务必记得此Application要在AndroidManifest中处理
 */

public class MyApplicationContext extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getMyApplicationContext(){
        return mContext;
    }
}
