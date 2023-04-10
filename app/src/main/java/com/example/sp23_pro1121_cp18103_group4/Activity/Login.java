package com.example.sp23_pro1121_cp18103_group4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;


import com.example.sp23_pro1121_cp18103_group4.DAO.NguoiDungDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.MainActivity;
import com.example.sp23_pro1121_cp18103_group4.Model.NguoiDung;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;

import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    EditText login_edUsername , login_edPassword ;
    CheckBox chkRemember;
    Button login_btnLogin;
    NhanVienDao dao;
    NhanVien nhanVien;

    NguoiDungDao nguoiDungDao;
    NguoiDung nguoiDung;
    TextView login_tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        signIn();
    }

    //*******//
    //ánh xạ
    public void init(){
        login_edUsername = findViewById(R.id.login_edUsername);
        login_edPassword = findViewById(R.id.login_edPassword);
        chkRemember = findViewById(R.id.login_chkRemember);
        login_btnLogin = findViewById(R.id.login_btnLogin);

        login_tvRegister = findViewById(R.id.login_tvRegister);
        login_edUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_edUsername.setText("");
            }
        });
        login_tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegisterActivity.class));
            }
        });

    }

    //********//
    //kiểm tra đăng nhập
    public void signIn(){
        dao = new NhanVienDao(getApplicationContext());
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        login_edUsername.setText(preferences.getString("Username",""));
        login_edPassword.setText(preferences.getString("Password",""));
        chkRemember.setChecked(preferences.getBoolean("Remember",false));
        login_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(v);
            }
        });
    }

    public void checkLogin(View v){
        String strUser = login_edUsername.getText().toString().trim();
        String strPass = login_edPassword.getText().toString().trim();
        dao = new NhanVienDao(this);
        nhanVien = new NhanVien();

        nguoiDungDao = new NguoiDungDao(this);
        nguoiDung = new NguoiDung();
        if (strUser.isEmpty() || strPass.isEmpty()){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
        }else if (dao.checkLogin(strUser,strPass) > 0 || strUser.equals("admin") && strPass.equals("admin")
        || nguoiDungDao.checkLogin(strUser,strPass) >0) {

        if (strUser.isEmpty() || strPass.isEmpty()){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
        }else if (dao.checkLogin(strUser,strPass) > 0 || strUser.equals("admin") && strPass.equals("admin")) {

            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            rememberPassword(strUser, strPass, chkRemember.isChecked());
            Intent intent = new Intent(Login.this,MainActivity.class);
            intent.putExtra("user",strUser);
            startActivity(intent);
            finish();
        }else if(strUser.equals("admin") && !strPass.equals("admin")){
            Toast.makeText(this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
        }else if(!strUser.equals("admin") && strPass.equals("admin")){
            Toast.makeText(this, "Sai tên đăng nhập", Toast.LENGTH_SHORT).show();
        }else if(dao.checkLogin(strUser,strPass) < 0){
            Toast.makeText(this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    //********//
    //hàm nhớ mật khẩu
    public void rememberPassword(String u , String p , boolean status){
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status){
            //xoa tinh trang luu mat khau
            editor.clear();
        }else{
            editor.putString("Username",u);
            editor.putString("Password",p);
            editor.putBoolean("Remember",status);
        }
        editor.commit();
    }
}