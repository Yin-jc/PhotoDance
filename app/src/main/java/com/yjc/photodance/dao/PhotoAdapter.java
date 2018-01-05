package com.yjc.photodance.dao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yjc.photodance.R;
import com.yjc.photodance.common.MyApplicationContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> mPhotos;
    private Context mContext;

    private static final int MAX_WIDTH = 149;
    private static final int MAX_HEIGHT = 149;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo);
        }
    }

    public PhotoAdapter(Context context){
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
        String photoUrl = photo.url;

        //屏幕的宽度(px值）
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        //Item的宽度，或图片的宽度
        int width = screenWidth/3;

        // TODO: 2018/1/5/005 设置放置的行数 

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.splash_image)
                .override(width, SIZE_ORIGINAL)
                .centerCrop();

        Glide.with(mContext).asBitmap().load(photoUrl).apply(options).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if(mPhotos == null){
            Log.d("Adapter", "0");
            return 0;
        }
        return mPhotos.size();
    }

    public void setPhotos(List<Photo> photos){
        mPhotos = photos;
    }

}
