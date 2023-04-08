package com.example.sp23_pro1121_cp18103_group4.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.Model.Top5;

import java.util.ArrayList;
import java.util.List;

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
        values.put("tenMon",monTrongBan.getTenMon());
        values.put("imgMon",monTrongBan.getImgMon());
        values.put("giaMon",monTrongBan.getGiaMon());

        return values;
    }
    public int update(MonTrongBan monTrongBan){
        ContentValues values= getValue(monTrongBan);
        values.put("soLuong",monTrongBan.getSoLuong());
        return db.update("MonTrongBan",values,"id=?",new String[]{String.valueOf(monTrongBan.getId())});

    }
    public int delete (String id){
return db.delete("MonTrongBan","id=?",new String[]{id});
    }

    public ArrayList<MonTrongBan> getData(String sql,String...Arg){
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
            monTrongBan.setGiaMon(Integer.parseInt(cursor.getString(4)));
            monTrongBan.setImgMon(cursor.getBlob(5));

            list.add(monTrongBan);
        }while (cursor.moveToNext());
        cursor.close();
        return list;
    }
    public ArrayList<MonTrongBan>  getAllData(){
        String getAllData="select*from MonTrongBan";
        return getData(getAllData);
    }
    public ArrayList<MonTrongBan> getDataByID(String id){
        String getDataByID="select*from MonTrongBan where id=?";
        return getData(getDataByID,id);
    }

    @SuppressLint("Range")

    public int getTong(String id){

        String sql = "select sum(soLuong * giaMon) as tong from MonTrongBan where maBan = ?";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,new String[]{id});


        while(c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("tong"))));
            }catch (Exception e){
                list.add(0);
            }
        }
            return list.get(0);
    }

    @SuppressLint("Range")

    public int getGIamGia(String id){
        String sql = "select ((sum(soLuong * giaMon))-((sum(soLuong * giaMon))*10/100)) as tong from MonTrongBan where maBan = ?";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,new String[]{id});

        while(c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("tong"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
    public ArrayList<MonTrongBan> getAllWithId(String id){
        String sql = "Select * from MonTrongBan where maBan= ?";
        return getData(sql,id);
    }
    public int getwid(String id){
        String sql = "Select * from MonTrongBan where maBan= ?";
        ArrayList<MonTrongBan> list = getData(sql,id);
        if(list.size()>0){
            return 1;
        }else{
            return -1;
        }
    }


    public int getwGia(String gia,String maban){
        String sql = "Select * from MonTrongBan where giaMon= ? and maBan = ?";
        ArrayList<MonTrongBan> list = getData(sql,gia,maban);
        if(list.size()>0){
            return 1;
        }else{
            return -1;
        }
    }

    public ArrayList<MonTrongBan> DeleteAll(String id){
        String sql = "delete from MonTrongBan where maBan = ?";
        return getData(sql,id);
    }

    @SuppressLint("Range")
    public ArrayList<Top5> getTOp(){

        String sqltop = "select tenMon,soLuong,sum(soLuong) as sl from MonTrongBan group by tenMon order by soLuong desc limit 5";
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
