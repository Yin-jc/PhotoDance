package com.yjc.photodance.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.yjc.photodance.R;
import com.yjc.photodance.bean.searchBean.SearchPhoto;
import com.yjc.photodance.model.Details;
import com.yjc.photodance.ui.ImageDetailsActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/5/3/003.
 */

public class SearchPhotoAdapter extends RecyclerView.Adapter<SearchPhotoAdapter.ViewHolder> {

    private static final String TAG = "SearchPhotoAdapter";
    private List<SearchPhoto.ResultsBean> mSearchPhotos = new ArrayList<>();
    private static Context mContext;
    private int page = 0;
    private String photoUrl;
    private static HashMap<Integer ,String> map = new HashMap<>();
    private int count = 1;
    private static int width;
    private static int height;

//    private static final int MAX_WIDTH = 149;
//    private static final int MAX_HEIGHT = 149;

     class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image;

        private TextView description;
        private CircleImageView userProfileImage;
        private TextView username;
        private ShineButton like;
        private ShineButton collection;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            image = cardView.findViewById(R.id.photo);
            description = cardView.findViewById(R.id.description);
            userProfileImage = cardView.findViewById(R.id.user_profile_image);
            username = cardView.findViewById(R.id.username);
            like = cardView.findViewById(R.id.like);
            collection = cardView.findViewById(R.id.collection);

//            CardView.LayoutParams params = (CardView.LayoutParams) image.getLayoutParams();
//            params.width = width;
//            params.height = height;
//            image.setLayoutParams(params);
//            int position = this.getAdapterPosition();
//            int position = (int) description.getTag();
//            SearchPhoto.ResultsBean photo = mSearchPhotos.get(position);


            // TODO: 2018/5/3/003 like 和 collection点击事件

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2018/5/3/003 全屏
//                    Toast.makeText(MyApplication.getMyApplicationContext(), "onClick",
//                            Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(mContext, ImageDetailsActivity.class);
//                    int p = (int) view.getTag(R.id.image_tag);
//                    List<Details> detailsList = DataSupport.select("id", "username", "location")
//                            .where("position = ?", String.valueOf(p))
//                            .find(Details.class);
//                    intent.putExtra("imageUrl", map.get(p));
//                    intent.putExtra("details", (Parcelable) detailsList.get(0));
//                    mContext.startActivity(intent);
                }
            });
        }
    }

    public SearchPhotoAdapter(Context context){
        mContext = context;

        //屏幕的宽度(px值）
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        //Item的宽度，或图片的宽度
        width = screenWidth / 3;

        // TODO: 2018/1/5/005 设置放置的行数
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        height = screenHeight / 5;
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
        Log.d(TAG, String.valueOf(position));

        SearchPhoto.ResultsBean photo = mSearchPhotos.get(position);
        photoUrl = photo.getUrls().getThumb();

//        holder.description.setTag(position);

        holder.description.setText(photo.getDescription());
        Glide.with(mContext)
                .load(photo.getUser().getProfile_image().getSmall())
                .into(holder.userProfileImage);
        holder.username.setText(photo.getUser().getName());

//        map.put(position, photo.getUrls().getRegular());
        //对于glide下设置imageView的tag的正确处理
//        holder.image.setTag(R.id.image_tag, position);

//        Details details = new Details();
//        details.setPosition(position);
//        details.setUserId(photo.getUser().getId());
//        details.setUsername(photo.getUser().getUsername());
//        details.setLocation(photo.getUser().getLocation());
//        details.setProfileImage(photo.getUser().getPortfolioUrl());
//        details.save();

        RequestOptions options = new RequestOptions()
                //占位符
//                .placeholder(R.drawable.splash_image)
//                .override(width, height)
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
        Log.d(TAG, "getItemCount: " + mSearchPhotos.size());
        return mSearchPhotos.size();
    }

    public void setSearchPhotos(List<SearchPhoto.ResultsBean> photos){
        if (mSearchPhotos != null){
            mSearchPhotos.clear();
        }
        //addAll 添加到list尾部，不会覆盖之前的数据
        mSearchPhotos.addAll(photos);
    }
}
