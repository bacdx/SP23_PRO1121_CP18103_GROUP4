package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.DatHang;

import java.util.ArrayList;
import java.util.List;

public class DatHangDao {
    private SQLiteDatabase db;
    DBHelper dbHelper;


    public DatHangDao(Context mContext) {
        DBHelper dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public long insertDatHang(DatHang datHang) {
        ContentValues values = new ContentValues();
        values.put("soLuong", datHang.getSoLuong());
        values.put("giaTien", datHang.getGiaTien());
        values.put("maMon", datHang.getMaMon());
        values.put("maDonHang",datHang.getmaDonHang());

        return db.insert("DatHang", null, values);
    }

    public int deleteDatHang(DatHang datHang) {
        return db.delete("DatHang", "maDatHang=?", new String[]{String.valueOf(datHang.getMaDatHang())});
    }
    public int update(DatHang datHang){
        ContentValues values = new ContentValues();
        values.put("soLuong", datHang.getSoLuong());
        values.put("giaTien", datHang.getGiaTien());
        values.put("maMon", datHang.getMaMon());
        values.put("maDonHang",datHang.getmaDonHang());
        return db.update("DatHang",values,"maDatHang=?",new String[]{String.valueOf(datHang.getMaDatHang())});
    };

    public List<DatHang> deleteAll(String maDatHang){
        String sql = "Delete from DatHang where maDatHang=?";
        return getData(sql,maDatHang);
    }
    public List<DatHang> getAll() {
        String sql = "Select * from DatHang where maDonHang is null";
        return getData(sql);
    }

    private List<DatHang> getData(String sql, String... Arg) {
        List<DatHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, Arg);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int maDatHang = c.getInt(0);
            int soLuong = c.getInt(2);
            int giaTien = c.getInt(3);
            int maMon = c.getInt(4);
            String maDonHang =c.getString(1);
            DatHang datHang = new DatHang(maDatHang, soLuong, giaTien, maMon,maDonHang);
            list.add(datHang);
            c.moveToNext();
        }
        return list;
    }

    public int checkThanhToan(String maMon ) {
        int check;
        String sql = "Select * from DatHang where maMon=? and maDonHang is null";
        List<DatHang> list = getData(sql, maMon);
        if (list.size() > 0){
            check = 1;
        }else{
            check = -1;
        }
        return check;
    }
}
