package com.example.sp23_pro1121_cp18103_group4.Fragment;


import android.annotation.SuppressLint;

import android.app.AlertDialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.KhachHangAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.KhachHangDao;

import com.example.sp23_pro1121_cp18103_group4.Adapter.LoaiMonAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.KhachHangDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.LoaiMonDao;

import com.example.sp23_pro1121_cp18103_group4.Model.KhachHang;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class KhachHangFragment extends Fragment {
    RecyclerView rc_khachHang;
    FloatingActionButton flAddKhachHang;
    //open dialog thêm khách hàng
    TextView khachhang_tvTitle;
    EditText khachhang_edHoTen, khachhang_edNamSinh, khachhang_edSoDT, khachhang_edDiaChi;

    RadioButton khachhang_rdGroup, khachhang_rdNam, khachhang_rdNu, khachhang_rdKhac;

//    RadioButton khachhang_rdNam, khachhang_rdNu, khachhang_rdKhac;

    Button btnSave, btnCancel;
    //database
    KhachHangDao dao;
    List<KhachHang> list;
    KhachHangAdapter adapter;
    //searchview
    private SearchView khachhang_SearchView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);
        init(view);
        setData();
        insertKhachHang();
        openSearchView(view);
        return view;
    }
    //***********//
//ánh xạ init

    public void init(View view) {
        rc_khachHang = view.findViewById(R.id.rc_khachHang);
        flAddKhachHang = view.findViewById(R.id.flAddKhachHang);
    }
    //***********//

    public void openSearchView(View view){
        khachhang_SearchView = view.findViewById(R.id.khachhang_searchView);
        khachhang_SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        dao = new KhachHangDao(getContext());
        list = dao.getAll();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rc_khachHang.setLayoutManager(manager);
        adapter = new KhachHangAdapter(getContext(), list);
        rc_khachHang.setAdapter(adapter);
    }


    //***********//

    public void insertKhachHang() {
        flAddKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogInsert(Gravity.CENTER);
            }
        });
    }


    //***********//

    @SuppressLint("MissingInflatedId")

    public void openDialogInsert(int gravity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_khach_hang, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //thiết lập custom dialog
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        //tạo mới object
        KhachHang khachHang = new KhachHang();
        khachhang_tvTitle = view.findViewById(R.id.khachhang_tvTitle);
        khachhang_tvTitle.setText("Thêm Khách Hàng");
        //ánh xạ edit text
        khachhang_edHoTen = view.findViewById(R.id.khachhang_edHoTen);
        khachhang_edNamSinh = view.findViewById(R.id.khachhang_edNamSinh);
        khachhang_edSoDT = view.findViewById(R.id.khachhang_edSoDT);
        khachhang_edDiaChi = view.findViewById(R.id.khachhang_edDiaChi);
        khachhang_rdNam = view.findViewById(R.id.khachhang_rdNam);
        khachhang_rdNu = view.findViewById(R.id.khachhang_rdNu);
        khachhang_rdKhac = view.findViewById(R.id.khachhang_rdKhac);
        //ánh xạ button
        btnSave = view.findViewById(R.id.khachhang_btnSave);
        btnCancel = view.findViewById(R.id.khachhang_btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() > 0) {
                    dao = new KhachHangDao(getContext());
                    khachHang.setHoTen(khachhang_edHoTen.getText().toString());
                    khachHang.setNamSinh(Integer.parseInt("" + khachhang_edNamSinh.getText().toString()));
                    khachHang.setSoDT(khachhang_edSoDT.getText().toString());
                    khachHang.setDiaChi(khachhang_edDiaChi.getText().toString());
                    if (khachhang_rdNam.isChecked()) {
                        khachHang.setGioiTinh("Nam");
                    } else if (khachhang_rdNu.isChecked()) {
                        khachHang.setGioiTinh("Nữ");
                    } else {
                        khachHang.setGioiTinh("Khác");
                    }
                    if (dao.insertKhachHang(khachHang) > 0) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        khachhang_edHoTen.setText("");
                        khachhang_edNamSinh.setText("");
                        khachhang_edSoDT.setText("");
                        khachhang_edDiaChi.setText("");
                        list.clear();
                        list = dao.getAll();
                        adapter = new KhachHangAdapter(getContext(), list);
                        rc_khachHang.setAdapter(adapter);
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khachhang_edHoTen.setText("");
                khachhang_edNamSinh.setText("");
                khachhang_edSoDT.setText("");
                khachhang_edDiaChi.setText("");
                dialog.dismiss();
            }
        });
    }



    //***********//
//tạo validate kiểm tra thông tin nhập
    public int validate() {


        int check = 1;
        if (khachhang_edHoTen.getText().toString().isEmpty() || khachhang_edNamSinh.getText().toString().isEmpty()
                || khachhang_edSoDT.getText().toString().isEmpty() || khachhang_edDiaChi.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Yêu cầu không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!khachhang_edNamSinh.getText().toString().matches("\\d+")) {
            Toast.makeText(getContext(), "Yêu cầu nhập số nguyên năm sinh", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if(khachhang_rdNam.isChecked() == false && khachhang_rdNu.isChecked() == false && khachhang_rdKhac.isChecked() == false){
            Toast.makeText(getContext(), "Giới tính không để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }


}