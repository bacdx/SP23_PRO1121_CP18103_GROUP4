package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;

import java.util.ArrayList;

public class BanAnDao {

    private SQLiteDatabase db;

    public BanAnDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long  InSertBanAN(BanAn obj){

        ContentValues contentValues  =new ContentValues();
        contentValues.put("tenBan",obj.getTenBanAN());
        return db.insert("Ban",null,contentValues);

    }

    public int  deleteBanAN(BanAn obj){

        return db.delete("Ban","maBan=?",new String[]{String.valueOf(obj.getId())});

    }

    @SuppressLint("Range")
    public ArrayList<BanAn> getDS(String sql, String... SelectArgs){
        ArrayList<BanAn> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while(c.moveToNext()){
            BanAn banAn = new BanAn();
            banAn.setId(Integer.parseInt(c.getString(c.getColumnIndex("maBan"))));
            banAn.setTenBanAN(c.getString(c.getColumnIndex("tenBan")));
            list.add(banAn);
        }
        return list;
    }


    public ArrayList<BanAn> getALL(){
        String sql = "select * from Ban ";
        return getDS(sql);
    }

}
