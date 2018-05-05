package com.yjc.photodance.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yjc.photodance.common.storage.bean.Info;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/5/5/005.
 */

public class DBUtil {

    public static Bitmap queryUserProfileImage(){
        List<Info> infos = DataSupport.select("userHeadImage")
                .where("username = ?", BmobUser.getCurrentUser().getUsername())
                .find(Info.class);
        if (infos.size() != 0){
            byte[] bytes = infos.get(0).getUserHeadImage();
            return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);
        }
        return null;
    }
}
