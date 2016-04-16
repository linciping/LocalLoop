package com.mengyou.library.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by 赐平 on 2015/11/26.
 */
public class FileHelper implements IOHelper{

    private Context mContext;

    public FileHelper(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    public void write(String value, String fileName) throws Exception {
        FileOutputStream outputStream=mContext.openFileOutput(fileName, Activity.MODE_PRIVATE);
        byte [] bytes = value.getBytes();
        outputStream.write(bytes);
        outputStream.close();
    }

    @Override
    public String read(String fileName) throws Exception {
        String res="";
        FileInputStream inputStream=mContext.openFileInput(fileName);
        int length = inputStream.available();
        byte [] buffer = new byte[length];
        inputStream.read(buffer);
        res =getString(buffer, "UTF-8");
        inputStream.close();
        return res;
    }

    public static String getString(byte[] buffer, String s) throws UnsupportedEncodingException {
        String result=new String(buffer,s);
        return result;
    }

    public static boolean vailDataFileSize(File file,long MaxSize)
    {
        long targetSize=file.length();
        if (targetSize>MaxSize)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void writeImage(Bitmap bitmap, String fileName) throws Exception {

    }

    @Override
    public Bitmap readImage(String fileName) throws Exception {
        return null;
    }
}
