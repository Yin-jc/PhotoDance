package com.yjc.photodance.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.yjc.photodance.BeanForJson.Photo;
import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;
import com.yjc.photodance.ui.ImageDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> mPhotos = new ArrayList<>();
    private static Context mContext;
    private int page = 0;
    private String photoUrl;
    private static HashMap<Integer ,String> map = new HashMap<>();
    private int count = 1;

    private static final int MAX_WIDTH = 149;
    private static final int MAX_HEIGHT = 149;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            image = cardView.findViewById(R.id.photo);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(MyApplication.getMyApplicationContext(), "onClick",
//                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, ImageDetailsActivity.class);
                    int p = (int) view.getTag(R.id.image_tag);
                    intent.putExtra("imageUrl", map.get(p));
                    mContext.startActivity(intent);
                }
            });
            //启用图片缩放功能
//            image.enable();
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
//        if (mPhotos.size() == page) {
        Photo photo = mPhotos.get(position);
        photoUrl = photo.getUrls().getThumb();
        map.put(position, photo.getUrls().getRegular());
        //对于glide下设置imageView的tag的正确处理
        holder.image.setTag(R.id.image_tag, position);

        //屏幕的宽度(px值）
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        //Item的宽度，或图片的宽度
        int width = screenWidth / 3;

        // TODO: 2018/1/5/005 设置放置的行数
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        int height = screenHeight / 5;

        RequestOptions options = new RequestOptions()
                //占位符
                .placeholder(R.mipmap.splash_image)
                .override(width, height)
                .centerCrop();

        //为了减少内存，可在此禁止内存缓存
        Glide.with(mContext)
                .asBitmap()
                .load(photoUrl)
                .apply(options)
                //设置渐变效果
                .transition(new BitmapTransitionOptions().crossFade(1000))
                .into(holder.image);
//        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
//        if (mPhotos != null) {
//            if (mPhotos.get(page++) == null) {
//                Log.d("Adapter", "0");
//                return 0;
//            }
//            return mPhotos.get(page++).size();
//        }
//        return 0;
//
        return mPhotos.size();
    }

    public void setPhotos(List<Photo> photos){
        mPhotos.addAll(photos);
    }

}
