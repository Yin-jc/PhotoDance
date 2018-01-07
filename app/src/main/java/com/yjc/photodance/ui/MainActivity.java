package com.yjc.photodance.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yjc.photodance.R;
import com.yjc.photodance.api.ApiConfig;
import com.yjc.photodance.api.PhotoApi;
import com.yjc.photodance.util.MultiMedia;
import com.yjc.photodance.util.SharedPreferenceDao;
import com.yjc.photodance.model.Account;
import com.yjc.photodance.bean.Photo;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.network.RetrofitServiceManager;

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
public class MainActivity extends AppCompatActivity{

    private Uri photoUri;
    private CircleImageView personalCenter;
    private ImageView takePhoto;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private Account account;
    private CircleImageView userHeadImage;
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
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        2s后将设置为未登录状态
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    SharedPreferenceDao.getInstance().saveBoolean("Login", false);
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
        refreshLayout = findViewById(R.id.refresh_layout);
        fab = findViewById(R.id.btn_up);

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
                switch (item.getItemId()){
                    //注销，返回登录界面
                    case R.id.nav_log_off:
                        Intent intentToLogin =
                                new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intentToLogin);
                        break;

                    //关于
                    case R.id.nav_about:
                        Intent intentToAbout =
                                new Intent(MainActivity.this, InfoActivity.class);
                        startActivity(intentToAbout);
                        break;

                    //设置
                    case R.id.nav_setting:
                        break;

                    //收藏
                    case R.id.nav_collection:
                        Intent intentToCollection =
                                new Intent(MainActivity.this, CollectionsActivity.class);
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


        // TODO: 2018/1/6/006 以后打开应用也自动刷新
        if (isFirstEnter) {
            refreshLayout.autoRefresh();//第一次进入触发自动刷新
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                flag = true;
                getPhotos(1, size);
                refreshLayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                flag = false;
                refreshlayout.finishLoadmore(1000);
                getPhotos(++page, size);
            }
        });

        //RecyclerView的设置
        recyclerView = findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        //StaggeredGridLayoutManager设置空隙处理方式为 不处理
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoAdapter(this);
        recyclerView.setAdapter(adapter);

        //悬浮按钮的点击事件处理
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/1/6/006 回到顶部
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/方正华隶.ttf");
    }

    /**
     * RxJava+Retrofit+OkHttp 网络请求库
     * @param page
     * @param size
     */
    private void getPhotos(int page, int size){

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
//                Log.d("MainActivity", data.photos.get(0).id);
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

}
