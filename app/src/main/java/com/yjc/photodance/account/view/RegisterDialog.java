package com.yjc.photodance.account.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.LinearLayout;

import com.dalimao.corelibrary.VerificationCodeInput;
import com.yjc.photodance.R;
//import com.yjc.photodance.account.model.AccountManagerImpl;
import com.yjc.photodance.account.model.AccountManagerImpl;
import com.yjc.photodance.account.model.IAccountManager;
import com.yjc.photodance.account.presenter.IRegisterPresenter;
import com.yjc.photodance.account.presenter.RegisterPresenterImpl;
import com.yjc.photodance.common.util.FormaUtil;
import com.yjc.photodance.common.util.ToastUtil;
import com.yjc.photodance.main.view.MainActivity;

/**
 * Created by Administrator on 2017/12/29/029.
 * todo 更换为DialogFragment
 */

public class RegisterDialog extends Dialog implements IRegisterView {

    private IRegisterPresenter mPresenter;
    private IAccountManager mManager;
    private LinearLayout mSetInfoLayout;
    private TextInputLayout mRegisterPhoneNum;
    private TextInputLayout mRegisterUsername;
    private TextInputLayout mRegisterPassword;
    private TextInputEditText mRegisterPhoneNumEdit;
    private TextInputEditText mRegisterUsernameEdit;
    private TextInputEditText mRegisterPasswordEdit;
    private VerificationCodeInput mVerificationCodeInput;
    private Button mRegister;
    private Button mGetSmsCode;
    private ImageView mDismiss;
    private Context mContext;
    private String mUsername;
    private String mPassword;
    private String mPhoneNumber;

    public RegisterDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        mManager = new AccountManagerImpl(mContext);
        mPresenter = new RegisterPresenterImpl(this, mManager);
    }

    public RegisterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public RegisterDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    private void initView(){

        mSetInfoLayout = findViewById(R.id.username_password);
        mRegisterPhoneNum = findViewById(R.id.register_phone_number);
        mRegisterUsername = findViewById(R.id.register_set_username);
        mRegisterPassword = findViewById(R.id.register_set_password);
        mRegisterPhoneNumEdit = mRegisterPhoneNum.findViewById(R.id.register_phone_number_edit);
        mRegisterUsernameEdit = mRegisterUsername.findViewById(R.id.register_set_username_edit);
        mRegisterPasswordEdit = mRegisterPassword.findViewById(R.id.register_set_password_edit);
        mGetSmsCode = findViewById(R.id.get_sms_code);
        mGetSmsCode.setEnabled(false);
        mRegister = findViewById(R.id.register_btn);
        mRegister.setEnabled(false);
        mDismiss = findViewById(R.id.dismiss);
        mVerificationCodeInput = findViewById(R.id.verificationCodeInput);
        mVerificationCodeInput.setEnabled(false);

        //设置可以计数
        mRegisterPhoneNum.setCounterEnabled(true);
        mRegisterUsername.setCounterEnabled(true);
        mRegisterPassword.setCounterEnabled(true);
        //计数的最大值
        mRegisterPhoneNum.setCounterMaxLength(11);
        mRegisterUsername.setCounterMaxLength(10);
        mRegisterPassword.setCounterMaxLength(10);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);
        initView();

        mGetSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    requestSendSmsCode();
                    mVerificationCodeInput.setEnabled(true);
            }
        });

        //验证码输入完成监听器
        mVerificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String code) {
                commit(code);
            }
        });

        mRegisterPhoneNumEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPhoneNumber= mRegisterPhoneNumEdit.getText().toString();
                if(mPhoneNumber.length() == 11){
                    boolean isLegal = check(mPhoneNumber);
                    if(isLegal){
                        mPresenter.requestCheckUserExist(mPhoneNumber);
                    }
                }
            }
        });

        mRegisterUsernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRegisterUsername.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUsername = mRegisterUsernameEdit.getText().toString();
                if(TextUtils.isEmpty(mUsername)){
                    mRegisterUsername.setError("用户名不能为空");
                    mRegister.setEnabled(false);
                }
            }
        });

        mRegisterPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRegisterPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPassword = mRegisterPasswordEdit.getText().toString();
                if(TextUtils.isEmpty(mPassword) || mPassword.length()<6){
                    mRegisterPassword.setError("密码格式错误，不能少于6个字符");
                    mRegister.setEnabled(false);
                }
                mRegister.setEnabled(true);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/4/13/013 记录用户名密码
                // TODO: 2018/4/13/013 注册成功后直接跳转主界面
                mPresenter.requestRegister(mPhoneNumber, mUsername, mPassword);
            }
        });

        mDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        
    }

    /**
     * 提交验证码
     * @param code
     */
    private void commit(String code) {
        mPresenter.requestCheckSmsCode(mPhoneNumber, code);
    }

    /**
     * 验证码倒计时
     * @param context
     */
    private CountDownTimer mCountDownTimer =new CountDownTimer(60*1000,
            1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mGetSmsCode.setEnabled(false);
            mGetSmsCode.setText(String.format(getContext().getString(R.string.after_time_resend),
                    millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            mGetSmsCode.setEnabled(true);
            mGetSmsCode.setText("重新发送");
            cancel();
        }
    };

    private void requestSendSmsCode() {
        mPresenter.requestSendSmsCode(mPhoneNumber);
    }

    /**
     * 检查手机号码是否合法
     */
    private boolean check(String phoneNumber) {
        boolean legal= FormaUtil.checkMobile(phoneNumber);
        if(!legal) {
            ToastUtil.show(getContext(), "非法手机号码，请重新输入");
            return false;
        }
        return true;
    }

    @Override
    public void showCountDownTimer() {
        mCountDownTimer.start();
    }

    @Override
    public void showSmsCodeCheckState(boolean suc) {
        if(!suc){
            //提示验证码错误
            mVerificationCodeInput.setEnabled(true);
            ToastUtil.show(mContext, "验证码错误，请重新输入");
        }else {
            //显示设置用户名密码UI
            mVerificationCodeInput.setVisibility(View.GONE);
            mGetSmsCode.setVisibility(View.GONE);
            mRegisterPhoneNum.setVisibility(View.GONE);
            mSetInfoLayout.setVisibility(View.VISIBLE);
            mRegister.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showUserExist(boolean exist) {

        if(!exist){
            //用户不存在，开始注册
            mGetSmsCode.setEnabled(true);
        }else {
            //  用户存在，进入登录，需要用户自行关闭dialog
            ToastUtil.show(getContext(), "手机号码已注册，请直接登录");
        }
    }

    @Override
    public void showRegisterSuc() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("username", mUsername);
        intent.putExtra("phoneNum", mPhoneNumber);
        mContext.startActivity(intent);
    }

    @Override
    public void showServerError() {
        ToastUtil.show(mContext, "服务器繁忙，请稍后重试");
    }
}
