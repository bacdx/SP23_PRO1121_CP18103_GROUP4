package com.example.sp23_pro1121_cp18103_group4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.DAO.NguoiDungDao;
import com.example.sp23_pro1121_cp18103_group4.Model.NguoiDung;
import com.example.sp23_pro1121_cp18103_group4.R;

public class RegisterActivity extends AppCompatActivity {
    EditText edUsername , edPassword , edRePassword;
    Button btnSave;
    NguoiDungDao dao;
    NguoiDung nguoiDung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //ánh xạ init()
        init();
        //register đăng ký
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister(v);
            }
        });
    }
    //ánh xạ
    public void init(){
        edUsername = findViewById(R.id.register_edUsername);
        edPassword = findViewById(R.id.register_edPassword);
        edRePassword = findViewById(R.id.register_edRePassword);
        btnSave = findViewById(R.id.register_btnSave);
    }
    private void openRegister(View view) {
        dao = new NguoiDungDao(getApplicationContext());
        nguoiDung = new NguoiDung();
        nguoiDung.setUsername(edUsername.getText().toString());
        nguoiDung.setPassword(edPassword.getText().toString());
        nguoiDung.setHoTen("user");
        nguoiDung.setSoDT("");
        nguoiDung.setDiaChi("");
        edUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUsername.setText("");
            }
        });
        int check = dao.checkUsername(String.valueOf(nguoiDung.getUsername()));
        if (!edPassword.getText().toString().equals(edRePassword.getText().toString())){
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }else if(check > 0){
            Toast.makeText(this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }else if(edUsername.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty()
                || edRePassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
        }else if(dao.registerTaiKhoan(nguoiDung) >0){
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(RegisterActivity.this,Login.class);
            Bundle bundle = new Bundle();
            bundle.putString("hoTenLogin",nguoiDung.getHoTen());
            bundle.putString("soDtLogin",nguoiDung.getSoDT());
            bundle.putString("diaChiLogin",nguoiDung.getDiaChi());
            mIntent.putExtra("bundleKhachHang",bundle);
            startActivity(mIntent);
        }
    }

}