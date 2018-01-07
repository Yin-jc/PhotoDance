package com.yjc.photodance.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2018/1/7/007.
 */

public class ImageFullScreenActivity extends AppCompatActivity{

    private static final String TAG = "ImageFullScreenActivity";
    private ImageView imageView;
    private ImageView back;
    private Uri photoUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);

        Toolbar toolbar=findViewById(R.id.toolbar_full_screen);
        setSupportActionBar(toolbar);

        back = findViewById(R.id.back_full_screen);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从收藏中打开
                if (MyApplication.getIsFromCollections()){
                    Intent intent = new Intent(ImageFullScreenActivity.this,
                            CollectionsActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(ImageFullScreenActivity.this,
                            ImageDetailsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        imageView = findViewById(R.id.image_full_screen);
        RequestOptions options = new RequestOptions();
        options.centerCrop();

        //从收藏中打开
        if (MyApplication.getIsFromCollections()){
            Glide.with(this)
                    .load(getIntent().getStringExtra("imageUrlFromCollections"))
                    .apply(options)
                    .into(imageView);
        }else {
            Glide.with(this)
                    .load(getIntent().getStringExtra("imageUrl"))
                    .apply(options)
                    .into(imageView);
        }

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, "保存到本地", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO: 2018/1/7/007 添加保存到本地的功能
                                imageView.setDrawingCacheEnabled(true);
                                boolean b = saveImage(imageView.getDrawingCache());
                                Log.d(TAG, String.valueOf(b));
                                imageView.setDrawingCacheEnabled(false);
                            }
                        }).show();
                return true;
            }
        });
    }

    private boolean saveImage(Bitmap bitmap){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/PhotoDance/save/photos/";
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

            //保存图片后发送广播通知更新数据库
            //Android 7.0 之后不可以直接使用Uri
            if(Build.VERSION.SDK_INT >= 24){
                photoUri = FileProvider.getUriForFile(MyApplication.getMyApplicationContext(),
                        "com.yjc.photodance.fileprovider", file);
            }else {
                photoUri= Uri.fromFile(file);
            }
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, photoUri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
