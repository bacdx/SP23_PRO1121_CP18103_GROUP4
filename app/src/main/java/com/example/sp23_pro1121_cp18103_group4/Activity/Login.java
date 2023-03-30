package com.example.sp23_pro1121_cp18103_group4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.MainActivity;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;

import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

       TextInputEditText edUserName, edPassword;
       Button  lg;
       CheckBox chk;
     private NhanVienDao dao ;
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
        for (int i=0 ; i<nhanVienList.size(); i++){
            NhanVien nhanVien = nhanVienList.get(i);

            if(u.equals(nhanVien.getUserName()) && p.equals(nhanVien.getPassWord())){
                return 1;
            }else {
                return 0;
            }
        }

        return 0;
    }
     String strU , strP;
    public void checkLogin(View view) {
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
                        startActivity(new Intent(Login.this,MainActivity.class));

                     }
                 },2000);
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