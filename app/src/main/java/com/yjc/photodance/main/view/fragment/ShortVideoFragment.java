package com.yjc.photodance.main.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.yjc.photodance.R;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.common.base.BaseFragment;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public class ShortVideoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ShortVideoAdapter adapter;

    public ShortVideoFragment(){
        this(null);
    }
    @SuppressLint("ValidFragment")
    public ShortVideoFragment(ShortVideoAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_short_video;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.short_video_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}
