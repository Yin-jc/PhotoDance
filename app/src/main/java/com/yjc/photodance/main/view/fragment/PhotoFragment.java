package com.yjc.photodance.main.view.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;

import com.yjc.photodance.R;
import com.yjc.photodance.adapter.PhotoAdapter;
import com.yjc.photodance.adapter.SearchPhotoAdapter;
import com.yjc.photodance.common.base.BaseFragment;
import com.yjc.photodance.common.storage.SharedPreferenceDao;
import com.yjc.photodance.main.view.MainActivity;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public class PhotoFragment extends BaseFragment {

    private static final String TAG = "PhotoFragment";
    private SearchPhotoAdapter searchAdapter;
    private RecyclerView photoRecycler;
    private PhotoAdapter adapter;
    private boolean isSearch;

    public PhotoFragment(){
    }

    public PhotoFragment(PhotoAdapter adapter, boolean isSearch){
        SharedPreferenceDao.getInstance().saveBoolean("needLoadData", true);
        this.adapter = adapter;
        this.isSearch = isSearch;
    }

    public PhotoFragment(SearchPhotoAdapter adapter, boolean isSearch){
        searchAdapter = adapter;
        this.isSearch = isSearch;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        photoRecycler = getActivity().findViewById(R.id.photo_recycler_view);

        //RecyclerView的设置
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        photoRecycler.setLayoutManager(layoutManager);
        if (isSearch){
            photoRecycler.setAdapter(searchAdapter);
        }else {
            photoRecycler.setAdapter(adapter);
        }

        photoRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0){

                }
            }
        });


    }

}
