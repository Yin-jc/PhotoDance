package com.yjc.photodance.main.presenter;

import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.main.model.IMainModel;
import com.yjc.photodance.main.view.IMainView;
import com.yjc.photodance.main.view.MainActivity;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public class MainPresenterImpl implements IMainPresenter{

    private IMainView mView;
    private IMainModel mModel;

    public MainPresenterImpl(IMainModel model){
        mModel = model;
    }

    @Override
    public void requestPhoto(PhotoAdapter adapter, int page, int size) {
        mModel.getPhoto(adapter, page, size);
    }

    @Override
    public void requestPhotoBySearch(PhotoAdapter adapter, String query) {
        mModel.getPhotoBySearch(adapter, query);
    }

    @Override
    public void requestVideo(ShortVideoAdapter adapter) {
        mModel.getVideo(adapter);

    }

    @Override
    public void requestLive() {

    }

    @Override
    public void requestMessage() {

    }
}
