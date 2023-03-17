package com.example.sp23_pro1121_cp18103_group4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "DB1";
    public static final int DBVersion = 1;
    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTableLoaiMon = "Create Table LoaiMon (maLoaiMon integer primary key autoincrement," +
                "tenLoaiMon text not null," +
                "imgLoaiMon blob)";
        db.execSQL(CreateTableLoaiMon);
        String CreateTableMon = "Create Table Mon(maMon integer primary key autoincrement," +
                "tenMon text not null," +
                "giaTien integer ," +
                "trangThai text not null," +
                "maLoaiMon integer references LoaiMon(maLoaiMon)," +
                "imgMon blob)";
        db.execSQL(CreateTableMon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DropTableLoaiMon = "Drop table if exists LoaiMon";
        db.execSQL(DropTableLoaiMon);
        //khoi tao lai database
        onCreate(db);
    }
}
