package com.yjc.photodance.common.storage.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/4/21/021.
 */

public class Info extends DataSupport{

    private String phoneNum;
    private String username;
    private byte[] backgroundImage;
    private byte[] userHeadImage;
    private String base;
    private String sex;
    private String email;
    private String home;
    private String company;
    private String profession;
    private String signature;

    public Info(String phoneNum, String username){
        this.phoneNum = phoneNum;
        this.username = username;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public byte[] getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(byte[] mBackgroundImage) {
        this.backgroundImage = mBackgroundImage;
    }

    public byte[] getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(byte[] mUserHeadImage) {
        this.userHeadImage = mUserHeadImage;
    }

    public String getUsername() {
        return username;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String mBase) {
        this.base = mBase;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String mSex) {
        this.sex = mSex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mEmail) {
        this.email = mEmail;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String mHome) {
        this.home = mHome;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String mCompany) {
        this.company = mCompany;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String mProfession) {
        this.profession = mProfession;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String mSignature) {
        this.signature = mSignature;
    }
}
