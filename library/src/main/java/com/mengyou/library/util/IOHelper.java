package com.mengyou.library.util;

import android.graphics.Bitmap;

/**
 * Created by 赐平 on 2015/11/26.
 */
public interface IOHelper {
    void write(String value, String path) throws Exception;
    String read(String path) throws Exception;
    void writeImage(Bitmap bitmap, String path) throws Exception;
    Bitmap readImage(String path) throws Exception;
 }
