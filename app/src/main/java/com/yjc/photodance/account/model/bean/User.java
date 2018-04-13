package com.yjc.photodance.account.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2018/4/13/013.
 */

public class User extends BmobObject{

    private String phoneNumber;
    private String username;
    private String password;
    private String expired;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
