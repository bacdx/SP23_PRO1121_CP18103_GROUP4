package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.KhachHangDao;
import com.example.sp23_pro1121_cp18103_group4.Model.KhachHang;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.MyViewHolder> implements Filterable {
    Context mContext;
    List<KhachHang> list;
    List<KhachHang> listSearch;
    KhachHangDao dao;
    //ánh xạ
    TextView khachhang_tvTitle;
    EditText khachhang_edHoTen, khachhang_edNamSinh, khachhang_edSoDT, khachhang_edDiaChi;
    RadioButton khachhang_rdNam, khachhang_rdNu, khachhang_rdKhac;
    Button btnSave, btnCancel;
    public KhachHangAdapter(Context mContext, List<KhachHang> list) {
        this.mContext = mContext;
        this.list = list;
        this.listSearch = list;
        dao = new KhachHangDao(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.khachhang_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        KhachHang khachHang = list.get(position);
        holder.tvHoTen.setText("Họ tên: " + khachHang.getHoTen());
        holder.tvNamSinh.setText("Năm sinh: " + khachHang.getNamSinh());
        if (khachHang.getGioiTinh().equals("Nam")) {
            holder.tvGioiTinh.setText("Giới tính: " + khachHang.getGioiTinh());
            holder.imgGioiTinh.setImageResource(R.drawable.rdnam);
        } else if (khachHang.getGioiTinh().equals("Nữ")) {
            holder.tvGioiTinh.setText("Giới tính: " + khachHang.getGioiTinh());
            holder.imgGioiTinh.setImageResource(R.drawable.rdnu);
        } else {
            holder.tvGioiTinh.setText("Giới tính: " + khachHang.getGioiTinh());
            holder.imgGioiTinh.setImageResource(R.drawable.rdkhac);
        }
        holder.tvSoDT.setText("Số điện thoại: " + khachHang.getSoDT());
        holder.tvDiaChi.setText("Địa chỉ: " + khachHang.getDiaChi());
        //set dialog xóa khách hàng
        holder.img_popupKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(mContext, holder.img_popupKhachHang);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_update:
                                openDialogUpdate(Gravity.CENTER, khachHang);
                                break;
                            case R.id.popup_delete:
                                openDialogDelete(holder.getAdapterPosition());
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
                if (strSearch.isEmpty()){
                    list = listSearch;
                }else{
                    List<KhachHang> mList = new ArrayList<>();
                    for (KhachHang khachHang : listSearch){
                        if (khachHang.getHoTen().toLowerCase().contains(strSearch.toLowerCase())){
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
                list = (List<KhachHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGioiTinh , img_popupKhachHang;
        TextView tvHoTen, tvNamSinh, tvGioiTinh, tvSoDT, tvDiaChi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGioiTinh = itemView.findViewById(R.id.khachhang_imgGioiTinh);
            img_popupKhachHang = itemView.findViewById(R.id.khachhang_Popupmenu);
            tvHoTen = itemView.findViewById(R.id.khachhang_tvHoTen);
            tvNamSinh = itemView.findViewById(R.id.khachhang_tvNamSinh);
            tvGioiTinh = itemView.findViewById(R.id.khachhang_tvGioiTinh);
            tvSoDT = itemView.findViewById(R.id.khachhang_tvSoDT);
            tvDiaChi = itemView.findViewById(R.id.khachhang_tvDiaChi);
        }

    }

    @SuppressLint("MissingInflatedId")
    public void openDialogUpdate(int gravity , KhachHang khachHang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_khach_hang, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //thiết lập custom dialog
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        //tạo mới object
        khachhang_tvTitle = view.findViewById(R.id.khachhang_tvTitle);
        khachhang_tvTitle.setText("Sửa Khách Hàng");
        //ánh xạ edit text
        khachhang_edHoTen = view.findViewById(R.id.khachhang_edHoTen);
        khachhang_edNamSinh = view.findViewById(R.id.khachhang_edNamSinh);
        khachhang_edSoDT = view.findViewById(R.id.khachhang_edSoDT);
        khachhang_edDiaChi = view.findViewById(R.id.khachhang_edDiaChi);
        khachhang_rdNam = view.findViewById(R.id.khachhang_rdNam);
        khachhang_rdNu = view.findViewById(R.id.khachhang_rdNu);
        khachhang_rdKhac = view.findViewById(R.id.khachhang_rdKhac);
        //lấy vị trí khách hàng
        khachhang_edHoTen.setText(khachHang.getHoTen());
        khachhang_edNamSinh.setText(String.valueOf(khachHang.getNamSinh()));
        khachhang_edSoDT.setText(khachHang.getSoDT());
        khachhang_edDiaChi.setText(khachHang.getDiaChi());
        if (khachHang.getGioiTinh().equals("Nam")) {
            khachhang_rdNam.setChecked(true);
            khachhang_rdNu.setChecked(false);
            khachhang_rdKhac.setChecked(false);
        } else if (khachHang.getGioiTinh().equals("Nữ")) {
            khachhang_rdNu.setChecked(true);
            khachhang_rdNam.setChecked(false);
            khachhang_rdKhac.setChecked(false);
        }else{
            khachhang_rdKhac.setChecked(true);
            khachhang_rdNu.setChecked(false);
            khachhang_rdNam.setChecked(false);
        }
        btnSave = view.findViewById(R.id.khachhang_btnSave);
        btnCancel = view.findViewById(R.id.khachhang_btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() > 0) {
                    dao = new KhachHangDao(mContext);
                    khachHang.setHoTen(khachhang_edHoTen.getText().toString());
                    khachHang.setNamSinh(Integer.parseInt("" + khachhang_edNamSinh.getText().toString()));
                    khachHang.setSoDT(khachhang_edSoDT.getText().toString());
                    khachHang.setDiaChi(khachhang_edDiaChi.getText().toString());
                    if (khachhang_rdNam.isChecked()) {
                        khachHang.setGioiTinh("Nam");
                    } else if (khachhang_rdNu.isChecked()) {
                        khachHang.setGioiTinh("Nữ");
                    } else {
                        khachHang.setGioiTinh("Khác");
                    }
                    if (dao.updateKhachHang(khachHang) > 0) {
                        Toast.makeText(mContext, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        khachhang_edHoTen.setText("");
                        khachhang_edNamSinh.setText("");
                        khachhang_edSoDT.setText("");
                        khachhang_edDiaChi.setText("");
                        list.clear();
                        list = dao.getAll();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(mContext, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khachhang_edHoTen.setText("");
                khachhang_edNamSinh.setText("");
                khachhang_edSoDT.setText("");
                khachhang_edDiaChi.setText("");
                dialog.dismiss();
            }
        });
    }
    //tạo validate kiểm tra thông tin nhập
    public int validate() {
        int check = 1;
        if (khachhang_edHoTen.getText().toString().isEmpty() || khachhang_edNamSinh.getText().toString().isEmpty()
                || khachhang_edSoDT.getText().toString().isEmpty() || khachhang_edDiaChi.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Yêu cầu không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!khachhang_edNamSinh.getText().toString().matches("\\d+")) {
            Toast.makeText(mContext, "Yêu cầu nhập số nguyên năm sinh", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        else if(khachhang_rdNam.isChecked() == false && khachhang_rdNu.isChecked() == false && khachhang_rdKhac.isChecked() == false){
            Toast.makeText(mContext, "Giới tính không để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public void openDialogDelete(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Bạn có chắc chắn muốn xóa không?");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao = new KhachHangDao(mContext);
                if (dao.deleteKhachHang(list.get(position)) > 0) {
                    Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    list.clear();
                    list = dao.getAll();
                    notifyDataSetChanged();
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
