package com.yjc.photodance.common.storage.bean;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class Video extends BmobObject{

    private BmobFile file;
    private Bitmap thumb;
    private String type;
    private String username;
    private BmobFile userimage;
    private Integer like = 0;
    private Integer comment = 0;
    private Integer id;


    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
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

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
