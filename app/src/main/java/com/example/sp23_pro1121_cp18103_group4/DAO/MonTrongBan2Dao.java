package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.Model.Top5;

import java.util.ArrayList;

public class MonTrongBan2Dao {

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public MonTrongBan2Dao( Context context) {
        this.context = context;
        dbHelper=new DBHelper(context);
        db=dbHelper.getReadableDatabase();
    }

    public long insert2(MonTrongBan monTrongBan2){
        ContentValues values= getValue2(monTrongBan2);
        return db.insert("MonTrongBan2",null,values);
    }
    public ContentValues getValue2(MonTrongBan monTrongBan2){
        ContentValues values=new ContentValues();
        values.put("maBan",monTrongBan2.getMaBan());
        values.put("maMon",monTrongBan2.getMaMon());
        values.put("soLuong",monTrongBan2.getSoLuong());
        values.put("tenMon",monTrongBan2.getTenMon());
        values.put("imgMon",monTrongBan2.getImgMon());
        values.put("giaMon",monTrongBan2.getTien());

        return values;
    }

    public ArrayList<MonTrongBan> getData2(String sql, String...Arg){
        ArrayList<MonTrongBan> list=new ArrayList<MonTrongBan>();
        Cursor cursor= db.rawQuery(sql,Arg);
        cursor.moveToFirst();
        do {
            MonTrongBan monTrongBan=new MonTrongBan();
            monTrongBan.setId(cursor.getInt(0));
            monTrongBan.setMaBan(cursor.getString(1));
            monTrongBan.setMaMon(cursor.getString(2));
            monTrongBan.setSoLuong(Integer.parseInt(cursor.getString(6)));
            monTrongBan.setTenMon(cursor.getString(3));
            monTrongBan.setTien(Integer.parseInt(cursor.getString(4)));
            monTrongBan.setImgMon(cursor.getBlob(5));

            list.add(monTrongBan);
        }while (cursor.moveToNext());
        cursor.close();
        return list;
    }

    public ArrayList<MonTrongBan> getALL(){

        String sql = "select * from MonTrongBan2";
        return getData2(sql);

    }


    @SuppressLint("Range")
    public ArrayList<Top5> getTOp(){

        String sqltop = "select tenMon,soLuong,sum(soLuong) as sl from MonTrongBan2 group by tenMon order by soLuong desc limit 5";
        ArrayList<Top5> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqltop,null);
        while(c.moveToNext()){
            Top5 top10 = new Top5();
            top10.setTenmon(c.getString(c.getColumnIndex("tenMon")));
            top10.setSoluong(c.getString(c.getColumnIndex("sl")));
            list.add(top10);
        }
        return list;
    }


}
