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
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHoaDon> {


    ArrayList<HoaDon> list ;
    Context context;
    HoaDonDao hoaDonDao ;

    public HoaDonAdapter(ArrayList<HoaDon> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void setList(ArrayList<HoaDon>list){
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


        holder.maHoaDon.setText("MãHD: "+list.get(position).getMaHoaDon()+"");
        holder.maNhanVien.setText("MãNV: "+list.get(position).getMaNV());
        holder.maKhachHang.setText("MãKH: "+list.get(position).getMaKH());
        holder.ngayLap.setText(list.get(position).getNgayLap());
        holder.tongTien.setText("Tổng tiền: "+list.get(position).getTongTien()+" VND");


        int index = position;
        hoaDonDao = new HoaDonDao(context);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHoaDon extends RecyclerView.ViewHolder{

        TextView maHoaDon,maNhanVien,maKhachHang,ngayLap,tongTien;

        public ViewHoaDon(@NonNull View itemView) {
            super(itemView);

            maHoaDon = itemView.findViewById(R.id.id);
            maNhanVien = itemView.findViewById(R.id.manv);
            maKhachHang = itemView.findViewById(R.id.makh);
            ngayLap = itemView.findViewById(R.id.ngaytao);
            tongTien = itemView.findViewById(R.id.tong);

        }
    }
}
