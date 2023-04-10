package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.DatHangDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.DatHang;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.List;


public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context mContext;
    List<DatHang> list;
    DatHangDao dao;
    MonDao monDao;
    TextView tvTongTien;

    public GioHangAdapter(Context mContext, List<DatHang> list) {
        this.mContext = mContext;
        this.list = list;
        dao = new DatHangDao(mContext);
        monDao = new MonDao(mContext);
    }

    public GioHangAdapter(Context mContext, List<DatHang> list, TextView tvTongTien) {
        this.mContext = mContext;
        this.list = list;
        this.tvTongTien = tvTongTien;
        dao = new DatHangDao(mContext);
        monDao = new MonDao(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.gio_hang_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DatHang datHang = list.get(position);
        Mon mon = monDao.getID(String.valueOf(datHang.getMaMon()));
        holder.tvTenMon.setText("Tên món: " + mon.getTenMon());
        holder.tvSoLuong.setText("" + datHang.getSoLuong());
        holder.tvGiaTien.setText("Giá tiền: " + datHang.getGiaTien() + "đ");
        Bitmap imageContent = BitmapFactory.decodeByteArray(mon.getImgMon(), 0, mon.getImgMon().length);
        holder.imgMon.setImageBitmap(imageContent);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có muốn bỏ sản phẩm này không?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao = new DatHangDao(mContext);
                        if (dao.deleteDatHang(list.get(holder.getAdapterPosition())) > 0) {
                            list.remove(holder.getAdapterPosition());
                            list.clear();
                            list = dao.getAll();
                            notifyDataSetChanged();
                            updateprice(v);
                            dialog.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

        //set số lượng giỏ hàng
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = datHang.getSoLuong();
                count++;
                if (count > 0) {
                    list.get(holder.getAdapterPosition()).setSoLuong(count);
                    holder.tvGiaTien.setText("Giá tiền: " + datHang.getGiaTien() * datHang.getSoLuong() + "đ");
                    notifyDataSetChanged();
                    updateprice(v);
                }
            }
        });
        //******//
        holder.imgSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = datHang.getSoLuong();
                count--;
                if (count > 0) {
                    list.get(holder.getAdapterPosition()).setSoLuong(count);
                    holder.tvGiaTien.setText("Giá tiền: " + datHang.getGiaTien() * datHang.getSoLuong() + "đ");
                    notifyDataSetChanged();
                    updateprice(v);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Bạn có muốn bỏ sản phẩm này không?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao = new DatHangDao(mContext);
                            if (dao.deleteDatHang(list.get(holder.getAdapterPosition())) > 0) {
                                list.remove(holder.getAdapterPosition());
                                list.clear();
                                list = dao.getAll();
                                notifyDataSetChanged();
                                updateprice(v);
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenMon, tvGiaTien, tvSoLuong;
        ImageView imgMon, imgSub, imgAdd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenMon = itemView.findViewById(R.id.giohang_tvTenMon);
            tvGiaTien = itemView.findViewById(R.id.giohang_tvGiaTien);
            tvSoLuong = itemView.findViewById(R.id.giohang_tvSoLuong);
            imgMon = itemView.findViewById(R.id.giohang_imgMon);
            imgSub = itemView.findViewById(R.id.giohang_imgSub);
            imgAdd = itemView.findViewById(R.id.giohang_imgAdd);
        }
    }

    public void updateprice(View view) {
        int sum = 0, i;
        for (i = 0; i < list.size(); i++)
            sum = sum + (list.get(i).getSoLuong() * list.get(i).getGiaTien());
        tvTongTien.setText("" + sum + "đ");
    }

}
