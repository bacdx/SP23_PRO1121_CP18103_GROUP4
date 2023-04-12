package com.example.sp23_pro1121_cp18103_group4.Adapter;


import android.content.Context;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sp23_pro1121_cp18103_group4.DAO.HoaDonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.HoaDon;
import com.example.sp23_pro1121_cp18103_group4.Model.HoaDonHT;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHoaDon> {


    ArrayList<HoaDonHT> list ;
    Context context;
    HoaDonDao hoaDonDao ;

    public HoaDonAdapter(ArrayList<HoaDonHT> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void setList(ArrayList<HoaDonHT>list){
        this.list=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHoaDon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewba = layoutInflater.inflate(R.layout.rcvitem_hoadon,parent,false);
        ViewHoaDon hoaDon = new ViewHoaDon(viewba);
        return hoaDon;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoaDon holder, int position) {
        HoaDonHT hoaDonHT=list.get(position);

        holder.maHoaDon.setText("Mã HD: "+hoaDonHT.getMaHD()+"");
        holder.maNhanVien.setText("Tên NV: "+hoaDonHT.getTenNV());
        holder.ngayLap.setText(hoaDonHT.getNgayLap());
        holder.tongTien.setText("Tổng tiền: "+hoaDonHT.getTongTien()+" VND");





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHoaDon extends RecyclerView.ViewHolder{

        TextView maHoaDon,maNhanVien,ngayLap,tongTien;

        public ViewHoaDon(@NonNull View itemView) {
            super(itemView);

            maHoaDon = itemView.findViewById(R.id.id);
            maNhanVien = itemView.findViewById(R.id.manv);
            ngayLap = itemView.findViewById(R.id.ngaytao);
            tongTien = itemView.findViewById(R.id.tong);

        }
    }
}
