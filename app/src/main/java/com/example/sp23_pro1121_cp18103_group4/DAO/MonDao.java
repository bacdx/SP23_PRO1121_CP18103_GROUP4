package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;

import java.util.ArrayList;
import java.util.List;

public class MonDao {
    private SQLiteDatabase db;

    public MonDao(Context mContext) {
        DBHelper dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public long insertMon(Mon mon){
        ContentValues values = new ContentValues();
        values.put("tenMon",mon.getTenMon());
        values.put("giaTien",mon.getGiaTien());
        values.put("trangThai",mon.getTrangThai());
        values.put("imgMon",mon.getImgMon());
        values.put("maLoaiMon",mon.getMaLoaiMon());
        return db.insert("Mon",null,values);
    }

    public int deleteMon(Mon mon){
        return db.delete("Mon","maMon=?",new String[]{String.valueOf(mon.getMaMon())});
    }

    public long updateMon(Mon mon){
        ContentValues values = new ContentValues();
        values.put("trangThai",mon.getTrangThai());
        return db.update("Mon",values,"maMon=?",new String[]{String.valueOf(mon.getMaMon())});
    }

    public List<Mon> getAllWithId(int maLoaiMon){
        String sql = "Select * from Mon where maLoaiMon="+maLoaiMon;
        return getData(sql);
    }

    private List<Mon> getData(String sql , String ... Arg) {
        List<Mon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,Arg);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maMon = c.getInt(0);
            String tenMon = c.getString(1);
            int giaTien = c.getInt(2);
            String trangThai = c.getString(3);
            int maLoaiMon = c.getInt(4);
            byte[] imgMon = c.getBlob(5);
            Mon mon = new Mon(maMon,tenMon,giaTien,trangThai,maLoaiMon);
            mon.setImgMon(imgMon);
            list.add(mon);
            c.moveToNext();
        }
        return list;
    }

}
