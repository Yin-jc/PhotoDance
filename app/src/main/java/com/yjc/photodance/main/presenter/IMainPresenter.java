package com.yjc.photodance.main.presenter;

import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.SearchPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public interface IMainPresenter {

    void requestPhoto(PhotoAdapter adapter, int page, int size);

    void requestPhotoFromUser(PhotoAdapter adapter);

    void requestPhotoBySearch(SearchPhotoAdapter adapter, String query);

    void requestVideo(ShortVideoAdapter adapter);

    void requestLive();

    void requestMessage();

    void requestUploadPhoto(String path);

    void requestUploadVideo(String path);
}
