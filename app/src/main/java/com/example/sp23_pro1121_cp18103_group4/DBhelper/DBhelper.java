package com.example.sp23_pro1121_cp18103_group4.DBhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DB_Name = "nhom4";
    public static final int Version = 1;

    public DBhelper(@Nullable Context context) {
        super(context, DB_Name, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
