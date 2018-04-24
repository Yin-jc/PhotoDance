package com.yjc.photodance.main.model;

import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public interface IMainModel {

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
    void getPhotoBySearch(PhotoAdapter adapter, String search);

    void getVideo(ShortVideoAdapter adapter);

    void getLive();

    void getMessage();

    void setAdapter();
}
