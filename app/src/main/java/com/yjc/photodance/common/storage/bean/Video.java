package com.yjc.photodance.common.storage.bean;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class Video extends BmobObject{

    private BmobFile file;
    private String thumbUpload;
    private String type;
    private String username;
    private BmobFile userimage;
    private Integer id;
    private List<String> like = new ArrayList<>();
    private List<String> comment = new ArrayList<>();
    private BmobFile thumbInternet;
    private Boolean isUpload;
    private String size;
    private String createTime;

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String getThumbUpload() {
        return thumbUpload;
    }

    public void setThumbUpload(String thumb) {
        this.thumbUpload = thumb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BmobFile getUserimage() {
        return userimage;
    }

    public void setUserimage(BmobFile userimage) {
        this.userimage = userimage;
    }

    public List<String> getLike() {
        return like;
    }

    public void setLike(List<String> like) {
        this.like = like;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BmobFile getThumbInternet() {
        return thumbInternet;
    }

    public void setThumbInternet(BmobFile thumbInternet) {
        this.thumbInternet = thumbInternet;
    }

    public Boolean getUpload() {
        return isUpload;
    }

    public void setUpload(Boolean upload) {
        isUpload = upload;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
