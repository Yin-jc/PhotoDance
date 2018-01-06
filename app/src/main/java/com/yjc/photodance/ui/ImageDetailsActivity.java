package com.yjc.photodance.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yjc.photodance.R;

/**
 * Created by Administrator on 2018/1/6/006.
 */

public class ImageDetailsActivity extends AppCompatActivity {

    private ImageView image;
    private ImageView love;
    private ImageView collection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        image = findViewById(R.id.details_image);
        love = findViewById(R.id.love);
        collection = findViewById(R.id.collection);

        String url = getIntent().getStringExtra("imageUrl");
        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(this)
                .load(url)
                .apply(options)
                .into(image);


    }
}
