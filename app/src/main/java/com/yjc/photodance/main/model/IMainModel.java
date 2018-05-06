package com.yjc.photodance.main.model;

import android.graphics.Bitmap;
import android.os.Handler;

import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.SearchPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.main.model.bean.Photo;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public interface IMainModel {

    int UPDATE_PROGRESS = 1;
    int UPLOAD_SUC = 2;
    int UPLOAD_FTRSTFRAME_SUC = 3;
    int UPLOAD_VIDEO_SUC = 4;
    int CAN_SAVE_VIDEO = 5;

    /**
     * 请求照片
     * @param adapter
     * @param page
     * @param size
     */
    void getPhoto(PhotoAdapter adapter ,int page, int size, boolean flag, boolean isFirstEnter);

    /**
     * 通过搜索请求照片
     * @param search
     * @param adapter
     */
    void getPhotoBySearch(SearchPhotoAdapter adapter, String search);

    void getVideo(ShortVideoAdapter adapter);

    void getLive();

    void getMessage();

    void setAdapter();

    void uploadPhoto(String path, boolean isFromSD);

    void uploadVideo(String path);

    void setHandler(Handler mHandler);

    void getUploadPhoto(PhotoAdapter adapter);

    void uploadUserProfileImage(Bitmap bitmap);

}
