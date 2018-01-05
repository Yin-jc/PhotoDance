package com.yjc.photodance.api;

import com.yjc.photodance.dao.PhotoData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
//import rx.Observable;

/**
 * Created by Administrator on 2018/1/5/005.
 * API接口
 */

public interface PhotoApi {

    @GET("data/福利/{size}/{page}")
    Observable<PhotoData> getPhotoData(@Path("size") int size, @Path("page") int page);

}
