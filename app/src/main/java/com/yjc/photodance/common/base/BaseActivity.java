package com.yjc.photodance.common.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yjc.photodance.R;
import com.yjc.photodance.account.view.LoginActivity;
import com.yjc.photodance.common.util.MultiMedia;
import com.yjc.photodance.main.view.MainActivity;
import com.yjc.photodance.model.Account;
import com.yjc.photodance.ui.CollectionsActivity;
import com.yjc.photodance.ui.InfoActivity;

import org.litepal.crud.DataSupport;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private CircleImageView personalCenter;
    private ImageView takePhoto;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private FloatingActionButton fab;
    private CircleImageView userHeadImage;
    private Bitmap userHeadImageBitmap;
    private ViewGroup contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initToolbar();
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * 初始化监听器
     */
    public void initListener(){
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //拍照
//                MultiMedia.takePhoto();
//            }
//        });

//        personalCenter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //打开侧滑菜单
//                drawer.openDrawer(GravityCompat.START);
//            }
//        });

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
                switch (item.getItemId()){
                    //注销，返回登录界面
                    case R.id.nav_log_off:
                        Intent intentToLogin =
                                new Intent(BaseActivity.this, LoginActivity.class);
                        startActivity(intentToLogin);
                        break;

                    //关于
                    case R.id.nav_about:
                        Intent intentToAbout =
                                new Intent(BaseActivity.this, InfoActivity.class);
                        startActivity(intentToAbout);
                        break;

                    //设置
                    case R.id.nav_setting:
                        break;

                    //收藏
                    case R.id.nav_collection:
                        Intent intentToCollection =
                                new Intent(BaseActivity.this, CollectionsActivity.class);
                        startActivity(intentToCollection);
                        break;

                    //分享
                    case R.id.nav_share:
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        //悬浮按钮的点击事件处理
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/1/6/006 回到顶部
            }
        });
    }

    /**
     * 初始化控件
     */
    public void initView(){
        contentView = findViewById(R.id.content_view);
        contentView.addView(View.inflate(this, getContentView(), null));
        personalCenter=findViewById(R.id.personal_center);
//        takePhoto=findViewById(R.id.take_photo);
        drawer=findViewById(R.id.drawer_layout);
        navigation=findViewById(R.id.nav_view);
        fab = findViewById(R.id.btn_up);
    }

    /**
     * 获取内容布局
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化数据
     */
    public void initData(){
        // TODO: 2018/4/15/015 头像处理
        //获取头像
//        account = DataSupport.findLast(Account.class);
//        Bitmap userHeadImageBitmap = BitmapFactory.decodeByteArray(account.getUserHeadImage(),
//                0, account.getUserHeadImage().length);
        personalCenter.setImageBitmap(userHeadImageBitmap);
    }

}
