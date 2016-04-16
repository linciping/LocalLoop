package com.mengyou.library.cache;

import android.content.Context;

import com.mengyou.library.util.SPTools;

import java.util.List;

/**
 * 缓存框架
 * @author linciping
 * @version 1.0
 */
public class CacheManager<T> {

    private Context mContext;
    private String key;

    public CacheManager(Context mContext,String key) {
        this.mContext=mContext;
        this.key=key;
    }

    /**
     * 是否读取缓存
     * @param key
     * @return
     */
    public boolean isReadCache(String key)
    {
        String value= SPTools.get(mContext,key,"").toString();
        if (value.equals(""))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 读取缓存数据
     * @param cla
     * @return
     */
    public List<T> getCacheData(Class<?> cla)
    {
        return null;
    }

    /**
     * 网络访问读取数据
     * @param cla
     * @return
     */
    public List<T> getNetData(Class<?> cla)
    {
        return null;
    }

    /**
     * 读取文件数据
     * @param cla
     * @return
     */
    public List<T> getFileData(Class<?> cla)
    {
        return null;
    }
}
