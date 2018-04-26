package com.yjc.photodance.main.view;

import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mikepenz.materialdrawer.Drawer;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yjc.photodance.R;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.common.base.BaseActivity;
import com.yjc.photodance.common.util.MultiMedia;
import com.yjc.photodance.main.model.IMainModel;
import com.yjc.photodance.main.model.MainModelImpl;
import com.yjc.photodance.main.presenter.IMainPresenter;
import com.yjc.photodance.main.presenter.MainPresenterImpl;
import com.yjc.photodance.main.view.fragment.LiveFragment;
import com.yjc.photodance.main.view.fragment.MessageFragment;
import com.yjc.photodance.main.view.fragment.PhotoFragment;
import com.yjc.photodance.main.view.fragment.ShortVideoFragment;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.main.view.fragment.UploadFragment;
import com.yjc.photodance.main.view.popupwindow.UploadPopupWindow;

/**
 * Created by Administrator on 2017/12/29/001.
 */

public class MainActivity extends BaseActivity{

    private boolean isFirstEnter = true;

    private int selectedTab = 0;

    //判断是下拉刷新还是上拉加载
    private boolean flag = true;

    private RefreshLayout refreshLayout;
    public AHBottomNavigation bottomNavigation;
    private PhotoAdapter photoAdapter;
    private ShortVideoAdapter videoAdapter;

    private IMainPresenter mPresenter;
    private IMainModel mModel;

    private ImageView search;
    private FloatingActionButton fab;
    private Toolbar toolbar;

    private String mUsername;
    private String mPhoneNum;
    private ImageView personalCenter;
    private ImageView camera;

    private UploadPopupWindow mUploadPopupWindow;

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

        mUsername = getIntent().getStringExtra("username");
        mPhoneNum = getIntent().getStringExtra("phoneNum");

        // TODO: 2018/4/16/016 修改AhBottomNavigation的源码，解决colorful icon的问题

        //BottomNavigation的初始化
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.light_gray));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        // Enable the translation of the FloatingActionButton
        bottomNavigation.manageFloatingActionButtonBehavior(fab);
        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);
        // Force to tint the drawable (useful for font with icon for example)
//        bottomNavigation.setForceTint(true);
        // Display color under navigation bar (API 21+)
//        bottomNavigation.setTranslucentNavigationEnabled(true);
        // Use colored navigation with circle reveal effect
//        bottomNavigation.setColored(true);
        // Change colors
//        bottomNavigation.setAccentColor(R.color.light_gray);

        // TODO: 2018/4/24/024 改配色
        AHBottomNavigationItem photoItem =
                new AHBottomNavigationItem("图片", R.drawable.ic_photo_library_white_24dp);
        AHBottomNavigationItem shortVideoItem =
                new AHBottomNavigationItem("视频", R.drawable.ic_videocam_white_24dp);
        AHBottomNavigationItem uploadItem =
                new AHBottomNavigationItem("上传", R.drawable.ic_add_box_white_24dp);
        AHBottomNavigationItem liveItem =
                new AHBottomNavigationItem("直播", R.drawable.ic_live_tv_white_24dp);
        AHBottomNavigationItem messageItem =
                new AHBottomNavigationItem("消息", R.drawable.ic_message_white_24dp);

        bottomNavigation.addItem(photoItem);
        bottomNavigation.addItem(shortVideoItem);
        bottomNavigation.addItem(uploadItem);
        bottomNavigation.addItem(liveItem);
        bottomNavigation.addItem(messageItem);

        photoAdapter = new PhotoAdapter(this);
        videoAdapter = new ShortVideoAdapter(this);

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

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                MultiMedia.takePhoto();
            }
        });

        personalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开侧滑菜单
                mDrawer.openDrawer();
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
                        replaceFragment(new ShortVideoFragment(videoAdapter));
                        refreshLayout.autoRefresh();
                        selectedTab = 1;
                        break;
                    case 2:
                        // TODO: 2018/4/25/025 弹出popupwindow
                        //产生背景变暗效果
//                        WindowManager.LayoutParams lp = getWindow().getAttributes();
//                        lp.alpha = 0.4f;
//                        getWindow().setAttributes(lp);
                        UploadFragment uploadFragment = new UploadFragment();
                        replaceFragment(uploadFragment);
                        mUploadPopupWindow = new UploadPopupWindow(MainActivity.this,
                                uploadFragment);
                        mUploadPopupWindow.showAtLocation(MainActivity.this.getWindow().getDecorView(),
                                Gravity.BOTTOM, 0, 0);
                        // TODO: 2018/4/25/025 动画
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mUploadPopupWindow.showImageView();
//                            }
//                        }, 200);
                        break;
                    case 3:
                        replaceFragment(new LiveFragment());
                        selectedTab = 2;
                        break;
                    case 4:
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
                        mPresenter.requestVideo(videoAdapter);
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
        personalCenter=findViewById(R.id.personal_center);
        camera=findViewById(R.id.camera);
        fab = findViewById(R.id.fab_search);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public String getUsername(){
        return mUsername;
    }

    public String getPhoneNum(){
        return mPhoneNum;
    }



}
