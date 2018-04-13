package com.yjc.photodance.account.presenter;

import android.os.Handler;
import android.os.Message;

import com.yjc.photodance.account.model.IAccountManager;
import com.yjc.photodance.account.view.IRegisterView;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/11/7/007.
 */

public class RegisterPresenterImpl implements IRegisterPresenter{

    private IRegisterView view;
    private IAccountManager accountManager;

    private static class MyHandler extends Handler {
        WeakReference<RegisterPresenterImpl> refContext;

        public MyHandler(RegisterPresenterImpl context) {
            refContext=new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterPresenterImpl presenter=refContext.get();
            switch (msg.what){
                case IAccountManager.SMS_SEND_SUC:
                    presenter.view.showCountDownTimer();
                    break;
                case IAccountManager.SMS_SEND_FAIL:
                    presenter.view.showError(IAccountManager.SMS_SEND_FAIL,"");
                    break;
                case IAccountManager.SMS_CHECK_SUC:
                    presenter.view.showSmsCodeCheckState(true);
                    break;
                case IAccountManager.SMS_CHECK_FAIL:
                    presenter.view.showError(IAccountManager.SMS_CHECK_FAIL,"");
                    break;
                case IAccountManager.USER_EXIST:
                    presenter.view.showUserExist(true);
                    break;
                case IAccountManager.USER_NOT_EXIST:
                    presenter.view.showUserExist(false);
                    break;
                case IAccountManager.REGISTER_SUC:
                    presenter.view.showRegisterSuc();
                default:
                    break;
            }
        }
    }

    public RegisterPresenterImpl(IRegisterView view,
                                 IAccountManager accountManager) {
        this.view = view;
        this.accountManager = accountManager;
        accountManager.setHandler(new MyHandler(this));
    }

    /**
     * 获取验证码
     * @param phone
     */
    @Override
    public void requestSendSmsCode(String phone) {
        accountManager.fetchSMSCode(phone);
    }

    /**
     * 校验验证码
     * @param phone
     * @param smsCode
     */
    @Override
    public void requestCheckSmsCode(String phone, String smsCode) {
        accountManager.checkSMSCode(phone,smsCode);
    }

    /**
     * 检查用户是否存在
     * @param phone
     */
    @Override
    public void requestCheckUserExist(String phone) {
        accountManager.checkUserExist(phone);
    }

    @Override
    public void requestRegister(String phoneNumber, String username, String password) {
        accountManager.register(phoneNumber, username, password);
    }
}
