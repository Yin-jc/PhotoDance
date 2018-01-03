package com.yjc.photodance.main;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.yjc.photodance.dao.Account;
import com.yjc.photodance.R;
import com.yjc.photodance.common.MyApplicationContext;

import org.litepal.LitePal;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/3/003.
 */

public class SelectUserHeadImageActivity extends AppCompatActivity {

    private CircleImageView userHeadImage;
    private PopupWindow popupWindow;
    private Bitmap userHeadImageBitmap;

    private static final int CHOOSE_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;

    private String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_userheadimage);

        LitePal.getDatabase();

        popupWindow=new SelectPicPopupWindow(MyApplicationContext.getMyApplicationContext(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()){
                            case R.id.take_photo:
//                                takePhoto();
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
    }

    private void selectPhoto(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    imagePath = handleImage(data);
                    userHeadImageBitmap = BitmapFactory.decodeFile(imagePath);
                    Account account = new Account();
                    account.setUserName("only_userHeadImage");
                    account.setPassword("only_userHeadImage");
                    account.setUserHeadImage(userHeadImageBitmap);
                    account.save();
                    userHeadImage.setImageBitmap(userHeadImageBitmap);
                }
                break;

            case TAKE_PHOTO:

                break;
        }

    }

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

}
