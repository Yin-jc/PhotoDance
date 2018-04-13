package com.yjc.photodance.account.model;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.yjc.photodance.account.model.bean.User;
import com.yjc.photodance.common.storage.SharedPreferenceDao;

import java.util.List;

import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
//import cn.bmob.v3.BmobSMS;
import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
//import com.yjc.mytaxi.MyTaxiApplication;
//import com.yjc.mytaxi.account.model.bean.Account;
//import com.yjc.mytaxi.account.model.response.LoginResponse;
//import com.yjc.mytaxi.common.dataBus.RxBus;
//import com.yjc.mytaxi.common.http.IHttpClient;
//import com.yjc.mytaxi.common.http.IRequest;
//import com.yjc.mytaxi.common.http.IResponse;
//import com.yjc.mytaxi.common.http.api.API;
//import com.yjc.mytaxi.common.http.biz.BaseBizResponse;
//import com.yjc.mytaxi.common.http.impl.BaseRequest;
//import com.yjc.mytaxi.common.http.impl.BaseResponse;
//import com.yjc.mytaxi.common.storage.SharedPreferenceDao;
//import com.yjc.mytaxi.common.util.DevUtil;
//import com.yjc.mytaxi.common.util.LogUtil;
//
//import rx.functions.Func1;

/**
 * Created by Administrator on 2017/11/7/007.
 */

public class AccountManagerImpl implements IAccountManager {

    private static final String TAG="AccountManagerImpl";
    //登录是否过期
    private boolean tokenValid=false;

    private Handler mHandler;
    private Context mContext;

    public AccountManagerImpl(Context context){
        mContext = context;
    }

    /**
     * 获取验证码
     * @param phoneNumber
     */
    @Override
    public void fetchSMSCode(final String phoneNumber) {

        BmobSMS.requestSMSCode(mContext, phoneNumber, "Yjc",
                new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if(e == null){//验证码发送成功
                            Log.i("bmob", "验证码发送成功，短信id: "+smsId);//用于查询本次短信发送详情
                            mHandler.sendEmptyMessage(IAccountManager.SMS_SEND_SUC);
                        }else {
                            Log.i("bmob", "验证码发送失败" + e.getMessage());
                            mHandler.sendEmptyMessage(IAccountManager.SMS_SEND_FAIL);
                        }
                    }
                });
    }

    /**
     * 校验验证码
     * @param phoneNumber
     * @param smsCode
     */
    @Override
    public void checkSMSCode(final String phoneNumber, final String smsCode) {

        BmobSMS.verifySmsCode(mContext, phoneNumber, smsCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){//短信验证码已验证成功
                    Log.i("bmob", "验证通过");
                    mHandler.sendEmptyMessage(IAccountManager.SMS_CHECK_SUC);
                }else{
                    Log.i("bmob", "验证失败：code =" + e.getErrorCode() +
                            ",msg = " + e.getLocalizedMessage());
                    mHandler.sendEmptyMessage(IAccountManager.SMS_CHECK_FAIL);
                }
            }
        });

    }

    /**
     * 检查用户是否存在
     * @param phoneNumber
     */
    @Override
    public void checkUserExist(final String phoneNumber) {

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("phoneNumber", phoneNumber);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> users, cn.bmob.v3.exception.BmobException e) {
                if(e == null){//查询成功，即用户存在
                    Log.i("bmob", "用户已存在");
                    mHandler.sendEmptyMessage(IAccountManager.USER_EXIST);
                }else {//查询失败，即用户不存在
                    Log.i("bmob", "用户不存在" + e.getMessage());
                    mHandler.sendEmptyMessage(IAccountManager.USER_NOT_EXIST);
                }
            }
        });
    }

    /**
     * 注册
     * @param phoneNumber
     * @param username
     * @param password
     */
    @Override
    public void register(String phoneNumber, String username, String password) {

        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
        user.setPassword(password);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, cn.bmob.v3.exception.BmobException e) {
                if(e == null){
                    Log.i("bmob", "注册成功，返回objectId为：" + objectId);
                    mHandler.sendEmptyMessage(IAccountManager.REGISTER_SUC);
                    SharedPreferenceDao.getInstance().saveBoolean("isLogin", true);
                    //设置token有效时间为24小时
                    SharedPreferenceDao.getInstance().saveString("tokenValid",
                            String.valueOf(System.currentTimeMillis()) + String.valueOf(24*60*60*1000));
                }else{
                    Log.i("bmob", "注册失败：" + e.getMessage());
                    mHandler.sendEmptyMessage(IAccountManager.SERVER_FAIL);
                }
            }
        });
    }

    /**
     * 登录
     * @param username
     * @param password
     */
    @Override
    public void login(String username, String password) {

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", username);
        bmobQuery.addWhereEqualTo("password", password);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, cn.bmob.v3.exception.BmobException e) {
                if(e == null){//登录成功
                    Log.i("bmob", "登录成功");
                    mHandler.sendEmptyMessage(IAccountManager.LOGIN_SUC);
                }else {//登录失败，用户名或密码错误
                    Log.i("bmob", "登录失败" + e.getMessage());
                    mHandler.sendEmptyMessage(IAccountManager.UN_OR_PW_ERROR);
                }
            }
        });
    }

    /**
     * token登录
     */
    @Override
    public void loginByToken() {
    }

    @Override
    public void isLogin() {

        boolean isLogin = SharedPreferenceDao.getInstance().getBoolean("isLogin");
        if(isLogin) {
            if (Long.parseLong(SharedPreferenceDao.getInstance().getString("tokenValid")) >
                    System.currentTimeMillis()) {
                //登录有效
                mHandler.sendEmptyMessage(IAccountManager.TOKEN_VALID);
            } else {
                //登录无效
                mHandler.sendEmptyMessage(IAccountManager.TOKEN_INVALID);
            }
        }else {
            //未登录
        }
    }

    @Override
    public void setHandler(Handler handler) {
        mHandler = handler;
    }

}



