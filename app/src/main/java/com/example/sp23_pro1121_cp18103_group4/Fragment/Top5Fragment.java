package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.Top5Adapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.HoaDonDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBan2Dao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBanDAO;
import com.example.sp23_pro1121_cp18103_group4.Model.Top5;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;


public class Top5Fragment extends Fragment {
    RecyclerView rcv;
    Top5Adapter top5Adapter;
    MonTrongBanDAO trongBanDAO;

    MonTrongBanDAO monTrongBanDAO ;
    ArrayList<Top5> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_top5, container, false);

        rcv = view.findViewById(R.id.rcvtop5);
        list = new ArrayList<>();
        trongBanDAO = new MonTrongBanDAO(getContext());
        monTrongBanDAO = new MonTrongBanDAO(getContext());

        try {
            list = monTrongBanDAO.getTOp();
            top5Adapter = new Top5Adapter(list,getContext());
            rcv.setAdapter(top5Adapter);
        }catch (Exception e){
            Toast.makeText(getContext(), "Chưa Có Hóa ĐƠn ", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

}