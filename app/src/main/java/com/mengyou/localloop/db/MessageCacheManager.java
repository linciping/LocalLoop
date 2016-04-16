package com.mengyou.localloop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mengyou.localloop.model.MessageCache;

/**
 * Created by Administrator on 2016/4/15.
 */
public class MessageCacheManager {

    private SQLiteDatabase db;
    private SQLiteHelper helper;

    public MessageCacheManager(Context context)
    {
        helper=new SQLiteHelper(context);
        db=helper.getWritableDatabase();
    }

    public void insert(MessageCache cache)
    {
        db.beginTransaction();
        try {
            db.execSQL("insert into messagecache values (null,?,?,?,?)",new Object[]{cache.getConversationId(),cache.getCouversationName()
                    ,cache.getCreateId(), JSON.toJSONString(cache.getMemberId())});
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.e("sql_error",e.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }
}
