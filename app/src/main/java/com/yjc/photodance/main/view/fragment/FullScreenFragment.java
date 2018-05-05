package com.yjc.photodance.main.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;
import com.yjc.photodance.adapter.CollectionPhotoAdapter;
import com.yjc.photodance.common.base.BaseFragment;
import com.yjc.photodance.common.util.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;


/**
 * Created by Administrator on 2018/5/4/004.
 */

@SuppressLint("ValidFragment")
public class FullScreenFragment extends BaseFragment {

    private static final String TAG = "FullScreenFragment";
    private String rawUrl;
    private ImageView imageView;

    public FullScreenFragment(String url){
        rawUrl = url;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_full_screen;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + (getActivity() == null));
        imageView = getActivity().findViewById(R.id.image_full_screen);
        Log.d(TAG, "onActivityCreated: " + rawUrl);

        RequestOptions options = new RequestOptions()
                //占位符
//                .placeholder(R.drawable.splash_image)
                .override(1200, 1920);

        Glide.with(getActivity())
                .load(rawUrl)
                .apply(options)
                .into(imageView);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, "保存到本地", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO: 2018/1/7/007 添加保存到本地的功能
                                imageView.setDrawingCacheEnabled(true);
                                boolean result = saveImage(imageView.getDrawingCache());
                                if (result){
                                    ToastUtil.show(getActivity(), "保存成功");
                                }else {
                                    ToastUtil.show(getActivity(), "保存失败,请稍后重试");
                                }
                                Log.d(TAG, String.valueOf(result));
                                imageView.setDrawingCacheEnabled(false);
                            }
                        }).show();
                return true;
            }
        });

    }

    private boolean saveImage(Bitmap bitmap){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/PhotoDance/download/photos/";
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //获取内部存储状态
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        //通过UUID生成字符串文件名
        String fileName = UUID.randomUUID().toString();
        //通过Random()类生成数组命名
//        Random random = new Random();
//        String fileName2 = String.valueOf(random.nextInt(Integer.MAX_VALUE));

        try {
            File file = new File(dirPath + fileName + ".jpg");
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
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, photoUri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
