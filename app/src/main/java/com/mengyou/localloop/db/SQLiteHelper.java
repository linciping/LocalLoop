package com.mengyou.localloop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="localloop";
    private static final int VERSION=1;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists messagecache " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,conversationId varchar,couversationName varchar,createId varchar,memberId varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
