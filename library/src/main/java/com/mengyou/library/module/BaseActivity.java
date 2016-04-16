package com.mengyou.library.module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.mengyou.library.util.SPTools;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * @author linciping
 * @version 1.0
 * @see AppCompatActivity
 * 基础Activity组件
 */
public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected  String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close();
    }

    /**
     * @version 1.0
     * 初始化UI
     */
    protected void initView() {

    }

    /**
     * @version 1.0
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * @version 1.0
     * 关闭数据
     */
    protected void close() {

    }

    /**
     * @version 1.0
     * 跳转到另一个Activity
     * @param otherActivity 需要跳转的Activity
     */
    protected void toOtherActivity(Class<?> otherActivity) {
        Intent intent = new Intent(mContext, otherActivity);
        startActivity(intent);
    }

    protected void toOtherService(Class<?> otherService) {
        Intent intent = new Intent(mContext, otherService);
        startService(intent);
    }

    protected void toOtherActivity(Class<?> otherActivity,String... objects)
    {
        Intent intent=new Intent(mContext,otherActivity);
        for (int i=0;i<objects.length;i++)
        {
           intent.putExtra(objects[i],objects[++i]);
        }
        startActivity(intent);
    }

    protected void toOtherActivity(Class<?> otherActivity, Map<String,String> map)
    {
        Intent intent=new Intent(mContext,otherActivity);
        Set<String> set=map.keySet();
        for (String key:set)
        {
            intent.putExtra(key,map.get(key));
        }
    }

    protected void stopService(Class<?> otherService) {
        Intent intent = new Intent(mContext, otherService);
        stopService(intent);
    }

    protected void putSharedPreferences(String key, Object object) {
        SPTools.put(mContext, key, object);
    }

    protected Object getSharedPreferences(String key, Object defaultObject) {
        return SPTools.get(mContext, key, defaultObject);
    }

    protected void removeSharedPreferences(String key) {
        SPTools.remove(mContext, key);
    }

    protected void clearSharedPreferences() {
        SPTools.clear(mContext);
    }

    /**
     * 缓存数据
     * @param cache 需要缓存的数据
     */
    protected void onCache(List<?> cache)
    {

    }

    /**
     * 缓存数据
     * @param cache 需要缓存的数据
     * @param method 缓存的方式
     */
    protected void onCache(List<?> cache,int method)
    {

    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            toast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    protected void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
