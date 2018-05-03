package com.yjc.photodance.main.model;

import android.os.Handler;

import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.SearchPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public interface IMainModel {

    int UPDATE_PROGRESS = 1;

    /**
     * 请求照片
     * @param adapter
     * @param page
     * @param size
     */
    void getPhoto(PhotoAdapter adapter ,int page, int size);

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

    void uploadPhoto(String path);

    void uploadVideo(String path);

    void setHandler(Handler mHandler);

}
