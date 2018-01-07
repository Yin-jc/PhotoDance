package com.yjc.photodance.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.yjc.photodance.ui.ImageDetailsActivity;
import com.yjc.photodance.ui.ImageFullScreenActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/7/007.
 */

public class PhotoAdapterForCollections extends RecyclerView.Adapter<PhotoAdapterForCollections.ViewHolder> {

    private static Context mContext;
    private List<String> collections = new ArrayList<>();
    private static HashMap<Integer ,String> map = new HashMap<>();
    private static int width;
    private static int height;

    public PhotoAdapterForCollections(Context context){
        mContext = context;
        for (int i = 1; i <= MyApplication.getCollectionsCountOnly(); i++){
            collections.add(SharedPreferenceDao.getInstance()
                    .getString("collectionUrl" + String.valueOf(i)));
        }

        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        //Item的宽度，或图片的宽度
        width = screenWidth / 3;

        // TODO: 2018/1/5/005 设置放置的行数
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        height = screenHeight / 5;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            image = cardView.findViewById(R.id.photo);
            CardView.LayoutParams params = (CardView.LayoutParams) image.getLayoutParams();
            params.width = width;
            params.height = height;
            image.setLayoutParams(params);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(MyApplication.getMyApplicationContext(), "onClick",
//                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, ImageFullScreenActivity.class);
                    int p = (int) view.getTag(R.id.image_tag);
                    intent.putExtra("imageUrlFromCollections", map.get(p));
                    mContext.startActivity(intent);
                }
            });
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
        holder.image.setTag(R.id.image_tag, position);
        map.put(position, collections.get(position));
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }
}
