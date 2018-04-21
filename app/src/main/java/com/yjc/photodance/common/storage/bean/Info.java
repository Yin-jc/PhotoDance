package com.yjc.photodance.common.storage.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/4/21/021.
 */

public class Info extends DataSupport{

    private String mPhoneNum;
    private String mUsername;
    private byte[] mBackgroundImage;
    private byte[] mUserHeadImage;
    private String mBase;
    private String mSex;
    private String mEmail;
    private String mHome;
    private String mCompany;
    private String mProfession;
    private String mSignature;

    public Info(String phoneNum, String username){
        mPhoneNum = phoneNum;
        mUsername = username;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    public byte[] getBackgroundImage() {
        return mBackgroundImage;
    }

    public void setBackgroundImage(byte[] mBackgroundImage) {
        this.mBackgroundImage = mBackgroundImage;
    }

    public byte[] getUserHeadImage() {
        return mUserHeadImage;
    }

    public void setUserHeadImage(byte[] mUserHeadImage) {
        this.mUserHeadImage = mUserHeadImage;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getBase() {
        return mBase;
    }

    public void setBase(String mBase) {
        this.mBase = mBase;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String mSex) {
        this.mSex = mSex;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getHome() {
        return mHome;
    }

    public void setHome(String mHome) {
        this.mHome = mHome;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getProfession() {
        return mProfession;
    }

    public void setProfession(String mProfession) {
        this.mProfession = mProfession;
    }

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String mSignature) {
        this.mSignature = mSignature;
    }
}
