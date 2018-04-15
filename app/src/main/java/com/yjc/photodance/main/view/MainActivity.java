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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yjc.photodance.R;
import com.yjc.photodance.account.view.LoginActivity;
import com.yjc.photodance.common.base.BaseActivity;
import com.yjc.photodance.common.http.api.ApiConfig;
import com.yjc.photodance.common.http.api.PhotoApi;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListener();
        initData();
    }

    /**
     * RxJava+Retrofit+OkHttp 网络请求库
     * @param page
     * @param size
     */
    private void getPhotos(final PhotoAdapter adapter, int page, int size){

        RetrofitServiceManager.getInstance().create(PhotoApi.class).getPhotoData(ApiConfig.getApplication_ID(),
                String.valueOf(page), String.valueOf(size))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<Photo>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Photo> photos) {
                if(isFirstEnter || !flag) {
                    isFirstEnter = false;
                    //下拉刷新，防止添加重复数据
                    adapter.setPhotos(photos);
                    adapter.notifyDataSetChanged();
                }
                Log.d("MainActivity", "onNext");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("MainActivity", "onComplete");
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
//        bottomNavigation.setAccentColor(0);
//        bottomNavigation.setInactiveColor(0);
//        bottomNavigation.setColored(true);
//        bottomNavigation.setForceTint(true);
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
    }

    @Override
    public void initListener() {
        super.initListener();

        // TODO: 2018/1/6/006 以后打开应用也自动刷新
        if (isFirstEnter) {
            refreshLayout.autoRefresh();//第一次进入触发自动刷新
        }
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                flag = true;
//                getPhotos(1, size);
//                refreshLayout.finishRefresh(2000);
//            }
//        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                flag = false;
//                refreshlayout.finishLoadmore(1000);
//                getPhotos(++page, size);
//            }
//        });

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case R.id.nav_photo:
                        replaceFragment(new PhotoFragment());
                        break;
                    case R.id.nav_short_video:
                        replaceFragment(new ShortVideoFragment());
                        break;
                    case R.id.nav_live:
                        replaceFragment(new LiveFragment());
                        break;
                    case R.id.nav_message:
                        replaceFragment(new MessageFragment());
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        refreshLayout = findViewById(R.id.refresh_layout);
        bottomNavigation = findViewById(R.id.bottom_navigation);

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
