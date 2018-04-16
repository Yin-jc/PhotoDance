package com.yjc.photodance.main.model;

import com.yjc.photodance.adapter.PhotoAdapter;

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

    void getShortVideo();

    void getLive();

    void getMessage();

    void setAdapter();
}
