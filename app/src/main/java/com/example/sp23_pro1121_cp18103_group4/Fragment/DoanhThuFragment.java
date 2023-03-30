package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sp23_pro1121_cp18103_group4.DAO.HoaDonDao;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.Calendar;


public class DoanhThuFragment extends Fragment {

    EditText tungay;
    EditText denngay;
    HoaDonDao hoaDonDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         tungay = view.findViewById(R.id.tungay);
         denngay = view.findViewById(R.id.denngay);

        Button thongke = view.findViewById(R.id.thongke);
        TextView tong = view.findViewById(R.id.tong);
        tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogDate();
            }
        });
        denngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogDate1();
            }
        });

        hoaDonDao = new HoaDonDao(getContext());

        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tong.setText(hoaDonDao.getDoanhThu(tungay.getText().toString(),denngay.getText().toString())+" VND");

            }
        });


    }


    void showdialogDate(){
        // su dung doi tuong lich (calendar) de cai dat
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        //Thiet Lap ngay gio hien tai
        calendar.setTimeInMillis(System.currentTimeMillis());
        //khoi tao dialog chon ngay thang nam
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int nam = i;
                int thang = i1; //nhan gia tri 0 -11
                int ngay = i2;
                // gan du lieu vao edit Text
                tungay.setText(ngay+"/"+(thang +1)+"/"+nam);
            }
        },calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        datePickerDialog.show();
    }

    void showdialogDate1(){
        // su dung doi tuong lich (calendar) de cai dat
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        //Thiet Lap ngay gio hien tai
        calendar.setTimeInMillis(System.currentTimeMillis());
        //khoi tao dialog chon ngay thang nam
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int nam = i;
                int thang = i1; //nhan gia tri 0 -11
                int ngay = i2;
                // gan du lieu vao edit Text
                denngay.setText(ngay+"/"+(thang +1)+"/"+nam);
            }
        },calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        datePickerDialog.show();
    }



}