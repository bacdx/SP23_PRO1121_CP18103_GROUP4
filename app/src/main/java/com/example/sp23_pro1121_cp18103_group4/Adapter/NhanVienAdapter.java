package com.example.sp23_pro1121_cp18103_group4.Adapter;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.MyViewHolder> {
    Context mContext;
    List<NhanVien> list;
    NhanVienDao dao;
    TextView nhanvien_tvTitle;
    EditText  nhanvien_edHoTen, nhanvien_eduser, nhanvien_edpass, nhanvien_edNamSinh, nhanvien_edSoDT;
    RadioButton nhanvien_rdNam, nhanvien_rdNu, nhanvien_rdKhac, rdo_quanli, rdo_nhanvien;
    Button nhanvien_btnSave, nhanvien_btnCancel;
    CheckBox nhanvien_chkTrangThai;

    public NhanVienAdapter(Context mContext, List<NhanVien> list){
        this.mContext = mContext;
        this.list = list;
        dao = new NhanVienDao(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.nhanvien_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NhanVien nhanVien = list.get(position);
        holder.tvHoTen.setText("Họ tên: " + nhanVien.getHoten());
        holder.tvUser.setText("User: " + nhanVien.getUserName());
        holder.tvPassword.setText("Password: " + nhanVien.getPassWord());
        holder.tvNamSinh.setText("NamSinh: " + nhanVien.getNamSinh());
        if (nhanVien.getGioiTinh().equals("Nam")) {
            holder.tvGioiTinh.setText("Giới tính: " + nhanVien.getGioiTinh());
            holder.imgGioiTinh.setImageResource(R.drawable.rdnam);
        } else if (nhanVien.getGioiTinh().equals("Nữ")) {
            holder.tvGioiTinh.setText("Giới tính: " + nhanVien.getGioiTinh());
            holder.imgGioiTinh.setImageResource(R.drawable.rdnu);
        } else {
            holder.tvGioiTinh.setText("Giới tính: " + nhanVien.getGioiTinh());
            holder.imgGioiTinh.setImageResource(R.drawable.rdkhac);
        }
        holder.tvSoDT.setText("SoDT: " + nhanVien.getSoDienThoai());
        holder.tvUyQuyen.setText("Uỷ Quyền: " + nhanVien.getUyQuyen());
        if (nhanVien.getStartus().equals("Đang làm việc")) {
            holder.tvStatus.setText("Trạng thái :" + nhanVien.getStartus());
            holder.tvStatus.setTextColor(Color.BLUE);
        } else {
            holder.tvStatus.setText("Trạng thái :" + nhanVien.getStartus());
            holder.tvStatus.setTextColor(Color.RED);
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Bạn có chắc chắn muốn xóa không?");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao = new NhanVienDao(mContext);
                        if (dao.deleteNhanVien(list.get(holder.getAdapterPosition())) > 0) {
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
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(Gravity.CENTER, nhanVien);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public final class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGioiTinh, imgEdit, imgDelete;
        TextView  tvHoTen, tvUser, tvPassword, tvNamSinh, tvGioiTinh, tvSoDT, tvUyQuyen, tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGioiTinh = itemView.findViewById(R.id.nhanvien_imgGioiTinh);
            imgEdit = itemView.findViewById(R.id.nhanvien_imgEdit);
            imgDelete = itemView.findViewById(R.id.nhanvien_imgDelete);
            tvHoTen = itemView.findViewById(R.id.nhanvien_tvHoTen);
            tvUser = itemView.findViewById(R.id.nhanvien_tvUser);
            tvPassword = itemView.findViewById(R.id.nhanvien_tvPassword);
            tvNamSinh = itemView.findViewById(R.id.nhanvien_tvNamSinh);
            tvGioiTinh = itemView.findViewById(R.id.nhanvien_tvGioiTinh);
            tvSoDT = itemView.findViewById(R.id.nhanvien_tvSoDT);
            tvUyQuyen = itemView.findViewById(R.id.nhanvien_tvUyQuyen);
            tvStatus = itemView.findViewById(R.id.nhanvien_tvStartus);
        }
    }
    public void openDialogUpdate(int gravity,NhanVien nhanVien) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_nhan_vien, null);
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
        nhanvien_tvTitle = view.findViewById(R.id.nhanvien_tvTitle);
        nhanvien_tvTitle.setText("Thêm Nhân Viên");
        //ánh xạ edit text
        nhanvien_edHoTen = view.findViewById(R.id.nhanvien_edHoTen);
        nhanvien_eduser = view.findViewById(R.id.nhanvien_eduser);
        nhanvien_edpass = view.findViewById(R.id.nhanvien_edpass);
        nhanvien_edNamSinh = view.findViewById(R.id.nhanvien_edNamSinh);
        nhanvien_edSoDT = view.findViewById(R.id.nhanvien_edSoDT);
        nhanvien_rdNam = view.findViewById(R.id.nhanvien_rdNam);
        nhanvien_rdNu = view.findViewById(R.id.nhanvien_rdNu);
        nhanvien_rdKhac = view.findViewById(R.id.nhanvien_rdKhac);
        rdo_quanli = view.findViewById(R.id.nhanvien_rdQuanLy);
        rdo_nhanvien = view.findViewById(R.id.nhanvien_rdNhanVien);
        nhanvien_chkTrangThai = view.findViewById(R.id.nhanvien_chkTrangThai);
        //set vi tri cac o edittext
        nhanvien_edHoTen.setText(nhanVien.getHoten());
        nhanvien_eduser.setText(nhanVien.getUserName());
        nhanvien_edpass.setText(nhanVien.getPassWord());
        nhanvien_edNamSinh.setText(nhanVien.getNamSinh()+"");
        nhanvien_edSoDT.setText(nhanVien.getSoDienThoai());
        if (nhanVien.getGioiTinh().equals("Nam")){
            nhanvien_rdNam.setChecked(true);
        }else if(nhanVien.getGioiTinh().equals("Nữ")){
            nhanvien_rdNu.setChecked(true);
        }else{
            nhanvien_rdKhac.setChecked(true);
        }

        if (nhanVien.getUyQuyen().equals("Quản lý")){
            rdo_quanli.setChecked(true);
        }else{
            rdo_nhanvien.setChecked(true);
        }

        if (nhanVien.getStartus().equals("Đang làm việc")){
            nhanvien_chkTrangThai.setChecked(true);
        }else{
            nhanvien_chkTrangThai.setChecked(false);
        }
        //ánh xạ button
        nhanvien_btnSave = view.findViewById(R.id.nhanvien_btnSave);
        nhanvien_btnCancel = view.findViewById(R.id.nhanvien_btnCancel);
        nhanvien_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() > 0) {
                    dao = new NhanVienDao(mContext);
                    nhanVien.setHoten(nhanvien_edHoTen.getText().toString());
                    nhanVien.setUserName(nhanvien_eduser.getText().toString());
                    nhanVien.setPassWord(nhanvien_edpass.getText().toString());
                    nhanVien.setNamSinh(Integer.parseInt("" + nhanvien_edNamSinh.getText().toString()));
                    nhanVien.setSoDienThoai(nhanvien_edSoDT.getText().toString());

                    if (rdo_quanli.isChecked()) {
                        nhanVien.setUyQuyen("Quản lý");
                    } else {
                        nhanVien.setUyQuyen("Nhân viên");
                    }

                    if (nhanvien_rdNam.isChecked()) {
                        nhanVien.setGioiTinh("Nam");
                    } else if (nhanvien_rdNu.isChecked()) {
                        nhanVien.setGioiTinh("Nữ");
                    } else {
                        nhanVien.setGioiTinh("Khác");
                    }

                    if (nhanvien_chkTrangThai.isChecked()){
                        nhanVien.setStartus("Đang làm việc");
                    }else{
                        nhanVien.setStartus("Đã nghỉ");
                    }
                    if (dao.updateNhanVien(nhanVien) > 0) {
                        Toast.makeText(mContext, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        nhanvien_edHoTen.setText("");
                        nhanvien_eduser.setText("");
                        nhanvien_edpass.setText("");
                        nhanvien_edNamSinh.setText("");
                        nhanvien_edSoDT.setText("");
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
        nhanvien_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }    //tạo validate kiểm tra thông tin nhập
    public int validate() {
        int check = 1;
        if (nhanvien_edHoTen.getText().toString().isEmpty() || nhanvien_eduser.getText().toString().isEmpty() || nhanvien_edpass.getText().toString().isEmpty() || nhanvien_edSoDT.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Yêu cầu không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!nhanvien_edNamSinh.getText().toString().matches("\\d+")) {
            Toast.makeText(mContext, "Yêu cầu nhập số nguyên năm sinh", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if(!nhanvien_rdNam.isChecked() && !nhanvien_rdNu.isChecked() && !nhanvien_rdKhac.isChecked()){
            Toast.makeText(mContext, "Giới tính không để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if(!rdo_quanli.isChecked() && !rdo_nhanvien.isChecked()){
            Toast.makeText(mContext, "Ủy quyền không để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
