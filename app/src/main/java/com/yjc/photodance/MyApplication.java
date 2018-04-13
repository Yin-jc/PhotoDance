package com.yjc.photodance;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.yjc.photodance.common.storage.SharedPreferenceDao;
import com.yjc.photodance.model.Account;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.lang.reflect.Field;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/12/29/029.
 * 获取全局Context
 * 务必记得此Application要在AndroidManifest中处理
 */

public class MyApplication extends Application {

    // TODO: 2018/1/7/007 实现成单例
    private static Context mContext;
    private static int collectionsCount = 1;
    private static boolean isFromCollections = false;
    private Typeface typeFace;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bmob后端云
        Bmob.initialize(this, "957299827f3a40ea662ad5891f81c247");

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

        setTypeface();

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

    public void setTypeface(){
        //华文彩云，加载外部字体assets/front/...
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/方正华隶.ttf");
        try {
            //与values/styles.xml中的<item name="android:typeface">sans</item>对应
//            Field field = Typeface.class.getDeclaredField("SERIF");
//            field.setAccessible(true);
//            field.set(null, typeFace);

//            Field field_1 = Typeface.class.getDeclaredField("DEFAULT");
//            field_1.setAccessible(true);
//            field_1.set(null, typeFace);

            //与monospace对应
//            Field field_2 = Typeface.class.getDeclaredField("MONOSPACE");
//            field_2.setAccessible(true);
//            field_2.set(null, typeFace);

            //与values/styles.xml中的<item name="android:typeface">sans</item>对应
            Field field_3 = Typeface.class.getDeclaredField("SANS_SERIF");
            field_3.setAccessible(true);
            field_3.set(null, typeFace);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
