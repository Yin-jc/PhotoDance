package com.yjc.photodance.account.view;

/**
 * Created by Administrator on 2018/4/12/012.
 */

public interface ILoginView {

    /**
     * 显示用户名或密码错误
     */
    void showUsernameOrPasswordError();

    /**
     * 显示服务器繁忙
     */
    void showServerError();

    /**
     * 显示登录成功
     */
    void showLoginSuc();

    /**
     * 显示Token有效
     */
    void showTokenValid();

    /**
     * 显示Token无效
     */
    void showTokenInvalid();

    /**
     * 显示未登录
     */
    void showNoLogin();

}
