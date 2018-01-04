package com.yjc.photodance.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.yjc.photodance.R;
import com.yjc.photodance.common.MultiMedia;
import com.yjc.photodance.common.NetworkWithOkHttp;
import com.yjc.photodance.common.SharedPreferenceDao;
import com.yjc.photodance.dao.Account;
import com.yjc.photodance.dao.Navigation;
import com.yjc.photodance.dao.NavigationAdapter;
import com.yjc.photodance.dao.Photo;
import com.yjc.photodance.dao.PhotoAdapter;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity{

//    public static final int TAKE_PHOTO = 1;
    private Uri photoUri;
    private CircleImageView personalCenter;
    private ImageView takePhoto;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private Account account;
    private List<Navigation> navigationList;
    private CircleImageView userHeadImage;
    private List<Photo> photos;

//    private boolean isSetHeadImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photos = NetworkWithOkHttp.NetworkRequest();

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

//        Glide.with(this).load("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg")
//                .into(takePhoto);

//        navigationList = new ArrayList<>();
//        Navigation navigation_collection = new Navigation("我的收藏", R.drawable.my_collection);
//        navigationList.add(navigation_collection);
//        Navigation navigation_logOff = new Navigation("注销", R.drawable.log_off);
//        navigationList.add(navigation_logOff);
//        NavigationAdapter adapter = new NavigationAdapter(this, R.layout.navigation_item,
//                navigationList);
//        ListView listView = findViewById(R.id.navigation_listView);
//        listView.setAdapter(adapter);

//        Bitmap userHeadImage = getIntent().getExtras().getParcelable("userHeadImageBitmap");
        //获取头像
        account = DataSupport.findLast(Account.class);
        Bitmap userHeadImageBitmap = BitmapFactory.decodeByteArray(account.getUserHeadImage(),
                0, account.getUserHeadImage().length);
        personalCenter.setImageBitmap(userHeadImageBitmap);

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
            account.setLogin(false);
            account.updateAll();
            //打开侧滑菜单
            drawer.openDrawer(GravityCompat.START);
            }
        });

        //必须要现获取header
        View headerView = navigation.getHeaderView(0);
        //头部图片
        userHeadImage = headerView.findViewById(R.id.userHeadImage);
        userHeadImage.setImageBitmap(userHeadImageBitmap);

        //navigation item点击事件
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // TODO: 2018/1/4/004 处理点击事件
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        PhotoAdapter adapter = new PhotoAdapter(this, photos);
        recyclerView.setAdapter(adapter);
    }

}
