package com.example.sp23_pro1121_cp18103_group4.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sp23_pro1121_cp18103_group4.Database.DBHelper;

public  class data {

 public static final String insertNhanVien=" insert into NhanVien(name,user,passWord,email,numberPhone,status)" +
         "values  ('admin','admin','admin','admin',so dien thoai,'Dang Lam Viec')," +
         "('Bac','bac','bac','bactxph20234@fpt.edu.vn',0359789536,'Dang Lam Viec')," +
         "('hoang','hoang','hoang','hoang@fpt.edu.vn',0111111111,'Dang Lam Viec')," +
         "('Long','long','long','Long@fpt.edu.vn',03333333,'Tam nghi')," +
         "('Son','son','son','son@fpt.edu.vn',022221111,'Dang Lam Viec');";
 public static final  String insertLoaiMon="insert into LoaiMon(tenLoaiMon,imgLoaiMon)" +
         "values ( 'Tra sua','null')," +
         "('Pizza','null')," +
         "('Dessert','null');";

 public static final  String insertMon="insert into LoaiMon(ten,giaTien,status,maMon,imgMon)" +
         "values ( 'Tra sua Chan Chau',''10000','null','đang có,'1','null')," +
         "( 'Tra sua QIQI',''10000','null','đang có,'1','null')," +
         "( 'Tra sua Nóng',''10000','null','hết hàng,'1','null')," +
         "('Pizza cay','100000',);";
}
