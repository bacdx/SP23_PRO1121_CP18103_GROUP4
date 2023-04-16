package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Fragment.DonHangChiTietFragment;
import com.example.sp23_pro1121_cp18103_group4.Model.DonHang;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.List;



public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    Context mContext;
    List<DonHang> list;

    public DonHangAdapter(Context mContext, List<DonHang> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertview = LayoutInflater.from(mContext).inflate(R.layout.don_hang_item,parent,false);
        return new MyViewHolder(convertview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        holder.tvMaDonHang.setText("Mã đơn hàng: "+donHang.getMaDonHang());
        holder.tvMaDonHang.setTextColor(Color.BLUE);
        holder.tvNgayThanhToan.setText("Ngày: "+donHang.getNgayThanhToan());
        holder.tvHoTen.setText("Họ tên: "+donHang.getTenNguoiDung());
        holder.tvTongTien.setText("Tổng tiền: "+donHang.getTongTien()+"đ");
        holder.tvTongTien.setTextColor(Color.RED);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("dhctMaDonHang",donHang.getMaDonHang());
                bundle.putString("dhctHoTen",donHang.getTenNguoiDung());
                bundle.putString("dhctSoDT",donHang.getSoDt());
                bundle.putString("dhctDiaChi",donHang.getDiaChi());
                bundle.putString("dhctNgay",donHang.getNgayThanhToan());
                bundle.putFloat("dhctTongTien",donHang.getTongTien());
                bundle.putString("dhctTrangThai",donHang.getTrangThai());
                Fragment fragment = new DonHangChiTietFragment();


                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFrame_collection_fragment,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public final static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaDonHang , tvNgayThanhToan , tvHoTen  , tvTongTien;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaDonHang = itemView.findViewById(R.id.donhang_tvMaDonHang);
            tvNgayThanhToan = itemView.findViewById(R.id.donHang_tvNgay);
            tvHoTen = itemView.findViewById(R.id.donHang_tvHoTen);
            tvTongTien = itemView.findViewById(R.id.donhang_tvTongTien);
        }
    }
}
