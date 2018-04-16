package com.yjc.photodance.main.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/29/001.
 * todo 悬浮按钮的逻辑，返回顶部
 * todo navigation中其他item的逻辑
 */
public class MainActivity extends BaseActivity{

    private Uri photoUri;
    private Account account;
    private List<Photo> photos;
    private PhotoAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isFirstEnter = true;
    private RefreshLayout refreshLayout;
    //PhotoApi中的参数
    private int size = 21;
    private int page = 1;
    //判断是下拉刷新还是上拉加载
    private boolean flag = true;

    private AHBottomNavigation bottomNavigation;
    private PhotoAdapter photoAdapter;

    private static final int SELECT_PHOTO_TAB = 0;
    private static final int SELECT_SHORT_VIDEO_TAB = 1;
    private static final int SELECT_LIVE_TAB = 2;
    private static final int SELECT_MESSGAE_TAB = 3;

    private int selectedTab = 0;

    private IMainPresenter mPresenter;
    private IMainModel mModel;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModel = new MainModelImpl();
        mPresenter = new MainPresenterImpl(mModel);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initData() {
        super.initData();

        // TODO: 2018/4/16/016 修改AhBottomNavigation的源码，解决colorful icon的问题

        //BottomNavigation的初始化
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setForceTint(false);
        bottomNavigation.manageFloatingActionButtonBehavior(fab);
        bottomNavigation.setTranslucentNavigationEnabled(true);

        AHBottomNavigationItem photoItem =
                new AHBottomNavigationItem("照片", R.drawable.nav_photo);
        AHBottomNavigationItem shortVideoItem =
                new AHBottomNavigationItem("短视频", R.drawable.nav_short_video);
        AHBottomNavigationItem liveItem =
                new AHBottomNavigationItem("直播", R.drawable.nav_live);
        AHBottomNavigationItem messageItem =
                new AHBottomNavigationItem("消息", R.drawable.nav_message);

        bottomNavigation.addItem(photoItem);
        bottomNavigation.addItem(shortVideoItem);
        bottomNavigation.addItem(liveItem);
        bottomNavigation.addItem(messageItem);

        photoAdapter = new PhotoAdapter(this);
    }

    @Override
    public void initListener() {
        super.initListener();

        // TODO: 2018/1/6/006 以后打开应用也自动刷新
        if (isFirstEnter) {
            //
            refreshLayout.autoRefresh();
            replaceFragment(new PhotoFragment(photoAdapter));
            mPresenter.requestPhoto(photoAdapter, 1, size);
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                flag = true;
                switch (selectedTab){
                    case 0:
                        mPresenter.requestPhoto(photoAdapter, 1, size);
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

        //悬浮按钮的点击事件处理
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/1/6/006 回到顶部
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        refreshLayout = findViewById(R.id.refresh_layout);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        fab = findViewById(R.id.btn_up);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    /**
     * 替换Fragment
     * @param fragment
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
