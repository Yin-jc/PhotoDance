package com.yjc.photodance;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;

import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.yjc.photodance.common.storage.SharedPreferenceDao;
import com.yjc.photodance.model.Account;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import java.io.File;
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

        SharedPreferenceDao.getInstance().saveString("tokenValid",
                String.valueOf(System.currentTimeMillis()));

        LitePal.initialize(this);
        //创建数据库
        Connector.getDatabase();

        //初始化小视频录制
        initSmallVideo();

    }

    public static Context getMyApplicationContext(){
        return mContext;
    }

    private void initSmallVideo() {
        // 设置拍摄视频缓存路径
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 初始化拍摄，遇到问题可选择开启此标记，以方便生成日志
        JianXiCamera.initialize(false,null);
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
