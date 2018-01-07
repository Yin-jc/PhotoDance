package com.yjc.photodance.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;
import com.yjc.photodance.common.SharedPreferenceDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/7/007.
 */

public class PhotoAdapterForCollections extends RecyclerView.Adapter<PhotoAdapterForCollections.ViewHolder> {

    private Context mContext;
    private List<String> collections = new ArrayList<>();

    public PhotoAdapterForCollections(Context context){
        mContext = context;
        for (int i = 1; i <= MyApplication.getCollectionsCountOnly(); i++){
            collections.add(SharedPreferenceDao.getInstance()
                    .getString("collectionUrl" + String.valueOf(i)));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            image = cardView.findViewById(R.id.photo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.photo_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(collections.get(position))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }
}
