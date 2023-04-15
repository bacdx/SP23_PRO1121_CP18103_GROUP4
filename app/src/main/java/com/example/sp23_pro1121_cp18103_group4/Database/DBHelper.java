package com.example.sp23_pro1121_cp18103_group4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sp23_pro1121_cp18103_group4.data.Data;


public class DBHelper extends SQLiteOpenHelper {
    public Data data = new Data();
    public static final String DBName = "FAST_FOOD";


    public static final int DBVersion = 1;


    public DBHelper(@Nullable Context context) {

        super(context, DBName, null, DBVersion);
    }
    private static final String TABLE_BAN = "create table Ban (" +
            "maBan integer not null primary key autoincrement," +
            "tenBan text not null ," +
            "status text );";
    private static final String TABLE_MON_TRONG_BAN = "create table MonTrongBan (" +
            "id integer not null primary key autoincrement ," +
            "maBan text references Ban(maBan)," +
            "maMon text references Mon(maMon)," +
            "maHoaDon text references HoaDon(maHoaDon),"+
            "tenMon text," +
            "tien float not null," +
            "imgMon text," +
            "soLuong integer);";

    private static final String TABLE_MON_TRONG_BAN2 = "create table MonTrongBan2 (" +
            "id integer not null primary key ," +
            "maBan integer references Ban(maBan)," +
            "maMon integer references Mon(maMon)," +
            "tenMon text," +
            "giaMon float not null," +
            "imgMon text," +
            "soLuong integer);";

    private static final String TABLE_LOAI_MON = "create table LoaiMon (maLoaiMon integer primary key autoincrement," +
            "tenLoaiMon text not null," +
            "imgLoaiMon text)";
    private static final String TABLE_MON = "Create Table Mon(maMon integer primary key autoincrement," +
            "tenMon text not null," +
            "giaTien float ," +
            "trangThai integer not null," +
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
            "uyQuyen integer," +
            "status text);";

    private static final String TABLE_HOADON = "create table HoaDon(" +
            "maHoaDon integer not null primary key autoincrement," +
            "maNV integer references NhanVien(maNV)," +
            "ngayLap date not null," +
            "maKhachHang integer references KhachHang(maKhachHang)," +
            "tongTien float); ";



    private static final String TABLE_kHACH_HANG = "create table KhachHang(" +
            "maKhachHang integer not null primary key," +
            "hoTen text," +
            "namSinh integer," +
            "gioiTinh text," +
            "soDT text," +
            "diaChi text)";


    //khách hàng dùng appp
    String CreatTalbeDatHang = "Create table DatHang(" +
            "maDatHang integer not null primary key autoincrement," +
            "soLuong integer," +
            "giaTien float," +
            "maMon integer references Mon(maMon))";
    String CreateTableNguoiDung = "Create table NguoiDung(" +
            "username text not null primary key," +
            "hoTen text," +
            "password text," +
            "soDT text," +
            "diaChi text)";

    String CreateTableDonHang = "Create table DonHang(" +
            "maDonHang text primary key ," +
            "ngayThanhToan text," +
            "trangThai text," +
            "tongTien float," +
            "maDatHang integer references DatMon(maDatHang)," +
            "username text references NguoiDung(username)," +
            "tenNguoiDung text ," +
            "soDT text ," +
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
        db.execSQL(TABLE_MON_TRONG_BAN2);
        db.execSQL(CreatTalbeDatHang);
        db.execSQL(CreateTableNguoiDung);
        db.execSQL(CreateTableDonHang);
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
        db.execSQL("Drop table if exists MonTrongBan2");
        //khoi tao lai database
        onCreate(db);
    }
}
