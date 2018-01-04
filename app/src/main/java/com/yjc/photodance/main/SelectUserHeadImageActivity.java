package com.yjc.photodance.main;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yjc.photodance.common.LitePalForBitmap;
import com.yjc.photodance.dao.Account;
import com.yjc.photodance.R;
import com.yjc.photodance.common.MyApplicationContext;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/3/003.
 */

public class SelectUserHeadImageActivity extends AppCompatActivity {

    private CircleImageView userHeadImage;
    private PopupWindow popupWindow;
    private Bitmap userHeadImageBitmap;
    private TextView next;

    private static final int CHOOSE_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;

    private String imagePath;
    private Uri photoUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_userheadimage);



        popupWindow=new SelectPicPopupWindow(MyApplicationContext.getMyApplicationContext(),
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

        userHeadImage = findViewById(R.id.userHeadImage_register);
        userHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAtLocation(getWindow().getDecorView(),
                        Gravity.BOTTOM, 0, 0);
            }
        });

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLoginActivity();
            }
        });
    }

    private void selectPhoto(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void takePhoto(){

        //创建File对象，用于存储照片
        //存放在当前应用关联缓存目录，可以跳过权限验证
        File photo=new File(MyApplicationContext.getMyApplicationContext().getExternalCacheDir() ,
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
            photoUri = FileProvider.getUriForFile(MyApplicationContext.getMyApplicationContext(),
                    "com.yjc.photodance.fileprovider", photo);
        }else {
            photoUri= Uri.fromFile(photo);
        }

        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    imagePath = handleImage(data);
//                    userHeadImageBitmap = BitmapFactory.decodeFile(imagePath);
                    userHeadImageBitmap = LitePalForBitmap.compressWithInsampleSize(imagePath);
                    saveUserHeadImageToDB();
                    userHeadImage.setImageBitmap(userHeadImageBitmap);
                    popupWindow.dismiss();
                    next.setVisibility(View.VISIBLE);
//                    gotoLoginActivity();
                }
                break;

            case TAKE_PHOTO:
                    if(resultCode == RESULT_OK){
                        try {
                            userHeadImageBitmap = BitmapFactory.decodeStream(getContentResolver().
                                    openInputStream(photoUri));
                            saveUserHeadImageToDB();
                            userHeadImage.setImageBitmap(userHeadImageBitmap);
                            popupWindow.dismiss();
                            next.setVisibility(View.VISIBLE);
//                            gotoLoginActivity();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                break;

            default:
                break;
        }

    }

    /**
     * Android4.4之后选取图片之后不再返回真实Uri，需要对返回的封装后的Uri进行解析
     * @param data
     * @return
     */
    private String handleImage(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)) {
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
        Cursor cursor = getContentResolver().query(uri, null, selection,
                null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void gotoLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
//        Bundle bundle = new Bundle();
//        //Bitmap默认实现Parcelable接口，直接传递即可
//        bundle.putParcelable("userHeadImageBitmap", userHeadImageBitmap);
//        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void saveUserHeadImageToDB(){
//        int size = LitePalForBitmap.compressWithInsampleSize(imagePath).getByteCount() / 1024 / 1024;
//        Log.d("Bitmap Size", Integer.toString(size));
        Account account = new Account();
        account.setUserName("only_userHeadImage");
        account.setPassword("only_userHeadImage");
        account.setUserHeadImage(LitePalForBitmap.img(userHeadImageBitmap));
        account.save();
//        Log.d("Save Success", Boolean.toString(b));
    }

}
