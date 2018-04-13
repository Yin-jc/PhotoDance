package com.yjc.photodance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;
import com.yjc.photodance.common.storage.SharedPreferenceDao;
import com.yjc.photodance.model.Details;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/6/006.
 */

public class ImageDetailsActivity extends AppCompatActivity {

    private ImageView image;
    private ImageView love;
    private ImageView collection;
    private String url;
    private ImageView back;
    private CircleImageView profileImage;
    private TextView detailsText;
    private Details details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        MyApplication.setIsFromCollections(false);

        Toolbar toolbar=findViewById(R.id.toolbar_full_screen);
        setSupportActionBar(toolbar);

        image = findViewById(R.id.details_image);

        url = getIntent().getStringExtra("imageUrl");
        details = getIntent().getParcelableExtra("details");

        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(this)
                .load(url)
                .apply(options)
                .into(image);

        back = findViewById(R.id.back_details);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageDetailsActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        love = findViewById(R.id.love);
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                love.setImageResource(R.drawable.redheart);
            }
        });

        collection = findViewById(R.id.collection);

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPreferenceDao.getInstance().getString("collectionUrl" +
                String.valueOf(MyApplication.getCollectionsCountOnly()-1)) != url){
                    SharedPreferenceDao.getInstance().saveString("collectionUrl" +
                            String.valueOf(MyApplication.getCollectionsCount()), url);
                }

                Toast.makeText(ImageDetailsActivity.this, "已收藏，可以在我的收藏中查看",
                        Toast.LENGTH_SHORT).show();
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageDetailsActivity.this, ImageFullScreenActivity.class);
                intent.putExtra("imageUrl", url);
                startActivity(intent);
            }
        });

//        profileImage = findViewById(R.id.profile_image);
        detailsText = findViewById(R.id.details);
        detailsText.setText(details.toString());
    }
}
