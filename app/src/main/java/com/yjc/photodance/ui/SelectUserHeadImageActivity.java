package com.yjc.photodance.ui;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yjc.photodance.account.view.LoginActivity;
import com.yjc.photodance.common.util.HandleBitmap;
//import com.yjc.photodance.common.util.HandleBitmap;
import com.yjc.photodance.model.Account;
import com.yjc.photodance.R;
import com.yjc.photodance.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/3/003.
 */

public class SelectUserHeadImageActivity extends AppCompatActivity {

    private Button next;
    private TextView please;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_userheadimage);

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLoginActivity();
            }
        });

        please = findViewById(R.id.please_set_image);
    }


    private void gotoLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
//        Bundle bundle = new Bundle();
//        //Bitmap默认实现Parcelable接口，直接传递即可
//        bundle.putParcelable("userHeadImageBitmap", userHeadImageBitmap);
//        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }



}
