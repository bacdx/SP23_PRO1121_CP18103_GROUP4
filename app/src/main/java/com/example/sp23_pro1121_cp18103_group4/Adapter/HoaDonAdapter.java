package com.example.sp23_pro1121_cp18103_group4.Adapter;

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
import com.example.sp23_pro1121_cp18103_group4.Model.ModelHoaDon;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHoaDon> {

    ArrayList<ModelHoaDon> list ;
    Context context;
    HoaDonDao hoaDonDao ;

    public HoaDonAdapter(ArrayList<ModelHoaDon> list, Context context) {
        this.list = list;
        this.context = context;
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

        holder.maHoaDon.setText(list.get(position).getMaHoaDon()+"");
        holder.maNhanVien.setText(list.get(position).getMaNV());
        holder.maKhachHang.setText(list.get(position).getMaKH());
        holder.ngayLap.setText(list.get(position).getNgayLap());
        holder.tongTien.setText(list.get(position).getTongTien()+"");

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
