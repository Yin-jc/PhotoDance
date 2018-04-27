package com.yjc.photodance.common.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.yjc.photodance.R;
import com.yjc.photodance.main.view.popupwindow.UploadPopupWindow;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/19/019.
 */

public abstract class BasePopupWindow extends PopupWindow {

    private static final String TAG = "BasePopupWindow";
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
        setHeight(getPopupWindowHeight());

        //设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.PopupWindowAnimation);

        //popupWindow添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        popupWindow.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = popupWindow.getTop();
//                Log.d(TAG, "onTouch: " + height);
//                int y=(int) event.getY();
//                Log.d(TAG, "onTouch: " + y);
//                if(event.getAction()==MotionEvent.ACTION_UP){
//                    if(y<height){
//                        try {
//                            Log.d(TAG, "onTouch: ");
//                            Class c = Class.forName
//                                    ("com.yjc.photodance.main.view.popupwindow.UploadPopupWindow");
//                            Method m = c.getDeclaredMethod("setExitAnimation");
//                            Field f = c.getDeclaredField("mDismiss");
//                            m.invoke(f, 0);
//                        } catch (ClassNotFoundException | NoSuchMethodException
//                                | IllegalAccessException | InvocationTargetException
//                                | NoSuchFieldException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                return true;
//            }
//        });

    }

    protected abstract int getPopupWindowWidth();

    protected abstract int getPopupWindowHeight();

    protected abstract int getLayoutId();

}
