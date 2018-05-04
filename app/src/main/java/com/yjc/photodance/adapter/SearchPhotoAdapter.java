package com.yjc.photodance.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ramotion.foldingcell.FoldingCell;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BaseActivity;
import com.yjc.photodance.common.jsonBean.searchBean.SearchPhoto;
import com.yjc.photodance.main.model.bean.Photo;
import com.yjc.photodance.main.view.fragment.FullScreenFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/5/3/003.
 */

public class SearchPhotoAdapter extends RecyclerView.Adapter<SearchPhotoAdapter.ViewHolder> {

    private static final String TAG = "SearchPhotoAdapter";
    private List<SearchPhoto.ResultsBean> mSearchPhotos = new ArrayList<>();
    private static Context mContext;
    private String photoUrl;
    private static HashMap<Integer ,String> map = new HashMap<>();
    private List<String> collectionThumbUrls = new ArrayList<>();
    private List<String> likeThunmUrls = new ArrayList<>();


     class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView image;

        private TextView description;
        private CircleImageView userProfileImage;
        private TextView username;
        private ShineButton like;
        private ShineButton collection;
        private FoldingCell foldingCell;
        private ImageView cellContentImage;
        private TextView createTime;
        private TextView size;
        private TextView likeCount;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            image = cardView.findViewById(R.id.photo);
            description = cardView.findViewById(R.id.description);
            userProfileImage = cardView.findViewById(R.id.user_profile_image);
            username = cardView.findViewById(R.id.username);
            like = cardView.findViewById(R.id.like);
            collection = cardView.findViewById(R.id.collection);
            foldingCell = cardView.findViewById(R.id.folding_cell);
            cellContentImage = cardView.findViewById(R.id.photo_cell_content);
            createTime = cardView.findViewById(R.id.create_time);
            size = cardView.findViewById(R.id.size);
            likeCount = cardView.findViewById(R.id.like_count);

            //折叠视图不可设置点击事件（折叠下显示的图片），否则会冲突
            foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: click");
                    foldingCell.toggle(false);
                }
            });

            searchCollectionPhotos();
            searchLikePhotos();
        }
    }

    public SearchPhotoAdapter(Context context){
        mContext = context;

        //屏幕的宽度(px值）
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        //Item的宽度，或图片的宽度
//        width = screenWidth / 3;

        // TODO: 2018/1/5/005 设置放置的行数
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
//        height = screenHeight / 5;

        Log.d(TAG, "SearchPhotoAdapter: " + collectionThumbUrls.size());
        Log.d(TAG, "SearchPhotoAdapter: " + likeThunmUrls.size());
    }

    /**
     * 创建ViewHolder实例
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 对子项的数据进行赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(TAG, String.valueOf(position));

        final SearchPhoto.ResultsBean photo = mSearchPhotos.get(position);

        //缩略图Url
        photoUrl = photo.getUrls().getThumb();

        if (collectionThumbUrls.contains(photoUrl)){
            //收藏的项目
            Log.d(TAG, "onBindViewHolder: true");
            holder.collection.setChecked(true);
        }

        if (likeThunmUrls.contains(position)){
            Log.d(TAG, "onBindViewHolder:  true");
            holder.like.setChecked(true);
        }

        if (photo.getDescription() != null){
            holder.description.setText(photo.getDescription());
        }
        if (photo.getUser().getProfile_image().getSmall() != null){
            Glide.with(mContext)
                    .load(photo.getUser().getProfile_image().getSmall())
                    .into(holder.userProfileImage);
        }

        holder.username.setText(photo.getUser().getName());
        holder.createTime.setText(photo.getCreated_at().substring(0, 10));
        String size = photo.getWidth() + "*" + photo.getHeight();
        holder.size.setText(size);
        holder.likeCount.setText(String.valueOf(photo.getLikes()));

        //收藏点击事件
        holder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo collectionPhoto = new Photo();
                collectionPhoto.setDescription(photo.getDescription());
                collectionPhoto.setUserProfileImage(photo.getUser().getProfile_image().getSmall());
                collectionPhoto.setUsername(photo.getUser().getUsername());
                collectionPhoto.setThumbUrl(photoUrl);
                collectionPhoto.setRegularUrl(photo.getUrls().getRegular());
                collectionPhoto.setRawUrl(photo.getUrls().getRaw());
                collectionPhoto.getCollection().add(BmobUser.getCurrentUser().getUsername());
                collectionPhoto.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.d(TAG, "done: 保存成功");
                        }else {
                            Log.d(TAG, "done: 保存失败" + e.getMessage());
                        }
                    }
                });
            }
        });

        //点赞点击事件
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo likePhoto = new Photo();
                likePhoto.setThumbUrl(photoUrl);
                likePhoto.getLike().add(BmobUser.getCurrentUser().getUsername());
                likePhoto.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.d(TAG, "done: 保存成功");
                        }else {
                            Log.d(TAG, "done: 保存失败" + e.getMessage());
                        }
                    }
                });
            }
        });

        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //占位符
//                .placeholder(R.drawable.splash_image)
//                .override(width, height)
                .centerCrop();

        //为了减少内存，可在此禁止内存缓存
        //加载未折叠视图的图片
        Glide.with(mContext)
                .asBitmap()
                .load(photoUrl)
                .apply(options)
                //设置渐变效果
                .transition(new BitmapTransitionOptions().crossFade(1000))
                .into(holder.image);

        //加载折叠视图的图片
        Glide.with(mContext)
                .asBitmap()
                .load(photoUrl)
                .apply(options)
                //设置渐变效果
                .transition(new BitmapTransitionOptions().crossFade(1000))
                .into(holder.cellContentImage);
//        }

        //图片点击事件
        holder.cellContentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.foldingCell.toggle(false);
                BaseActivity activity = (BaseActivity) mContext;
                activity.replaceFragment(new FullScreenFragment(photo.getUrls().getRegular()));
            }
        });
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

    private void searchCollectionPhotos(){
        BmobQuery<Photo> query = new BmobQuery<>();
        query.addWhereContains("collection", BmobUser.getCurrentUser().getUsername());
        query.findObjects(new FindListener<Photo>() {
            @Override
            public void done(List<Photo> photos, BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: 查询成功");
                    for (Photo photo : photos){
                        collectionThumbUrls.add(photo.getThumbUrl());
                    }
                }else {
                    Log.d(TAG, "done: 查询失败");
                }
            }
        });
    }

    private void searchLikePhotos(){
        BmobQuery<Photo> query = new BmobQuery<>();
        query.addWhereContains("like", BmobUser.getCurrentUser().getUsername());
        query.findObjects(new FindListener<Photo>() {
            @Override
            public void done(List<Photo> photos, BmobException e) {
                if (e == null){
                    Log.d(TAG, "done: 查询成功");
                    for (Photo photo : photos){
                        likeThunmUrls.add(photo.getThumbUrl());
                    }
                }else {
                    Log.d(TAG, "done: 查询失败");
                }
            }
        });
    }
}
