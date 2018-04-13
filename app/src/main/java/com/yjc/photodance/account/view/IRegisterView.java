package com.yjc.photodance.account.view;

/**
 * Created by Administrator on 2018/4/12/012.
 */

public interface IRegisterView {

    /**
     * 显示倒计时
     */
    void showCountDownTimer();

    /**
     * 显示验证状态
     * @param b
     */
    void showSmsCodeCheckState(boolean b);

    /**
     * 显示用户存在状态
     * @param b
     */
    void showUserExist(boolean b);

    /**
     * 显示注册成功
     */
    void showRegisterSuc();

    /**
     * 显示服务器繁忙
     */
    void showServerError();

}
