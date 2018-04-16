package com.yjc.photodance.main.presenter;

import com.yjc.photodance.adapter.PhotoAdapter;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public interface IMainPresenter {

    void requestPhoto(PhotoAdapter adapter, int page, int size);

    void requestShortVideo();

    void requestLive();

    void requestMessage();
}
