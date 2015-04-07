package com.stc.launchersetting.com.stc.launchersetting.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cninlaxu on 2015/4/7.
 */
public class DBOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="setting";
    private static final int DATABASE_VERSION = 1;
    public DBOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSql="CREATE TABLE setting(_id integer primary key autoincrement,key varchar(10),type integer,value integer default 0)";
        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS setting");
        this.onCreate(sqLiteDatabase);
    }
}
