package com.yjc.photodance.common.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.yjc.photodance.MyApplication;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/1/001.
 */

public class MultiMedia {

    private static Uri photoUri;
    private static final Context sContext = MyApplication.getMyApplicationContext();

    private static final int IMAGE_TYPE = 1;
    private static final int VIDEO_TYPE = 2;

    public static void takePhoto(){

        //创建File对象，用于存储照片
        //存放在当前应用缓存数据的位置，可以跳过权限验证
        // TODO: 2018/1/4/004 字符串拼接对多张图片命名 
        File photo=new File(MyApplication.getMyApplicationContext().getExternalCacheDir() ,
                "photo1.jpg");
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
            photoUri = FileProvider.getUriForFile(MyApplication.getMyApplicationContext(),
                    "com.yjc.photodance.fileprovider", photo);
        }else {
            photoUri= Uri.fromFile(photo);
        }

        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sContext.startActivity(intent);
    }

    /**
     * Android4.4之后选取图片之后不再返回真实Uri，需要对返回的封装后的Uri进行解析
     * @param data
     * @return
     */
    public static String handleFile(Intent data, int fileType) {
        String path = null;
        if (data != null) {
            Uri uri = data.getData();
            if (DocumentsContract.isDocumentUri(MyApplication.getMyApplicationContext(), uri)) {
                //如果是document类型的Uri，则通过document id处理
                String docuId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    //解析出数字格式的id
                    String id = docuId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    switch (fileType) {
                        case IMAGE_TYPE:
                            path = getFilePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    selection, fileType);
                            break;
                        case VIDEO_TYPE:
                            path = getFilePath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                    selection, fileType);
                            break;
                        default:
                            break;
                    }
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://" +
                            "downloads/public_downloads"), Long.valueOf(docuId));
                    path = getFilePath(contentUri, null, fileType);
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的Uri，直接获取图片路径即可
                path = uri.getPath();
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的Uri，则使用普通方式处理
                path = getFilePath(uri, null, fileType);
            }
        }
        return path;
    }

    private static String getFilePath(Uri uri, String selection, int fileType){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = MyApplication.getMyApplicationContext().getContentResolver()
                .query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                switch (fileType){
                    case IMAGE_TYPE:
                        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        break;
                    case VIDEO_TYPE:
                        path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        break;
                    default:
                        break;
                }
            }
            cursor.close();
        }
        return path;
    }
    
}
