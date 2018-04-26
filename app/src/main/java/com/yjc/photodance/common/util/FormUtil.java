package com.yjc.photodance.common.util;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/1/001.
 */

public class FormUtil {

    public static boolean checkMobile(String mobile){
        String regex="(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }
}
