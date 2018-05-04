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
import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BaseActivity;
import com.yjc.photodance.main.model.bean.Photo;
import com.yjc.photodance.main.view.fragment.FullScreenFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/7/007.
 */

public class CollectionPhotoAdapter extends RecyclerView.Adapter<CollectionPhotoAdapter.ViewHolder> {

    private static final String TAG = "CollectionPhotoAdapter";
    private Context mContext;
    private List<Photo> collectionPhotos;

    public CollectionPhotoAdapter(Context context){
        mContext = context;
        searchCollectionPhotos();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private ImageView image;
        private TextView description;
        private CircleImageView userProfileImage;
        private TextView username;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            image = cardView.findViewById(R.id.collection_photo);
            description = cardView.findViewById(R.id.description);
            userProfileImage = cardView.findViewById(R.id.user_profile_image);
            username = cardView.findViewById(R.id.username);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.collection_photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Photo photo = collectionPhotos.get(position);
        holder.description.setText(photo.getDescription());
        holder.username.setText(photo.getUsername());
        Glide.with(mContext)
                .load(photo.getUserProfileImage())
                .into(holder.userProfileImage);
        Glide.with(mContext)
                .load(photo.getThumbUrl())
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity activity = (BaseActivity) mContext;
                activity.replaceFragment(new FullScreenFragment(photo.getRegularUrl()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectionPhotos.size();
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
                    collectionPhotos = photos;
                    Log.d(TAG, "done: " + collectionPhotos.size());
                }else {
                    Log.d(TAG, "done: 查询失败");
                }
            }
        });
    }
}
