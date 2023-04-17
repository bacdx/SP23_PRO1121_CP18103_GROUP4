package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.HoaDon;
import com.example.sp23_pro1121_cp18103_group4.Model.HoaDonHT;
import com.example.sp23_pro1121_cp18103_group4.Model.Top5;

import java.util.ArrayList;

public class HoaDonDao {
    private SQLiteDatabase db;

    public HoaDonDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertHoaDon(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maNV", hoaDon.getMaNV());
        values.put("maKhachHang", hoaDon.getMaKH());
        values.put("ngayLap", hoaDon.getNgayLap());
        values.put("tongTien", hoaDon.getTongTien());
        return db.insert("HoaDon", null, values);
    }



    public int deleteHoaDon(HoaDon hoaDon) {
        return db.delete("HoaDon", "maHoaDon", new String[]{String.valueOf(hoaDon.getMaHoaDon())});
    }
    private ArrayList<HoaDon> getData(String sql, String... Arg) {
        ArrayList<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, Arg);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            String maHoaDon = c.getString(0);
            String maNV = c.getString(1);
            String maKH = c.getString(3);
            String ngayLap =  c.getString(2);
            int tongTien = c.getInt(4);

            HoaDon hoaDon = new HoaDon(maHoaDon, maNV,maKH, ngayLap,tongTien);
            list.add(hoaDon);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public ArrayList<HoaDon> getAll() {
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
                list.add((int) Float.parseFloat(""+c.getString(c.getColumnIndex("doanhthu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
    public ArrayList<HoaDon> getDataByDate(String sDay, String endDay){
        String sql = "select *  from HoaDon where ngayLap between date(?) and date(?)";
        return getData(sql,sDay,endDay);
    }
    public int getDataNew(){
        String sql = "select max(maHoaDon)  from HoaDon ";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
            int maHoaDon=cursor.getInt(0);
            cursor.close();
            return maHoaDon;

    }
    public ArrayList <HoaDonHT> getListHD(){
        ArrayList<HoaDonHT> list=new ArrayList<>();

        String sql="SELECT HoaDon.maHoaDon, NhanVien.name, HoaDon.tongTien, HoaDon.ngayLap " +
                "FROM HoaDon " +
                "INNER JOIN NhanVien ON HoaDon.maNV = NhanVien.maNV";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        do{
            HoaDonHT hoaDonHT=new HoaDonHT();
           hoaDonHT.setMaHD(cursor.getString(0));
           hoaDonHT.setTenNV(cursor.getString(1));
           hoaDonHT.setTongTien(cursor.getString(2));
           hoaDonHT.setNgayLap(cursor.getString(3));
           list.add(hoaDonHT);
        }while (cursor.moveToNext());
        cursor.close();

        return list;
    }




}
