package com.yjc.photodance.main.view.popupwindow;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BasePopupWindow;
import com.yjc.photodance.common.util.MultiMedia;
import com.yjc.photodance.main.view.MainActivity;
import com.yjc.photodance.main.view.fragment.UploadFragment;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class UploadPopupWindow extends BasePopupWindow {

    private ImageView mUploadPhoto;
    private ImageView mUploadVideo;
    private ImageView mDismiss;
    private UploadFragment fragment;

    private static final int SELECT_PHOTO = 1;
    private static final int SELECT_VIDEO = 2;

    public UploadPopupWindow(Context context, UploadFragment fragment) {
        super(context);
        this.fragment = fragment;
        init();
    }

    private void init(){

        mUploadPhoto = popupWindow.findViewById(R.id.upload_photo);
        mUploadVideo = popupWindow.findViewById(R.id.upload_video);
        mDismiss = popupWindow.findViewById(R.id.dismiss);


        mUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/4/25/025 上传video界面
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("video/*");
                fragment.startActivityForResult(intent, SELECT_VIDEO);
            }
        });

        mUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/4/25/025 上传photo界面
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                fragment.startActivityForResult(intent, SELECT_PHOTO);
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

    public void showImageView(){
        setEnterAnimation(mUploadVideo);
        setEnterAnimation(mUploadPhoto);
    }
}
