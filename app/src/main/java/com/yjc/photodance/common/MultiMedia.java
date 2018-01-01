package com.yjc.photodance.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.yjc.photodance.main.MainActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/1/001.
 */

public class MultiMedia {

    private static Uri photoUri;

    public static void takePhoto(){

        //创建File对象，用于存储照片
        //存放在当前应用缓存数据的位置，可以跳过权限验证
        File photo=new File(MyApplicationContext.getMyApplicationContext().getExternalCacheDir() ,
                "photo.jpg");
        if(photo.exists()){
            photo.delete();
        }
        try {
            photo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Android 7.0 之后不可以直接使用Uri
        if(Build.VERSION.SDK_INT >= 24){
            photoUri = FileProvider.getUriForFile(MyApplicationContext.getMyApplicationContext(),
                    "com.yjc.photodance.fileprovider", photo);
        }else {
            photoUri= Uri.fromFile(photo);
        }

        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        MyApplicationContext.getMyApplicationContext().startActivity(intent);
    }
}
