package com.yjc.photodance.api;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public class ApiConfig {

//    private static final String API_KEY = "8bbb39fe13ce4ae8920b762bd0048aa7";
//    private static final String BASE_URL = "https://api.flickr.com/services/";
//    private static final String BASE_URL = "http://gank.io/api/";
    private static final String BASE_URL =
        "https://api.unsplash.com/";
    private static final String Application_ID =
            "0535d74bb5e01f81938fc7e4156e801f9d06cebb3117b9e207ee5ff5d3f66abd";

//    public static String getApiKey(){
//        return API_KEY;
//    }

    public static String getApplication_ID(){
        return Application_ID;
    }

    public static String getBaseUrl(){
        return BASE_URL;
    }
}
