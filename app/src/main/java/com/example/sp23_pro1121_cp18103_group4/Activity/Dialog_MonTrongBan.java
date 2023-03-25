package com.example.sp23_pro1121_cp18103_group4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.Adapter_MaBan;
import com.example.sp23_pro1121_cp18103_group4.DAO.BanAnDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBanDAO;
import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Dialog_MonTrongBan extends AppCompatActivity {

    MonTrongBanDAO trongBanDAO;
    MonTrongBan monTrongBan;

    Adapter_MaBan adapter_maBan;
    ArrayList<BanAn> listBanAN;
    BanAnDao banAnDao;

    String id12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogthemmontrongban);

        TextInputEditText soluong = findViewById(R.id.soluong);
        Spinner spinner = findViewById(R.id.spn);

        Button luu = findViewById(R.id.luu);
        Button huy = findViewById(R.id.huy);

        Intent intent = new Intent();

        Bundle bundle = getIntent().getBundleExtra("thongtin");;
        String tenmon = bundle.getString("tenmon");
        int giamon = bundle.getInt("giamon");

        listBanAN = new ArrayList<>();
        banAnDao = new BanAnDao(this);
        adapter_maBan = new Adapter_MaBan(this);
        listBanAN = banAnDao.getALL();
        adapter_maBan.setDaTa(listBanAN);
        spinner.setAdapter(adapter_maBan);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id12 = String.valueOf(listBanAN.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        trongBanDAO = new MonTrongBanDAO(this);

        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monTrongBan = new MonTrongBan();

                if(soluong.getText().toString().length()==0){
                    Toast.makeText(Dialog_MonTrongBan.this, "Khong Được Để Trống ", Toast.LENGTH_SHORT).show();
                    return;
                }

                 if (!soluong.getText().toString().matches("\\d+")) {
                    Toast.makeText(Dialog_MonTrongBan.this, "Yêu cầu  Số Lượng  là số", Toast.LENGTH_SHORT).show();
                    return;
                 }

                monTrongBan.setSoLuong(Integer.parseInt(soluong.getText().toString()));
                monTrongBan.setTenMon(tenmon);
                monTrongBan.setGiaMon(giamon);
                monTrongBan.setMaBan(String.valueOf(id12));
                if(trongBanDAO.insert(monTrongBan)>0){
                    Toast.makeText(Dialog_MonTrongBan.this, "Thành CÔng" , Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Dialog_MonTrongBan.this, "THất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}