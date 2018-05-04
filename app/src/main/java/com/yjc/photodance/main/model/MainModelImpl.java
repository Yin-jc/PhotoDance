package com.yjc.photodance.main.model;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mabeijianxi.smallvideorecord2.LocalMediaCompress;
import com.mabeijianxi.smallvideorecord2.model.AutoVBRMode;
import com.mabeijianxi.smallvideorecord2.model.LocalMediaConfig;
import com.mabeijianxi.smallvideorecord2.model.OnlyCompressOverBean;
import com.yjc.photodance.account.model.bean.User;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.SearchPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.common.jsonBean.LatestPhoto;
import com.yjc.photodance.common.jsonBean.searchBean.SearchPhoto;
import com.yjc.photodance.common.http.api.ApiConfig;
import com.yjc.photodance.common.http.api.PhotoApi;
import com.yjc.photodance.common.network.RetrofitServiceManager;
import com.yjc.photodance.common.storage.bean.Video;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/16/016.
 */

public class MainModelImpl implements IMainModel {

    private static final String TAG = "MainModelImpl";

    private String mPhotoPath;
    private String mVideoPath;

    private String mPhotoPathAfterUpload;
    private String mVideoPathAfterUpload;

    private Handler mHandler;

    private String[] info;

    @Override
    public void getPhoto(final PhotoAdapter adapter, int page, int size) {
        RetrofitServiceManager.getInstance().create(PhotoApi.class).getPhotos(ApiConfig.getApplication_ID(),
                String.valueOf(page), String.valueOf(size))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LatestPhoto>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LatestPhoto> photos) {
//                        if(isFirstEnter || !flag) {
//                            isFirstEnter = false;
                            //下拉刷新，防止添加重复数据
                            adapter.setPhotos(photos);
                            adapter.notifyDataSetChanged();
//                        }
                        Log.d("MainActivity", "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("MainActivity", "onComplete");
                    }
                });
    }

    @Override
    public void getPhotoBySearch(final SearchPhotoAdapter adapter, String search) {
        RetrofitServiceManager.getInstance().create(PhotoApi.class).getPhotosBySearch(
                ApiConfig.getApplication_ID(), search, String.valueOf(1), String.valueOf(30))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchPhoto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchPhoto photo) {
                        Log.d(TAG, "onNext: ");
                        adapter.setSearchPhotos(photo.getResults());
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("search", "onComplete");
                    }
                });
    }

    @Override
    public void getVideo(final ShortVideoAdapter adapter) {
        BmobQuery<Video> query = new BmobQuery<>();
        query.findObjects(new FindListener<Video>() {
            @Override
            public void done(List<Video> videos, BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: 查询成功");
                    adapter.setVideos(videos);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void getLive() {

    }

    @Override
    public void getMessage() {

    }

    @Override
    public void setAdapter() {

    }

    @Override
    public void uploadPhoto(String path) {
        Log.d(TAG, "uploadPhoto: " + path);
        //需要对path进行截取，默认传过来的带有file://前缀
        final BmobFile file = new BmobFile(new File(path.substring(7)));
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    mPhotoPathAfterUpload = file.getFileUrl();
                    Log.d(TAG, "done: " + "上传成功" + mPhotoPathAfterUpload);
                    savePhoto(mPhotoPathAfterUpload);
                }else {
                    Log.d(TAG, "done: " + "上传失败" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                Message message = Message.obtain(mHandler, UPDATE_PROGRESS, value);
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    public void uploadVideo(String path) {
        Log.d(TAG, "uploadVideo: " + path);
        info = getVideoInfo(path);
        Log.d(TAG, "uploadVideo: " + info[0] + " " + info[1]);
        new MyTask().execute(path);
    }

    @Override
    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void getUploadPhoto(final PhotoAdapter adapter) {
        BmobQuery<com.yjc.photodance.main.model.bean.Photo> query = new BmobQuery<>();
        query.addWhereEqualTo("isUpload", true);
        query.findObjects(new FindListener<com.yjc.photodance.main.model.bean.Photo>() {
            @Override
            public void done(List<com.yjc.photodance.main.model.bean.Photo> photos, BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: 查询成功");
                    Log.d(TAG, "done: " + photos.size());
                    adapter.setUploadPhotos(photos);
                }else {
                    Log.d(TAG, "done: 查询失败");
                }
            }
        });
    }

    class MyTask extends AsyncTask<String, Integer, String>{

        private OnlyCompressOverBean onlyCompressOverBean;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ");
            mVideoPath = null;
        }

        @Override
        protected String doInBackground(String... path) {
            Log.d(TAG, "doInBackground: ");
            // 选择本地视频压缩
            LocalMediaConfig.Buidler builder = new LocalMediaConfig.Buidler();
            LocalMediaConfig config = builder
                    .setVideoPath(path[0])
                    .captureThumbnailsTime(1)
                    .doH264Compress(new AutoVBRMode())
                    .setFramerate(15)
                    .setScale(1.0f)
                    .build();
            onlyCompressOverBean = new LocalMediaCompress(config).startCompress();
            if(onlyCompressOverBean.isSucceed()){
                Log.d(TAG, "doInBackground: " + onlyCompressOverBean.getVideoPath());
                return onlyCompressOverBean.getVideoPath();
            }
            return mVideoPath;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "onProgressUpdate: ");
//            Message message = Message.obtain(mHandler, UPDATE_PROGRESS, values);
//            mHandler.sendMessage(message);
        }

        @Override
        protected void onPostExecute(String path) {
            Log.d(TAG, "onPostExecute: ");
            Bitmap firstFrame = getVideoFirstFrame(path);
            Log.d(TAG, "onPostExecute: " + firstFrame);
            uploadVideoAfterCompress(path, firstFrame);
        }
    }

    private void uploadVideoAfterCompress(String path, final Bitmap firstFrame){

        final BmobFile file = new BmobFile(new File(path));
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    mVideoPathAfterUpload = file.getFileUrl();
                    Log.d(TAG, "done: " + "上传成功" + mVideoPathAfterUpload);
                    Log.d(TAG, "done: " + User.getCurrentUser().getMobilePhoneNumber());
                    saveVideo(file, firstFrame);
                }else {
                    Log.d(TAG, "done: " + "上传失败" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                Message message = Message.obtain(mHandler, UPDATE_PROGRESS, value);
                mHandler.sendMessage(message);
            }
        });
    }

    private void savePhoto(String url){
        BmobUser user = User.getCurrentUser();
        String username = user.getUsername();
        com.yjc.photodance.main.model.bean.Photo photo =
                new com.yjc.photodance.main.model.bean.Photo();
        photo.setUsername(username);
        photo.setUploadPhotoUrl(url);
        photo.setIsUpload(true);
        // TODO: 2018/5/4/004 上传用户头像
        photo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: " + "保存成功");
                }else {
                    Log.d(TAG, "done: " + "保存失败" + e.getMessage());
                }
            }
        });
//        newUser.update(user.getObjectId(), new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null){
//                    Log.d("bmob", "done: " + "保存成功");
//                }else {
//                    Log.d("bmob", "done: " + "保存失败" + e.getMessage());
//                }
//            }
//        });
    }

    private void saveVideo(BmobFile file, Bitmap firstFrame){
        BmobUser user = User.getCurrentUser();
        String username = user.getUsername();

        Video video = new Video();
        video.setFile(file);
        video.setUsername(username);
        video.setThumb(firstFrame);
        // width >= height
        if(Integer.parseInt(info[0]) >= Integer.parseInt(info[1])){
            video.setType("TYPE_LIST");
        }else {
            video.setType("TYPE_STAGGERED");
        }

        video.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: 保存成功");
                }else {
                    Log.d(TAG, "done: 保存失败");
                }
            }
        });
//        newUser.update(user.getObjectId(), new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null){
//                    Log.d("bmob", "done: " + "保存成功");
//                }else {
//                    Log.d("bmob", "done: " + "保存失败" + e.getMessage());
//                }
//            }
//        });
    }

    /**
     * 获得视频的尺寸
     * @param path
     * @return
     */
    private String[] getVideoInfo(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        String width = "";
        String height = "";
        try {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) " +
                        "AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
            retriever.setDataSource(path, headers);
            width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            retriever.release();
        }
        if (!width.equals("") && !height.equals("")){
            return new String[]{width, height};
        }
        Log.d(TAG, "getVideoInfo: " + width + " " + height);
        return new String[]{"500", "300"};
    }

    private Bitmap getVideoFirstFrame(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            return retriever.getFrameAtTime();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            retriever.release();
        }
        return null;
    }

}
