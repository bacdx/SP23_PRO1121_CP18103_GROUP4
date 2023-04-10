package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.NguoiDungAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.NguoiDungDao;
import com.example.sp23_pro1121_cp18103_group4.MainActivity;
import com.example.sp23_pro1121_cp18103_group4.Model.NguoiDung;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;

public class ThongTinFragment extends Fragment {



    EditText edHoTen, edUser, edSoDt, edDiaChi;
    Button btnSave;
    NguoiDungDao dao;
    NguoiDung nguoiDung;
    String username;
    List<NguoiDung> list = new ArrayList<>();
    NguoiDungAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin, container, false);
        edHoTen = view.findViewById(R.id.khachhang_updateHoTen);
        edUser = view.findViewById(R.id.khachhang_updateUser);
        edSoDt = view.findViewById(R.id.khachhang_updateSoDT);
        edDiaChi = view.findViewById(R.id.khachHang_updateDiaChi);
        btnSave = view.findViewById(R.id.khachhang_UpdateSave);
        edUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUser.setText("");
            }
        });
        dao = new NguoiDungDao(getContext());
        Bundle bundle = this.getArguments();
        username = bundle.getString("user");
        nguoiDung = dao.getID(username);
        edHoTen.setText(nguoiDung.getHoTen());
        edUser.setText(username);
        edSoDt.setText(nguoiDung.getSoDT());
        edDiaChi.setText(nguoiDung.getDiaChi());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nguoiDung.setHoTen(edHoTen.getText().toString());
                nguoiDung.setUsername(edUser.getText().toString());
                nguoiDung.setSoDT(edSoDt.getText().toString());
                nguoiDung.setDiaChi(edDiaChi.getText().toString());
                if (edHoTen.getText().toString().isEmpty() || edSoDt.getText().toString().isEmpty()
                        || edDiaChi.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Khong dc de trong", Toast.LENGTH_SHORT).show();
                }else{
                    if (dao.updateKhachHang(nguoiDung) > 0) {
                        Toast.makeText(getContext(), "Update thanh cong", Toast.LENGTH_SHORT).show();
                        Intent mIntent = new Intent(getContext(), MainActivity.class);
                        mIntent.putExtra("user",nguoiDung.getUsername());
                        startActivity(mIntent);
                        list.clear();
                        list = dao.getAll();
                        adapter = new NguoiDungAdapter(getContext(),list);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(), "Update khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}