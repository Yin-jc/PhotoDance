package com.yjc.photodance.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yjc.photodance.R;
import com.yjc.photodance.common.SharedPreferenceDao;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userHeadImage = findViewById(R.id.userHeadImage);
        loginUsername = findViewById(R.id.login_user_name);
        loginPassword = findViewById(R.id.login_password);
        loginUsernameEdit = loginUsername.findViewById(R.id.login_user_name_edit);
        loginPasswordEdit = loginPassword.findViewById(R.id.login_password_edit);
        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterDialog dialog = new RegisterDialog(LoginActivity.this);
                dialog.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsernameEdit.getText().toString();
                String password = loginPasswordEdit.getText().toString();

                String usernameRegistered = SharedPreferenceDao.getInstance().
                        getString("username");
                String passwordRegistered = SharedPreferenceDao.getInstance().
                        getString("password");

                if(username.equals(usernameRegistered) && password.equals(passwordRegistered)){
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        userHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/12/29/029 弹出popupWindow 进行头像选择 保存到本机
                // TODO：更换MainActivity中左上角的头像，以及drawerLayout中的头像
            }
        });

    }


}
