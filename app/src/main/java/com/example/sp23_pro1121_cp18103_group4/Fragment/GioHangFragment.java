package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.GioHangAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.DatHangDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.DonHangDao;
import com.example.sp23_pro1121_cp18103_group4.Model.DatHang;
import com.example.sp23_pro1121_cp18103_group4.Model.DonHang;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class GioHangFragment extends Fragment {
    List<DatHang> list = new ArrayList<>();
    RecyclerView rc_gioHang;
    //adapter
    GioHangAdapter adapter;
    //sqlite
    DatHangDao dao;
    DonHangDao donHangDao;
    //model
    DatHang datHang;
    DonHang donHang;
    //ánh xạ
    TextView tvTongTien;
    Button btnThanhToan;
    String username, hoTen, soDt, diaChi;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        tvTongTien = view.findViewById(R.id.giohang_tvTongTien);
        rc_gioHang = view.findViewById(R.id.rc_gioHang);
        dao = new DatHangDao(getContext());
        list = dao.getAll();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rc_gioHang.setLayoutManager(manager);
        adapter = new GioHangAdapter(getContext(), list, tvTongTien);
        rc_gioHang.setAdapter(adapter);
        openTongTien(view);
        openThanhToan(view);
        return  view;
    }

    public void openTongTien(View view) {
        int sum = 0, i;
        for (i = 0; i < list.size(); i++) {
            sum = sum + (list.get(i).getSoLuong() * list.get(i).getGiaTien());
        }
        tvTongTien.setText("" + sum + "đ");
    }


    public void openThanhToan(View view) {
        btnThanhToan = view.findViewById(R.id.giohang_btnThanhToan);
        donHang = new DonHang();
        datHang = new DatHang();
        Bundle bundle = this.getArguments();
        username = bundle.getString("usernameGioHang");
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dia_chi_nhan_hang,null);
                builder.setView(v);
                Dialog dialog = builder.create();
                dialog.show();
                TextView tvTilte = dialog.findViewById(R.id.donhang_tvTitle);
                tvTilte.setText("Nhập thông tin nhận hàng");
                EditText edHoTen , edSoDT , edDiaChi;
                edHoTen = dialog.findViewById(R.id.donhang_edHoTen);
                edSoDT = dialog.findViewById(R.id.donhang_edSoDT);
                edDiaChi = dialog.findViewById(R.id.donhang_edDiaChi);
                donHang = new DonHang();
                Button btnSave , btnCancel;
                btnSave = dialog.findViewById(R.id.donhang_btnSave);
                btnCancel = dialog.findViewById(R.id.donhang_btnCancel);
                Random random = new Random();
                int val = random.nextInt(1000);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edDiaChi.getText().toString().isEmpty() || edHoTen.getText().toString().isEmpty() ||
                                edSoDT.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }else{
                            donHang.setMaDonHang("18092003"+val);
                            donHang.setNgayThanhToan(df.format(Calendar.getInstance().getTime()));
                            donHang.setTrangThai("Đang xử lý");
                            int tongTien = 0, i;
                            for (i = 0; i < list.size(); i++) {
                                tongTien = tongTien + (list.get(i).getSoLuong() * list.get(i).getGiaTien());
                                donHang.setTongTien(tongTien);
                            }
                            donHang.setMaNguoiDung(username);
                            donHang.setTenNguoiDung(edHoTen.getText().toString());
                            donHang.setSoDt(edSoDT.getText().toString());
                            donHang.setDiaChi(edDiaChi.getText().toString());
                            dao = new DatHangDao(getContext());
                            donHangDao = new DonHangDao(getContext());
                            if (donHangDao.insertDonHang(donHang) > 0) {
                                Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                for (i = 0; i < list.size(); i++) {
                                    dao.deleteAll(String.valueOf(list.get(i).getMaDatHang()));
                                }
                                list.clear();
                                list = dao.getAll();
                                adapter = new GioHangAdapter(getContext(), list, tvTongTien);
                                adapter.notifyDataSetChanged();
                                rc_gioHang.setAdapter(adapter);
                                int resetTongTien = 0;
                                for (int j = 0; j < list.size(); j++) {
                                    resetTongTien = resetTongTien + (list.get(j).getSoLuong() * list.get(j).getGiaTien());
                                }
                                tvTongTien.setText("" + resetTongTien + "đ");
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Thanh toán không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edHoTen.setText("");
                        edDiaChi.setText("");
                        edSoDT.setText("");
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}