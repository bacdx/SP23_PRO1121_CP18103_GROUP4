package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDao {
    private SQLiteDatabase db;

    public KhachHangDao(Context mContext) {
        DBHelper dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public long insertKhachHang(KhachHang khachHang){
        ContentValues values = new ContentValues();
        values.put("hoTen",khachHang.getHoTen());
        values.put("namSinh",khachHang.getNamSinh());
        values.put("gioiTinh",khachHang.getGioiTinh());
        values.put("soDT",khachHang.getSoDT());
        values.put("diaChi",khachHang.getDiaChi());
        return db.insert("KhachHang",null,values);
    }

    public int deleteKhachHang(KhachHang khachHang){
        return db.delete("KhachHang","maKhachHang=?",new String[]{String.valueOf(khachHang.getMaKhachHang())});
    }

    public long updateKhachHang(KhachHang khachHang){
        ContentValues values = new ContentValues();
        values.put("hoTen",khachHang.getHoTen());
        values.put("namSinh",khachHang.getNamSinh());
        values.put("gioiTinh",khachHang.getGioiTinh());
        values.put("soDT",khachHang.getSoDT());
        values.put("diaChi",khachHang.getDiaChi());
        return db.update("KhachHang",values,"maKhachHang=?",new String[]{String.valueOf(khachHang.getMaKhachHang())});
    }

    public ArrayList<KhachHang> getAll(){
        String sql = "Select * from KhachHang";
        return getData(sql);
    }

    private ArrayList<KhachHang> getData(String sql,String ... Arg) {
        ArrayList<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,Arg);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maKhachHang = c.getInt(0);
            String hoTen = c.getString(1);
            int namSinh = c.getInt(2);
            String gioiTinh = c.getString(3);
            String soDT = c.getString(4);
            String diaChi = c.getString(5);
            KhachHang khachHang = new KhachHang(maKhachHang,hoTen,namSinh,gioiTinh,soDT,diaChi);
            list.add(khachHang);
            c.moveToNext();
        }
        return list;
    }
}
