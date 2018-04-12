package com.yjc.photodance.account.view;

/**
 * Created by Administrator on 2018/4/12/012.
 */

public interface IRegisterView extends IView {

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

}
