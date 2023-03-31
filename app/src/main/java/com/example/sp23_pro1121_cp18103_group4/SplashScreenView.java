package com.example.sp23_pro1121_cp18103_group4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenView extends AppCompatActivity {

    NhanVienDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_view);

//        dao = new NhanVienDao(this);
//        List<NhanVien> list = dao.getAll();
//        if(list.isEmpty()){
//            dao.insert();
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenView.this,MainActivity.class));
            }
        },3000);
    }
}