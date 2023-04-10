package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.DAO.NguoiDungDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.Model.NguoiDung;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;
import com.example.sp23_pro1121_cp18103_group4.R;

public class ChangePassFragment extends Fragment {

    EditText edOldPass , edNewPass, edNewRePass;
    Button btnSave , btnCancel;
    NguoiDungDao nguoiDungDao;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        edOldPass = view.findViewById(R.id.oldPassword);
        edNewPass = view.findViewById(R.id.newPassword);
        edNewRePass = view.findViewById(R.id.newRePassword);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edOldPass.setText("");
                edNewPass.setText("");
                edNewRePass.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doc user , pass trong sharePreferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("Username","");
                nguoiDungDao = new NguoiDungDao(getContext());
                if (validate() > 0){
                    NguoiDung nguoiDung = nguoiDungDao.getID(user);
                    nguoiDung.setPassword(edNewPass.getText().toString());
                    nguoiDungDao.updateTaiKhoan(nguoiDung);
                    if (nguoiDungDao.updateTaiKhoan(nguoiDung) > 0){
                        Toast.makeText(getContext(), "Doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
                        edOldPass.setText("");
                        edNewPass.setText("");
                        edNewRePass.setText("");
                    }else{
                        Toast.makeText(getContext(), "Doi mat khau that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate(){
        int check = 1;
        if (edOldPass.getText().length() == 0 || edNewPass.getText().length() == 0 || edNewRePass.getText().length() == 0){
            Toast.makeText(getContext(), "Ban can nhap day du thong tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else{
            //doc user , pass trong SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String oldPass = sharedPreferences.getString("Password","");
            String pass = edNewPass.getText().toString();
            String repass = edNewRePass.getText().toString();
            if (!oldPass.equals(edOldPass.getText().toString())){
                Toast.makeText(getContext(), "Mat khau cu sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(repass)){
                Toast.makeText(getContext(), "Mat khau khong trung khop", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}