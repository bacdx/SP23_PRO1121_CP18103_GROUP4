package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.LoaiMonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.LoaiMon;
import com.example.sp23_pro1121_cp18103_group4.MonActivity;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.List;


public class LoaiMonAdapter extends RecyclerView.Adapter<LoaiMonAdapter.MyViewHolder> {
    Context mContext;
    List<LoaiMon> list;
    LoaiMonDao dao;
    EditText edTenLoaiMon;
    ImageView imgLoaiMon;
    public LoaiMonAdapter(Context mContext, List<LoaiMon> list) {
        this.mContext = mContext;
        this.list = list;
        dao = new LoaiMonDao(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.loaimon_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LoaiMon loaiMon = list.get(position);
        holder.tvTenLoaiMon.setText(loaiMon.getTenLoaiMon());
        if(loaiMon.getImgLoaiMon()==null){

        }else{
            Bitmap imageContent = BitmapFactory.decodeByteArray(loaiMon.getImgLoaiMon(),0,loaiMon.getImgLoaiMon().length);
            holder.imgLoaiMon.setImageBitmap(imageContent);
        }
        holder.imgLoaiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có chắc chắn muốn xóa?");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao = new LoaiMonDao(mContext);
                        if (dao.deleteLoaiMon(list.get(holder.getAdapterPosition()))>0){
                            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            list.remove(holder.getAdapterPosition());
                            list.clear();
                            list = dao.getAll();
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(mContext, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ""+loaiMon.getTenLoaiMon(), Toast.LENGTH_SHORT).show();
                //bundel activity
                Intent mIntent = new Intent(mContext, MonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("maLoaiMon", loaiMon.getMaLoaiMon());
                mIntent.putExtra("getId",bundle);
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenLoaiMon;
        ImageView imgLoaiMon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLoaiMon = itemView.findViewById(R.id.loaimon_tvTenLoai);
            imgLoaiMon = itemView.findViewById(R.id.loaimon_imgLoaiMon);
        }
    }
}
