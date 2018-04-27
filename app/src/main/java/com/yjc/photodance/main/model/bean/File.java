package com.yjc.photodance.main.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/4/27/027.
 */

public class File extends BmobObject {

    private BmobFile file;
    private BmobFile thumb;
    private String username;
    private String type;

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public BmobFile getThumb() {
        return thumb;
    }

    public void setThumb(BmobFile thumb) {
        this.thumb = thumb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
