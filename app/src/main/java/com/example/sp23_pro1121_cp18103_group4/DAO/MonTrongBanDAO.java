package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;

import java.util.ArrayList;

public class MonTrongBanDAO {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public MonTrongBanDAO(Context context) {

        this.context = context;
        dbHelper=new DBHelper(context);
        db=dbHelper.getReadableDatabase();
    }

    public long insert(MonTrongBan monTrongBan){
       ContentValues values= getValue(monTrongBan);
       return db.insert("MonTrongBan",null,values);
    }
    public ContentValues getValue(MonTrongBan monTrongBan){
        ContentValues values=new ContentValues();
        values.put("maBan",monTrongBan.getMaBan());
        values.put("maMon",monTrongBan.getMaMon());
        values.put("soLuong",monTrongBan.getSoLuong());
        return values;
    }
    public int update(MonTrongBan monTrongBan){
        ContentValues values= getValue(monTrongBan);
        return db.update("MonTrongBan",values,"id=?",new String[]{String.valueOf(monTrongBan.getId())});

    }
    public int delete (String id){
return db.delete("MonTrongBan","id=?",new String[]{id});
    }

    public ArrayList<MonTrongBan> getData(String sql,String...selectionArgs){
        ArrayList<MonTrongBan> list=new ArrayList<MonTrongBan>();
        Cursor cursor= db.rawQuery(sql,selectionArgs);
        MonTrongBan monTrongBan=new MonTrongBan();
        cursor.moveToFirst();
        do {
            monTrongBan.setId(cursor.getInt(0));
            monTrongBan.setMaBan(cursor.getString(1));
            monTrongBan.setMaMon(cursor.getString(2));
            monTrongBan.setSoLuong(Integer.parseInt(cursor.getString(3)));
            list.add(monTrongBan);
        }while (cursor.moveToNext());
        cursor.close();
        return list;
    }
    public ArrayList<MonTrongBan>  getAllData(){
        String getAllData="select*from MonTrongBan";
        return getData(getAllData,null);
    }
    public ArrayList<MonTrongBan> getDataByID(String id){
        String getDataByID="select*from MonTrongBan where id=?";
        return getData(getDataByID,id);

    }
}
