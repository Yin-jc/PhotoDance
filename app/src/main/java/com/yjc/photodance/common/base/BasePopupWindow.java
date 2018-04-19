package com.yjc.photodance.common.base;

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
 * Created by Administrator on 2018/4/19/019.
 */

public abstract class BasePopupWindow extends PopupWindow {

    private View popupWindow;

    public BasePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            popupWindow = inflater.inflate(getLayoutId(), null);
        }

        //设置SelectPicPopupWindow的View
        setContentView(popupWindow);

        //设置SelectPicPopupWindow弹出窗体的宽
        setWidth(getPopupWindowWidth());
        //设置SelectPicPopupWindow弹出窗体的高
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.popupWindow_translate);

        //实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0x00000000);

        //设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);

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

    protected abstract int getPopupWindowWidth();

    protected abstract int getLayoutId();

}
