package com.yjc.photodance.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.yjc.photodance.R;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class SelectPicPopupWindow extends PopupWindow {

    private Button takePhoto, selectPhoto, cancel;
    private View popupWindow;

    public SelectPicPopupWindow(Context context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupWindow = inflater.inflate(R.layout.popup_window_layout, null);

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

        //设置SelectPicPopupWindow的View
        this.setContentView(popupWindow);

        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

//        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);

        //实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0x00000000);

        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //popupWindow添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        popupWindow.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = popupWindow.findViewById(R.id.popup_window).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
