package com.yjc.photodance.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yjc.photodance.R;
import com.yjc.photodance.account.model.AccountManagerImpl;
import com.yjc.photodance.account.model.IAccountManager;
import com.yjc.photodance.account.presenter.ILoginPresenter;
import com.yjc.photodance.account.presenter.LoginPresenterImpl;
import com.yjc.photodance.common.util.ToastUtil;
import com.yjc.photodance.main.view.MainActivity;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {

//    private CircleImageView userHeadImage;
    private TextInputLayout loginUsername;
    private TextInputLayout loginPassword;
    private TextInputEditText loginUsernameEdit;
    private TextInputEditText loginPasswordEdit;
    private Button login;
    private TextView register;

    private ILoginPresenter mPresenter;
    private IAccountManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mManager = new AccountManagerImpl(this);
        mPresenter = new LoginPresenterImpl(this, mManager);

        // TODO: 2018/4/13/013  密码显示开关

        mPresenter.isLogin();

        /**
         * 注册点击事件
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterDialog dialog = new RegisterDialog(LoginActivity.this);
                dialog.show();
            }
        });

        /**
         * 登录点击事件
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsernameEdit.getText().toString();
                String password = loginPasswordEdit.getText().toString();

                // TODO: 2018/4/13/013 上传输入的用户名和密码与数据库对比
                mPresenter.requestLogin(username, password);
            }
        });
    }

    private void initView(){
//        userHeadImage = findViewById(R.id.userHeadImage_login);
        loginUsername = findViewById(R.id.login_user_name);
        loginPassword = findViewById(R.id.login_password);
        loginUsernameEdit = loginUsername.findViewById(R.id.login_user_name_edit);
        loginPasswordEdit = loginPassword.findViewById(R.id.login_password_edit);
        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register);
    }

    @Override
    public void showUsernameOrPasswordError() {
        ToastUtil.show(this, "用户名或密码错误，请重新输入");
    }

    @Override
    public void showServerError() {
        ToastUtil.show(this, "服务器繁忙，请稍后重试");
    }

    /**
     * 登录成功跳转
     */
    @Override
    public void showLoginSuc() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void showTokenValid() {
        ToastUtil.show(this, "自动登录中...");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void showTokenInvalid() {
        ToastUtil.show(this, "登录失效，请重新登录");
    }

    @Override
    public void showNoLogin() {
        //检测到未登录则不做任何操作
        return;
    }
}
