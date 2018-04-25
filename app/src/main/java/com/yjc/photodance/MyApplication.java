package com.yjc.photodance;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.yjc.photodance.common.storage.SharedPreferenceDao;
import com.yjc.photodance.model.Account;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import java.lang.reflect.Field;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/12/29/029.
 * 获取全局Context
 * 务必记得此Application要在AndroidManifest中处理
 */

public class MyApplication extends LitePalApplication {

    // TODO: 2018/1/7/007 实现成单例
    private static Context mContext;
    private static int collectionsCount = 1;
    private static boolean isFromCollections = false;


    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bmob后端云
        Bmob.initialize(this, "957299827f3a40ea662ad5891f81c247");

        mContext = getApplicationContext();

        LitePal.initialize(this);
        //创建数据库
        Connector.getDatabase();

    }

    public static Context getMyApplicationContext(){
        return mContext;
    }

    public static int getCollectionsCount(){
        return collectionsCount++;
    }

    public static int getCollectionsCountOnly(){
        return collectionsCount;
    }

    public static boolean getIsFromCollections(){
        return isFromCollections;
    }

    public static void setIsFromCollections(boolean b){
        isFromCollections = b;
    }

}
