package com.yjc.photodance.main.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.yjc.photodance.R;
import com.yjc.photodance.adapter.CollectionPhotoAdapter;
import com.yjc.photodance.adapter.ShortVideoAdapter;
import com.yjc.photodance.common.base.BaseFragment;
import com.yjc.photodance.main.model.bean.Photo;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2018/5/4/004.
 */

public class CollectionFragment extends BaseFragment {

    private static final String TAG = "CollectionFragment";
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

        searchCollectionPhotos();

        RecyclerView recyclerView = getActivity().findViewById(R.id.collection_photo_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void searchCollectionPhotos(){
        BmobQuery<Photo> query = new BmobQuery<>();
        String[] users = {BmobUser.getCurrentUser().getUsername()};
        query.addWhereContainsAll("collection", Arrays.asList(users));
        query.findObjects(new FindListener<Photo>() {
            @Override
            public void done(List<Photo> photos, BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: 查询成功");
                    adapter.setPhotos(photos);
                    adapter.notifyDataSetChanged();
                }else {
                    Log.d(TAG, "done: 查询失败");
                }
            }
        });
    }
}
