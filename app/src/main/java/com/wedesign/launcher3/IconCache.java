package com.wedesign.launcher3;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

public class IconCache {
    private static final String TAG = "Launcher.IconCache";

    private static final int INITIAL_ICON_CACHE_CAPACITY = 50;

    final IconDB mIconDb;

    private final HashMap<ComponentName, CacheEntry> mCache =
            new HashMap<ComponentName, CacheEntry>(INITIAL_ICON_CACHE_CAPACITY);

    public IconCache(Context context) {
        mIconDb = new IconDB(context, LauncherDBHelper.LAUNCHER_DB_NAME, null, 2);
    }

    public static class CacheEntry {
        public int screen;
        public int cellX;
        public int cellY;
        public CharSequence title;
        public Bitmap smallIcon;
        public Bitmap largeIcon;
    }

    private static final class IconDB extends SQLiteOpenHelper {
        private final static String TABLE_NAME = "icons";
        private final static String COLUMN_COMPONENT_NAME = "componentName";
        private final static String COLUMN_SCREEN = "screen";
        private final static String COLUMN_CELLX = "cellX";
        private final static String COLUMN_CELLY = "cellY";
        private final static String COLUMN_TITLE = "title";
        private final static String COLUMN_SMALL_ICON = "smallIcon";
        private final static String COLUMN_LARGE_ICON = "largeIcon";

        public IconDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    "_id INTEGER PRIMARY KEY, " +
                    COLUMN_COMPONENT_NAME + " TEXT NOT NULL, " +
                    COLUMN_SCREEN + " INTEGER, " +
                    COLUMN_CELLX + " INTEGER, " +
                    COLUMN_CELLY + " INTEGER, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_SMALL_ICON + " BLOB, " +
                    COLUMN_LARGE_ICON + " BLOB);" );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_NAME);
        }
    }

    synchronized void addIconToDBAndMemCache(ComponentName componentName, CacheEntry entry) {
        mCache.put(componentName, entry);

        ContentValues values = new ContentValues();
        values.put(IconDB.COLUMN_COMPONENT_NAME, componentName.toShortString());
        values.put(IconDB.COLUMN_SCREEN, entry.screen);
        values.put(IconDB.COLUMN_CELLX, entry.cellX);
        values.put(IconDB.COLUMN_CELLY, entry.cellY);
        values.put(IconDB.COLUMN_TITLE, entry.title.toString());
        values.put(IconDB.COLUMN_SMALL_ICON, Utilities.flattenBitmap(entry.smallIcon));
        values.put(IconDB.COLUMN_LARGE_ICON, Utilities.flattenBitmap(entry.largeIcon));

        SQLiteDatabase db = mIconDb.getWritableDatabase();
        db.insert(IconDB.TABLE_NAME, null, values);
    }
}
