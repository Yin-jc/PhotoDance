package com.yjc.photodance.account.presenter;

/**
 * Created by Administrator on 2017/11/7/007.
 */

public interface IRegisterPresenter {

    /**
     * 请求下发验证码
     * @param phone
     */
    void requestSendSmsCode(String phone);

    /**
     * 请求校验验证码
     * @param phone
     * @param smsCode
     */
    void requestCheckSmsCode(String phone, String smsCode);

    /**
     * 请求校验用户是否存在
     * @param phone
     */
    void requestCheckUserExist(String phone);

    /**
     * 请求登录
     * @param username
     * @param password
     */
    void requestRegister(String phoneNumber, String username, String password);
}
