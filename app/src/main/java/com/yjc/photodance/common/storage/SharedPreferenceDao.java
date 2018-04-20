package com.yjc.photodance.common.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yjc.photodance.MyApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/29/029.
 * 静态内部类的方式实现单例模式
 */

public class SharedPreferenceDao {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private HashSet<String> defaultSet= new HashSet<>();

    private SharedPreferenceDao(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSet.add("");
    }

    public static SharedPreferenceDao getInstance(){
        return SharedPreferenceDaoInner.INSTANCE;
    }

    public static class SharedPreferenceDaoInner{
        private static final SharedPreferenceDao INSTANCE =
                new SharedPreferenceDao(MyApplication.getMyApplicationContext());
    }

//    //存储
//    public <T> void save(String key, T value){
//        if(value instanceof Boolean){
//            editor.putBoolean(key, (boolean) value);
//        }else if(value instanceof  String){
//            editor.putString(key, (String) value);
//        }
//        //提交
//        editor.apply();
//        // TODO: 2017/12/29/029 需要存取什么类型可以继续添加
//    }

    public void saveBoolean(String key, boolean value){
        editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void saveString(String key, String value){
        editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveSet(String key, Set set){
        editor = pref.edit();
        editor.putStringSet(key, set);
        editor.apply();
    }

    //取布尔值
    public boolean getBoolean(String key){
        return pref.getBoolean(key, false);
    }

    //取字符串
    public String getString(String key){
        return pref.getString(key, "");
    }

    //取HashSet
    public HashSet<String> getSet(String key){
        return (HashSet<String>) pref.getStringSet(key, defaultSet);
    }

}
