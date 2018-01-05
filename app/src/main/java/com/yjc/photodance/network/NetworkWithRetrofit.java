package com.yjc.photodance.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yjc.photodance.api.PhotoApi;
import com.yjc.photodance.dao.Photo;
import com.yjc.photodance.dao.PhotoAdapter;
import com.yjc.photodance.dao.PhotoData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public class NetworkWithRetrofit {

    // TODO: 2018/1/5/005 做一个单例

    public static final String BASE_URL = "http://gank.io/api/";

    public NetworkWithRetrofit() {

        //创建Retrofit实例，配置接口和转换器
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //获取接口实例
        PhotoApi photoApi  = retrofit.create(PhotoApi.class);

//        //调用方法得到一个Call
//        Call<PhotoData> call = photoApi.getPhotoData(20,1);
//
//        //进行网络异步请求
//        call.enqueue(new Callback<PhotoData>() {
//            @Override
//            public void onResponse(@NonNull Call<PhotoData> call, @NonNull Response<PhotoData> response) {
//                PhotoData data = response.body();
//                Log.d("NetworkWithRetrofit", data.toString());
//
//            }
//            @Override
//            public void onFailure(Call<PhotoData> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

    }
}
