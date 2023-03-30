package com.example.sp23_pro1121_cp18103_group4.Adapter;


import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.BanAnDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBanDAO;
import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.R;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MonTrongBanAdapter extends RecyclerView.Adapter<MonTrongBanAdapter.View_montrongban> {


    ArrayList<MonTrongBan> list;
    Context context;

    MonDao monDao ;
    Mon mon;

    MonTrongBanDAO trongBanDAO;
    List<Mon> listMon;

    ImageView imgMon;
    private static final int PICK_IMAGE_REQUEST = 100;
    static byte[] imageContent;

    public MonTrongBanAdapter(ArrayList<MonTrongBan> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View_montrongban onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewba = layoutInflater.inflate(R.layout.rcv_montrongban,parent,false);
        View_montrongban view_montrongban = new View_montrongban(viewba);
        return view_montrongban;
    }
    @Override
    public void onBindViewHolder(@NonNull View_montrongban holder, int position) {

        int index = position;
        MonTrongBan monTrongBan = list.get(position);
        trongBanDAO = new MonTrongBanDAO(context);

            holder.tenmon.setText(list.get(position).getTenMon());
            holder.soluong.setText(list.get(index).getSoLuong()+"");
            holder.tongtien.setText(list.get(index).getSoLuong() * list.get(index).getGiaMon()+" VND");
            Bitmap imageContent = BitmapFactory.decodeByteArray(monTrongBan.getImgMon(), 0, monTrongBan.getImgMon().length);
            holder.img.setImageBitmap(imageContent);

        holder.tenmon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Cảnh Báo");
                    builder.setMessage("Bạn Có Muốn Xóa Món Này Không ?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(trongBanDAO.delete(String.valueOf(list.get(index).getId()))>0){
                                list.remove(index);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Thành CÔng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class View_montrongban extends RecyclerView.ViewHolder{
        TextView tenmon,soluong,tongtien;

        ImageView img;

        public View_montrongban(@NonNull View itemView) {
            super(itemView);

            tenmon = itemView.findViewById(R.id.tenmon);
            soluong = itemView.findViewById(R.id.soluong);
            tongtien = itemView.findViewById(R.id.tongtien);

            img = itemView.findViewById(R.id.img);


        }
    }


}
