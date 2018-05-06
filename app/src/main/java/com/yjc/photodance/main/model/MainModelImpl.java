package com.yjc.photodance.main.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.mabeijianxi.smallvideorecord2.LocalMediaCompress;
import com.mabeijianxi.smallvideorecord2.model.AutoVBRMode;
import com.mabeijianxi.smallvideorecord2.model.LocalMediaConfig;
import com.mabeijianxi.smallvideorecord2.model.OnlyCompressOverBean;
import com.yjc.photodance.MyApplication;
import com.yjc.photodance.account.model.bean.User;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.SearchPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.common.jsonBean.LatestPhoto;
import com.yjc.photodance.common.jsonBean.searchBean.SearchPhoto;
import com.yjc.photodance.common.http.api.ApiConfig;
import com.yjc.photodance.common.http.api.PhotoApi;
import com.yjc.photodance.common.network.RetrofitServiceManager;
import com.yjc.photodance.common.storage.SharedPreferenceDao;
import com.yjc.photodance.common.storage.bean.Video;
import com.yjc.photodance.common.util.DBUtil;
import com.yjc.photodance.common.util.ToastUtil;
import com.yjc.photodance.main.model.bean.Photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
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

    private Handler mHandler;


    /**
     * 获取图片
     * @param adapter
     * @param page
     * @param size
     * @param flag 上滑还是下拉
     * @param isNeedLoadData 是否需要加载数据
     */
    @Override
    public void getPhoto(final PhotoAdapter adapter, int page, int size, final boolean flag,
                         final boolean isNeedLoadData) {
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
                        if(isNeedLoadData || ! flag) {
                            //下拉刷新，防止添加重复数据
                            SharedPreferenceDao.getInstance().saveBoolean("needLoadData", false);
                            adapter.setPhotos(photos);
                            adapter.notifyDataSetChanged();
                        }
                        Log.d("MainActivity", "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("MainActivity", "onComplete");
                        //上传头像
//                        String imagePath = saveBitmapToSD(DBUtil.queryUserProfileImage(),
//                                "USER_PROFILE_IMAGE");
                    }
                });
    }

    /**
     * 获取图片（搜索）
     * @param adapter
     * @param search
     */
    @Override
    public void getPhotoBySearch(final SearchPhotoAdapter adapter, String search) {
        RetrofitServiceManager.getInstance().create(PhotoApi.class).getPhotosBySearch(
                ApiConfig.getApplication_ID(), search, String.valueOf(1), String.valueOf(50))
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

    /**
     * 获取视频
     * @param adapter
     */
    @Override
    public void getVideo(final ShortVideoAdapter adapter) {
        BmobQuery<Video> query = new BmobQuery<>();
        //按id升序排列
        query.order("id");
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

    /**
     * 上传图片
     * @param path 图片路径
     * @param isFromSD 本地相册上传还是拍摄上传
     */
    @Override
    public void uploadPhoto(String path, boolean isFromSD) {
        Log.d(TAG, "uploadPhoto: " + path);

        final int[] size;
        final String filePath;

        if (isFromSD){
            //不做处理
            filePath = path;
            size = getPhotoSize(path);
        }else {
            //需要对path进行截取，默认传过来的带有file://前缀
            filePath = path.substring(7);
            size = getPhotoSize(path.substring(7));
        }

//        if (userProfileImageUrl != null){
//            //已经上传过头像
//            file.upload(new UploadFileListener() {
//                @Override
//                public void done(BmobException e) {
//                    if(e == null){
//                        //此处并不直接将文件上传，为了在加载图片时更方便
//                        savePhoto(file.getFileUrl(), userProfileImageUrl, size);
//                    }else {
//                        Log.d(TAG, "done: " + "上传失败" + e.getMessage());
//                    }
//                }
//
//                @Override
//                public void onProgress(Integer value) {
//                    Message message = Message.obtain(mHandler, UPDATE_PROGRESS, value);
//                    mHandler.sendMessage(message);
//                }
//            });
//        }else {
            //没有上传过头像
            String imagePath =
                    saveBitmapToSD(DBUtil.queryUserProfileImage(), "USER_PROFILE_IMAGE");

            final String[] filePaths = new String[]{filePath, imagePath};
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    if(urls.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                        savePhoto(urls.get(0), urls.get(1), size);
                    }
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                    //curIndex--表示当前第几个文件正在上传
                    //curPercent--表示当前上传文件的进度值（百分比）
                    //total--表示总的上传文件数
                    //totalPercent--表示总的上传进度（百分比）
                    Message message = Message.obtain(mHandler, UPDATE_PROGRESS, totalPercent);
                    mHandler.sendMessage(message);
                }

                @Override
                public void onError(int statusCode, String errorMessage) {
                    Log.d(TAG, "onError: " + statusCode + errorMessage);
                }
            });
    }

    /**
     * 上传视频
     * @param path 视频路径
     */
    @Override
    public void uploadVideo(String path) {
        Log.d(TAG, "uploadVideo: " + path);

        new MyTask().execute(path);
    }

    @Override
    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    /**
     * 获取上传的图片
     * @param adapter
     */
    @Override
    public void getUploadPhoto(final PhotoAdapter adapter) {
        BmobQuery<Photo> query = new BmobQuery<>();
        query.addWhereEqualTo("isUpload", true);
        query.findObjects(new FindListener<Photo>() {
            @Override
            public void done(List<Photo> photos, BmobException e) {
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

    @Override
    public void uploadUserProfileImage(Bitmap bitmap) {

    }

    /**
     * 异步任务 视频压缩
     */
    class MyTask extends AsyncTask<String, Integer, String[]>{

        private OnlyCompressOverBean onlyCompressOverBean;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected String[] doInBackground(String... path) {
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
                return new String[]{onlyCompressOverBean.getVideoPath(),
                        onlyCompressOverBean.getPicPath()};
            }
            return new String[]{"", ""};
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "onProgressUpdate: ");
        }

        @Override
        protected void onPostExecute(String[] path) {
            Log.d(TAG, "onPostExecute: ");
            uploadVideoFirstFrame(path);
        }
    }

    /**
     * 压缩后上传视频
     * @param path
     * @param url
     */
    private void uploadVideoAfterCompress(final String path, final String url){

        String imagePath =
                saveBitmapToSD(DBUtil.queryUserProfileImage(), "USER_PROFILE_IMAGE");
        final String[] filePaths = new String[]{path, imagePath};
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                if (urls.size() == filePaths.length){
                    //上传成功
                    saveVideo(files.get(0), url, path, files.get(1));
                }
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                Message message = Message.obtain(mHandler, UPDATE_PROGRESS, totalPercent);
                mHandler.sendMessage(message);
            }

            @Override
            public void onError(int statusCode, String errorMsg) {
                Log.d(TAG, "onError: " + statusCode + errorMsg);
            }
        });
//        final BmobFile file = new BmobFile(new File(path));
//        file.upload(new UploadFileListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e == null){
//                    //必须上传的视频才可以去获取url
//                    Log.d(TAG, "done: " + User.getCurrentUser().getMobilePhoneNumber());
//                    saveVideo(file, url, path);
//                }else {
//                    Log.d(TAG, "done: " + "上传失败" + e.getMessage());
//                }
//            }
//
//            @Override
//            public void onProgress(Integer value) {
//
//            }
//        });
    }

    /**
     * 保存图片到数据库中
     * @param url 图片Url
     * @param userProfileImageUrl 用户头像Url
     * @param size 图片尺寸
     */
    private void savePhoto(String url, String userProfileImageUrl, int[] size){
        BmobUser user = User.getCurrentUser();
        String username = user.getUsername();
        Photo photo = new Photo();
        photo.setUsername(username);
        photo.setUploadPhotoUrl(url);
        photo.setIsUpload(true);
        photo.setSize(size[0] + "*" + size[1]);
        photo.setCreateTime(getUploadTime());
        photo.setUserProfileImage(userProfileImageUrl);
        photo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: " + "保存成功");
                    mHandler.sendEmptyMessage(IMainModel.UPLOAD_SUC);
                }else {
                    Log.d(TAG, "done: " + "保存失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 上传视频第一帧
     * @param path 第一帧路径
     */
    private void uploadVideoFirstFrame(final String[] path){

        final BmobFile firstFrameFile = new BmobFile(new File(path[1]));
        firstFrameFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: 上传成功");
                    uploadVideoAfterCompress(path[0], firstFrameFile.getFileUrl());
                }else {
                    Log.d(TAG, "done: 上传失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 保存视频到数据库中
     * @param file 视频文件
     * @param firstFrameUrl 第一帧Url
     * @param originalPath 视频原始路径，为了获取相关信息
     * @param userProfileImage 用户头像文件
     */
    private void saveVideo(BmobFile file, String firstFrameUrl, String originalPath,
                           BmobFile userProfileImage){

        String videoSize = getVideoSize(new File(originalPath));
//        float f = Float.parseFloat(videoSize.split("MB")[0]);
//        if (f >= 10.00){
//            //大于10MB禁止上传
//            ToastUtil.show(MyApplication.getMyApplicationContext(), "文件过大");
//        }
        String[] info = getVideoInfo(originalPath);
        String mimeType = getVideoMimeType(originalPath);
        Log.d(TAG, "saveVideo: " + videoSize);
        Log.d(TAG, "uploadVideo: " + info[0] + " " + info[1]);
        Log.d(TAG, "uploadVideo: " + mimeType);

        BmobUser user = User.getCurrentUser();
        String username = user.getUsername();
        Video video = new Video();
        video.setFile(file);
        video.setUsername(username);
        video.setUserimage(userProfileImage);
        video.setThumbUpload(firstFrameUrl);
        video.setUpload(true);
        video.setSize(videoSize);
        video.setCreateTime(getUploadTime());
        video.setMimeType(mimeType);
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
                    mHandler.sendEmptyMessage(IMainModel.UPLOAD_SUC);
                }else {
                    Log.d(TAG, "done: 保存失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 获得视频的尺寸,为了给type赋值
     * @param path 视频路径
     * @return
     */
    private String[] getVideoInfo(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        String width = "";
        String height = "";
        String rotation = "";
        try {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) " +
                        "AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
            retriever.setDataSource(path, headers);
            width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            retriever.release();
        }
        if (!width.equals("") && !height.equals("")){
            if ("90".equals(rotation)){//竖屏
                return new String[]{height, width};
            }else {//横屏
                return new String[]{width, height};
            }

        }
        Log.d(TAG, "getVideoInfo: " + width + " " + height);
        return new String[]{"500", "300"};
    }



    /**
     * 将Bitmap存储到本地
     * @param bitmap
     * @param customName 自定义名称后缀
     * @return
     */
    private String saveBitmapToSD(Bitmap bitmap, String customName){

        //获取内部存储状态
//        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写
//        if (!state.equals(Environment.MEDIA_MOUNTED)) {
//            return false;
//        }
        //通过UUID生成字符串文件名
        String fileName = UUID.randomUUID().toString() + "_" + customName;

        try {
            File file = new File(MyApplication.getMyApplicationContext().getExternalCacheDir(),
                    fileName + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            //Android 7.0 之后不可以直接使用Uri
            Uri photoUri;
            if(Build.VERSION.SDK_INT >= 24){
                photoUri = FileProvider.getUriForFile(MyApplication.getMyApplicationContext(),
                        "com.yjc.photodance.fileprovider", file);
            }else {
                photoUri = Uri.fromFile(file);
            }
            //保存图片后发送广播通知更新数据库
            MyApplication.getMyApplicationContext().sendBroadcast(
                    new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, photoUri));

            Log.d(TAG, "saveBitmapToSD: " + photoUri.toString());
            return photoUri.toString().substring(7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取视频的大小
     * @param file 视频路径
     * @return
     */
    private String getVideoSize(File file){
        try {
            FileInputStream stream = new FileInputStream(file);
            return formatFileSize(stream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 计算文件的存储大小
     * @param size
     * @return
     */
    private String formatFileSize(int size){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formatSize = decimalFormat.format((double) size / 1048576) + "MB";
        if (Double.parseDouble(formatSize.split("MB")[0]) < 1.00){
            return "0" + formatSize;
        }
        return formatSize;
    }

    /**
     * 获取文件上传时间
     * @return
     */
    private String getUploadTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取图片的宽高
     * @param path 图片路径
     * @return
     */
    private int[] getPhotoSize(String path){
        //获取真实宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int width=options.outWidth;
        int height=options.outHeight;
        return new int[]{width, height};
    }

    /**
     * 获取视频类型
     * @param path 视频路径
     * @return
     */
    private String getVideoMimeType(String path){
        MediaMetadataRetriever retriever = null;
        try {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE).substring(6);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            retriever.release();
        }
        return "mp4";
    }

    /**
     * 获取视频时长
     * @param path 视频路径
     * @return
     */
    private String getVideoDuration(String path){
        int minute = 0;
        int second = 0;
        MediaMetadataRetriever retriever = null;
        try {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            //ms
            int duration = Integer.parseInt(retriever.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_DURATION));
            duration = duration / 1000; //s
            if (duration < 60){//1分钟以内
                return "00 : " + String.valueOf(duration);
            }else {
                duration = duration / 60;
                if (duration < 60){//1小时以内
                    return String.valueOf(duration);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            retriever.release();
        }
        return "";
    }

    private String saveUserProfileImageToSD(Bitmap bitmap){
        //通过UUID生成字符串文件名
        String fileName = "USER_PROFILE_IMAGE";
        try {
            File file = new File(MyApplication.getMyApplicationContext().getExternalCacheDir(),
                    fileName + ".jpg");
            if (file.exists()){
                //文件已存在
                Log.d(TAG, "saveUserProfileImageToSD: " + file.getAbsolutePath());
                return file.getAbsolutePath();
            }else {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
            //Android 7.0 之后不可以直接使用Uri
            Uri photoUri;
            if(Build.VERSION.SDK_INT >= 24){
                photoUri = FileProvider.getUriForFile(MyApplication.getMyApplicationContext(),
                        "com.yjc.photodance.fileprovider", file);
            }else {
                photoUri = Uri.fromFile(file);
            }
            //保存图片后发送广播通知更新数据库
            MyApplication.getMyApplicationContext().sendBroadcast(
                    new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, photoUri));

            Log.d(TAG, "saveBitmapToSD: " + photoUri.toString());
            return photoUri.toString().substring(7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getVideoFirstFrame(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            return saveBitmapToSD(retriever.getFrameAtTime(), "first_frame");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            retriever.release();
        }
        return "";
    }

}
