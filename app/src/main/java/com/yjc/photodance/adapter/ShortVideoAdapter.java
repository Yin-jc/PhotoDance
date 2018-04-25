package com.yjc.photodance.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.yjc.photodance.R;
import com.yjc.photodance.common.storage.bean.Video;
import com.yjc.photodance.dkplayer.widget.controller.StandardVideoController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class ShortVideoAdapter extends RecyclerView.Adapter<ShortVideoAdapter.ViewHolder> {

    private static final int TYPE_LIST = 1;
    private static final int TYPE_STAGGERED = 2;
    private static final String TAG = "ShortVideoAdapter";
    private Context mContext;
    private List<Video> mVideos = new ArrayList<>();
    private static Bitmap firstFrameBitmap;

    public ShortVideoAdapter(Context context){
        mContext = context;
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
        StaggeredGridLayoutManager.LayoutParams params =
                (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        switch (viewType){
            case TYPE_LIST:
                params.setFullSpan(true);
                view.setLayoutParams(params);
                return new ViewHolder(view);
            case TYPE_STAGGERED:
                params.setFullSpan(false);
                view.setLayoutParams(params);
                return new ViewHolder(view);
            default:
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mVideos != null) {
            Video video = mVideos.get(position);
//        new MyAsyncTask().execute(video);
            // TODO: 2018/4/24/024 第一帧在子线程中获取还没有回传数据
            //显示视频第一帧
            Glide.with(mContext)
                    .load(firstFrameBitmap)
                    .into(holder.controller.getThumb());
            switch (holder.getItemViewType()) {
                case TYPE_LIST:
                    holder.videoView.setTitle(video.getFilename());
                    holder.videoView.setUrl(video.getFileUrl());
                    holder.videoView.setVideoController(holder.controller);
                    holder.videoView.setPlayerConfig(holder.config);
                    break;
                case TYPE_STAGGERED:
                    holder.videoView.setTitle(video.getFilename());
                    holder.videoView.setUrl(video.getFileUrl());
                    holder.videoView.setVideoController(holder.controller);
                    holder.videoView.setPlayerConfig(holder.config);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.d("ShortVideoAdapter", "getItemCount: " + mVideos.size());
        return mVideos.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mVideos.get(position).getType()){
            case "TYPE_STAGGERED":
                return TYPE_STAGGERED;
            case "TYPE_LIST":
                return TYPE_LIST;
            default:
                break;
        }
        return 0;
    }

    /**
     * 获取视频第一帧
     * @param bitmap
     * @return
     */
    private static void setFirstFrameBitmap(Bitmap bitmap){
        firstFrameBitmap = bitmap;
    }

    static class MyAsyncTask extends AsyncTask<Video, Void, Bitmap>{
        private MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Bitmap doInBackground(Video... videos) {
            if(retriever != null){
                retriever.setDataSource(videos[0].getFileUrl(), new HashMap<String, String>());
                return retriever.getFrameAtTime(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute: " + (bitmap == null));
            setFirstFrameBitmap(bitmap);
            retriever.release();
        }
    }

    public void setVideos(List<Video> videos){
        mVideos = videos;
    }

}
