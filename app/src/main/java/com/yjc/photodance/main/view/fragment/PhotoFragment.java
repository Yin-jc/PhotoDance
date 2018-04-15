package com.yjc.photodance.main.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yjc.photodance.R;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.common.base.BaseFragment;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public class PhotoFragment extends BaseFragment {

    private RecyclerView photoRecycler;
    private PhotoAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        photoRecycler = getActivity().findViewById(R.id.photo_recycler_view);

        //RecyclerView的设置
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        //StaggeredGridLayoutManager设置空隙处理方式为 不处理
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        photoRecycler.setLayoutManager(layoutManager);
        adapter = new PhotoAdapter(getContext());
        photoRecycler.setAdapter(adapter);
    }
}
