package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.NguoiDungDao;
import com.example.sp23_pro1121_cp18103_group4.Model.NguoiDung;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;


public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.MyViewHolder> implements Filterable {

    Context mContext;
    List<NguoiDung> list;
    List<NguoiDung> listSearch;
    NguoiDungDao dao;

    public NguoiDungAdapter(Context mContext, List<NguoiDung> list) {
        this.mContext = mContext;
        this.list = list;
        this.listSearch = list;
        dao = new NguoiDungDao(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.nguoidung_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NguoiDung nguoiDung = list.get(position);
        holder.tvHoTen.setText("Họ tên: " + nguoiDung.getHoTen());
        holder.tvUsername.setText("Tên tài khoản: "+ nguoiDung.getUsername());
        holder.tvPassword.setText("Password: "+nguoiDung.getPassword());
        holder.tvSoDT.setText("Số điện thoại: " + nguoiDung.getSoDT());
        holder.tvDiaChi.setText("Địa chỉ: " + nguoiDung.getDiaChi());
        //set dialog xóa khách hàng

        holder.img_popupKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.img_popupKhachHang);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_update:
                                Toast.makeText(mContext, "abc", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.popup_delete:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {

                    list = listSearch;

                } else {
                    List<NguoiDung> mList = new ArrayList<>();
                    for (NguoiDung khachHang : listSearch) {
                        if (khachHang.getHoTen().toLowerCase().contains(strSearch.toLowerCase())) {
                            mList.add(khachHang);
                        }
                    }
                    list = mList;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<NguoiDung>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView nguoidung_imgGioiTinh, img_popupKhachHang;
        TextView tvHoTen , tvSoDT, tvDiaChi , tvUsername , tvPassword;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nguoidung_imgGioiTinh = itemView.findViewById(R.id.nguoidung_imgGioiTinh);
            img_popupKhachHang = itemView.findViewById(R.id.khachhang_Popupmenu);
            tvHoTen = itemView.findViewById(R.id.nguoidung_tvHoTen);
            tvSoDT = itemView.findViewById(R.id.nguoidung_tvSoDT);
            tvDiaChi = itemView.findViewById(R.id.nguoidung_tvDiaChi);
            tvUsername = itemView.findViewById(R.id.nguoidung_tvUsername);
            tvPassword = itemView.findViewById(R.id.nguoidung_tvPassword);
        }
    }
}
