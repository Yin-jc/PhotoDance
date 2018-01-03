package com.yjc.photodance.dao;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/1/3/003.
 */

public class Account extends DataSupport{

    private String userName;
    private String password;
    private Bitmap userHeadImage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(Bitmap userHeadImage) {
        this.userHeadImage = userHeadImage;
    }
}
