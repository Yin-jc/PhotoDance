package com.yjc.photodance.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.yjc.photodance.common.MultiMedia;
import com.yjc.photodance.common.SharedPreferenceDao;
import com.yjc.photodance.dao.Account;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity{

//    public static final int TAKE_PHOTO = 1;
    private Uri photoUri;
    private ImageView personalCenter;
    private ImageView takePhoto;
    private DrawerLayout drawer;
    private NavigationView navigation;

    private boolean isSetHeadImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //10s后将设置为未登录状态
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
////                    Account account = new Account();
////                    account.setLogin(false);
////                    account.updateAll();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        personalCenter=findViewById(R.id.personal_center);
        takePhoto=findViewById(R.id.take_photo);
        drawer=findViewById(R.id.drawer_layout);
        navigation=findViewById(R.id.nav_view);

//        Bitmap userHeadImage = getIntent().getExtras().getParcelable("userHeadImageBitmap");
        Account account = DataSupport.findLast(Account.class);
        personalCenter.setImageBitmap(BitmapFactory.decodeByteArray(account.getUserHeadImage(),
                0, account.getUserHeadImage().length));

//        Account account1 = new Account();
        account.setLogin(false);
        account.updateAll();

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                MultiMedia.takePhoto();
            }
        });

        personalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSetHeadImage){

                    isSetHeadImage = true;
                }else {
                    //打开侧滑菜单
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    private void selectPhoto(){
        // TODO: 2017/12/28/028 ContentProvider读取相册内容
    }
}
