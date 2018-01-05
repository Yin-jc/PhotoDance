package com.yjc.photodance.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yjc.photodance.dao.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class NetworkWithOkHttp {

    public static List<Photo> mPhotos;

    public static List<Photo> NetworkRequest(){

        //异步请求
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String resData = response.body().string();
//
////                Gson gson = new Gson();
////                mPhotos = gson.fromJson(resData, new TypeToken<List<Photo>>(){}.getType());
//                try {
//                    JSONArray jsonArray = new JSONArray(resData);
//                    for (int i=0; i<jsonArray.length(); i++){
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String id = jsonObject.getString("_id");
//                        String url = jsonObject.getString("url");
//                        mPhotos.add(new Photo(id, url));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
        //同步请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String chineseEncodeStr = java.net.URLEncoder.encode("福利", "UTF-8");
                    String chineseDecodeStr = java.net.URLDecoder.decode(chineseEncodeStr, "UTF-8");
                    Request request = new Request.Builder()
                            .url("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg")
                            .build();
                    Response response = client.newCall(request).execute();
                    String resData = response.body().string();
                    Log.d("NetWork", resData);
                    JSONArray jsonArray = new JSONArray(resData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("_id");
                        String url = jsonObject.getString("url");
//                        mPhotos.add(new Photo(id, url));
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return mPhotos;
    }

}
