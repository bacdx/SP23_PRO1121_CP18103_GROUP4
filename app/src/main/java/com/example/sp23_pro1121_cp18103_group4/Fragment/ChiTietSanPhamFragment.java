package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class ChiTietSanPhamFragment extends Fragment {
    TextView tvChiTietTenMon, tvChiTietGiaTien;
    ImageView imgChiTietImgMon;
    EditText chitiet_edSoLuong;
    Button chitiet_btnAdd, chitiet_btnThanhToan;
    TextView tvTongTien;
    //model , dao
    DatHang datHang;
    DatHangDao dao;
    DonHang donHang;
    DonHangDao donHangDao;
    //
    int maMon, giaTien;
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
        ((Activity) getContext()).setTitle("Chi tiết sản phẩm");
        //ánh xạ
        init(view);
        //tạo mới đối tạo , dao sqliet
        datHang = new DatHang();
        dao = new DatHangDao(getContext());
        //tạo bundle lấy dữ liệu
        Bundle bundle = this.getArguments();
        maMon = bundle.getInt("chiTietMaMon");
        tenMon = bundle.getString("chiTietTenMon");
        giaTien = bundle.getInt("chiTietGiaTien");
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
        chitiet_btnThanhToan = view.findViewById(R.id.chitiet_btnThanhToan);
        imgChiTietImgMon = view.findViewById(R.id.chitiet_imgMon);

    }

    //*********//
    //tạo hàm set dữ liệu
    public void setBundle() {
        tvChiTietTenMon.setText(tenMon);
        tvChiTietGiaTien.setText("Giá tiền: " + giaTien + "đ");
        Bitmap imageContent = BitmapFactory.decodeByteArray(imgMon, 0, imgMon.length);
        imgChiTietImgMon.setImageBitmap(imageContent);
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
                        datHang.setMaMon(maMon);
                        datHang.setGiaTien(Integer.parseInt("" + giaTien));
                        if (dao.insertDatHang(datHang) > 0) {
                            Toast.makeText(getContext(), "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                            chitiet_edSoLuong.setText("");
                            list = dao.getAll();
                            adapter = new GioHangAdapter(getContext(), list);
                            adapter.notifyDataSetChanged();
                            Fragment fragment = new FragmentAllMon();
                            FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.mainFrame_collection_fragment, fragment).commit();
                        } else {
                            Toast.makeText(getContext(), "Thêm giỏ hàng không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        chitiet_btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datHang = new DatHang();
                dao = new DatHangDao(getContext());
                if (!chitiet_edSoLuong.getText().toString().matches("\\d+")) {
                    Toast.makeText(getContext(), "Yêu cầu nhập số nguyên", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    datHang.setSoLuong(Integer.parseInt("" + chitiet_edSoLuong.getText().toString()));
                    int soluong = Integer.parseInt("" + chitiet_edSoLuong.getText().toString());
                    int donhang_giaTien = giaTien;
                    datHang.setMaMon(maMon);
                    datHang.setGiaTien(Integer.parseInt("" + giaTien));
                    chitiet_edSoLuong.setText("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dia_chi_nhan_hang, null);
                    builder.setView(v);
                    Dialog dialog = builder.create();
                    dialog.show();
                    TextView tvTilte = dialog.findViewById(R.id.donhang_tvTitle);
                    tvTilte.setText("Nhập thông tin nhận hàng");
                    EditText edHoTen, edSoDT, edDiaChi;
                    edHoTen = dialog.findViewById(R.id.donhang_edHoTen);
                    edSoDT = dialog.findViewById(R.id.donhang_edSoDT);
                    edDiaChi = dialog.findViewById(R.id.donhang_edDiaChi);
                    donHang = new DonHang();
                    Button btnSave, btnCancel;
                    btnSave = dialog.findViewById(R.id.donhang_btnSave);
                    btnCancel = dialog.findViewById(R.id.donhang_btnCancel);
                    Random random = new Random();
                    int val = random.nextInt(1000);
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (edDiaChi.getText().toString().isEmpty() || edHoTen.getText().toString().isEmpty() ||
                                    edSoDT.getText().toString().isEmpty()) {
                                Toast.makeText(getContext(), "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            } else {
                                donHang.setMaDonHang("18092003" + val);
                                donHang.setNgayThanhToan(df.format(Calendar.getInstance().getTime()));
                                donHang.setTrangThai("Đang xử lý");
                                //lấy số lượng từ Edittext , giá tiền từ bundle món
                                int tongTien = 0;
                                tongTien = tongTien + (soluong * donhang_giaTien);
                                //set tổng tiền cho đơn hàng
                                donHang.setTongTien(tongTien);
                                donHang.setTenNguoiDung(edHoTen.getText().toString());
                                donHang.setSoDt(edSoDT.getText().toString());
                                donHang.setDiaChi(edDiaChi.getText().toString());
                                dao = new DatHangDao(getContext());
                                donHangDao = new DonHangDao(getContext());
                                if (donHangDao.insertDonHang(donHang) > 0) {
                                    Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Thanh toán không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edHoTen.setText("");
                            edDiaChi.setText("");
                            edSoDT.setText("");
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}