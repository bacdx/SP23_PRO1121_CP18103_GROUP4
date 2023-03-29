package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.KhachHangDao;
import com.example.sp23_pro1121_cp18103_group4.Model.KhachHang;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.MyViewHolder> {
    Context mContext;
    List<KhachHang> list;
    KhachHangDao dao;
    TextView khachhang_tvTitle;
    EditText khachhang_edHoTen, khachhang_edNamSinh, khachhang_edSoDT, khachhang_edDiaChi;
    RadioButton khachhang_rdNam, khachhang_rdNu, khachhang_rdKhac;
    Button btnSave, btnCancel;
    public KhachHangAdapter(Context mContext, List<KhachHang> list) {
        this.mContext = mContext;
        this.list = list;
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
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có chắc chắn muốn xóa không?");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao = new KhachHangDao(mContext);
                        if (dao.deleteKhachHang(list.get(holder.getAdapterPosition())) > 0) {
                            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            list.remove(holder.getAdapterPosition());
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
        });

        //thiết lập dialog sửa khách hàng
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(Gravity.CENTER,khachHang);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGioiTinh, imgEdit, imgDelete;
        TextView tvHoTen, tvNamSinh, tvGioiTinh, tvSoDT, tvDiaChi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGioiTinh = itemView.findViewById(R.id.khachhang_imgGioiTinh);
            imgEdit = itemView.findViewById(R.id.khachhang_imgEdit);
            imgDelete = itemView.findViewById(R.id.khachhang_imgDelete);
            tvHoTen = itemView.findViewById(R.id.khachhang_tvHoTen);
            tvNamSinh = itemView.findViewById(R.id.khachhang_tvNamSinh);
            tvGioiTinh = itemView.findViewById(R.id.khachhang_tvGioiTinh);
            tvSoDT = itemView.findViewById(R.id.khachhang_tvSoDT);
            tvDiaChi = itemView.findViewById(R.id.khachhang_tvDiaChi);
        }
    }

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
        return check;
    }
}
