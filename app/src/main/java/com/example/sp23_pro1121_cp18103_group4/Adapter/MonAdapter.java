package com.example.sp23_pro1121_cp18103_group4.Adapter;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBanDAO;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class MonAdapter extends RecyclerView.Adapter<MonAdapter.MyViewHolder> {
    Context mContext;
    List<Mon> list;
    MonDao dao;
    EditText edTenMon, edGiaTien;
    CheckBox chkTrangThai;
    int maLoaiMon;
    ImageView imgMon;

    MonTrongBan monTrongBan;

    MonTrongBanDAO trongBanDAO;

    private static final int PICK_IMAGE_REQUEST = 100;
    static byte[] imageContent;
    public MonAdapter(Context mContext, List<Mon> list) {
        this.mContext = mContext;
        this.list = list;
        dao = new MonDao(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_of_item = LayoutInflater.from(mContext).inflate(R.layout.mon_item, parent, false);
        return new MyViewHolder(view_of_item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mon mon = list.get(position);
        holder.mon_tvTenMon.setText(mon.getTenMon());

            int index = position;
        holder.mon_tvTenMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(mContext, androidx.appcompat.R.style.Theme_AppCompat);
                dialog.setContentView(R.layout.dialogthemmontrongban);
                trongBanDAO = new MonTrongBanDAO(mContext);

                TextInputEditText soluong = dialog.findViewById(R.id.soluong);
                Button luu = dialog.findViewById(R.id.luu);
                Button huy = dialog.findViewById(R.id.huy);

                 monTrongBan = new MonTrongBan();
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("tenmon",list.get(position).getTenMon());
//                intent.pu
//
                 luu.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         monTrongBan.setSoLuong(Integer.parseInt(soluong.getText().toString()));
//                         monTrongBan.setMaBan(String.valueOf(maban));
                         monTrongBan.setTenMon(list.get(position).getTenMon());
                         monTrongBan.setMaMon(String.valueOf(mon.getMaMon()));
                         if(trongBanDAO.insert(monTrongBan)>0){
                             Toast.makeText(mContext, "Thành Công", Toast.LENGTH_SHORT).show();


                         }else{
                             Toast.makeText(mContext, "Thất Bại", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

                dialog.show();
            }
        });


        holder.mon_tvGiaTien.setText("Giá tiền: " + mon.getGiaTien());
        if (mon.getTrangThai().equals("Còn hàng")) {
            holder.mon_tvTrangThai.setText(mon.getTrangThai());
            holder.mon_tvTrangThai.setTextColor(Color.BLUE);
        } else {
            holder.mon_tvTrangThai.setText(mon.getTrangThai());
            holder.mon_tvTrangThai.setTextColor(Color.RED);
            holder.mon_tvTrangThai.setPaintFlags(holder.mon_tvGiaTien.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        Bitmap imageContent = BitmapFactory.decodeByteArray(mon.getImgMon(),0,mon.getImgMon().length);
        holder.mon_imgMon.setImageBitmap(imageContent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("tenMon",mon.getTenMon());
//                bundle.putInt("giaTien",mon.getGiaTien());
//                bundle.putString("trangThai",mon.getTrangThai());
//                bundle.putByteArray("imgMon",mon.getImgMon());
//                Intent mIntent = new Intent(mContext, MonActivity.class);
//                mIntent.putExtra("monData",bundle);
//                mContext.startActivity(mIntent);
                openDialogUpdate(Gravity.CENTER);
            }
        });
    }
    public void openDialogUpdate(int gravity) {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_mon);
        dialog.create();
        dialog.show();
        //custom dialog
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        //tạo mới model
        Mon mon = new Mon();
        TextView mon_tvTitle;
        Button btnSave;
        imgMon = dialog.findViewById(R.id.mon_addImg);
        imgMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                ((Activity) mContext).startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        edTenMon = dialog.findViewById(R.id.mon_edTenMon);
        edGiaTien = dialog.findViewById(R.id.mon_edGiaTien);
        chkTrangThai = dialog.findViewById(R.id.mon_chkTrangThai);
        edTenMon.setText(mon.getTenMon());
        edGiaTien.setText(String.valueOf(""+mon.getGiaTien()));

//        if (mon.getTrangThai().equals("Còn hàng")){
//            chkTrangThai.setChecked(true);
//        }else{
//            chkTrangThai.setChecked(false);
//        }
        btnSave = dialog.findViewById(R.id.mon_btnSave);
        mon_tvTitle = dialog.findViewById(R.id.mon_tvTitle);
        mon_tvTitle.setText("Thêm Món Ăn");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkTrangThai.isChecked()) {
                    mon.setTrangThai("Còn hàng");
                } else {
                    mon.setTrangThai("Hết hàng");
                }
                mon.setTenMon(edTenMon.getText().toString());
                mon.setGiaTien(Integer.parseInt("" + edGiaTien.getText().toString()));
                mon.setMaLoaiMon(maLoaiMon);
                mon.setImgMon(imageContent);
                dao = new MonDao(mContext);
                if (validate() > 0) {
                    if (dao.insertMon(mon) > 0) {
                        Toast.makeText(mContext, "thanh cong", Toast.LENGTH_SHORT).show();
                        edTenMon.setText("");
                        edGiaTien.setText("");
                        chkTrangThai.setChecked(false);
                        list.clear();
                        dialog.dismiss();
                        list = dao.getAllWithId(mon.getMaLoaiMon());
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "that bai", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
    }
    //validate check
    public int validate() {
        int check = 1;
        if (edTenMon.getText().toString().isEmpty() || edGiaTien.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!edGiaTien.getText().toString().matches("\\d+")) {
            Toast.makeText(mContext, "Yêu cầu giá tiền phải là số", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mon_tvTenMon, mon_tvGiaTien, mon_tvTrangThai;
        ImageView mon_imgMon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mon_tvTenMon = itemView.findViewById(R.id.mon_tvTenMon);
            mon_tvGiaTien = itemView.findViewById(R.id.mon_tvGiaTien);
            mon_tvTrangThai = itemView.findViewById(R.id.mon_tvTrangThai);
            mon_imgMon = itemView.findViewById(R.id.mon_imgMon);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri imageUri = intent.getData();
            try {
                InputStream inputStream = mContext.getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgMon.setImageBitmap(bitmap);
                imageContent = getBytes(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //lấy loại hình ảnh
    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
