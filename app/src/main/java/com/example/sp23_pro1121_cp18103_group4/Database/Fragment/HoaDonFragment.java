package com.example.sp23_pro1121_cp18103_group4.Database.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sp23_pro1121_cp18103_group4.Adapter.HoaDonAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.HoaDonDao;

import com.example.sp23_pro1121_cp18103_group4.Model.HoaDon;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class HoaDonFragment extends Fragment {

    RecyclerView rcv;
    HoaDonDao hoaDonDao;
    ArrayList<HoaDon> list;
    HoaDonAdapter hoaDonAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
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
        list = hoaDonDao.getAll();

        hoaDonAdapter = new HoaDonAdapter(list,getContext());
        rcv.setAdapter(hoaDonAdapter);

    }
}