package com.yjc.photodance.account.view;

/**
 * Created by Administrator on 2018/4/12/012.
 */

public interface IView {

    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 显示错误
     * @param code
     * @param msg
     */
    void showError(int code, String msg);
}
