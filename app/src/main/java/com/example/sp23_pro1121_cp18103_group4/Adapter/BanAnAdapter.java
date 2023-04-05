package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Activity.Dialog_MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.DAO.BanAnDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.HoaDonDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.KhachHangDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBanDAO;

import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.Fragment.AllMonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.LoaiMonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.MonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ThemBanFragment;
import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.Model.HoaDon;
import com.example.sp23_pro1121_cp18103_group4.Model.KhachHang;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;
import com.example.sp23_pro1121_cp18103_group4.MonActivity;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BanAnAdapter extends RecyclerView.Adapter<BanAnAdapter.ViewBanan> {


    Context context;
    ArrayList<BanAn> list ;
    BanAnDao daoBanAN;
    HoaDonDao hoaDonDao;

    BanAnAdapter banAnAdapter;

    String tenNV,tenKH;

    ArrayList<NhanVien> list1 ;

    ArrayList<KhachHang> listkh;

    public BanAnAdapter(Context context, ArrayList<BanAn> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewBanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewba = layoutInflater.inflate(R.layout.rcvitembanan,parent,false);
        ViewBanan viewBanan = new ViewBanan(viewba);
        return viewBanan;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewBanan holder, int position) {

        holder.tenban.setText(list.get(position).getTenBanAN());
        int index = position;
        daoBanAN = new BanAnDao(context);
        MonTrongBanDAO trongBanDAO1;
        trongBanDAO1 = new MonTrongBanDAO(context);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh Báo");
                builder.setMessage("Bạn Có Muốn Xóa Bàn Này Không ?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(daoBanAN.deleteBanAN(list.get(index))>0){
                            list.remove(index);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa THành Công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Xóa THất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        holder.themmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AllMonFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFrame_collection_fragment,fragment).commit();
            }
        });
        try {
            trongBanDAO1.getwid(String.valueOf(list.get(index).getId()));
            if( trongBanDAO1.getwid(String.valueOf(list.get(index).getId()))>0){
                holder.anh.setImageResource(R.drawable.nguoian2);
            }else{
                holder.anh.setImageResource(R.drawable.banan);
            }
        }catch (Exception e){
        }
        holder.hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<MonTrongBan> listmtb;
                MonTrongBanDAO trongBanDAO;
                listmtb = new ArrayList<>();
                trongBanDAO = new MonTrongBanDAO(context);
                try {
                    listmtb = trongBanDAO.getAllWithId(String.valueOf(list.get(index).getId()));
                }catch (Exception e){
                    Toast.makeText(context, "Chưa Thêm Món Ăn Vào Bàn ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat);
                dialog.setContentView(R.layout.dialog_tinh_tien);

                RecyclerView rcv = dialog.findViewById(R.id.rcv);
                TextView tenban = dialog.findViewById(R.id.tenban);
                TextView ngay = dialog.findViewById(R.id.ngay);
                CheckBox checkBox1 = dialog.findViewById(R.id.checkb);
                TextView tong = dialog.findViewById(R.id.tong);
                Button thanhtoan = dialog.findViewById(R.id.thanhtoan);

                DateFormat df = new SimpleDateFormat("d/M/yyyy");

                MonTrongBanAdapter monTrongBanAdapter;
                tenban.setText(list.get(index).getTenBanAN());
                ngay.setText(df.format(Calendar.getInstance().getTime()));
                monTrongBanAdapter = new MonTrongBanAdapter(listmtb,context);
                rcv.setAdapter(monTrongBanAdapter);

                tong.setText(trongBanDAO.getTong(String.valueOf(list.get(index).getId()))+"");

                checkBox1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean check = ((CheckBox)v).isChecked();
                        if(check){
                            tong.setText(trongBanDAO.getGIamGia(String.valueOf(list.get(index).getId()))+"");
                        }else{
                            tong.setText(trongBanDAO.getTong(String.valueOf(list.get(index).getId()))+"");
                        }
                    }
                });
                thanhtoan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Dialog dialog1 = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat);
                        dialog1.setContentView(R.layout.dialog_thanhtoan_hoa_don);

                        Spinner spn_nhanvien = dialog1.findViewById(R.id.spn_nhanvien);
                        Spinner spn_khachhang = dialog1.findViewById(R.id.spn_khachhang);

                        Button luu = dialog1.findViewById(R.id.luu);
                        Button huy = dialog1.findViewById(R.id.huy);



                        NhanVienDao daonv = new NhanVienDao(context);
                         Spiner_NhanVien nhanVien = new Spiner_NhanVien(context);

                         list1 = new ArrayList<>();
                         list1 = daonv.getAll();
                         nhanVien.setDaTa(list1);
                         spn_nhanvien.setAdapter(nhanVien);

                         spn_nhanvien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                             @Override
                             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                 tenNV = list1.get(position).getHoten();
                             }

                             @Override
                             public void onNothingSelected(AdapterView<?> parent) {

                             }
                         });

                        KhachHangDao hangDao = new KhachHangDao(context);
                        Spiner_KhachHang spiner_khachHang = new Spiner_KhachHang(context);
                         listkh = new ArrayList<>();
                        listkh = hangDao.getAll();
                        spiner_khachHang.setDaTA(listkh);
                        spn_khachhang.setAdapter(spiner_khachHang);

                        spn_khachhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                tenKH = listkh.get(position).getHoTen();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        huy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });



                        luu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                HoaDon hoaDon;
                                hoaDon = new HoaDon();
                                hoaDonDao = new HoaDonDao(context);

                                hoaDon.setMaBan(String.valueOf(list.get(index).getId()));
                               try {
                                   hoaDon.setMaKH(tenKH);
                                   hoaDon.setMaNV(tenNV);

                               }catch (Exception e){
                                   Toast.makeText(context, "Chưa ĐỦ Thông Tin", Toast.LENGTH_SHORT).show();
                                   return;
                               }
                                hoaDon.setNgayLap(ngay.getText().toString());
                                hoaDon.setTongTien(Integer.parseInt(tong.getText().toString()));
                                hoaDonDao.insertHoaDon(hoaDon);
                                try {
                                    trongBanDAO.DeleteAll(String.valueOf(list.get(index).getId()));
                                }catch (Exception e){
                                    Toast.makeText(context, "Thanh Toán Thành CÔng", Toast.LENGTH_SHORT).show();
                                }
                                dialog1.dismiss();
                            }
                        });
                        dialog1.show();
                    }
                });

                dialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewBanan extends RecyclerView.ViewHolder{
        TextView tenban,status ;
        ImageView delete,themmon,hoadon;
        ImageButton anh ;

        public ViewBanan(@NonNull View itemView) {
            super(itemView);
            tenban = itemView.findViewById(R.id.tenban);
            delete = itemView.findViewById(R.id.delete);
            themmon = itemView.findViewById(R.id.themmon);
            anh  = itemView.findViewById(R.id.anh);
            hoadon = itemView.findViewById(R.id.hoadon);
        }
    }
}
