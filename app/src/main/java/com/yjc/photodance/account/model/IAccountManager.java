package com.yjc.photodance.account.model;

import android.os.Handler;

/**
 * Created by Administrator on 2017/11/7/007.
 * 账号相关业务逻辑的抽象
 */

public interface IAccountManager {

    //服务器错误
    int SERVER_FAIL = -999;
    //验证码发送成功
    int SMS_SEND_SUC = 1;
    //验证码发送失败
    int SMS_SEND_FAIL = -1;
    //验证码校验成功
    int SMS_CHECK_SUC = 2;
    //验证码校验失败
    int SMS_CHECK_FAIL = -2;
    //用户已经存在
    int USER_EXIST = 3;
    //用户不存在
    int USER_NOT_EXIST = -3;
    //注册成功
    int REGISTER_SUC = 4;
    //登录成功
    int LOGIN_SUC = 5;
    //登录有效
    int TOKEN_VALID = 6;
    //登录过期
    int TOKEN_INVALID = -6;
    //用户名或密码错误
    int UN_OR_PW_ERROR = -7;

    /**
     * 下发验证码
     * @param phone
     */
    void fetchSMSCode(String phone);

    /**
     * 校验验证码
     * @param phone
     * @param smsCode
     */
    void checkSMSCode(String phone, String smsCode);

    /**
     * 检验用户是否注册
     * @param phone
     */
    void checkUserExist(String phone);

    /**
     * 注册
     * @param username
     * @param password
     */
    void register(String phoneNumber, String username, String password);

    /**
     * 登录
     * @param username
     * @param password
     */
    void login(String username, String password);

    /**
     * token登录
     */
    void loginByToken();

    /**
     * 是否登录
     * @return
     */
    void isLogin();

    void setHandler(Handler mHandler);
}
