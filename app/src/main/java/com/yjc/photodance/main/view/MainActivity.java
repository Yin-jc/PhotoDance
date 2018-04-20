package com.yjc.photodance.main.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHHelper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yjc.photodance.R;
import com.yjc.photodance.account.view.LoginActivity;
import com.yjc.photodance.common.base.BaseActivity;
import com.yjc.photodance.common.http.api.ApiConfig;
import com.yjc.photodance.common.http.api.PhotoApi;
import com.yjc.photodance.main.model.IMainModel;
import com.yjc.photodance.main.model.MainModelImpl;
import com.yjc.photodance.main.presenter.IMainPresenter;
import com.yjc.photodance.main.presenter.MainPresenterImpl;
import com.yjc.photodance.main.view.fragment.LiveFragment;
import com.yjc.photodance.main.view.fragment.MessageFragment;
import com.yjc.photodance.main.view.fragment.PhotoFragment;
import com.yjc.photodance.main.view.fragment.ShortVideoFragment;
import com.yjc.photodance.ui.CollectionsActivity;
import com.yjc.photodance.ui.InfoActivity;
import com.yjc.photodance.common.util.MultiMedia;
import com.yjc.photodance.model.Account;
import com.yjc.photodance.bean.Photo;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.common.network.RetrofitServiceManager;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/29/001.
 */

public class MainActivity extends BaseActivity{

    private Uri photoUri;
    private Account account;
    private List<Photo> photos;
//    private PhotoAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isFirstEnter = true;
    //PhotoApi中的参数
    private int size = 21;
    private int page = 1;
    private int selectedTab = 0;

    //判断是下拉刷新还是上拉加载
    private boolean flag = true;

    private RefreshLayout refreshLayout;
    private AHBottomNavigation bottomNavigation;
    private PhotoAdapter photoAdapter;

    private IMainPresenter mPresenter;
    private IMainModel mModel;

    private CircleImageView personalCenter;
    private ImageView takePhoto;
    private CircleImageView userHeadImage;
    private Bitmap userHeadImageBitmap;
    private ImageView search;
    private FloatingActionButton fab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModel = new MainModelImpl();
        mPresenter = new MainPresenterImpl(mModel);
        
        initView();
        initData();
        initListener();
        initToolbar();

    }

    @Override
    public void initData() {
        super.initData();

        // TODO: 2018/4/16/016 修改AhBottomNavigation的源码，解决colorful icon的问题

        //BottomNavigation的初始化
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.color_blue));

        AHBottomNavigationItem photoItem =
                new AHBottomNavigationItem(R.string.nav_photo, R.drawable.nav_photo,
                        R.color.color_red);
        AHBottomNavigationItem shortVideoItem =
                new AHBottomNavigationItem(R.string.nav_short_video, R.drawable.nav_short_video,
                        R.color.color_blue);
        AHBottomNavigationItem liveItem =
                new AHBottomNavigationItem(R.string.nav_live, R.drawable.nav_live,
                        R.color.color_red);
        AHBottomNavigationItem messageItem =
                new AHBottomNavigationItem(R.string.nav_message, R.drawable.nav_message,
                        R.color.color_gray);

        bottomNavigation.addItem(photoItem);
        bottomNavigation.addItem(shortVideoItem);
        bottomNavigation.addItem(liveItem);
        bottomNavigation.addItem(messageItem);

        photoAdapter = new PhotoAdapter(this);

        // TODO: 2018/4/15/015 头像处理
        //获取头像
//        account = DataSupport.findLast(Account.class);
//        Bitmap userHeadImageBitmap = BitmapFactory.decodeByteArray(account.getUserHeadImage(),
//                0, account.getUserHeadImage().length);
//        personalCenter.setImageBitmap(userHeadImageBitmap);
    }

    @Override
    public void initListener() {
        super.initListener();

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
//                MainActivity.super.drawer.openDrawer(GravityCompat.START);
//            }
//        });
        
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/4/19/019 查找View
                //Test
                Log.d("MainActivity", "search");
                mPresenter.requestPhotoBySearch(photoAdapter, "office");
            }
        });

        //必须要现获取header
//        View headerView = super.navigation.getHeaderView(0);
        //头部图片
//        userHeadImage = headerView.findViewById(R.id.userHeadImage);
//        userHeadImage.setImageBitmap(userHeadImageBitmap);

        //默认选中第一个tab不会触发此监听器
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){//index of items
                    case 0:
                        replaceFragment(new PhotoFragment(photoAdapter));
                        selectedTab = 0;
                        break;
                    case 1:
                        replaceFragment(new ShortVideoFragment());
                        selectedTab = 1;
                        break;
                    case 2:
                        replaceFragment(new LiveFragment());
                        selectedTab = 2;
                        break;
                    case 3:
                        replaceFragment(new MessageFragment());
                        selectedTab = 3;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        // TODO: 2018/1/6/006 以后打开应用也自动刷新
        if (isFirstEnter) {
            //第一次进入自动刷新
//            refreshLayout.autoRefresh();
            replaceFragment(new PhotoFragment(photoAdapter));
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                flag = true;
                switch (selectedTab){
                    case 0:
//                        mPresenter.requestPhoto(photoAdapter, 1, size);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
//                getPhotos(1, size);
                refreshLayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                flag = false;
                refreshlayout.finishLoadmore(1000);
//                getPhotos(++page, size);
            }
        });

        //悬浮按钮的点击事件处理
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO: 2018/1/6/006 回到顶部
//            }
//        });
    }

    @Override
    public void initView() {
        super.initView();
        refreshLayout = findViewById(R.id.refresh_layout);
        bottomNavigation = findViewById(R.id.bottom_navigation);
//        fab = findViewById(R.id.btn_up);
        personalCenter=findViewById(R.id.personal_center);
//        takePhoto=findViewById(R.id.take_photo);
        search = findViewById(R.id.search);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }



}
