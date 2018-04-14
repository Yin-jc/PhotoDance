package com.yjc.photodance.common.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yjc.photodance.common.http.api.ApiConfig;
import com.yjc.photodance.common.http.api.PhotoApi;

import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public class NetworkWithRetrofit {

    // TODO: 2018/1/5/005 做一个单例



    public NetworkWithRetrofit() {

        //创建Retrofit实例，配置接口和转换器
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.getBaseUrl())
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
