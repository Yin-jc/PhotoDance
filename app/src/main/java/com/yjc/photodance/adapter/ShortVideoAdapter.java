package com.yjc.photodance.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.yjc.photodance.R;
import com.yjc.photodance.common.storage.bean.ShortVideo;
import com.yjc.photodance.dkplayer.widget.controller.StandardVideoController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class ShortVideoAdapter extends RecyclerView.Adapter<ShortVideoAdapter.ViewHolder> {

    private static final int TYPE_LIST = 1;
    private static final int TYPE_STAGGERED = 2;
    private Context mContext;
    private List<ShortVideo> mVideos = new ArrayList<>();

    public ShortVideoAdapter(Context context){
        mContext = context;
        ShortVideo shortVideo1 = new ShortVideo();
        shortVideo1.setTitle("1");
        shortVideo1.setUrl("http://bmob-cdn-18353.b0.upaiyun.com/2018/04/22/dec91e04406b4d9a80719022fa7beb1b.mp4");
        ShortVideo shortVideo2 = new ShortVideo();
        shortVideo2.setTitle("2");
        shortVideo2.setUrl("http://bmob-cdn-18353.b0.upaiyun.com/2018/04/22/32741f8d40a4f5918013975a886eb86f.mp4");
        mVideos.add(shortVideo1);
        mVideos.add(shortVideo2);
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private IjkVideoView videoView;
        private StandardVideoController controller;
        private PlayerConfig config;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            videoView = cardView.findViewById(R.id.video_view);
            controller = new StandardVideoController(mContext);

            //高级设置
            config = new PlayerConfig.Builder()
                    .enableCache() //启用边播边缓存功能
                    .autoRotate() //启用重力感应自动进入/退出全屏功能
                    //启动硬解码，启用后可能导致视频黑屏，音画不同步,此app横竖屏切换黑屏
//                    .enableMediaCodec()
                    .usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
                    .savingProgress() //保存播放进度
                    .addToPlayerManager()//required
                    .build();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.short_video_item, parent, false);
        if(viewType == TYPE_LIST){
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            params.setFullSpan(true);
            view.setLayoutParams(params);
            return new ViewHolder(view);
        }else if(viewType == TYPE_STAGGERED){
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            params.setFullSpan(false);
            view.setLayoutParams(params);
            return new ViewHolder(view);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShortVideo video = mVideos.get(position);

        //显示视频第一帧
//        Glide.with(mContext)
//                .load(video.getThumb())
//                .into(holder.controller.getThumb());

        holder.videoView.setTitle("");
        holder.videoView.setUrl(video.getUrl());
        holder.videoView.setVideoController(holder.controller);
        holder.videoView.setPlayerConfig(holder.config);
    }

    @Override
    public int getItemCount() {
        Log.d("ShortVideoAdapter", "getItemCount: " + mVideos.size());
        return mVideos.size();
    }
}
