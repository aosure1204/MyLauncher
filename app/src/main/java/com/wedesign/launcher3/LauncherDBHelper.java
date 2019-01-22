package com.wedesign.launcher3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LauncherDBHelper extends SQLiteOpenHelper {
    public static final String LAUNCHER_DB_NAME = "launcher.db";
    public static final String WORKSPACE_SCREENS_TABLE_NAME = "workspaceScreens";
    public static final String FAVORITES_TABLE_NAME = "favorites";

    private static final String CREATE_WORKSPACE_SCREENS = "CREATE TABLE workspaceScreens ("
            + "_id INTEGER PRIMARY KEY,"
            + "screenRank INTEGER,"
            + "modified INTEGER NOT NULL DEFAULT 0)";

    private static final String CREATE_FAVORITES = "CREATE TABLE favorites (" +
            "_id INTEGER PRIMARY KEY," +
            "title TEXT, " +
            "intent TEXT, " +
            "screen INTEGER," +
            "cellX INTEGER," +
            "cellY INTEGER," +
            "smallIcon BLOB," +
            "largeIcon BLOB," +
            "modified INTEGER NOT NULL DEFAULT 0)";

    public LauncherDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORKSPACE_SCREENS);
        db.execSQL(CREATE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists workspaceScreens");
        db.execSQL("drop table if exists favorites");
    }
}
