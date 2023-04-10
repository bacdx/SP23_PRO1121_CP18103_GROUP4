package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.DonHang;

import java.util.ArrayList;
import java.util.List;

public class DonHangDao {
    private SQLiteDatabase db;

    public DonHangDao(Context mContext) {
        DBHelper dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public long insertDonHang(DonHang donHang){
        ContentValues values = new ContentValues();
        values.put("maDonHang",donHang.getMaDonHang());
        values.put("ngayThanhToan",donHang.getNgayThanhToan());
        values.put("trangThai",donHang.getTrangThai());
        values.put("tongTien",donHang.getTongTien());
        values.put("maDatHang",donHang.getMaDatHang());
        values.put("username",donHang.getMaNguoiDung());
        values.put("tenNguoiDung",donHang.getTenNguoiDung());
        values.put("soDT",donHang.getSoDt());
        values.put("diaChi",donHang.getDiaChi());
        return db.insert("DonHang",null,values);
    }

    public long updateDonHang(DonHang donHang){
        ContentValues values = new ContentValues();
        values.put("tenNguoiDung",donHang.getTenNguoiDung());
        values.put("soDT",donHang.getSoDt());
        values.put("diaChi",donHang.getDiaChi());
        return db.update("DonHang",values,"maDonHang=?",new String[]{String.valueOf(donHang.getMaDonHang())});
    }

    public int deleteDonHang(String maDatHang){
        return db.delete("DonHang","maDonHang=?",new String[]{String.valueOf(maDatHang)});
    }

    public List<DonHang> getAllWithId(String maKhachHang){
        String sql = "Select * from DonHang where username=?";
        return getData(sql,maKhachHang);
    }

    public List<DonHang> getAll( ){
        String sql = "Select * from DonHang";
        return getData(sql);
    }

    public int checkTrong(String donHang ) {
        int check;
        String sql = "Select * from DonHang where username=?";
        List<DonHang> list = getData(sql, donHang);
        if (list.size() > 0){
            check = 1;
        }else{
            check = -1;
        }
        return check;
    }
    public int checkRong() {
        int check;
        String sql = "Select * from DonHang";
        List<DonHang> list = getData(sql);
        if (list.size() > 0){
            check = 1;
        }else{
            check = -1;
        }
        return check;
    }
    private List<DonHang> getData(String sql , String ... Arg) {
        List<DonHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,Arg);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String maDonHang = c.getString(0);
            String ngayThanhToan = c.getString(1);
            String trangThai = c.getString(2);
            int tongTien = c.getInt(3);
            int maDatHang = c.getInt(4);
            String maKhachHang = c.getString(5);
            String tenNguoiDung = c.getString(6);
            String soDt = c.getString(7);
            String diaChi = c.getString(8);
            DonHang donHang = new DonHang(maDonHang,ngayThanhToan,trangThai,tongTien,maDatHang,maKhachHang,tenNguoiDung,soDt,diaChi);
            list.add(donHang);
            c.moveToNext();
        }
        return list;
    }
}
