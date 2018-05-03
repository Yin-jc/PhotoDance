package com.yjc.photodance.main.presenter;

import android.os.Handler;
import android.os.Message;

import com.yjc.photodance.account.model.IAccountManager;
import com.yjc.photodance.account.presenter.LoginPresenterImpl;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.SearchPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.main.model.IMainModel;
import com.yjc.photodance.main.view.IMainView;
import com.yjc.photodance.main.view.MainActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public class MainPresenterImpl implements IMainPresenter{

    private IMainView mView;
    private IMainModel mModel;

    public MainPresenterImpl(IMainView view, IMainModel model){
        mView = view;
        mModel = model;
        model.setHandler(new MyHandler(this));
    }

    private static class MyHandler extends Handler {
        WeakReference<MainPresenterImpl> refContext;

        public MyHandler(MainPresenterImpl context) {
            refContext=new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            MainPresenterImpl presenter=refContext.get();
            switch (msg.what){
                case IMainModel.UPDATE_PROGRESS:
                    presenter.mView.updateProgress((Integer) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void requestPhoto(PhotoAdapter adapter, int page, int size) {
        mModel.getPhoto(adapter, page, size);
    }

    @Override
    public void requestPhotoBySearch(SearchPhotoAdapter adapter, String query) {
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

    @Override
    public void requestUploadPhoto(String path) {
        mModel.uploadPhoto(path);
    }

    @Override
    public void requestUploadVideo(String path) {
        mModel.uploadVideo(path);
    }
}
