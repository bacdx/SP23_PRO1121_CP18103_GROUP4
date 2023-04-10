package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDao {
    private SQLiteDatabase db;

    public NguoiDungDao(Context mContext) {
        DBHelper dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public long registerTaiKhoan(NguoiDung nguoiDung){
        ContentValues values = new ContentValues();
        values.put("hoTen",nguoiDung.getHoTen());
        values.put("username",nguoiDung.getUsername());
        values.put("password",nguoiDung.getPassword());
        values.put("soDT",nguoiDung.getSoDT());
        values.put("diaChi",nguoiDung.getDiaChi());
        return db.insert("NguoiDung",null,values);
    }

    public long updateTaiKhoan(NguoiDung nguoiDung){
        ContentValues values = new ContentValues();
        values.put("password",nguoiDung.getPassword());
        return db.update("NguoiDung",values,"username=?",new String[]{String.valueOf(nguoiDung.getUsername())});
    }
    public long updateKhachHang(NguoiDung nguoiDung){
        ContentValues values = new ContentValues();
        values.put("hoTen",nguoiDung.getHoTen());
        values.put("username",nguoiDung.getUsername());
        values.put("soDT",nguoiDung.getSoDT());
        values.put("diaChi",nguoiDung.getDiaChi());
        return db.update("NguoiDung",values,"username=?",new String[]{String.valueOf(nguoiDung.getUsername())});
    }
    public List<NguoiDung> getAll(){
        String sql = "Select * from NguoiDung";
        return getData(sql);
    }
    private List<NguoiDung> getData(String sql, String ... Arg) {
        List<NguoiDung> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,Arg);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String username = c.getString(0);
            String hoTen = c.getString(1);
            String password = c.getString(2);
            String soDT = c.getString(3);
            String diaChi = c.getString(4);
            NguoiDung nguoiDung = new NguoiDung(username,hoTen,password,soDT,diaChi);
            list.add(nguoiDung);
            c.moveToNext();
        }
        return list;
    }


    public NguoiDung getID(String user){
        String sql = "Select * from NguoiDung where username=?";
        List<NguoiDung> list = getData(sql,user);
        return list.get(0);
    }

    public int checkLogin(String user,String pass){
        String sql = "Select * from NguoiDung where username=? and password=?";
        List<NguoiDung> list  = getData(sql,user,pass);
        if (list.size() == 0){
            return -1;
        }
        return 1;
    }

    public int checkUsername(String username) {
        int check;
        String sql = "Select * from NguoiDung where username=?";
        List<NguoiDung> list = getData(sql, username);
        if (list.size() > 0){
            check = 1;
        }else{
            check = -1;
        }
        return check;
    }
}
