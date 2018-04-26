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

    public View popupWindow;

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
        setAnimationStyle(R.style.PopupWindowAnimation);

        //popupWindow添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        popupWindow.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = popupWindow.getTop();
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
