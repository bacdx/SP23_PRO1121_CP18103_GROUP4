package com.example.sp23_pro1121_cp18103_group4.Adapter;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Activity.Dialog_MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ChiTietSanPhamFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.Mon_Trong_Ban_Fragment;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MonAdapter extends RecyclerView.Adapter<MonAdapter.MyViewHolder> implements Filterable {
    Context mContext;
    List<Mon> list;
    List<Mon> listSearchView;
    MonDao dao;
    EditText edTenMon, edGiaTien;
    CheckBox chkTrangThai;
    int maLoaiMon;
    ImageView imgMon;
    private static final int PICK_IMAGE_REQUEST = 100;
    static byte[] imageContent;

    public MonAdapter(Context mContext, List<Mon> list) {
        this.mContext = mContext;
        this.list = list;
        this.listSearchView = list;
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
        holder.mon_tvGiaTien.setText("Giá tiền: " + mon.getGiaTien());
        if (mon.getTrangThai().equals("Còn hàng")) {
            holder.mon_tvTrangThai.setText("Còn hàng");
            holder.mon_tvTrangThai.setTextColor(Color.BLUE);
            holder.mon_tvTrangThai.setPaintFlags(holder.mon_tvGiaTien.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        } else {
            holder.mon_tvTrangThai.setText("Hết hàng");
            holder.mon_tvTrangThai.setTextColor(Color.RED);
            holder.mon_tvTrangThai.setPaintFlags(holder.mon_tvGiaTien.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        Bitmap imageContent = BitmapFactory.decodeByteArray(mon.getImgMon(), 0, mon.getImgMon().length);
        holder.mon_imgMon.setImageBitmap(imageContent);
        if (mon.getTrangThai().equals("Còn hàng")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("maMon", mon.getMaMon());
                    Fragment fragment = new Mon_Trong_Ban_Fragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainFrame_collection_fragment, fragment).commit();
                }
            });
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Sản phẩm này đã hết hàng!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.img_popupMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.img_popupMon);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_update:
                                openDiaLogUpdateMon(Gravity.CENTER, mon);
                                break;
                            case R.id.popup_delete:
                                openDialogDeleteMon(holder.getAdapterPosition(), mon);
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

    //thiết lập getFilter cho search view
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    list = listSearchView;
                } else {
                    List<Mon> mList = new ArrayList<>();
                    for (Mon mon : listSearchView) {
                        if (mon.getTenMon().toLowerCase().contains(strSearch.toLowerCase())) {
                            mList.add(mon);
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
                list = (List<Mon>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mon_tvTenMon, mon_tvGiaTien, mon_tvTrangThai;
        ImageView mon_imgMon;
        ImageView img_popupMon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mon_tvTenMon = itemView.findViewById(R.id.mon_tvTenMon);
            mon_tvGiaTien = itemView.findViewById(R.id.mon_tvGiaTien);
            mon_tvTrangThai = itemView.findViewById(R.id.mon_tvTrangThai);
            mon_imgMon = itemView.findViewById(R.id.mon_imgMon);
            img_popupMon = itemView.findViewById(R.id.img_popupMenuMon);
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

    //*********//
    //Thiết lập dialog update món
    public void openDiaLogUpdateMon(int gravity, Mon mon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_update_mon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        EditText mon_edTenMon, mon_edGiaTien;
        CheckBox mon_chkTrangThai;
        Button btnSave, btnCancel;
        ImageView mon_imgMon;
        mon_edTenMon = dialog.findViewById(R.id.mon_edTenMon);
        mon_edGiaTien = dialog.findViewById(R.id.mon_edGiaTien);
        mon_chkTrangThai = dialog.findViewById(R.id.mon_chkTrangThai);
        mon_edTenMon.setText(mon.getTenMon());
        mon_edGiaTien.setText(String.valueOf(mon.getGiaTien()));
        mon_imgMon = dialog.findViewById(R.id.mon_addImg);
        btnSave = dialog.findViewById(R.id.mon_btnSave);
        btnCancel = dialog.findViewById(R.id.mon_btnCancel);
        if (mon.getTrangThai().equals("Còn hàng")) {
            mon_chkTrangThai.setChecked(true);
        } else {
            mon_chkTrangThai.setChecked(false);
        }
        if (mon.getImgMon() != null) {
            byte[] img = mon.getImgMon();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            mon_imgMon.setImageBitmap(bitmap);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mon_chkTrangThai.isChecked()) {
                    mon.setTrangThai("Còn hàng");
                } else {
                    mon.setTrangThai("Hết hàng");
                }
                dao = new MonDao(mContext);
                if (dao.updateMon(mon) > 0) {
                    Toast.makeText(mContext, "thanh cong", Toast.LENGTH_SHORT).show();
                    list.clear();
                    dialog.dismiss();
                    list = dao.getAllWithId(mon.getMaLoaiMon());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //***********//
    //Thiết lập dialog delete món
    private void openDialogDeleteMon(int adapterPosition, Mon mon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Bạn có chắc chắn muốn xóa?");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao = new MonDao(mContext);
                if (dao.deleteMon(list.get(adapterPosition)) > 0) {
                    Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.remove(adapterPosition);
                    list.clear();
                    list = dao.getAllWithId(mon.getMaLoaiMon());
                    notifyDataSetChanged();
                } else {
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
}
