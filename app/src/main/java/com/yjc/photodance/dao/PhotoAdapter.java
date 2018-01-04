package com.yjc.photodance.dao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yjc.photodance.R;
import com.yjc.photodance.common.MyApplicationContext;

import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> mPhotos;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo);
        }
    }

    public PhotoAdapter(Context context, List<Photo> photos){
        mPhotos = photos;
        mContext = context;
    }

    /**
     * 创建ViewHolder实例
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.photo_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * 对子项的数据进行赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = mPhotos.get(position);
        String photoUrl = photo.getPhotoUrl();
        Glide.with(mContext).load(photoUrl).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if(mPhotos == null){
            return 0;
        }
        return mPhotos.size();
    }

}
