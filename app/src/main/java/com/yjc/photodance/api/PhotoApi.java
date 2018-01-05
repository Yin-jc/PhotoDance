package com.yjc.photodance.api;

import com.yjc.photodance.dao.Photo;
import com.yjc.photodance.dao.PhotoData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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

    @GET("data/福利/{size}/{page}")
    Observable<PhotoData> getPhotoData(@Path("size") int size, @Path("page") int page);

}
