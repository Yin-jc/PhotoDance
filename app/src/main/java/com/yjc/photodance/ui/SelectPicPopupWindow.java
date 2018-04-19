package com.yjc.photodance.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BasePopupWindow;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class SelectPicPopupWindow extends BasePopupWindow {

    private Button takePhoto, selectPhoto, cancel;
    private View popupWindow;

    public SelectPicPopupWindow(Context context, View.OnClickListener itemsOnClick) {
        super(context);

        takePhoto = popupWindow.findViewById(R.id.take_photo);
        selectPhoto = popupWindow.findViewById(R.id.select_photo);
        cancel = popupWindow.findViewById(R.id.cancel);

        //取消按钮
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });

        //设置按钮监听
        takePhoto.setOnClickListener(itemsOnClick);
        selectPhoto.setOnClickListener(itemsOnClick);

    }

    @Override
    protected int getPopupWindowWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_window_bottom;
    }
}
