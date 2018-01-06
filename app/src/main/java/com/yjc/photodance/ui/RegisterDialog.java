package com.yjc.photodance.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yjc.photodance.R;
import com.yjc.photodance.common.SharedPreferenceDao;
import com.yjc.photodance.dao.Account;

/**
 * Created by Administrator on 2017/12/29/029.
 */

public class RegisterDialog extends Dialog {

    private TextInputLayout registerUsername;
    private TextInputLayout registerPassword;
    private TextInputEditText registerUsernameEdit;
    private TextInputEditText registerPasswordEdit;
    private Button register;
    private ImageView dismiss;
    private byte[] userHeadImage;
    private SelectPicPopupWindow popupWindow;
    private View loginView;
    private Context mContext;
    private String username;
    private String password;
    private int height;

    public RegisterDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public RegisterDialog(@NonNull Context context, byte[] userHeadImage) {
        super(context);
        mContext = context;
        this.userHeadImage = userHeadImage;
    }

    public RegisterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public RegisterDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);

        registerUsername = findViewById(R.id.register_user_name);
        registerPassword = findViewById(R.id.register_password);
        registerUsernameEdit = registerUsername.findViewById(R.id.register_user_name_edit);
        registerPasswordEdit = registerPassword.findViewById(R.id.register_password_edit);
        register = findViewById(R.id.register_btn);
        register.setEnabled(false);
        dismiss = findViewById(R.id.dismiss);

        //设置可以计数
        registerUsername.setCounterEnabled(true);
        //计数的最大值
        registerUsername.setCounterMaxLength(10);
        //设置可以计数
        registerPassword.setCounterEnabled(true);
        //计数的最大值
        registerPassword.setCounterMaxLength(10);

        registerUsernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                register.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerUsername.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                register.setEnabled(true);
                username = registerUsernameEdit.getText().toString();
                if(TextUtils.isEmpty(username)){
                    registerUsername.setError("用户名不能为空");
                    register.setEnabled(false);
                }
            }
        });

        registerPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                register.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                register.setEnabled(true);
                password = registerPasswordEdit.getText().toString();
                if(TextUtils.isEmpty(password) || password.length()<6){
                    registerPassword.setError("密码格式错误，不能少于6个字符");
                    register.setEnabled(false);
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferenceDao.getInstance().saveBoolean("login", true);

                Account account = new Account();
                account.setUserName(username);
                account.setPassword(password);
                account.setUserHeadImage(userHeadImage);
                account.setRegister(true);
                account.setLogin(true);
                account.save();

                //删除数据库中临时的数据
//                DataSupport.deleteAll(Account.class, "username = ?", "only_userHeadImage");

                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                cancel();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

//        userHeadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                dismiss();
//                //隐藏软键盘,再执行一次会弹出软键盘
//                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
////                popupWindow.showAtLocation(findViewById(R.id.register_dialog),
////                        Gravity.BOTTOM, 0, 0);
//            }
//        });

    }

}
