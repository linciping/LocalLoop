package com.mengyou.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.mengyou.library.R;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * ImageLoader工具类
 */
public class MyImageLoader {

    /**
     * 生成ImageLoader的配置文件
     * @param context
     * @return
     */
    public static ImageLoaderConfiguration newConfig(Context context) {
        File cacheDir = getDiskCacheDir(context,"bitmap");
        ImageLoaderConfiguration config = null;//开始构建
        try {
            config = new ImageLoaderConfiguration
                    .Builder(context)
                    .memoryCacheExtraOptions(240, 400) // max width, max height，即保存的每个缓存文件的最大长宽
                    .threadPoolSize(3)//线程池内加载的数量
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .memoryCache(new LruMemoryCache(6 * 1024 * 1024))
                    // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                    .memoryCacheSize(6 * 1024 * 1024)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .diskCache(new LruDiskCache(cacheDir, new HashCodeFileNameGenerator(), 100 * 1024 * 1024))
                    .defaultDisplayImageOptions(MyImageLoader.newOption())
                    .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                    .writeDebugLogs() // Remove for release app
                    .build();
        } catch (IOException e) {
            Log.e("cacheDiskError",e.getMessage());
        }
        return config;
    }

    /**
     * 根据存储器的情况，来判断缓存路径
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 配置显示项
     * @return
     */
    public static DisplayImageOptions newOption() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.load_backgroud_64)
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
                //设置图片加入缓存前，对bitmap进行设置//.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
        return options;
    }

    /**
     * drawable id转化为imageloaderURL
     * @param drawableId
     * @return
     */
    public static String formatDrawable(int drawableId)
    {
        return "drawable://"+drawableId;
    }

    /**
     * file id转化为imageloaderURL
     * @param path
     * @return
     */
    public static String formatFile(String path)
    {
        return "file://"+path;
    }

    /**
     * assets id转化为imageloaderURL
     * @param path
     * @return
     */
    public static String formatAssets(String path)
    {
        return "assets://"+path;
    }

    /**
     * content id转化为imageloaderURL
     * @param uri
     * @return
     */
    public static String formatContent(String uri)
    {
        return "content://"+uri;
    }
}
