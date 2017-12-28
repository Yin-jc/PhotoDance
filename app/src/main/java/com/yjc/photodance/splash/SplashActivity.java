package com.yjc.photodance.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yjc.photodance.main.LoginActivity;
import com.yjc.photodance.main.MainActivity;
import com.yjc.photodance.R;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean isLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        isLogin = pref.getBoolean("login", false);

        /**
         * 延时3s加载跳转主界面
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLogin){
                    Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                }else {
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
                //结束此Activity
                finish();
            }
        } , 3000);

    }


}
