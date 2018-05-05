package com.yjc.photodance.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.ramotion.foldingcell.FoldingCell;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.yjc.photodance.R;
import com.yjc.photodance.common.storage.bean.Video;
import com.yjc.photodance.common.util.DBUtil;
import com.yjc.photodance.common.util.HandleBitmap;
import com.yjc.photodance.dkplayer.widget.controller.StandardVideoController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class ShortVideoAdapter extends RecyclerView.Adapter<ShortVideoAdapter.ViewHolder> {

    private static final int TYPE_LIST = 1;
    private static final int TYPE_STAGGERED = 2;
    private static final String TAG = "ShortVideoAdapter";
    private Context mContext;
    private List<Video> mVideos = new ArrayList<>();
    private ExecutorService cachedThreadPool;
    private Activity mActivity;
    private Bitmap mUserProfileImage;

    public ShortVideoAdapter(Context context, Activity activity){
        mContext = context;
        mActivity = activity;
        cachedThreadPool = Executors.newCachedThreadPool();
        mUserProfileImage = DBUtil.queryUserProfileImage();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private IjkVideoView videoView;
        private StandardVideoController controller;
        private PlayerConfig config;

        private ShineButton likeBtn;
        private TextView title;
        private CircleImageView userImage;
        private TextView username;
        private ImageView comment;
        private TextView commentQuantity;
        private EditText commentContent;
        private ListView commentList;
        private TextView commentPlaceHolder;
        private FoldingCell foldingCell;
        private ImageView firstFrame;
        private TextView createTime;
        private TextView size;
        private TextView likeCount;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            videoView = cardView.findViewById(R.id.video_view);
            controller = new StandardVideoController(mContext);

            likeBtn = cardView.findViewById(R.id.like);
            likeBtn.init(mActivity);
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) likeBtn.getTag();
                    Video video = mVideos.get(position);
                    //不要new一个新的对象，否则数据不会保持
                    video.getLike().add(BmobUser.getCurrentUser().getUsername());
                    video.update(video.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Log.d(TAG, "done: 修改成功");
                            }else {
                                Log.d(TAG, "done: 修改失败" + e.getMessage());
                            }
                        }
                    });
                }
            });

            title = cardView.findViewById(R.id.title);
            userImage = cardView.findViewById(R.id.user_head_image);
            username = cardView.findViewById(R.id.username);
            comment = cardView.findViewById(R.id.comment);
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    final View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_comment, null);
                    commentList = v.findViewById(R.id.comment_list);
                    commentPlaceHolder = v.findViewById(R.id.comment_placeholder);
                    int position = (int) comment.getTag();
                    Video video = mVideos.get(position);
                    if (video.getComment().size() > 0){
                        commentPlaceHolder.setVisibility(View.GONE);
                        commentList.setVisibility(View.VISIBLE);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>
                            (mContext, android.R.layout.simple_list_item_1, video.getComment());
                    commentList.setAdapter(adapter);
                    builder.setView(v);
                    builder.setTitle("评论");
                    builder.setIcon(R.drawable.nav_message);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            commentContent = v.findViewById(R.id.comment_content);
                            int position = (int) comment.getTag();
                            Video video = mVideos.get(position);
                            if (commentContent.getText().toString().length() >= 1){
                                //有效评论才添加
                                String commentStr = BmobUser.getCurrentUser().getUsername() + ":" +
                                        commentContent.getText().toString();
                                video.getComment().add(commentStr);
                                video.update(video.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null){
                                            Log.d(TAG, "done: 修改成功");
                                        }else {
                                            Log.d(TAG, "done: 修改失败" + e.getMessage());
                                        }
                                    }
                                });
                            }
//                            Video newVideo = new Video();
//                            newVideo.getComment().add(commentStr);
//                            newVideo.update(video.getObjectId(), new UpdateListener() {
//                                @Override
//                                public void done(BmobException e) {
//                                    if (e == null){
//                                        Log.d(TAG, "done: 修改成功");
//                                    }else {
//                                        Log.d(TAG, "done: 修改失败" + e.getMessage());
//                                    }
//                                }
//                            });
                        }
                    });
                    //点击取消键退出dialog
                    builder.setNegativeButton(android.R.string.cancel, null);
                    //点击外面退出dialog
                    builder.create().setCanceledOnTouchOutside(true);
                    builder.show();
                }
            });
            commentQuantity = cardView.findViewById(R.id.comment_quantity);

            foldingCell = cardView.findViewById(R.id.folding_cell);
            firstFrame = cardView.findViewById(R.id.first_frame);

            //折叠视图不可设置点击事件（折叠下显示的图片），否则会冲突
            foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: click");
                    foldingCell.toggle(false);
                }
            });

            createTime = cardView.findViewById(R.id.create_time);
            size = cardView.findViewById(R.id.size);
            likeCount = cardView.findViewById(R.id.like_count);

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
        switch (viewType){
            case TYPE_LIST:
                View listView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.short_video_item, parent, false);
                StaggeredGridLayoutManager.LayoutParams ListParams =
                        (StaggeredGridLayoutManager.LayoutParams) listView.getLayoutParams();
                ListParams.setFullSpan(true);
                listView.setLayoutParams(ListParams);
                return new ViewHolder(listView);
            case TYPE_STAGGERED:
                View staggeredView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.video_item_staggered, parent, false);
                StaggeredGridLayoutManager.LayoutParams staggeredParams =
                        (StaggeredGridLayoutManager.LayoutParams) staggeredView.getLayoutParams();
                staggeredParams.setFullSpan(false);
                staggeredView.setLayoutParams(staggeredParams);
                return new ViewHolder(staggeredView);
            default:
                break;
        }
        return new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.short_video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = mVideos.get(position);
        String[] fileName;
        boolean isUpload = video.getUpload();
        // TODO: 2018/4/24/024 第一帧在子线程中获取还没有回传数据
        //显示视频第一帧
        if (isUpload){
            //上传的文件
            Glide.with(mContext)
                    .load(video.getThumbUpload())
                    .into(holder.controller.getThumb());
            //加载折叠视图第一帧
            Glide.with(mContext)
                    .load(video.getThumbUpload())
                    .into(holder.firstFrame);
        }else {
            //网络的文件
            Glide.with(mContext)
                    .load(video.getThumbInternet().getFileUrl())
                    .into(holder.controller.getThumb());
            //加载折叠视图第一帧
            Glide.with(mContext)
                    .load(video.getThumbInternet().getFileUrl())
                    .into(holder.firstFrame);
        }

        switch (holder.getItemViewType()) {
                case TYPE_LIST:
                    fileName = video.getFile().getFilename().split(".mp4");
                    holder.title.setText(fileName[0]);
                    if (video.getUserimage() != null){
                        Glide.with(mContext)
                                .load(video.getUserimage().getFileUrl())
                                .into(holder.userImage);
                    }
                    holder.userImage.setImageBitmap(mUserProfileImage);
                    holder.size.setText(video.getSize());
                    holder.createTime.setText(video.getCreateTime());
                    holder.likeCount.setText(String.valueOf(video.getLike().size()));
                    holder.likeBtn.setTag(position);
                    holder.username.setText(video.getUsername());
                    holder.comment.setTag(position);
                    holder.commentQuantity.setText(String.valueOf(video.getComment().size()));
                    holder.videoView.setTitle(video.getFile().getFilename());
                    holder.videoView.setUrl(video.getFile().getFileUrl());
                    holder.videoView.setVideoController(holder.controller);
                    holder.videoView.setPlayerConfig(holder.config);
                    break;
                case TYPE_STAGGERED:
                    fileName = video.getFile().getFilename().split(".mp4");
                    holder.title.setText(fileName[0]);
                    if (video.getUserimage() != null){
                        Glide.with(mContext)
                                .load(video.getUserimage().getFileUrl())
                                .into(holder.userImage);
                    }
                    holder.userImage.setImageBitmap(mUserProfileImage);
                    holder.username.setText(video.getUsername());
                    holder.size.setText(video.getSize());
                    holder.createTime.setText(video.getCreateTime());
                    holder.likeCount.setText(String.valueOf(video.getLike().size()));
                    holder.comment.setTag(position);
                    holder.commentQuantity.setText(String.valueOf(video.getComment().size()));
                    holder.videoView.setTitle(video.getFile().getFilename());
                    holder.videoView.setUrl(video.getFile().getFileUrl());
                    holder.videoView.setVideoController(holder.controller);
                    holder.videoView.setPlayerConfig(holder.config);
                    break;
                default:
                    break;
            }
    }

    @Override
    public int getItemCount() {
        Log.d("ShortVideoAdapter", "getItemCount: " + mVideos.size());
        for (Video video : mVideos){
            Log.d(TAG, "getItemCount: " + video.getType());
        }
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

    static class MyAsyncTask extends AsyncTask<Video, Void, Bitmap>{
        private MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Bitmap doInBackground(Video... videos) {
            if(retriever != null){
                retriever.setDataSource(videos[0].getFile().getFileUrl(), new HashMap<String, String>());
                return retriever.getFrameAtTime(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute: " + (bitmap == null));
            retriever.release();
        }
    }

    // TODO: 2018/5/2/002 卡顿十分严重，plan B 直接上传第一帧图片
    public void setVideos(List<Video> videos){
        Collections.reverse(videos);
        mVideos = videos;
//        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        for (final Video video : mVideos){
//            Future<Bitmap> future = cachedThreadPool.submit(new Callable<Bitmap>() {
//                @Override
//                public Bitmap call() throws Exception {
//                    try {
//                        retriever.setDataSource(video.getFile().getFileUrl(), new HashMap<String, String>());
//                        return retriever.getFrameAtTime();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }finally {
////                        retriever.release();
//                    }
//                    return null;
//                }
//            });
//            try {
//                video.setThumb(future.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

    }

}
