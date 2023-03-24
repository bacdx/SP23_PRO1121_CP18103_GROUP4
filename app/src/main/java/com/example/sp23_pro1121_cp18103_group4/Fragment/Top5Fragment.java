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
import com.example.sp23_pro1121_cp18103_group4.Model.Top5;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;


public class Top5Fragment extends Fragment {
    RecyclerView rcv;
    Top5Adapter top5Adapter;
    HoaDonDao hoaDonDao;
    ArrayList<Top5> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcv = view.findViewById(R.id.rcv);

    }

    @Override
    public void onResume() {
        super.onResume();

        list = new ArrayList<>();
        hoaDonDao = new HoaDonDao(getContext());
        list = hoaDonDao.getTop5();
        top5Adapter = new Top5Adapter(list,getContext());
        rcv.setAdapter(top5Adapter);

    }
}