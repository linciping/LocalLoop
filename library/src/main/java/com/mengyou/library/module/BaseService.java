package com.mengyou.library.module;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author linciping
 * @version 1.0
 * @since 1.6
 * 基础Service组件
 */
public class BaseService extends Service {

    protected Context mContext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected void close() {

    }

    protected void toOtherActivity(Class<?> otherActivity) {
        Intent intent = new Intent(mContext, otherActivity);
        startActivity(intent);
    }

    protected void toOtherService(Class<?> otherService) {
        Intent intent = new Intent(mContext, otherService);
        startService(intent);
    }

    protected void stopService(Class<?> otherService) {
        Intent intent = new Intent(mContext, otherService);
        stopService(intent);
    }
}
