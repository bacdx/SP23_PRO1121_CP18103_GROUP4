package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.LoaiMon;
import com.example.sp23_pro1121_cp18103_group4.Model.ModelHoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDao {
    private SQLiteDatabase db;

    public HoaDonDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertHoaDon(ModelHoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDon.getMaHoaDon());
        values.put("maBan", hoaDon.getMaBan());
        values.put("maNV", hoaDon.getMaNV());
        values.put("maKH", hoaDon.getMaKH());
        values.put("ngayLap", hoaDon.getNgayLap());
        values.put("tongTien", hoaDon.getTongTien());
        return db.insert("ModelHoaDon", null, values);
    }

    public int deleteHoaDon(ModelHoaDon hoaDon) {
        return db.delete("ModelHoaDon", "maHoaDon", new String[]{String.valueOf(hoaDon.getMaHoaDon())});
    }

    public long updateHoaDon(ModelHoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDon.getMaHoaDon());
        values.put("maBan", hoaDon.getMaBan());
        values.put("maNV", hoaDon.getMaNV());
        values.put("maKH", hoaDon.getMaKH());
        values.put("ngayLap", hoaDon.getNgayLap());
        values.put("tongTien", hoaDon.getTongTien());
        return db.update("ModelHoaDon", values, "maHoaDon", new String[]{String.valueOf(hoaDon.getMaHoaDon())});
    }

    public List<ModelHoaDon> getAll() {
        String sql = "Select * from ModelHoaDon";
        return getData(sql);
    }

    private List<ModelHoaDon> getData(String sql, String... Arg) {
        List<ModelHoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, Arg);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String maHoaDon = c.getString(0);
            String maBan = c.getString(1);
            String maNV = c.getString(2);
            String maKH = c.getString(3);
            String ngayLap =  c.getString(4);
            int tongTien = c.getInt(5);
            ModelHoaDon hoaDon = new ModelHoaDon(maHoaDon, maBan, maNV,maKH, ngayLap,tongTien);
            list.add(hoaDon);
            c.moveToNext();
        }
        return list;
    }
}
