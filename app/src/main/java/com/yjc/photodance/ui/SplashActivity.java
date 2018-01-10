package com.yjc.photodance.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.yjc.photodance.util.SharedPreferenceDao;
import com.yjc.photodance.model.Account;
import com.yjc.photodance.R;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class SplashActivity extends AppCompatActivity {

    private boolean isLogin;
    private boolean isLogin_pref;
    private boolean isRegister;
//    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isLogin_pref = SharedPreferenceDao.getInstance().getBoolean("Login");

        Account account = DataSupport.findLast(Account.class);
        isLogin = account.isLogin();
        isRegister = account.isRegister();

        //Android 6.0及以上动态获取权限
//        if(Build.VERSION.SDK_INT >= 23) {
//            for (int i = 0; i < permissions.length; i++) {
//                if (ContextCompat.checkSelfPermission(SplashActivity.this, permissions[i])
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(SplashActivity.this,
//                            permissions,1);
//                }else {
//                    //此分支为用户已授予权限后再次打开应用，直接启动
//                    gotoLoginOrMainActivity();
//                }
//            }
//        }
        if(Build.VERSION.SDK_INT >= 23){
            if(ContextCompat.checkSelfPermission(SplashActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }else {
                gotoLoginOrMainActivity();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
//            case 1:
//                for (int i=0; i < permissions.length; i++){
//                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//
//                    }else {
//                        //只要拒绝其中一个权限就退出应用
//                        finish();
//                    }
//                    gotoLoginOrMainActivity();
//                }
//
//
//                break;
            case 1:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    gotoLoginOrMainActivity();
                }else {
                    finish();
                }
                break;

            default:
                break;
        }
    }

    private void gotoLoginOrMainActivity(){
        /**
         * 延时3s加载跳转主界面
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLogin && isLogin_pref) {
                    Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                }else if(isRegister){
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                } else {
                    Intent selectUserHeadImageIntent = new Intent(SplashActivity.this,
                            SelectUserHeadImageActivity.class);
                    startActivity(selectUserHeadImageIntent);
                }
                //结束此Activity
                finish();
            }
        } , 3000);
    }
}
