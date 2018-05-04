package com.yjc.photodance.common.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.yjc.photodance.R;
import com.yjc.photodance.adapter.CollectionPhotoAdapter;
import com.yjc.photodance.common.util.ToastUtil;
import com.yjc.photodance.main.view.fragment.CollectionFragment;
import com.yjc.photodance.main.view.fragment.InfoFragment;
import com.yjc.photodance.main.view.InfoActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2018/4/15/015.
 * 初始化DrawerLayout
 */

public abstract class BaseActivity extends AppCompatActivity{

    private static final String TAG = "BaseActivity";
    protected Drawer mDrawer;
    private Toolbar mToolbar;

    private String oldPwd;
    private String newPwd;
    private String newPwdConfirm;

    private CollectionPhotoAdapter adapter;

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
        ViewGroup mContentView = findViewById(R.id.content_view);
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

        adapter = new CollectionPhotoAdapter(this);

        SecondaryDrawerItem item_info = new SecondaryDrawerItem()
                .withIdentifier(1).withName("个人信息")
                .withIcon(GoogleMaterial.Icon.gmd_person);
        SecondaryDrawerItem item_collection = new SecondaryDrawerItem()
                .withIdentifier(2).withName("我的收藏")
                .withIcon(GoogleMaterial.Icon.gmd_star);
        SecondaryDrawerItem item_setting = new SecondaryDrawerItem()
                .withIdentifier(3).withName("修改密码")
                .withIcon(GoogleMaterial.Icon.gmd_settings);
        SecondaryDrawerItem item_about = new SecondaryDrawerItem()
                .withIdentifier(4).withName("关于")
                .withIcon(GoogleMaterial.Icon.gmd_info);
        SecondaryDrawerItem item_logoff = new SecondaryDrawerItem()
                .withIdentifier(5).withName("注销")
                .withIcon(GoogleMaterial.Icon.gmd_power_settings_new);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_background)
                .withHeightDp(220)
                .withPaddingBelowHeader(true)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(BmobUser.getCurrentUser().getUsername())
                                // TODO: 2018/5/3/003 信息设置完毕回传数据
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
                        new DividerDrawerItem(),
                        item_setting,
                        item_about,
                        item_logoff)
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
                                replaceFragment(new CollectionFragment(adapter));
                                mDrawer.closeDrawer();
                                break;
                            case 3://change password
                                Log.d("BaseActivity", "item6");
                                changePassword();
                                mDrawer.closeDrawer();
                                break;
                            case 4://about
                                Log.d("BaseActivity", "item5");
                                startActivity(new Intent
                                        (BaseActivity.this, InfoActivity.class));
                                mDrawer.closeDrawer();
                                break;
                            case 5://logoff
                                Log.d("BaseActivity", "item7");
                                //清除缓存用户对象
                                BmobUser.logOut();
                                // 现在的currentUser是null
                                BmobUser currentUser = BmobUser.getCurrentUser();
                                Log.d(TAG, "onItemClick: " + currentUser);
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
        //返回栈，按返回键直接返回上一个Fragment
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {
        mToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void changePassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setCustomTitle(LayoutInflater.from(BaseActivity.this)
                .inflate(R.layout.custom_title_change_pwd_layout, null));
        View v = LayoutInflater.from(BaseActivity.this).inflate(R.layout.dialog_change_password, null);
        builder.setView(v);
        TextInputLayout oldPwdLayout = v.findViewById(R.id.old_pwd_layout);
        final TextInputEditText oldPwdEdit = oldPwdLayout.findViewById(R.id.old_pwd_edit);

        final TextInputLayout newPwdLayout = v.findViewById(R.id.new_pwd_layout);
        final TextInputEditText newPwdEdit = newPwdLayout.findViewById(R.id.new_pwd_edit);

        final TextInputLayout newPwdConfirmLayout = v.findViewById(R.id.new_pwd_confirm_layout);
        final TextInputEditText newPwdConfirmEdit = newPwdConfirmLayout.findViewById(R.id.new_pwd_confirm_edit);


        //设置可以计数
        oldPwdLayout.setCounterEnabled(true);
        newPwdLayout.setCounterEnabled(true);
        newPwdConfirmLayout.setCounterEnabled(true);
        //计数的最大值
        oldPwdLayout.setCounterMaxLength(10);
        newPwdLayout.setCounterMaxLength(10);
        newPwdConfirmLayout.setCounterMaxLength(10);
        //控制密码可见开关启用
        oldPwdLayout.setPasswordVisibilityToggleEnabled(true);
        oldPwdLayout.setPasswordVisibilityToggleDrawable(getResources()
                .getDrawable(R.drawable.show_hide_pwd_selector));
        newPwdLayout.setPasswordVisibilityToggleEnabled(true);
        newPwdLayout.setPasswordVisibilityToggleDrawable(getResources()
                .getDrawable(R.drawable.show_hide_pwd_selector));
        newPwdConfirmLayout.setPasswordVisibilityToggleEnabled(true);
        newPwdConfirmLayout.setPasswordVisibilityToggleDrawable(getResources()
                .getDrawable(R.drawable.show_hide_pwd_selector));

        oldPwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                oldPwd = oldPwdEdit.getText().toString();
            }
        });

        newPwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPwdLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                newPwd = newPwdEdit.getText().toString();
                if (TextUtils.isEmpty(newPwd) || newPwd.length() < 6){
                    newPwdLayout.setError("密码格式错误，不能少于6个字符");
                }
            }
        });

        newPwdConfirmEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPwdConfirmLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                newPwdConfirm = newPwdConfirmEdit.getText().toString();
                if (TextUtils.isEmpty(newPwdConfirm) || newPwdConfirm.length() < 6){
                    newPwdConfirmLayout.setError("密码格式错误，不能少于6个字符");
                }
                if (!TextUtils.equals(newPwdConfirm, newPwd)){
                    newPwdConfirmLayout.setError("两次输入密码不一致");
                }
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BmobQuery<BmobUser> query = new BmobQuery<>();
                query.addWhereEqualTo("password", oldPwd);
                query.findObjects(new FindListener<BmobUser>() {
                    @Override
                    public void done(List<BmobUser> list, BmobException e) {
                        if (e == null){
                            Log.d(TAG, "done: 查询成功");
                            //查找到旧密码存在才允许修改密码
                            BmobUser.updateCurrentUserPassword(oldPwd, newPwd,
                                    new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                                ToastUtil.show(BaseActivity.this,
                                                        "修改成功");
                                            }else{
                                                Log.d(TAG, "done: " + e.getMessage());
                                                ToastUtil.show(BaseActivity.this,
                                                        "修改失败:" + e.getMessage());
                                            }
                                        }
                                    });
                        }else {
                            Log.d(TAG, "done: 查询失败");
                            ToastUtil.show(BaseActivity.this, "旧密码输入错误，请重新输入");
                        }
                    }
                });
            }
        });
        //点击取消键退出dialog
        builder.setNegativeButton(android.R.string.cancel, null);
        //点击外面退出dialog
        builder.create().setCanceledOnTouchOutside(true);
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
    }

}
