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
    private boolean isLogin;
    private boolean isRegister;

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

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }
}
