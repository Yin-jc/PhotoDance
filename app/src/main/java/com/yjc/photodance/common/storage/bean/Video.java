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
    private Object thumb;
    private String type;
    private String username;
    private BmobFile userimage;
    private Integer id;
    private List<String> like = new ArrayList<>();
    private List<String> comment = new ArrayList<>();


    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public Object getThumb() {
        return thumb;
    }

    public void setThumb(Object thumb) {
        this.thumb = thumb;
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
}
