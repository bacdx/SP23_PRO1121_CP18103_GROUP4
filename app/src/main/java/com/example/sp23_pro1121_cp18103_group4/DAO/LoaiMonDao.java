package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.LoaiMon;

import java.util.ArrayList;
import java.util.List;


public class LoaiMonDao {
    private SQLiteDatabase db;

    public LoaiMonDao(Context mContext) {
        DBHelper dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public long insertLoaiMon(LoaiMon loaiMon){
        ContentValues values = new ContentValues();
        values.put("tenLoaiMon",loaiMon.getTenLoaiMon());
        values.put("imgLoaiMon",loaiMon.getImgLoaiMon());
        return db.insert("LoaiMon",null,values);
    }

    public int deleteLoaiMon(LoaiMon loaiMon){
        return db.delete("LoaiMon","maLoaiMon=?",new String[]{String.valueOf(loaiMon.getMaLoaiMon())});
    }

    public long updateLoaiMon(LoaiMon loaiMon){
        ContentValues values = new ContentValues();
        values.put("tenLoaiMon",loaiMon.getTenLoaiMon());
        values.put("imgLoaiMon",loaiMon.getImgLoaiMon());
        return db.update("LoaiMon",values,"maLoaiMon=?",new String[]{String.valueOf(loaiMon.getMaLoaiMon())});
    }

    public List<LoaiMon> getAll(){
        String sql = "Select * from LoaiMon";
        return getData(sql);
    }

    private List<LoaiMon> getData(String sql , String  ... Arg) {
        List<LoaiMon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,Arg);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int maLoaiMon = c.getInt(0);
            String tenLoaiMon = c.getString(1);
            byte[] imgLoaiMon = c.getBlob(2);
            LoaiMon loaiMon = new LoaiMon(maLoaiMon,tenLoaiMon);
            loaiMon.setImgLoaiMon(imgLoaiMon);
            list.add(loaiMon);
            c.moveToNext();
        }
        return list;
    }
}
