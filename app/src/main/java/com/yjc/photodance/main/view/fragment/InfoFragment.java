package com.yjc.photodance.main.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BaseFragment;
import com.yjc.photodance.common.storage.SharedPreferenceDao;
import com.yjc.photodance.common.storage.bean.Info;
import com.yjc.photodance.common.storage.bean.Video;
import com.yjc.photodance.common.util.HandleBitmap;
import com.yjc.photodance.common.util.ToastUtil;
import com.yjc.photodance.main.view.MainActivity;
import com.yjc.photodance.main.view.popupwindow.SelectPicPopupWindow;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.yjc.photodance.common.util.MultiMedia.handleFile;

/**
 * Created by Administrator on 2018/4/20/020.
 * todo 数据库实现数据存储
 */

public class InfoFragment extends BaseFragment {

    private static final String TAG = "InfoFragment";
    // TODO: 2018/4/21/021 换背景图片
    private ImageView mBackground;
    private CircleImageView mUserHeadImage;
    private TextView mUsername;
    private EditText mBase;
    private EditText mEmail;
    private EditText mHome;
    private EditText mCompany;
    private EditText mProfession;
    private EditText mSignature;
    private RadioGroup mSex;
    private RadioButton mMale;
    private RadioButton mFemale;
    private Button mSaveBtn;
    private HashSet<String> info = new HashSet<>();

    private PopupWindow popupWindow;
    private Bitmap userHeadImageBitmap;
    private Bitmap backgroundBitmap;
    private static final int CHOOSE_PHOTO_FOR_BACKGROUND = 2;
    private static final int CHOOSE_PHOTO_FOR_USERHEADIMAGE = -2;
    private static final int TYPE = 1;
    private static final int TAKE_PHOTO = 3;

    private String imagePathForBackground;
    private String imagePathForUserHeadImage;
    private Uri photoUri;

    private String mSexStr;
    private String mUsernameStr;
    private String mPhoneNumStr;

    private boolean isFirstEnter = SharedPreferenceDao.getInstance()
            .getBoolean("isFirstEnterInfoFragment");
//    private boolean isFirstEnter = true;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        //短生命周期对象引用长生命周期对象，不会造成内存泄漏
        MainActivity activity = (MainActivity) getActivity();
        mPhoneNumStr = activity.getPhoneNum();
        Log.d(TAG, "onActivityCreated: " + mPhoneNumStr);
        mUsernameStr = activity.getUsername();

        Log.d(TAG, "onActivityCreated: " + isFirstEnter);
        initData();
        initListener();
    }

    // TODO: 2018/4/20/020 初始化username 注册时使用sharedPreferenceDao存储，在此取出
    private void initData(){
//        HashSet<String> info_dao = SharedPreferenceDao.getInstance().getSet("info");
        //HashSet转ArrayList
//        ArrayList<String> info_array = new ArrayList<>(info_dao);

        //username 不可修改
        mUsername.setText(mUsernameStr);

        if(! isFirstEnter){
            List<Info> infos = DataSupport
                    .where("phoneNum = ?", mPhoneNumStr)
                    .find(Info.class);
            Info info = infos.get(0);
            mBackground.setImageBitmap(BitmapFactory.decodeByteArray(info.getBackgroundImage(),
                    0, info.getBackgroundImage().length));
            mUserHeadImage.setImageBitmap(BitmapFactory.decodeByteArray(info.getUserHeadImage(),
                    0, info.getUserHeadImage().length));
            mBase.setText(info.getBase());
            // TODO: 2018/4/21/021 获取性别设置
            mEmail.setText(info.getEmail());
            mHome.setText(info.getHome());
            mCompany.setText(info.getCompany());
            mProfession.setText(info.getProfession());
            mSignature.setText(info.getSignature());
        }
    }

    private void initView(){
        mBackground = getActivity().findViewById(R.id.background_info);
        mUserHeadImage = getActivity().findViewById(R.id.userHeadImage_info);
        mUsername = getActivity().findViewById(R.id.username_info);
        mBase = getActivity().findViewById(R.id.base_info);
        mEmail = getActivity().findViewById(R.id.email_info);
        mHome = getActivity().findViewById(R.id.home_info);
        mCompany = getActivity().findViewById(R.id.company_info);
        mProfession = getActivity().findViewById(R.id.profession_info);
        mSignature = getActivity().findViewById(R.id.signature_info);
        mSex = getActivity().findViewById(R.id.rg_sex);
        mMale = getActivity().findViewById(R.id.rb_male);
        mFemale = getActivity().findViewById(R.id.rb_female);
        mSaveBtn = getActivity().findViewById(R.id.save_info);
    }

    private void saveOrUpdateData(boolean isFirstEnter){
        Info info = new Info(mPhoneNumStr, mUsernameStr);
        if(backgroundBitmap != null && userHeadImageBitmap != null){
            info.setBackgroundImage(HandleBitmap.img(backgroundBitmap));
            info.setUserHeadImage(HandleBitmap.img(userHeadImageBitmap));
        }
        info.setBase(mBase.getText().toString());
        info.setSex(mSexStr);
        info.setEmail(mEmail.getText().toString());
        info.setHome(mHome.getText().toString());
        info.setCompany(mCompany.getText().toString());
        info.setProfession(mProfession.getText().toString());
        info.setSignature(mSignature.getText().toString());
        if(isFirstEnter){
            //第一次编辑信息就保存
            boolean result = info.save();
            Log.d(TAG, "save success?" + String.valueOf(result));
//            uploadUserProfileImage(userHeadImageBitmap);
        }else {
            //之后修改信息就更新
            info.updateAll("phoneNum = ?", mPhoneNumStr);
//            uploadUserProfileImage(userHeadImageBitmap);
        }

    }

    private void initListener(){

        popupWindow=new SelectPicPopupWindow(MyApplication.getMyApplicationContext(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()){
                            case R.id.take_photo:
                                takePhoto();
                                break;
                            case R.id.select_photo:
                                selectPhoto(CHOOSE_PHOTO_FOR_USERHEADIMAGE);
                                break;
                            default:
                                break;
                        }
                    }
                });

        mSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rb_male:
                        mSexStr = mMale.getText().toString();
                        break;
                    case R.id.rb_female:
                        mSexStr = mFemale.getText().toString();
                        break;
                    default:
                        break;
                }
            }
        });

        mUserHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出popupwindow
                popupWindow.showAtLocation(getActivity().getWindow().getDecorView(),
                        Gravity.BOTTOM, 0, 0);
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + isFirstEnter);
                saveOrUpdateData(isFirstEnter);
                SharedPreferenceDao.getInstance().saveBoolean("isFirstEnterInfoFragment",
                        false);
                ToastUtil.show(getActivity(), "保存成功");
                Log.d(TAG, "onClick: " + isFirstEnter);
                // TODO: 2018/4/21/021 退出此Fragment
            }
        });

        mBackground.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectPhoto(CHOOSE_PHOTO_FOR_BACKGROUND);
                return true;
            }
        });
    }

    private void takePhoto(){
        //创建File对象，用于存储照片
        //存放在当前应用关联缓存目录，可以跳过权限验证
        File photo=new File(MyApplication.getMyApplicationContext().getExternalCacheDir() ,
                "photo.jpg");
        if(photo.exists()){
            photo.delete();
        }
        try {
            photo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Android 7.0 之后不可以直接使用Uri
        if(Build.VERSION.SDK_INT >= 24){
            photoUri = FileProvider.getUriForFile(MyApplication.getMyApplicationContext(),
                    "com.yjc.photodance.fileprovider", photo);
        }else {
            photoUri= Uri.fromFile(photo);
        }

        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void selectPhoto(int requestCode){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        switch (requestCode){
            case CHOOSE_PHOTO_FOR_BACKGROUND:
                startActivityForResult(intent, CHOOSE_PHOTO_FOR_BACKGROUND);
                break;
            case CHOOSE_PHOTO_FOR_USERHEADIMAGE:
                startActivityForResult(intent, CHOOSE_PHOTO_FOR_USERHEADIMAGE);
                break;
            default:
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            // TODO: 2018/4/20/020 同步头像
            case CHOOSE_PHOTO_FOR_USERHEADIMAGE:
                if(resultCode == RESULT_OK){
                    imagePathForUserHeadImage = handleFile(data, TYPE);
                    userHeadImageBitmap = HandleBitmap.compressForFile(imagePathForUserHeadImage);
                    mUserHeadImage.setImageBitmap(userHeadImageBitmap);
                    popupWindow.dismiss();
                }
                break;

            case CHOOSE_PHOTO_FOR_BACKGROUND:
                if(resultCode == RESULT_OK){
                    imagePathForBackground = handleFile(data ,TYPE);
                    backgroundBitmap = HandleBitmap.compressForFile(imagePathForBackground);
                    mUserHeadImage.setImageBitmap(backgroundBitmap);
                }
                break;

            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        userHeadImageBitmap = HandleBitmap.compressForStream(
                                getActivity().getContentResolver().openInputStream(photoUri));
                        mUserHeadImage.setImageBitmap(userHeadImageBitmap);
                        popupWindow.dismiss();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }

    }

    private void uploadUserProfileImage(Bitmap userProfileImage){
        MainActivity activity = (MainActivity) getActivity();
        activity.uploadUserProfileImage(userProfileImage);
    }

}
