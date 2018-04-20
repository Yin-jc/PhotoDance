package com.yjc.photodance.common.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yjc.photodance.R;
import com.yjc.photodance.account.view.LoginActivity;
import com.yjc.photodance.common.util.MultiMedia;
import com.yjc.photodance.main.view.MainActivity;
import com.yjc.photodance.main.view.fragment.InfoFragment;
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

    private ViewGroup mContentView;

    private Drawer mDrawer;
    private Toolbar mToolbar;
    private String mUsername = "Yjc";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    /**
     * 初始化监听器
     */
    public void initListener(){
    }

    /**
     * 初始化控件
     */
    public void initView(){
        mContentView = findViewById(R.id.content_view);
        mContentView.addView(View.inflate(this, getContentView(), null));
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

        SecondaryDrawerItem item_info = new SecondaryDrawerItem()
                .withIdentifier(1).withName("个人信息")
                .withIcon(GoogleMaterial.Icon.gmd_person);
        SecondaryDrawerItem item_collection = new SecondaryDrawerItem()
                .withIdentifier(2).withName("我的收藏")
                .withIcon(GoogleMaterial.Icon.gmd_collections);
        SecondaryDrawerItem item_star = new SecondaryDrawerItem()
                .withIdentifier(3).withName("我的点赞")
                .withIcon(GoogleMaterial.Icon.gmd_star);
        SecondaryDrawerItem item_upload = new SecondaryDrawerItem()
                .withIdentifier(4).withName("我的上传")
                .withIcon(GoogleMaterial.Icon.gmd_file_upload);
        SecondaryDrawerItem item_setting = new SecondaryDrawerItem()
                .withIdentifier(5).withName("设置")
                .withIcon(GoogleMaterial.Icon.gmd_settings);
        SecondaryDrawerItem item_about = new SecondaryDrawerItem()
                .withIdentifier(6).withName("关于")
                .withIcon(GoogleMaterial.Icon.gmd_info);
        SecondaryDrawerItem item_logoff = new SecondaryDrawerItem()
                .withIdentifier(7).withName("注销")
                .withIcon(GoogleMaterial.Icon.gmd_power_settings_new);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.shape_gradient_1)
                .withHeightDp(220)
                .withPaddingBelowHeader(true)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(mUsername)
                                .withEmail("example@example.com")
                                .withIcon(getResources().getDrawable(R.drawable.personal_center))
                )
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withSliderBackgroundDrawableRes(R.drawable.drawer_background)
                .withToolbar(mToolbar)
                .withSelectedItem(-1)
                .addDrawerItems(
                        item_info,
                        item_collection,
                        item_star,
                        item_upload,
                        new DividerDrawerItem(),
                        item_about,
                        item_setting,
                        item_logoff)
                .withFooter(R.layout.drawer_footer)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()){
                            case 1://info
                                Log.d("BaseActivity", "item1");
                                replaceFragment(new InfoFragment());
                                mDrawer.closeDrawer();
                                break;
                            case 2://collection
                                Log.d("BaseActivity", "item2");
                                break;
                            case 3://star
                                Log.d("BaseActivity", "item3");
                                break;
                            case 4://upload
                                Log.d("BaseActivity", "item4");
                                break;
                            case 5://about
                                Log.d("BaseActivity", "item5");
                                break;
                            case 6://setting
                                Log.d("BaseActivity", "item6");
                                break;
                            case 7://logoff
                                Log.d("BaseActivity", "item7");
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                })
                .build();
    }

    /**
     * 替换Fragment
     * @param fragment
     */
    public void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {
        mToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
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

//    private void fun(){
//        try {
//            Class mainClass = Class.forName("com.yjc.photodance.main.view.MainActivity");
//            toolbar = (Toolbar) mainClass.getDeclaredField("toolbar").getGenericType();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//    }

}
