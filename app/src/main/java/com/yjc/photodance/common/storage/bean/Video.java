package com.yjc.photodance.common.storage.bean;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class Video extends BmobFile{

    private BmobFile file;
    private String thumb;
    private String type;

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
