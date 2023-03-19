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
        values.put("name",khachHang.getName());
        values.put("numberPhone",khachHang.getNumberPhone());
        return db.insert("KhachHang",null,values);
    }

    public int deleteKhachHang(KhachHang khachHang){
        return db.delete("KhachHang","maKhachHang=?",new String[]{String.valueOf(khachHang.getMaKhachHang())});
    }

    public long updateKhachHang(KhachHang khachHang){
        ContentValues values = new ContentValues();
        values.put("name",khachHang.getName());
        values.put("numberPhone",khachHang.getNumberPhone());
        return db.update("KhachHang",values,"maKhachHang=?",new String[]{String.valueOf(khachHang.getMaKhachHang())});
    }

    public List<KhachHang> getAll(){
        String sql = "Select * from KhachHang";
        return getData(sql);
    }

    private List<KhachHang> getData(String sql,String ... Arg) {
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,Arg);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maKhachHang = c.getInt(0);
            String name = c.getString(1);
            String numberPhone = c.getString(2);
            KhachHang khachHang = new KhachHang(maKhachHang,name,numberPhone);
            list.add(khachHang);
            c.moveToNext();
        }
        return list;
    }
}
