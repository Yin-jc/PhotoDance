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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
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

import java.lang.reflect.Field;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public abstract class BaseActivity extends AppCompatActivity{


    protected DrawerLayout drawer;
    protected NavigationView navigation;
    private ViewGroup contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    /**
     * 初始化监听器
     */
    public void initListener(){
        //drawer_navigation item点击事件
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
    }

    /**
     * 初始化控件
     */
    public void initView(){
        contentView = findViewById(R.id.content_view);
        contentView.addView(View.inflate(this, getContentView(), null));
        drawer=findViewById(R.id.drawer_layout);
        navigation=findViewById(R.id.nav_view);
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

    }

//    private void makeActionOverflowMenuShown() {
//        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
//        try {
//            ViewConfiguration config = ViewConfiguration.get(this);
//            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
//            if (menuKeyField != null) {
//                menuKeyField.setAccessible(true);
//                menuKeyField.setBoolean(config, false);
//            }
//        } catch (Exception e) {
//
//        }
//    }

}
