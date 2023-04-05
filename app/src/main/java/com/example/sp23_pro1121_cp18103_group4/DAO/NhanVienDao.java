package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
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
        values.put("name",nhanVien.getHoten());
        values.put("user",nhanVien.getUserName());
        values.put("passWord",nhanVien.getPassWord());
        values.put("numberPhone",nhanVien.getSoDienThoai());
        values.put("ngaySinh",nhanVien.getNamSinh());
        values.put("uyQuyen",nhanVien.getUyQuyen());
        values.put("status",nhanVien.getStartus());
        values.put("gioiTinh",nhanVien.getGioiTinh());
        return db.insert("NhanVien",null,values);

    }


    public int deleteNhanVien(NhanVien nhanVien){
        return db.delete("NhanVien","maNV=?",new String[]{String.valueOf(nhanVien.getMaNV())});
    }
    public long updateNhanVien(NhanVien nhanVien){
        ContentValues values = new ContentValues();
        values.put("name",nhanVien.getHoten());
        values.put("user",nhanVien.getUserName());
        values.put("passWord",nhanVien.getPassWord());
        values.put("numberPhone",nhanVien.getSoDienThoai());
        values.put("ngaySinh",nhanVien.getNamSinh());
        values.put("uyQuyen",nhanVien.getUyQuyen());
        values.put("status",nhanVien.getStartus());
        values.put("gioiTinh",nhanVien.getGioiTinh());
        return db.update("NhanVien",values,"maNV=?",new String[]{String.valueOf(nhanVien.getMaNV())});
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
            int manv = c.getInt(0);
            String hoten = c.getString(1);
            String userName = c.getString(2);
            String PassWord = c.getString(3);
            String numberPhone = c.getString(4);
            String gioiTinh=c.getString(5);
            String Startus = c.getString(8);
            String UyQuyen = c.getString(7);
            int NamSinh = c.getInt(6);
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(manv);
            nhanVien.setHoten(hoten);
            nhanVien.setUserName(userName);
            nhanVien.setPassWord(PassWord);
            nhanVien.setSoDienThoai(numberPhone);
            nhanVien.setGioiTinh(gioiTinh);
            nhanVien.setNamSinh(NamSinh);
            nhanVien.setUyQuyen(UyQuyen);
            nhanVien.setStartus(Startus);
            list.add(nhanVien);
            c.moveToNext();
        }
        return list;
    }

    public NhanVien getNV() {
        String sql = "select * from NhanVien";
        List<NhanVien> list = getData(sql);
        return list.get(0);
    }

    //get id nhan vien check ủy quyền
    public NhanVien getID(String user){
        String sql = "Select * from NhanVien where user=?";
        List<NhanVien> list = getData(sql,user);
        return list.get(0);
    }


    public int checkLogin(String user,String pass){
        String sql = "Select * from NhanVien where user=? and passWord=?";
        List<NhanVien> list  = getData(sql,user,pass);
        if (list.size() == 0){
            return -1;
        }
        return 1;
    }
}
