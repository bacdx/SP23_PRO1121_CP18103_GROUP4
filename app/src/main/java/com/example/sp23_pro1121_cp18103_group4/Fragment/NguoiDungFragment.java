package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sp23_pro1121_cp18103_group4.Adapter.NguoiDungAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.NguoiDungDao;
import com.example.sp23_pro1121_cp18103_group4.Model.NguoiDung;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.List;

public class NguoiDungFragment extends Fragment {
    RecyclerView rc_nguoidung;
    //database
    NguoiDungDao dao;
    List<NguoiDung> list;
    NguoiDungAdapter adapter;
    //searchview
    private SearchView nguoidung_SearchView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nguoi_dung, container, false);
        ((Activity)getContext()).setTitle("Quản lý người dùng");
        init(view);
        setData();
//        insertKhachHang();
        openSearchView(view);
        return view;
    }

    //***********//
//ánh xạ init

    public void init(View view) {
        rc_nguoidung = view.findViewById(R.id.rc_nguoiDung);
    }
    //***********//

    public void openSearchView(View view){
        nguoidung_SearchView = view.findViewById(R.id.nguoidung_searchView);
        nguoidung_SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    //***********//
    public void setData() {
        dao = new NguoiDungDao(getContext());
        list = dao.getAll();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rc_nguoidung.setLayoutManager(manager);
        adapter = new NguoiDungAdapter(getContext(), list);
        rc_nguoidung.setAdapter(adapter);
    }

}