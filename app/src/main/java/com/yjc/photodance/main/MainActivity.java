package com.yjc.photodance.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.yjc.photodance.R;
import com.yjc.photodance.common.SharedPreferenceDao;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity{

//    public static final int TAKE_PHOTO = 1;
    private Uri photoUri;
    private ImageView personalCenter;
    private ImageView takePhoto;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private SelectPicPopupWindow popupWindow;

    private boolean isSetHeadImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //10s后将设置为未登录状态
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    SharedPreferenceDao.getInstance().saveBoolean("login", false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        personalCenter=findViewById(R.id.personal_center);
        takePhoto=findViewById(R.id.take_photo);
        drawer=findViewById(R.id.drawer_layout);
        navigation=findViewById(R.id.nav_view);

        popupWindow=new SelectPicPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.take_photo:
                        takePhoto();
                        break;
                    case R.id.select_photo:
                        selectPhoto();
                        break;
                    default:
                        break;
                }
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                takePhoto();
            }
        });

        personalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSetHeadImage){
                    popupWindow.showAtLocation(MainActivity.this.findViewById(R.id.drawer_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                        0, 0);
                    isSetHeadImage = true;
                }else {
                    //打开侧滑菜单
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    private void takePhoto(){

        //创建File对象，用于存储照片
        //存放在当前应用缓存数据的位置，可以跳过权限验证
        File photo=new File(getExternalCacheDir() , "photo.jpg");
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
            photoUri = FileProvider.getUriForFile(MainActivity.this,
                    "com.yjc.photodance.fileprovider", photo);
        }else {
            photoUri=Uri.fromFile(photo);
        }

        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivity(intent);
    }

    private void selectPhoto(){
        // TODO: 2017/12/28/028 ContentProvider读取相册内容
    }
}
