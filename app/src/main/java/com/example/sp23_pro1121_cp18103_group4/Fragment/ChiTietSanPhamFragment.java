package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.GioHangAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.DatHangDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.DonHangDao;
import com.example.sp23_pro1121_cp18103_group4.Model.DatHang;
import com.example.sp23_pro1121_cp18103_group4.Model.DonHang;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ChiTietSanPhamFragment extends Fragment {
    TextView tvChiTietTenMon, tvChiTietGiaTien;
    ImageView imgChiTietImgMon;
    EditText chitiet_edSoLuong;
    Button chitiet_btnAdd ;
    TextView tvTongTien;
    //model , dao
    DatHang datHang;
    DatHangDao dao;
    DonHang donHang;
    DonHangDao donHangDao;
    //
    String maMon;
    float giaTien;
    String tenMon;
    byte[] imgMon;
    List<DatHang> list = new ArrayList<>();
    //set ngay hien tai
    GioHangAdapter adapter;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_san_pham, container, false);
        ((Activity)getContext()).setTitle("Chi tiết sản phẩm");
        //ánh xạ
        init(view);
        //tạo mới đối tạo , dao sqliet
        datHang = new DatHang();
        dao = new DatHangDao(getContext());
        //tạo bundle lấy dữ liệu
        Bundle bundle = this.getArguments();
        maMon = bundle.getString("chiTietMaMon");
        tenMon = bundle.getString("chiTietTenMon");
        giaTien = bundle.getFloat("chiTietGiaTien");
        imgMon = bundle.getByteArray("chiTietImgMon");
        //set dữ liệu bundle vào textview , img
        setBundle();

        //nhập số lượng thêm vào giỏ hàng

        openThemGioHang();
        return view;
    }

    //*********//
    //ánh xạ
    public void init(View view) {
        tvChiTietTenMon = view.findViewById(R.id.chitiet_tvMon);
        tvChiTietGiaTien = view.findViewById(R.id.chitiet_tvGiaTien);
        chitiet_edSoLuong = view.findViewById(R.id.chitiet_edSoLuong);
        chitiet_btnAdd = view.findViewById(R.id.chitiet_btnAdd);
        imgChiTietImgMon = view.findViewById(R.id.chitiet_imgMon);

    }

    //*********//
    //tạo hàm set dữ liệu
    public void setBundle() {
        tvChiTietTenMon.setText(tenMon);
        tvChiTietGiaTien.setText("Giá tiền: " + giaTien + "đ");
        if(imgMon!=null){
    Bitmap imageContent = BitmapFactory.decodeByteArray(imgMon, 0, imgMon.length);
    imgChiTietImgMon.setImageBitmap(imageContent);
}
    }

    //*********//
    //tạo hàm thêm sản phẩm vào giỏ
    public void openThemGioHang() {
        chitiet_btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = dao.checkThanhToan(String.valueOf(maMon));
                if (check > 0) {
                    Toast.makeText(getContext(), "Đã có món trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (chitiet_edSoLuong.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Không đc để trống", Toast.LENGTH_SHORT).show();
                    } else if (!chitiet_edSoLuong.getText().toString().matches("\\d+")) {
                        Toast.makeText(getContext(), "Yêu cầu nhập số nguyên", Toast.LENGTH_SHORT).show();
                    } else {
                        datHang = new DatHang();
                        dao = new DatHangDao(getContext());
                        datHang.setSoLuong(Integer.parseInt("" + chitiet_edSoLuong.getText().toString()));
                        datHang.setMaMon(Integer.parseInt(maMon));
                        datHang.setGiaTien(giaTien);
                        if (dao.insertDatHang(datHang) > 0) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            chitiet_edSoLuong.setText("");
                            list = dao.getAll();
                            adapter = new GioHangAdapter(getContext(), list);
                            adapter.notifyDataSetChanged();
                            Fragment fragment = new FragmentAllMon();
                            FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.mainFrame_collection_fragment, fragment).commit();
                        } else {
                            Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}