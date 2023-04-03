package com.example.sp23_pro1121_cp18103_group4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sp23_pro1121_cp18103_group4.data.Data;


public class DBHelper extends SQLiteOpenHelper {
    public Data data = new Data();
    public static final String DBName = "FAST_FOOD";


    public static final int DBVersion = 15;





    public DBHelper(@Nullable Context context) {

        super(context, DBName, null, DBVersion);
    }
    private static final String TABLE_BAN = "create table Ban (" +
            "maBan integer not null primary key autoincrement," +
            "tenBan text not null ," +
            "status text );";
    private static final String TABLE_MON_TRONG_BAN = "create table MonTrongBan (" +
            "id integer not null primary key ," +
            "maBan integer references Ban(maBan)," +
            "maMon integer references Mon(maMon)," +
            "tenMon text," +
            "giaMon integer not null," +
            "imgMon text," +
            "soLuong integer);";
    private static final String TABLE_LOAI_MON = "create table LoaiMon (maLoaiMon integer primary key autoincrement," +
            "tenLoaiMon text not null," +
            "imgLoaiMon text)";
    private static final String TABLE_MON = "Create Table Mon(maMon integer primary key autoincrement," +
            "tenMon text not null," +
            "giaTien integer ," +
            "trangThai text not null," +
            "maLoaiMon integer references LoaiMon(maLoaiMon)," +
            "imgMon text)";
    private static final String TABLE_NHANVIEN = "create table NhanVien(" +
            "maNV integer not null primary key," +
            "name text," +
            "user text," +
            "passWord text," +
            "numberPhone text," +

            "gioiTinh text," +
            "ngaySinh date," +

            "uyQuyen text," +
            "status text);";
    private static final String TABLE_HOADON = "create table HoaDon(" +
            "maHoaDon integer not null primary key," +
            "maBan integer references Ban(maBan)," +
            "maNV integer references NhanVien(maNV)," +
            "ngayLap date not null," +
            "maKhachHang integer references KhachHang(maKhachHang)," +
            "tongTien integer); ";



    private static final String TABLE_kHACH_HANG = "create table KhachHang(" +
            "maKhachHang integer not null primary key," +
            "hoTen text," +
            "namSinh integer," +
            "gioiTinh text," +
            "soDT text," +
            "diaChi text)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NHANVIEN);
        db.execSQL(TABLE_LOAI_MON);
        db.execSQL(TABLE_MON);
        db.execSQL(TABLE_BAN);
        db.execSQL(TABLE_MON_TRONG_BAN);
        db.execSQL(TABLE_kHACH_HANG);
        db.execSQL(TABLE_HOADON);
        db.execSQL(Data.insertNhanVien);
//        db.execSQL(data.insertLoaiMon);
//        db.execSQL(data.insertMon);
//        db.execSQL(data.insertBan);
//        db.execSQL(data.insertMonTrongBan);
//        db.execSQL(data.insertKhachHang);
//        db.execSQL(data.insertHoaDon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists NhanVien");
        db.execSQL("Drop table if exists LoaiMon");
        db.execSQL("Drop table if exists Mon");
        db.execSQL("Drop table if exists Ban");
        db.execSQL("Drop table if exists MonTrongBan");
        db.execSQL("Drop table if exists KhachHang");
        db.execSQL("Drop table if exists HoaDon");
        //khoi tao lai database
        onCreate(db);
    }
}
