package com.stc.launchersetting.com.stc.launchersetting.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by cninlaxu on 2015/4/7.
 */
public class SettingService {
    private DBOpenHelper dbOpenHelper;

    public SettingService(Context context){
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void save(Setting setting){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Setting tmp = this.findByType(setting.get_type());
        if(null==tmp){

            db.execSQL("INSERT into setting(key, type, value) values(?, ?, ?)",
                    new Object[]{setting.get_key(),setting.get_type(),setting.get_keyValue()});
        }
        else {
            //update
            ContentValues contentValues = new ContentValues();
            contentValues.put("value",setting.get_keyValue());
            String s = String.valueOf(tmp.get_id());
            int ret = db.update("setting", contentValues, "_id=?", new String[]{String.valueOf(tmp.get_id())});
            Log.d("dbTag","update ret"+ret);
            //db.execSQL("update setting set value="+setting.get_keyValue() + " where _id="+setting.get_id());

        }
    }
    public List<Setting> findAll(){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<Setting> settings = new ArrayList<Setting>();
        Cursor cursor = db.rawQuery("SELECT * FROM setting",null);
        while (cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String key = cursor.getString(cursor.getColumnIndex("key"));
            int value = cursor.getInt(cursor.getColumnIndex("value"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            Setting setting = new Setting(key, type);
            setting.set_id(id);
            setting.set_keyValue(value);
            settings.add(setting);
        }
        return settings;
    }

    public Setting find(final String keyField){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from setting where key=?",
                new String[]{keyField});

        if (cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String key = cursor.getString(cursor.getColumnIndex("key"));
            int keyValue = cursor.getInt(cursor.getColumnIndex("value"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));

            Setting setting = new Setting(key, type);
            setting.set_id(id);
            setting.set_keyValue(keyValue);
            return setting;
        }
        return null;
    }

    public Setting findByType(Integer ttype){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from setting where type=?",
                new String[]{ttype.toString()});
        if (cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String key = cursor.getString(cursor.getColumnIndex("key"));
            int keyValue = cursor.getInt(cursor.getColumnIndex("value"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            Setting setting = new Setting(key, type);
            setting.set_id(id);
            setting.set_keyValue(keyValue);
            return setting;
        }
        return null;
    }
    public Setting find(Integer _id){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from setting where _id=?",
                new String[]{_id.toString()});
        if (cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String key = cursor.getString(cursor.getColumnIndex("key"));
            int keyValue = cursor.getInt(cursor.getColumnIndex("value"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            Setting setting = new Setting(key, type);
            setting.set_id(id);
            setting.set_keyValue(keyValue);
            return setting;
        }
        return null;
    }
}
