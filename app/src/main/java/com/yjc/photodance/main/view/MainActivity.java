package com.yjc.photodance.main.view;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yjc.photodance.R;
import com.yjc.photodance.adapter.SearchPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.common.base.BaseActivity;
import com.yjc.photodance.common.storage.bean.Info;
import com.yjc.photodance.common.util.MultiMedia;
import com.yjc.photodance.common.util.ToastUtil;
import com.yjc.photodance.main.model.IMainModel;
import com.yjc.photodance.main.model.MainModelImpl;
import com.yjc.photodance.main.model.bean.Photo;
import com.yjc.photodance.main.presenter.IMainPresenter;
import com.yjc.photodance.main.presenter.MainPresenterImpl;
import com.yjc.photodance.main.view.fragment.PhotoFragment;
import com.yjc.photodance.main.view.fragment.ShortVideoFragment;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.main.view.fragment.UploadFragment;
import com.yjc.photodance.main.view.popupwindow.UploadPopupWindow;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by Administrator on 2017/12/29/001.
 */

public class MainActivity extends BaseActivity implements IMainView{

    private static final String TAG = "MainActivity";
    private boolean isFirstEnter = true;

    private int selectedTab = 0;

    //判断是下拉刷新还是上拉加载
    private boolean flag = true;

    private RefreshLayout refreshLayout;
    public AHBottomNavigation bottomNavigation;
    private PhotoAdapter photoAdapter;
    private SearchPhotoAdapter searchPhotoAdapter;
    private ShortVideoAdapter videoAdapter;

    public IMainPresenter mPresenter;
    private IMainModel mModel;

    private FloatingActionButton fab;

    private String mUsername;
    private String mPhoneNum;
    private ImageView personalCenter;
    private ImageView camera;

    private UploadPopupWindow mUploadPopupWindow;

    private int first_page_size;
    private int page = 1;
    private int per_page = 12;

    private Bitmap userHeadImageBitmap;

    private int uploadPhotoCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModel = new MainModelImpl();
        mPresenter = new MainPresenterImpl(this, mModel);

        getUploadPhotoCount();

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
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.dark_white));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        // Enable the translation of the FloatingActionButton
        bottomNavigation.manageFloatingActionButtonBehavior(fab);
        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(true);
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

        bottomNavigation.addItem(photoItem);
        bottomNavigation.addItem(uploadItem);
        bottomNavigation.addItem(shortVideoItem);

        photoAdapter = new PhotoAdapter(this);
        searchPhotoAdapter = new SearchPhotoAdapter(this);
        videoAdapter = new ShortVideoAdapter(this, this);

        //获取头像
        List<Info> infos = DataSupport.select("userHeadImage")
                .where("username = ?", BmobUser.getCurrentUser().getUsername())
                .find(Info.class);
        //用户未设置头像，显示默认的
        if (infos.size() != 0){
            if (infos.get(0).getUserHeadImage() != null){
                userHeadImageBitmap = BitmapFactory.decodeByteArray(infos.get(0).getUserHeadImage(),
                        0, infos.get(0).getUserHeadImage().length);
            }else {
                userHeadImageBitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.personal_center);
            }
        }
        personalCenter.setImageBitmap(userHeadImageBitmap);

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

        //默认选中第一个tab不会触发此监听器
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){//index of items
                    case 0:
                        replaceFragment(new PhotoFragment(photoAdapter, false));
                        refreshLayout.autoRefresh();
                        selectedTab = 0;
                        break;
                    case 1:
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
                        break;
                    case 2:
                        replaceFragment(new ShortVideoFragment(videoAdapter));
                        refreshLayout.autoRefresh();
                        selectedTab = 2;
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
            refreshLayout.autoRefresh();
            replaceFragment(new PhotoFragment(photoAdapter, false));
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                flag = true;
                switch (selectedTab){
                    case 0:
                        Log.d(TAG, "onRefresh: " + first_page_size);
                        if (uploadPhotoCount > 0){
                            first_page_size = 12 -uploadPhotoCount;
                        }else {
                            first_page_size = 100;
                        }
                        mPresenter.requestPhotoFromUser(photoAdapter);
                        mPresenter.requestPhoto(photoAdapter, page, first_page_size, flag, isFirstEnter);
                        isFirstEnter = false;
                        break;
                    case 1:
                        break;
                    case 2:
                        mPresenter.requestVideo(videoAdapter);
                        break;
//                    case 3:
//                        break;
                    default:
                        break;
                }
                refreshLayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                flag = false;
                mPresenter.requestPhoto(photoAdapter, ++page, per_page, flag, false);
                refreshlayout.finishLoadmore(1000);
            }
        });

        //悬浮按钮的点击事件处理
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //搜索
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //icon和title必须一起设置，写一个布局
                builder.setCustomTitle(LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.custom_title_search_layout, null));
                View v = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.dialog_search_photos, null);
                builder.setView(v);
                final EditText search = v.findViewById(R.id.search_content);
                builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String searchContent = search.getText().toString();
                        replaceFragment(new PhotoFragment(searchPhotoAdapter, true));
                        Log.d(TAG, "onClick: " + searchContent);
                        mPresenter.requestPhotoBySearch(searchPhotoAdapter, searchContent);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().setCanceledOnTouchOutside(true);
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
            }
        });
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


    @Override
    public void updateProgress(int value) {
        UploadFragment fragment = (UploadFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        Log.d(TAG, "updateProgress: " + fragment);
        ProgressBar progressBar = fragment.getProgressBar();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(value);
    }

    @Override
    public void showUploadSuc() {
        ToastUtil.show(this, "上传成功");
    }

    public void uploadUserProfileImage(Bitmap userProfileImage){
        mPresenter.requestUploadUserProfileImage(userProfileImage);
    }

    private void getUploadPhotoCount(){
        BmobQuery<Photo> query = new BmobQuery<>();
        query.addWhereEqualTo("isUpload", true);
        query.count(Photo.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if (e == null){
                    uploadPhotoCount = count;
                    Log.d(TAG, "done: 查询成功" + count);
                }else {
                    Log.d(TAG, "done: 查询失败" + e.getMessage());
                }
            }
        });
    }
}
