package com.yjc.photodance.account.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2018/4/13/013.
 */

public class User extends BmobUser{

    //已有属性 用户名、密码、邮箱、手机号码

    //注意此处定义的属性一定要和表中的列对应好

    private BmobFile file;
    private String type;
    private BmobFile thumb;
    private String expired;

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BmobFile getThumb() {
        return thumb;
    }

    public void setThumb(BmobFile thumb) {
        this.thumb = thumb;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
