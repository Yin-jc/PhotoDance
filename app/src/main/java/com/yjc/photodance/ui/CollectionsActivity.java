package com.yjc.photodance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.yjc.photodance.R;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.PhotoAdapterForCollections;

/**
 * Created by Administrator on 2018/1/7/007.
 */

public class CollectionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PhotoAdapterForCollections adapter;
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        Toolbar toolbar=findViewById(R.id.toolbar_full_screen);
        setSupportActionBar(toolbar);

        back = findViewById(R.id.back_collections);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(CollectionsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //RecyclerView的设置
        recyclerView = findViewById(R.id.recycler_view_collections);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        //StaggeredGridLayoutManager设置空隙处理方式为 不处理
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoAdapterForCollections(this);
        recyclerView.setAdapter(adapter);
    }
}
