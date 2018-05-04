package com.yjc.photodance.main.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.yjc.photodance.R;
import com.yjc.photodance.adapter.CollectionPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.common.base.BaseFragment;

/**
 * Created by Administrator on 2018/5/4/004.
 */

public class CollectionFragment extends BaseFragment {

    private CollectionPhotoAdapter adapter;

    public CollectionFragment(){
        this(null);
    }
    @SuppressLint("ValidFragment")
    public CollectionFragment(CollectionPhotoAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.collection_photo_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}
