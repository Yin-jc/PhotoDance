package com.yjc.photodance.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yjc.photodance.R;
import com.yjc.photodance.common.SharedPreferenceDao;
import com.yjc.photodance.dao.Account;

import org.litepal.crud.DataSupport;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class LoginActivity extends AppCompatActivity {

    private CircleImageView userHeadImage;
    private TextInputLayout loginUsername;
    private TextInputLayout loginPassword;
    private TextInputEditText loginUsernameEdit;
    private TextInputEditText loginPasswordEdit;
    private Button login;
    private TextView register;
    private byte[] userHeadImageBitmap;
    private List<Account> accounts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userHeadImage = findViewById(R.id.userHeadImage_login);
        loginUsername = findViewById(R.id.login_user_name);
        loginPassword = findViewById(R.id.login_password);
        loginUsernameEdit = loginUsername.findViewById(R.id.login_user_name_edit);
        loginPasswordEdit = loginPassword.findViewById(R.id.login_password_edit);
        login = findViewById(R.id.login_btn);
//        login.setEnabled(false);
        register = findViewById(R.id.register);

//        accounts = DataSupport.select("userHeadImage")
//                .where("username = ?", "only_userHeadImage")
//                .find(Account.class);
//        accounts = DataSupport.findAll(Account.class);
        Account account = DataSupport.findLast(Account.class);
        userHeadImageBitmap = account.getUserHeadImage();
//        userHeadImageBitmap = getIntent().getExtras().getParcelable("userHeadImageBitmap");
        if(userHeadImageBitmap != null) {
            userHeadImage.setImageBitmap(BitmapFactory.decodeByteArray(userHeadImageBitmap,
                    0, userHeadImageBitmap.length));
        }

        //隐藏软键盘
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterDialog dialog = new RegisterDialog(LoginActivity.this,
                        userHeadImageBitmap);
//                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
//                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialog.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsernameEdit.getText().toString();
                String password = loginPasswordEdit.getText().toString();

                Account account = DataSupport.findLast(Account.class);

                String usernameRegistered = account.getUserName();
                String passwordRegistered = account.getPassword();

//                String usernameRegistered = SharedPreferenceDao.getInstance().
//                        getString("username");
//                String passwordRegistered = SharedPreferenceDao.getInstance().
//                        getString("password");

                if(username.equals(usernameRegistered) && password.equals(passwordRegistered)){
                    Account account1 = new Account();
                    account1.setLogin(true);
                    account1.updateAll();
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
//                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误",
                            Toast.LENGTH_SHORT).show();
                }

//                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    //改变background的透明度
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


}
