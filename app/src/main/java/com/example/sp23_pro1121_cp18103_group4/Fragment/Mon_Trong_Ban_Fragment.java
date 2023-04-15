package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.Adapter_MaBan;
import com.example.sp23_pro1121_cp18103_group4.DAO.BanAnDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBan2Dao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBanDAO;
import com.example.sp23_pro1121_cp18103_group4.DialogThanhToan;
import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class Mon_Trong_Ban_Fragment extends Fragment {

    MonTrongBanDAO trongBanDAO;



    MonTrongBan monTrongBan;

    Adapter_MaBan adapter_maBan;
    ArrayList<BanAn> listBanAN;
    BanAnDao banAnDao;
    String id12;
    MonDao monDao;
    Mon mon;

    int check = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialogthemmontrongban, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText soluong = view.findViewById(R.id.soluong);
        Spinner spinner = view.findViewById(R.id.spn);

        Button luu = view.findViewById(R.id.luu);
        Button huy = view.findViewById(R.id.huy);


        Bundle bundle = this.getArguments();
        int maMon = bundle.getInt("maMon");
        mon=new MonDao(getContext()).getID(String.valueOf(maMon));


        listBanAN = new ArrayList<>();
        BanAn banAn=DialogThanhToan.BAN_MANG_VE;


        banAnDao = new BanAnDao(getContext());
        adapter_maBan = new Adapter_MaBan(getContext());
        listBanAN = banAnDao.getALL();
        listBanAN.add(banAn);
        adapter_maBan.setDaTa(listBanAN);
        spinner.setAdapter(adapter_maBan);

//        mon = new Mon();
//        monDao = new MonDao(getContext());
//        mon = monDao.getALLTien(giamon);
        trongBanDAO = new MonTrongBanDAO(getContext());
//        monTrongBan2Dao = new MonTrongBan2Dao(getContext());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id12 = String.valueOf(listBanAN.get(position).getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monTrongBan = new MonTrongBan();

                if(soluong.getText().toString().length()==0){
                    Toast.makeText(getContext(), "Khong Được Để Trống ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!soluong.getText().toString().matches("\\d+")) {
                    Toast.makeText(getContext(), "Yêu cầu  Số Lượng  là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mon.getTrangThai()== Mon.HET_HANG){
                    Toast.makeText(getContext(), "Món Này Đã Hết Hàng !", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    check = trongBanDAO.getwGia(String.valueOf(maMon),id12);
                    if(check>0){
                        Toast.makeText(getContext(), "Đã Có Món Ăn Này Trong Bàn", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){
                }

                monTrongBan.setImgMon(mon.getImgMon());
                monTrongBan.setSoLuong(Integer.parseInt(soluong.getText().toString()));
                monTrongBan.setTenMon(mon.getTenMon());
                monTrongBan.setTien(mon.getGiaTien());
                monTrongBan.setMaBan(String.valueOf(id12));
                monTrongBan.setMaMon(String.valueOf(maMon));
                monTrongBan.setMaHoaDon("0");
                if(trongBanDAO.insert(monTrongBan)>0){

                    Fragment fragment = new ThemBanFragment();
                    FragmentTransaction transaction = ((getActivity())).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainFrame_collection_fragment,fragment).commit();

                    Toast.makeText(getContext(), "Thành CÔng" , Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "THất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}