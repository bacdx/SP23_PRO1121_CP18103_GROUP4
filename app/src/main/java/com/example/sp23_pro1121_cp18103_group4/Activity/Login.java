package com.example.sp23_pro1121_cp18103_group4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.MainActivity;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;

import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

       TextInputEditText edUserName, edPassword;
       Button lg;
       CheckBox chk;
     private NhanVienDao dao ;
     private  NhanVien nhanVien;
     private List<NhanVien> nhanVienList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName   =  findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
          lg  =  findViewById(R.id.dangnhap);
          chk = findViewById(R.id.chkRememberPass);
          dao = new NhanVienDao(getApplicationContext());
          nhanVienList = dao.getAll();

    }
    public void rememberUp(String u, String p, boolean status)
    {
        SharedPreferences sPef = getSharedPreferences("User File",MODE_PRIVATE);
        SharedPreferences.Editor editor = sPef.edit();
        if(status == false)
        {
            editor.clear();
        }
        else
        {
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);

        }
        editor.commit();
    }
    public int isLogin(String u , String p)
    {
        int check = 1;
        for (int i=0 ; i<nhanVienList.size(); i++){
             nhanVien = nhanVienList.get(i);

            if(u.equals(nhanVien.getUserName()) && p.equals(nhanVien.getPassWord())){
                 check= 1;
                 break;
            }else {
                 check = -1;
            }

        }

        return check;
    }
     String strU , strP;
    public void checkLogin(View view) {

//        List<NhanVien> list = dao.getAll();
//        if(list.isEmpty()){
//            dao.insert();
//        }
         strU = edUserName.getText().toString();
         strP = edPassword.getText().toString();
         if(strU.isEmpty() || strP.isEmpty())
         {
             Toast.makeText(getApplicationContext(), "U,P khong duoc de trong", Toast.LENGTH_LONG).show();
         }else
         {
             if(isLogin(strU,strP)>0)
             {
                 Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", Toast.LENGTH_LONG).show();
                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
//                        startActivity(new Intent(Login.this,MainActivity.class));
                         Intent intent = new Intent(Login.this,MainActivity.class);
                         Bundle bundle = new Bundle();
                         bundle.putString("user",strU);
                         bundle.putString("uyquyen",nhanVien.getUyQuyen());
                         intent.putExtra("thongtin",bundle);
                         startActivity(intent);


                     }
                 },500);
             }
             else
             {
                 Toast.makeText(getApplicationContext(), "Sai U ,P", Toast.LENGTH_LONG).show();
             }
         }
    }

    public void saveUP(View view) {
        String u = edUserName.getText().toString();
        String p = edPassword.getText().toString();
        boolean status = chk.isChecked();
        rememberUp(u,p,status);

    }
}