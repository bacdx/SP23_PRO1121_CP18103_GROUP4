package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.LoaiMon;
import com.example.sp23_pro1121_cp18103_group4.Model.ModelHoaDon;
import com.example.sp23_pro1121_cp18103_group4.Model.Top5;

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
        values.put("maBan", hoaDon.getMaBan());
        values.put("maNV", hoaDon.getMaNV());
        values.put("maKhachHang", hoaDon.getMaKH());
        values.put("ngayLap", hoaDon.getNgayLap());
        values.put("tongTien", hoaDon.getTongTien());
        return db.insert("HoaDon", null, values);
    }

    public int deleteHoaDon(ModelHoaDon hoaDon) {
        return db.delete("HoaDon", "maHoaDon", new String[]{String.valueOf(hoaDon.getMaHoaDon())});
    }

    public long updateHoaDon(ModelHoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maBan", hoaDon.getMaBan());
        values.put("maNV", hoaDon.getMaNV());
        values.put("maKH", hoaDon.getMaKH());
        values.put("ngayLap", hoaDon.getNgayLap());
        values.put("tongTien", hoaDon.getTongTien());
        return db.update("HoaDon", values, "maHoaDon", new String[]{String.valueOf(hoaDon.getMaHoaDon())});
    }



    private ArrayList<ModelHoaDon> getData(String sql, String... Arg) {
        ArrayList<ModelHoaDon> list = new ArrayList<>();
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

    public ArrayList<ModelHoaDon> getAll() {
        String sql = "Select * from HoaDon";
        return getData(sql);
    }


    @SuppressLint("Range")
    public int getDoanhThu(String tungay, String denngay){

        String sql = "select sum(tongTien) as doanhthu from HoaDon where ngayLap between ? and ?";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,new String[]{tungay,denngay});

        while(c.moveToNext()){

            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhthu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }



}
