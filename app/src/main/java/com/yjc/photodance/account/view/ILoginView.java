package com.yjc.photodance.account.view;

/**
 * Created by Administrator on 2018/4/12/012.
 */

public interface ILoginView extends IView {

    /**
     * 显示登录成功
     */
    void showLoginSuc();

    /**
     * 显示登录失败
     * @param code
     * @param msg
     */
    void showLoginFail(int code, String msg);
}
