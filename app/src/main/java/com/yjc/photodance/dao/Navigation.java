package com.yjc.photodance.dao;

/**
 * Created by Administrator on 2018/1/4/004.
 * Navigation的Bean类
 */

public class Navigation {

    private String title;
    private int imageId;

    public Navigation(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
