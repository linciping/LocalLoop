package com.mengyou.library.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2015/11/7 0007.
 */
public class StatusBarBulider {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarForLOLLIPOP(Activity activity,String statusBarColor,String navigationBarColor)
    {
        Window window = activity.getWindow();
        window.setStatusBarColor(Color.parseColor(statusBarColor));
        window.setNavigationBarColor(Color.parseColor(navigationBarColor));
    }

    public static void setStatusBarForKITKAT(Activity activity,int statusBarColor)
    {
        StatusBarBulider bulider=new StatusBarBulider();
        bulider.setTranslucentStatus(activity,true);
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(statusBarColor);//通知栏所需颜色
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
