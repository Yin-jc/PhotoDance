package com.yjc.photodance.dao;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class Photo {

//    @SerializedName("_id")
    private String id;

//    @SerializedName("url")
    private String photoUrl;

    public Photo(String id, String photoUrl) {
        this.id = id;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
