package com.yjc.photodance.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BasePopupWindow;

/**
 * Created by Administrator on 2018/4/19/019.
 */

public class TopPopupWindow extends BasePopupWindow{

    private Button takePhoto, search, upload;
    private View popupWindow;

    public TopPopupWindow(Context context) {
        super(context);

        takePhoto = popupWindow.findViewById(R.id.top_take_photo);
        search = popupWindow.findViewById(R.id.top_search);
        upload = popupWindow.findViewById(R.id.top_upload);

        //设置按钮监听
    }

    @Override
    protected int getPopupWindowWidth() {
        return getWidth()/4;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_window_top;
    }
}
