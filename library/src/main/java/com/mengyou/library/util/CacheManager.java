package com.mengyou.library.util;

import android.app.Activity;
import android.content.Context;
import android.util.SizeF;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
public class CacheManager<T> {

    private Activity mContext;
    private String cachePath;

    public CacheManager(Activity activity, String cacheName) {
        this.mContext = activity;
        if (SDCardTools.isSDCardEnable()) {
            cachePath += SDCardTools.getSDCardPath() + cacheName;
        } else {
            cachePath += SDCardTools.getRootDirectoryPath() + cacheName;
        }
    }

    public void setCacheData(List<T> datas) {
        File file = new File(cachePath);
        String json = JSON.toJSONString(datas);
        if (!file.exists()) {
            file.mkdir();
        } else {
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
                byte[] bytes = json.getBytes("utf-8");
                outputStream.write(bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取缓存数据
     *
     * @return
     */
    public List<T> getCacheData(Class<?> cla) {
        List<T> cacheData = null;
        String json="";
        FileInputStream inputStream;
        try {
            inputStream=new FileInputStream(cachePath);
            byte[] bbuf = new byte[1024];
            //用于保存实际读取的字节数
            int hasRead = 0;
            //使用循环来重复“取水”的过程
            while((hasRead = inputStream.read(bbuf))>0)
            {
                //取出"竹筒"中(字节),将字节数组转成字符串输入
                json+=new String(bbuf,0,hasRead);
            }
            inputStream.close();
            cacheData=(List<T>) JSONArray.parseArray(json,cla);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheData;
    }

    /**
     * 是否包含缓存
     *
     * @return
     */
    public boolean isHasCache() {
        return false;
    }
}
