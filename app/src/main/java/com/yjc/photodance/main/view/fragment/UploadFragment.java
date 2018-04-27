package com.yjc.photodance.main.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BaseFragment;
import com.yjc.photodance.common.util.HandleBitmap;
import com.yjc.photodance.common.util.MultiMedia;
import com.yjc.photodance.main.view.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class UploadFragment extends BaseFragment {

    private static final int SELECT_PHOTO = 1;
    private static final int SELECT_VIDEO = 2;
    private static final int TAKE_PHOTO = 3;
    private static final int RECORD_VIDEO = 4;
    private static final int PHOTO = 1;
    private static final int VIDEO = 2;
    private static final String TAG = "UploadFragment";
    private String mPhotoPath;
    private Bitmap mSelectPhoto;
    private Bitmap mTakePhoto;
    private String mVideoPath;
    private Bitmap mSelectVideoFirstFrame;

    private VideoView mVideoContainer;

    private ImageView mPhotoContainer;
    private ImageView mVideoFirstFrameContainer;

    private ImageView mPlaceHolder;
    private Button mConfirm;
    private Button mCancel;
    private ProgressBar mProgressBar;

    private FrameLayout mVideoLayout;

    private Uri photoUri;
    private Uri videoUri;

    private int optionCode;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initListener();
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data 调用相机回传的data 为空，不能直接用来获取照片
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    mConfirm.setEnabled(true);
                    mCancel.setEnabled(true);
                    mVideoLayout.setVisibility(View.GONE);
                    mPlaceHolder.setVisibility(View.GONE);
                    mPhotoContainer.setVisibility(View.VISIBLE);
                    mPhotoPath = MultiMedia.handleFile(data, PHOTO);
                    mSelectPhoto = HandleBitmap.compressForFile(mPhotoPath);
                    mPhotoContainer.setImageBitmap(mSelectPhoto);
                    optionCode = SELECT_PHOTO;
                }else {
                    mConfirm.setEnabled(false);
                    mCancel.setEnabled(false);
                    mPlaceHolder.setVisibility(View.VISIBLE);
                }
                break;
            case SELECT_VIDEO:
                //获取视频第一帧显示
                if(resultCode == RESULT_OK){
                    mConfirm.setEnabled(true);
                    mCancel.setEnabled(true);
                    mVideoLayout.setVisibility(View.VISIBLE);
                    mPlaceHolder.setVisibility(View.GONE);
                    mVideoContainer.setVisibility(View.VISIBLE);
                    mVideoPath = MultiMedia.handleFile(data, VIDEO);
                    Log.d(TAG, "onActivityResult: " + mVideoPath);
                    mVideoContainer.setVideoPath(mVideoPath);
                    MediaController controller = new MediaController(getActivity());
                    mVideoContainer.setMediaController(controller);
                    mVideoContainer.start();
                    optionCode = SELECT_VIDEO;

                    /**
                     * SurfaceView + MediaPlayer 播放视频
                     */
//                    try {
//                        SurfaceHolder holder = mVideoContainer.getHolder();
//                        holder.addCallback(new SurfaceHolder.Callback() {
//                            @Override
//                            public void surfaceCreated(SurfaceHolder surfaceHolder) {
//                                //这一步是关键，制定用于显示视频的SurfaceView对象（通过setDisplay（））
//                                mPlayer.setDisplay(surfaceHolder);
//                            }
//
//                            @Override
//                            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//                            }
//
//                            @Override
//                            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//
//                            }
//                        });
//                        mPlayer.setDataSource(mVideoPath);
//                        mPlayer.prepare();
//                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            @Override
//                            public void onPrepared(MediaPlayer mp) {
//                                mPlayer.start();
//                                mPlayer.setLooping(true);
//                            }
//                        });
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    /**
                     * ijkPlayer 播放视频 but 无法播放本地视频
                     */
//                    mVideoContainer.setVisibility(View.VISIBLE);
//                    mVideoContainer.setVideoController(new StandardVideoController(getActivity()));
//                    mVideoContainer.setUrl(mVideoPath);
//                    mVideoContainer.setTitle(mVideoPath);
//                    mVideoContainer.setPlayerConfig(
//                            //高级设置
//                            new PlayerConfig.Builder()
//                                    .enableCache() //启用边播边缓存功能
//                                    .autoRotate() //启用重力感应自动进入/退出全屏功能
//                                    //启动硬解码，启用后可能导致视频黑屏，音画不同步,此app横竖屏切换黑屏
////                                  .enableMediaCodec()
//                                    .usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
//                                    .usingAndroidMediaPlayer()
//                                    .savingProgress() //保存播放进度
//                                    .addToPlayerManager()//required
//                                    .build());
//                    mVideoContainer.start();
                }else {
                    mConfirm.setEnabled(false);
                    mCancel.setEnabled(false);
                    mPlaceHolder.setVisibility(View.VISIBLE);
                }
                break;
            case TAKE_PHOTO:
                Log.d(TAG, "onActivityResult: " + resultCode);
                Log.d(TAG, "onActivityResult: " + data);
                if (resultCode == RESULT_OK){
                    Log.d(TAG, "onActivityResult: " + resultCode);
                    mConfirm.setEnabled(true);
                    mCancel.setEnabled(true);
                    mPlaceHolder.setVisibility(View.GONE);
                    mVideoContainer.setVisibility(View.GONE);
                    mVideoFirstFrameContainer.setVisibility(View.GONE);
                    mPhotoContainer.setVisibility(View.VISIBLE);
                    try {
                        mTakePhoto = HandleBitmap.compressForStream(
                                    getActivity().getContentResolver().openInputStream(photoUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    mPhotoPath = String.valueOf(photoUri);
                    Log.d(TAG, "onActivityResult: " + mPhotoPath);
//                    mTakePhoto = HandleBitmap.compressForFile(mPhotoPath);
                    Log.d(TAG, "onActivityResult: " + mTakePhoto);
                    mPhotoContainer.setImageBitmap(mTakePhoto);
                    optionCode = TAKE_PHOTO;
                }else {
                    mConfirm.setEnabled(false);
                    mCancel.setEnabled(false);
                    mPlaceHolder.setVisibility(View.VISIBLE);
                }
                break;
            case RECORD_VIDEO:
                if(resultCode == RESULT_OK){
                    mConfirm.setEnabled(true);
                    mCancel.setEnabled(true);
                    mVideoLayout.setVisibility(View.VISIBLE);
                    mPlaceHolder.setVisibility(View.GONE);
                    mVideoContainer.setVisibility(View.VISIBLE);
                    mPhotoContainer.setVisibility(View.GONE);
                    mVideoPath = String.valueOf(videoUri);
                    mVideoContainer.setVideoPath(mVideoPath);
                    MediaController controller = new MediaController(getActivity());
                    mVideoContainer.setMediaController(controller);
                    mVideoContainer.start();
                    optionCode = RECORD_VIDEO;
                }else {
                    mConfirm.setEnabled(false);
                    mCancel.setEnabled(false);
                    mPlaceHolder.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取本地视频文件的第一帧
     */
    private void getVideoFirstFrame(){
        mVideoFirstFrameContainer.setVisibility(View.VISIBLE);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(mVideoPath);
            mSelectVideoFirstFrame = retriever.getFrameAtTime();
            mVideoFirstFrameContainer.setImageBitmap(mSelectVideoFirstFrame);
        }catch (RuntimeException e){
            e.printStackTrace();
        }finally {
            retriever.release();
        }
    }

    public void takePhoto(){
        //创建File对象，用于存储照片
        //存放在当前应用缓存数据的位置，可以跳过权限验证
        // TODO: 2018/1/4/004 字符串拼接对多张图片命名
        File photo=new File(MyApplication.getMyApplicationContext().getExternalCacheDir() ,
                "photo.jpg");
        if(photo.exists()){
            photo.delete();
        }
        try {
            photo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Android 7.0 之后不可以直接使用Uri
        if(Build.VERSION.SDK_INT >= 24){
            Log.d(TAG, "takePhoto: 1" );
            photoUri = FileProvider.getUriForFile(MyApplication.getMyApplicationContext(),
                    "com.yjc.photodance.fileprovider", photo);
        }else {
            Log.d(TAG, "takePhoto: 2");
            photoUri= Uri.fromFile(photo);
        }

        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        //photoUri 即为 mSaveUri(源码)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    public void recordVideo(){
        // 录制
//        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
//                .fullScreen(false)
//                .smallVideoWidth(360)
//                .smallVideoHeight(480)
//                .recordTimeMax(6000)
//                .recordTimeMin(1500)
//                .maxFrameRate(20)
//                .videoBitrate(600000)
//                .captureThumbnailsTime(1)
//                .build();
//        MediaRecorderActivity.goSmallVideoRecorder(getActivity(),
//                AlertDialog.Builder.class.getName(), config);
//        Log.d(TAG, "recordVideo: " + "goSmallVideoRecorder");
//        MainActivity activity = (MainActivity) getActivity();
//        Log.d(TAG, "recordVideo: " + activity.bottomNavigation.getCurrentItem());
//        activity.bottomNavigation.setCurrentItem(2);
//        Log.d(TAG, "recordVideo: " + activity.bottomNavigation.getCurrentItem());
//        Log.d(TAG, "recordVideo: " + "setCurrentItem");
//        activity.replaceFragment(new UploadFragment());
//        mConfirm.setEnabled(true);
//        mCancel.setEnabled(true);
//        mPhotoContainer.setVisibility(View.GONE);
//        mPlaceHolder.setVisibility(View.GONE);
//        mVideoContainer.setVisibility(View.VISIBLE);
//        String videoPath = getActivity().getIntent().getStringExtra(MediaRecorderActivity.VIDEO_URI);
//        String videoScreenShotPath =
//                getActivity().getIntent().getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
//        mVideoContainer.setVideoPath(videoPath);
//        MediaController controller = new MediaController(getActivity());
//        mVideoContainer.setMediaController(controller);
//        mVideoContainer.start();

        //创建File对象，用于存储视频
        //存放在当前应用缓存数据的位置，可以跳过权限验证
        // TODO: 2018/1/4/004 字符串拼接对多张图片命名
        File video=new File(MyApplication.getMyApplicationContext().getExternalCacheDir() ,
                "video.mp4");
        if(video.exists()){
            video.delete();
        }
        try {
            video.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Android 7.0 之后不可以直接使用Uri
        if(Build.VERSION.SDK_INT >= 24){
            Log.d(TAG, "takePhoto: 1" );
            videoUri = FileProvider.getUriForFile(MyApplication.getMyApplicationContext(),
                    "com.yjc.photodance.fileprovider", video);
        }else {
            Log.d(TAG, "takePhoto: 2");
            videoUri= Uri.fromFile(video);
        }

        //启动相机程序
        Intent intent=new Intent("android.media.action.VIDEO_CAPTURE");
        //photoUri 即为 mSaveUri(源码)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(intent, RECORD_VIDEO);
    }

    private void initListener(){
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                // TODO: 2018/4/26/026 上传逻辑
//                if(mPhotoContainer.getVisibility() == View.VISIBLE){
//                    //上传照片
//                    mPhotoPath = String.valueOf(photoUri);
//                    Log.d(TAG, "onClick: " + mPhotoPath);
//                    activity.mPresenter.requestUploadPhoto(mPhotoPath);
//                }else if(mVideoContainer.getVisibility() == View.VISIBLE){
//                    //上传视频
//                    Log.d(TAG, "onClick: " + mVideoPath);
//                    activity.mPresenter.requestUploadVideo(mVideoPath);
//                }
                switch (optionCode){
                    case SELECT_PHOTO:
                        mConfirm.setEnabled(false);
                        activity.mPresenter.requestUploadPhoto(mPhotoPath);
                        break;
                    case TAKE_PHOTO:
                        mConfirm.setEnabled(false);
                        activity.mPresenter.requestUploadPhoto(String.valueOf(photoUri));
                        break;
                    case SELECT_VIDEO:
                        mConfirm.setEnabled(false);
                        activity.mPresenter.requestUploadVideo(mVideoPath);
                        break;
                    case RECORD_VIDEO:
                        mConfirm.setEnabled(false);
                        activity.mPresenter.requestUploadVideo(String.valueOf(videoUri));
                        break;
                    default:
                        break;
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConfirm.setEnabled(false);
                mCancel.setEnabled(false);
                mPhotoContainer.setVisibility(View.GONE);
                mVideoContainer.setVisibility(View.GONE);
                mVideoFirstFrameContainer.setVisibility(View.GONE);
                mPlaceHolder.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView(){
        mPhotoContainer = getActivity().findViewById(R.id.photo_container);
        mVideoFirstFrameContainer = getActivity().findViewById(R.id.video_first_frame_container);
        mVideoContainer = getActivity().findViewById(R.id.video_container);

        mPlaceHolder = getActivity().findViewById(R.id.place_holder);
        mConfirm = getActivity().findViewById(R.id.confirm_btn);
        mCancel = getActivity().findViewById(R.id.cancel_btn);
        mVideoLayout = getActivity().findViewById(R.id.video_layout);
        mProgressBar = getActivity().findViewById(R.id.progressbar);
    }

    public ProgressBar getProgressBar(){
        return mProgressBar;
    }

    private void getVideoInfo(String path){
        File file = new File(path);
    }

}
