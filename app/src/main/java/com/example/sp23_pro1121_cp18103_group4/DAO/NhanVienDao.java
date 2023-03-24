package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.KhachHang;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDao {
    private SQLiteDatabase db;
    public NhanVienDao(Context mContext) {
        DBHelper dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }
    public long insertNhanVien(NhanVien nhanVien){
        ContentValues values = new ContentValues();
        values.put("hoten",nhanVien.getHoten());
        values.put("userName",nhanVien.getUserName());
        values.put("PassWord",nhanVien.getPassWord());
        values.put("SoDienThoai",nhanVien.getSoDienThoai());
        return db.insert("NhanVien",null,values);

    }
    public int deleteNhanVien(NhanVien nhanVien){
        return db.delete("NhanVien","id",new String[]{String.valueOf(nhanVien.getId())});
    }
    public long updateNhanVien(NhanVien nhanVien){
        ContentValues values = new ContentValues();
        values.put("hoten",nhanVien.getHoten());
        values.put("userName",nhanVien.getUserName());
        values.put("PassWord",nhanVien.getPassWord());
        values.put("SoDienThoai",nhanVien.getSoDienThoai());
        return db.update("NhanVien",values,"id",new String[]{String.valueOf(nhanVien.getId())});
    }
    public List<NhanVien> getAll(){
        String sql = "Select * from NhanVien";
        return getData(sql);
    }
    private List<NhanVien> getData(String sql,String ... Arg) {
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,Arg);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int id = c.getInt(0);
            String hoten = c.getString(1);
            String userName = c.getString(2);
            String PassWord = c.getString(3);
            String SoDienThoai = c.getString(4);
            NhanVien nhanVien = new NhanVien(id, hoten, userName, PassWord, SoDienThoai);
            list.add(nhanVien);
            c.moveToNext();
        }
        return list;
    }
}
