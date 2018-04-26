package com.yjc.photodance.main.view.popupwindow;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BasePopupWindow;
import com.yjc.photodance.main.view.MainActivity;
import com.yjc.photodance.main.view.fragment.UploadFragment;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class UploadPopupWindow extends BasePopupWindow {

    private static final String TAG = "UploadPopupWindow";
    private ImageView mUploadPhotoFromSD;
    private ImageView mUploadVideoFromSD;
    private ImageView mUploadPhotoByCamera;
    private ImageView mUploadVideoByCamera;
    private ImageView mDismiss;
    private UploadFragment mFragment;
    private Context mContext;

    private static final int SELECT_PHOTO = 1;
    private static final int SELECT_VIDEO = 2;


    public UploadPopupWindow(Context context, UploadFragment fragment) {
        super(context);
        mContext = context;
        mFragment = fragment;
        init();
    }

    private void init(){

        mUploadPhotoFromSD = popupWindow.findViewById(R.id.upload_photo_from_SD);
        mUploadVideoFromSD = popupWindow.findViewById(R.id.upload_video_from_SD);
        mUploadPhotoByCamera = popupWindow.findViewById(R.id.upload_photo_by_camera);
        mUploadVideoByCamera = popupWindow.findViewById(R.id.upload_video_by_camera);
        mDismiss = popupWindow.findViewById(R.id.dismiss);


        mUploadVideoFromSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/4/25/025 上传video界面
                dismiss();
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("video/*");
                mFragment.startActivityForResult(intent, SELECT_VIDEO);
            }
        });

        mUploadPhotoFromSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                // TODO: 2018/4/25/025 上传photo界面
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                mFragment.startActivityForResult(intent, SELECT_PHOTO);
            }
        });

        mUploadPhotoByCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                //拍照之后照片要回传到哪个界面，就在哪个界面调用相机
                mFragment.takePhoto();
            }
        });

        mUploadVideoByCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                // TODO: 2018/4/26/026
                // 录制
                mFragment.recordVideo();
//                MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
//
//                        .fullScreen(false)
//                        .smallVideoWidth(360)
//                        .smallVideoHeight(480)
//                        .recordTimeMax(6000)
//                        .recordTimeMin(1500)
//                        .maxFrameRate(20)
//                        .videoBitrate(600000)
//                        .captureThumbnailsTime(1)
//                        .build();
//                MediaRecorderActivity
//                        .goSmallVideoRecorder((Activity) mContext,
//                                MainActivity.class.getName(), config);
            }
        });

        mDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 1.0f;
//                getWindow().setAttributes(lp);
            }
        });
    }


    @Override
    protected int getPopupWindowWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_window_upload;
    }

    private void setEnterAnimation(ImageView imageView){

        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(imageView,
                "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(imageView,
                "scaleY", 0.0f, 1.0f);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(imageView,
                "translationY", 1.0f, 0.0f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleXAnim, scaleYAnim, transYAnim);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(300);
        set.start();

    }

    private void setExitAnimation(ImageView imageView){

        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(imageView,
                "scaleX", 1.0f, 0.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(imageView,
                "scaleY", 1.0f, 0.0f);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(imageView,
                "translationY", 0.0f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleXAnim, scaleYAnim, transYAnim);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(3000);
        set.start();

    }

}
