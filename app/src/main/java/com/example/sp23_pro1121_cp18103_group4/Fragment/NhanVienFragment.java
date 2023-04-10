package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.NhanVienAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class NhanVienFragment extends Fragment {
    RecyclerView rc_nhanvien;
    FloatingActionButton flAddKNhanVien;
    TextView nhanvien_tvTitle;
    EditText nhanvien_edMaNV, nhanvien_edHoTen, nhanvien_eduser, nhanvien_edpass, nhanvien_edNamSinh, nhanvien_edSoDT,  nhanvien_edUyQuyen, nhanvien_edStartus;
    RadioButton nhanvien_rdNam, nhanvien_rdNu, nhanvien_rdKhac,rdo_quanli,rdo_nhanvien;
    Button nhanvien_btnSave, nhanvien_btnCancel;
    NhanVienDao dao;
    List<NhanVien> list;
    NhanVienAdapter adapter;

    CheckBox checklamviec;
    NhanVien nhanVien;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nhan_vien, container, false);
        init(view);
        setData();
        insertNhanVien();
        return view;
    }
    //ánh xạ init
    public void init(View view) {
        rc_nhanvien = view.findViewById(R.id.rc_nhanvien);
        flAddKNhanVien = view.findViewById(R.id.flAddKNhanVien);
    }
    public void setData() {
        dao = new NhanVienDao(getContext());
        list = dao.getAll();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rc_nhanvien.setLayoutManager(manager);
        adapter = new NhanVienAdapter(getContext(),list);
        rc_nhanvien.setAdapter(adapter);
    }
    //phương thức thêm nhan vien
    public void insertNhanVien(){
        flAddKNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogInsert(Gravity.CENTER);
            }
        });
    }
    @SuppressLint("MissingInflatedId")
    public void openDialogInsert(int gravity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_nhan_vien, null);
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
          nhanVien = new NhanVien();
//        NhanVien nhanVien = new NhanVien(manv,hoten,userName,PassWord,numberPhone,gioiTinh,NamSinh,UyQuyen,Startus);
        nhanvien_tvTitle = view.findViewById(R.id.nhanvien_tvTitle);
        nhanvien_tvTitle.setText("Thêm Nhân Viên");
        //ánh xạ edit text
        nhanvien_edHoTen= view.findViewById(R.id.nhanvien_edHoTen);
        nhanvien_eduser= view.findViewById(R.id.nhanvien_eduser);
        nhanvien_edpass= view.findViewById(R.id.nhanvien_edpass);
        nhanvien_edNamSinh= view.findViewById(R.id.nhanvien_edNamSinh);
        nhanvien_edSoDT= view.findViewById(R.id.nhanvien_edSoDT);
//        nhanvien_edUyQuyen= view.findViewById(R.id.nhanvien_edUyQuyen);
        nhanvien_edStartus= view.findViewById(R.id.nhanvien_Status);
        nhanvien_rdNam = view.findViewById(R.id. nhanvien_rdNam);
        nhanvien_rdNu = view.findViewById(R.id. nhanvien_rdNu);
        nhanvien_rdKhac = view.findViewById(R.id. nhanvien_rdKhac);
        rdo_quanli = view.findViewById(R.id.quanli);
        rdo_nhanvien = view.findViewById(R.id.nhanvien);
//        checklamviec = view.findViewById(R.id.check1);

        //ánh xạ button
        nhanvien_btnSave = view.findViewById(R.id.nhanvien_btnSave);
        nhanvien_btnCancel = view.findViewById(R.id.nhanvien_btnCancel);
        nhanvien_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() > 0) {
                    dao = new NhanVienDao(getContext());
                    nhanVien.setHoten(nhanvien_edHoTen.getText().toString());
                    nhanVien.setUserName(nhanvien_eduser.getText().toString());
                    nhanVien.setPassWord(nhanvien_edpass.getText().toString());
                    nhanVien.setNamSinh(Integer.parseInt("" + nhanvien_edNamSinh.getText().toString()));
                    nhanVien.setSoDienThoai(nhanvien_edSoDT.getText().toString());
                    nhanVien.setStartus(nhanvien_edStartus.getText().toString());

                    if(rdo_quanli.isChecked()){
                        nhanVien.setUyQuyen("quanli");
                    }else{
                        nhanVien.setUyQuyen("nhanvien");
                    }



                    if (nhanvien_rdNam.isChecked()) {
                        nhanVien.setGioiTinh("Nam");
                    } else if (nhanvien_rdNu.isChecked()) {
                        nhanVien.setGioiTinh("Nữ");
                    } else {
                        nhanVien.setGioiTinh("Khác");
                    }
                    if (dao.insertNhanVien(nhanVien) > 0) {
                        Toast.makeText(getContext(), "Thêm thành công" , Toast.LENGTH_SHORT).show();
                        nhanvien_edHoTen.setText("");
                        nhanvien_eduser.setText("");
                        nhanvien_edpass.setText("");
                        nhanvien_edNamSinh.setText("");
                        nhanvien_edSoDT.setText("");

                        list.clear();
                        list = dao.getAll();
                        adapter = new NhanVienAdapter(getContext(), list);
                        rc_nhanvien.setAdapter(adapter);
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nhanvien_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    //tạo validate kiểm tra thông tin nhập
    public int validate() {
        int check = 1;
        if(nhanvien_edHoTen.getText().toString().isEmpty() || nhanvien_eduser.getText().toString().isEmpty() || nhanvien_edpass.getText().toString().isEmpty()  || nhanvien_edSoDT.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Yêu cầu không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if (!nhanvien_edNamSinh.getText().toString().matches("\\d+")) {
            Toast.makeText(getContext(), "Yêu cầu nhập số nguyên năm sinh", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}