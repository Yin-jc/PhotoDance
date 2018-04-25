package com.yjc.photodance.main.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BaseFragment;
import com.yjc.photodance.common.util.HandleBitmap;
import com.yjc.photodance.common.util.MultiMedia;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class UploadFragment extends BaseFragment {

    private static final int SELECT_PHOTO = 1;
    private static final int SELECT_VIDEO = 2;
    private String mPhotoPath;
    private Bitmap mSelectPhoto;
    private String mVideoPath;
    private Bitmap mSelectVideoFirstFrame;

    private ImageView mPhotoContainer;
    private ImageView mVideoContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPhotoContainer = getActivity().findViewById(R.id.photo_container);
        mVideoContainer = getActivity().findViewById(R.id.video_first_frame_container);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    mPhotoPath = MultiMedia.handleImage(data);
                    mSelectPhoto = HandleBitmap.compressForFile(mPhotoPath);
                    mPhotoContainer.setImageBitmap(mSelectPhoto);
                }
                break;
            case SELECT_VIDEO:
                //获取视频第一帧显示
                if(resultCode == RESULT_OK){
                    mVideoPath = MultiMedia.handleImage(data);
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    try {
                        retriever.setDataSource(mVideoPath);
                        mSelectVideoFirstFrame = retriever.getFrameAtTime();
                        mVideoContainer.setImageBitmap(mSelectVideoFirstFrame);
                    }catch (RuntimeException e){
                        e.printStackTrace();
                    }finally {
                        retriever.release();
                    }
                }
        }
    }
}
