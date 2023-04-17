package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sp23_pro1121_cp18103_group4.Adapter.DonHangAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.DonHangDao;
import com.example.sp23_pro1121_cp18103_group4.Model.DonHang;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;


public class DonHangFragment extends Fragment {


    DonHangDao dao;
    List<DonHang> list = new ArrayList<>();
    DonHangAdapter adapter;
    RecyclerView rc_donHang;
    DonHang donHang;
    String username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_hang, container, false);
        TextView tvDonHangTrong = view.findViewById(R.id.donhang_tvDonHangTrong);
        donHang = new DonHang();
        rc_donHang = view.findViewById(R.id.rc_donHang);
        dao = new DonHangDao(getContext());
//        Bundle bundle = this.getArguments();
//        username = bundle.getString("user");
//        int check = dao.checkTrong(String.valueOf(username));
//        if (check > 0) {
//            tvDonHangTrong.setText("");
////            username = bundle.getString("user");
////            list = dao.getAllWithId(username);
//            list = dao.getAll();
//            LinearLayoutManager manager = new LinearLayoutManager(getContext());
//            rc_donHang.setLayoutManager(manager);
//            adapter = new DonHangAdapter(getContext(), list);
//            rc_donHang.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }else{
//            tvDonHangTrong.setText("Đơn Hàng Trống");
//        }
        int check = dao.checkRong();
        if (check>0) {
            tvDonHangTrong.setText("");
            list = dao.getAll();
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            rc_donHang.setLayoutManager(manager);
            adapter = new DonHangAdapter(getContext(), list);
            rc_donHang.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            tvDonHangTrong.setText("Đơn Hàng Trống");
        }
        return view;
    }
}