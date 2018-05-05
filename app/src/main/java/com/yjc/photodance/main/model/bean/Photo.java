package com.yjc.photodance.main.model.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/5/4/004.
 */

public class Photo extends BmobObject{

    private String thumbUrl;
    private String regularUrl;
    private String rawUrl;
    private List<String> collection = new ArrayList<>();
    private List<String> like = new ArrayList<>();
    private String description;
    private String userProfileImage;
    private String username;
    private String uploadPhotoUrl;
    private Boolean isUpload;
    private String createTime;
    private String size;

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getRegularUrl() {
        return regularUrl;
    }

    public void setRegularUrl(String regularUrl) {
        this.regularUrl = regularUrl;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public List<String> getCollection() {
        return collection;
    }

    public List<String> getLike() {
        return like;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUploadPhotoUrl() {
        return uploadPhotoUrl;
    }

    public void setUploadPhotoUrl(String uploadPhotoUrl) {
        this.uploadPhotoUrl = uploadPhotoUrl;
    }

    public Boolean getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Boolean isUpload) {
        this.isUpload = isUpload;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
