package com.douban.douban.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：Andayoung on 2016/10/28 15:49
 * 邮箱：zhoup324@163.com
 */
public class BookDBOpenHelper extends SQLiteOpenHelper {
    public BookDBOpenHelper(Context context) {
        super(context, "doubanBook.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bookInfo (_id integer primary key autoincrement,bookName text," +
                "txtPath text,imgPath text,pageNum integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exits bookInfo");
        db.execSQL("CREATE TABLE bookInfo(_id integer primary key autoincrement,bookName text" +
                ",txtPath text,imgPath text,pageNum integer");

    }
}
