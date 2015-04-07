package com.stc.launchersetting.com.stc.launchersetting.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by cninlaxu on 2015/4/7.
 */
public class SettingProvider extends ContentProvider {
    private DBOpenHelper dbOpenHelper;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int SETTINGS = 1;
    private static final int SETTING = 2;

    static {
        MATCHER.addURI("com.stc.launchersetting.settingProvicer", "setting", SETTINGS);
        MATCHER.addURI("com.stc.launchersetting.settingProvicer", "setting/#", SETTING);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new DBOpenHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (MATCHER.match(uri))
        {
            case SETTINGS:
                return db.query("setting", projection, selection,
                        selectionArgs, null, null, sortOrder);

            case SETTING:
                long id = ContentUris.parseId(uri);
                String where = "type="+id;
                if (selection != null && !"".equals(selection)){
                    where = selection + " and " + where;
                }
                return db.query("setting", projection, where, selectionArgs, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("UnKwon Uri:"+uri.toString());
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
