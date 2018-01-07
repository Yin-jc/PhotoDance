package com.yjc.photodance;

import android.app.Application;
import android.content.Context;

import com.yjc.photodance.common.SharedPreferenceDao;
import com.yjc.photodance.dao.Account;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/**
 * Created by Administrator on 2017/12/29/029.
 * 获取全局Context
 * 务必记得此Application要在AndroidManifest中处理
 */

public class MyApplication extends Application {

    // TODO: 2018/1/7/007 实现成单例
    private static Context mContext;
    private static int collectionsCount = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePal.initialize(this);
        //创建数据库
        Connector.getDatabase();

        Account account = new Account();
        account.setUserName("0");
        account.setPassword("0");
        account.setLogin(false);
        account.setRegister(false);
        account.save();

        SharedPreferenceDao.getInstance().saveString("collectionUrl0", "temp");

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
}
