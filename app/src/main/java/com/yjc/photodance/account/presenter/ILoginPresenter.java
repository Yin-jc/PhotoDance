package com.yjc.photodance.account.presenter;

/**
 * Created by Administrator on 2018/4/13/013.
 */

public interface ILoginPresenter {

    /**
     * 请求登录
     */
    void requestLogin(String username, String password);

    /**
     * 请求token登录
     */
    void requestLoginByToken();

    /**
     * 检查是否登录
     * @return
     */
    void isLogin();
}
