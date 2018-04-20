package com.yjc.photodance.main.view.fragment;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yjc.photodance.MyApplication;
import com.yjc.photodance.R;
import com.yjc.photodance.common.base.BaseFragment;
import com.yjc.photodance.common.storage.SharedPreferenceDao;
import com.yjc.photodance.common.util.HandleBitmap;
import com.yjc.photodance.model.Account;
import com.yjc.photodance.ui.SelectPicPopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/4/20/020.
 * todo 数据库实现数据存储
 */

public class InfoFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mBackground;
    private CircleImageView mUserHeadImage;
    private EditText mUsername;
    private EditText mBase;
    private EditText mEmail;
    private EditText mHome;
    private EditText mCompany;
    private EditText mProfession;
    private EditText mSignature;
    private HashSet<String> info = new HashSet<>();

    private PopupWindow popupWindow;
    private Bitmap userHeadImageBitmap;
    private static final int CHOOSE_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;

    private String imagePath;
    private Uri photoUri;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    // TODO: 2018/4/20/020 初始化username 注册时使用sharedPreferenceDao存储，在此取出
    private void initData(){
        HashSet<String> info_dao = SharedPreferenceDao.getInstance().getSet("info");
        //HashSet转ArrayList
        ArrayList<String> info_array = new ArrayList<>(info_dao);
        mUsername.setText(info_array.get(0));
        mBase.setText(info_array.get(1));
        mEmail.setText(info_array.get(2));
        mHome.setText(info_array.get(3));
        mCompany.setText(info_array.get(4));
        mProfession.setText(info_array.get(5));
        mSignature.setText(info_array.get(6));
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
    }

    private void saveData(){
        // TODO: 2018/4/20/020 重复问题
        info.add(mUsername.getText().toString());
        info.add(mBase.getText().toString());
        info.add(mEmail.getText().toString());
        info.add(mHome.getText().toString());
        info.add(mCompany.getText().toString());
        info.add(mProfession.getText().toString());
        info.add(mSignature.getText().toString());

        SharedPreferenceDao.getInstance().saveSet("info", info);

    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
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
                                selectPhoto();
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.background_info:
                // TODO: 2018/4/20/020 打开系统图册
                selectPhoto();
                break;
            case R.id.userHeadImage_info:
                // TODO: 2018/4/20/020 弹出popupwindow
                popupWindow.showAtLocation(getActivity().getWindow().getDecorView(),
                        Gravity.BOTTOM, 0, 0);
                break;
            default:
                break;
        }
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

    private void selectPhoto(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     * Android4.4之后选取图片之后不再返回真实Uri，需要对返回的封装后的Uri进行解析
     * @param data
     * @return
     */
    private String handleImage(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(getActivity(), uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docuId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docuId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://" +
                        "downloads/public_downloads"), Long.valueOf(docuId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection,
                null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            // TODO: 2018/4/20/020 同步头像
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    imagePath = handleImage(data);
                    userHeadImageBitmap = HandleBitmap.compressForFile(imagePath);
//                    saveUserHeadImageToDB();
                    mUserHeadImage.setImageBitmap(userHeadImageBitmap);
                    popupWindow.dismiss();
                }
                break;

            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        userHeadImageBitmap = HandleBitmap.compressForStream(
                                getActivity().getContentResolver().openInputStream(photoUri));
//                        saveUserHeadImageToDB();
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

    private void saveUserHeadImageToDB(){
        Account account = new Account();
        account.setUserName("only_userHeadImage");
        account.setPassword("only_userHeadImage");
        account.setUserHeadImage(HandleBitmap.img(userHeadImageBitmap));
        account.save();
    }
}
