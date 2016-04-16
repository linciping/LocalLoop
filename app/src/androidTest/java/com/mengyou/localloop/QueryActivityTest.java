package com.mengyou.localloop;

import android.app.Activity;
import android.test.ActivityTestCase;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2016/3/2.
 */
public class QueryActivityTest extends ActivityTestCase {

    @Test
    public void testQuery() {

        AVQuery<AVObject> query=new AVQuery<>("Post");
        query.whereEqualTo("pubUser","客服");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Log.d("成功", "查询到" + list.size() + " 条符合条件的数据");
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
    }
}
