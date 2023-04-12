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
import com.example.sp23_pro1121_cp18103_group4.DialogThanhToan;
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
        BanAn banAn=list.get(position);
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

                try {
                    trongBanDAO1.getwid(String.valueOf(list.get(index).getId()));
                    if( trongBanDAO1.getwid(String.valueOf(list.get(index).getId()))<0){

                        Toast.makeText(context, "Chưa Có Món Trong Bàn", Toast.LENGTH_SHORT).show();
                        return ;
                    }else{
                        DialogThanhToan dialogThanhToan=new DialogThanhToan(view.getContext(), banAn.getId());
                        dialogThanhToan.show();
                    }
                }catch (Exception e){
                }


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
