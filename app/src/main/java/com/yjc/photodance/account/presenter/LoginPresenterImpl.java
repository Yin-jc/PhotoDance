package com.yjc.photodance.account.presenter;

import android.os.Handler;
import android.os.Message;

import com.yjc.photodance.account.model.IAccountManager;
import com.yjc.photodance.account.view.ILoginView;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/4/13/013.
 * todo eventBus
 */

public class LoginPresenterImpl implements ILoginPresenter {

    private ILoginView view;
    private IAccountManager accountManager;

    @Override
    public void requestLogin(String username, String password) {
        accountManager.login(username, password);
    }

    @Override
    public void requestLoginByToken() {
        accountManager.loginByToken();
    }

    @Override
    public void isLogin() {
        accountManager.isLogin();
    }

    private static class MyHandler extends Handler {
        WeakReference<LoginPresenterImpl> refContext;

        public MyHandler(LoginPresenterImpl context) {
            refContext=new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginPresenterImpl presenter=refContext.get();
            switch (msg.what){
                case IAccountManager.LOGIN_SUC:
                    presenter.view.showLoginSuc();
                    break;
                case IAccountManager.UN_OR_PW_ERROR:
                    presenter.view.showUsernameOrPasswordError();
                    break;
                case IAccountManager.SERVER_FAIL:
                    presenter.view.showServerError();
                    break;
                case IAccountManager.TOKEN_VALID:
                    presenter.view.showTokenValid();
                    break;
                case IAccountManager.TOKEN_INVALID:
                    presenter.view.showTokenInvalid();
                    break;
                case IAccountManager.NO_LOGIN:
                    presenter.view.showNoLogin();
                default:
                    break;
            }
        }
    }

    public LoginPresenterImpl(ILoginView view,
                                 IAccountManager accountManager) {
        this.view = view;
        this.accountManager = accountManager;
        accountManager.setHandler(new MyHandler(this));
    }

}
