package com.yjc.photodance.api;

import com.yjc.photodance.bean.Photo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
//import rx.Observable;

/**
 * Created by Administrator on 2018/1/5/005.
 * API接口
 */

public interface PhotoApi {

//    @GET("rest")
//    Observable<PhotoData> getPhotoData(@Query("method") String method,
//                                       @Query("api_key") String api_key,
//                                       @Query("format") String format,
//                                       @Query("nojsoncallback") String nojsoncallback);

//    @GET("data/福利/{size}/{page}")
//    Observable<PhotoData> getPhotoData(@Path("size") int size, @Path("page") int page);

    //https://api.unsplash.com/photos/?client_id=0535d74bb5e01f81938fc7e4156e801f9d06cebb3117b9e207ee5ff5d3f66abd

    @GET("photos")
    Observable<List<Photo>> getPhotoData(@Query("client_id") String client_id,
                                       @Query("page") String page,
                                       @Query("per_page") String per_page);

//    @GET("/photos")
//    Observable<List<Photo>> getPhotoData(@Query("client_id") String client_id);
}
