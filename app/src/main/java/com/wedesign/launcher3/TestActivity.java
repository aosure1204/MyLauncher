package com.wedesign.launcher3;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class TestActivity extends Activity {
    private static final String TAG = "TestActivity";

    private ImageButton mBtnDBTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        setContentView(R.layout.activity_other);

        mBtnDBTest = (ImageButton) findViewById(R.id.btn_db_test);
        mBtnDBTest.setOnClickListener(new View.OnClickListener(){

//            @Override
//            public void onClick(View v) {
//                LauncherDBHelper dbHelper = new LauncherDBHelper(MainActivity.this, LauncherDBHelper.LAUNCHER_DB_NAME, null, 1);
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//                ContentValues values = new ContentValues();
//                values.put("screenRank", 1);
//                db.insert(LauncherDBHelper.WORKSPACE_SCREENS_TABLE_NAME, null, values);
//            }

            @Override
            public void onClick(View v) {
                LauncherDBHelper dbHelper = new LauncherDBHelper(TestActivity.this, LauncherDBHelper.LAUNCHER_DB_NAME, null, 2);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                int row = 0;
                // 查询icons表中所有的数据
                Cursor cursor = db.query("icons", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        row++;
                        // 遍历Cursor对象，取出数据并打印
                        String title = cursor.getString(cursor.getColumnIndex("title"));
                        Log.d(TAG, "icons title is " + title);
                    } while (cursor.moveToNext());
                }
                cursor.close();

                Log.d(TAG, "icons row is " + row);
            }
        });
    }
}
