package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.sp23_pro1121_cp18103_group4.Adapter.DonHangAdapter;
import com.example.sp23_pro1121_cp18103_group4.Adapter.GioHangAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.DatHangDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.DonHangDao;
import com.example.sp23_pro1121_cp18103_group4.Model.DatHang;
import com.example.sp23_pro1121_cp18103_group4.Model.DonHang;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;



public class DonHangChiTietFragment extends Fragment {

    TextView tvHoTen, tvSoDT, tvDiaChi, tvNgay, tvTongTien, tvTrangThai ,img_Update;
    Button btn_Update;
    int maDatHang;
    DatHangDao datHangDao;
    List<DatHang> datHangList = new ArrayList<>();
    GioHangAdapter gioHangAdapter;
    //don hang
    DonHang donHang;
    DonHangDao dao;
    DonHangAdapter adapter;
    List<DonHang> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_hang_chi_tiet, container, false);
        ((Activity) getContext()).setTitle("Đơn hàng chi tiết");
        //ánh xạ dữ liệu
        init(view);
        //set bundle lấy dữ liệu từ giỏ hàng adapter
        openBundle();

        //update thông tin nhận hàng
        openDialogUpdate();
        //update trạng thái hủy hàng
        openUpdateTrangThai(view);
        return view;
    }

    //init ánh xạ
    public void init(View view) {
        tvHoTen = view.findViewById(R.id.dhct_tvHoTen);
        tvSoDT = view.findViewById(R.id.dhct_tvSoDT);
        tvDiaChi = view.findViewById(R.id.dhct_tvDiaChi);
        tvNgay = view.findViewById(R.id.dhct_tvNgay);
        tvTongTien = view.findViewById(R.id.dhct_tvTongTien);
        tvTrangThai = view.findViewById(R.id.dhct_tvTrangThai);
        img_Update = view.findViewById(R.id.dhct_updateThongTin);

    }

    //get data lay du lieu trong sqlite


    //get du lieu tu bundl
    public void openBundle() {
        Bundle bundle = this.getArguments();
        maDatHang = bundle.getInt("dhctMaDatHang");
        String hoTen = bundle.getString("dhctHoTen");
        String soDT = bundle.getString("dhctSoDT");
        String diaChi = bundle.getString("dhctDiaChi");
        String ngay = bundle.getString("dhctNgay");
        float tongTien = bundle.getFloat("dhctTongTien");
        String trangThai = bundle.getString("dhctTrangThai");
        tvHoTen.setText("Họ tên: " + hoTen);
        tvSoDT.setText("Số điện thoại: " + soDT);
        tvDiaChi.setText("Địa chỉ: " + diaChi);
        tvNgay.setText("Ngày: " + ngay);
        tvTongTien.setText("Tổng tiền: " + tongTien + "đ");
        tvTrangThai.setText("Trạng thái: " + trangThai);
    }

    //open dialog thong tin
    public void openDialogUpdate() {
        Bundle bundle = this.getArguments();
        String maDonHang = bundle.getString("dhctMaDonHang");
        String hoTen = bundle.getString("dhctHoTen");
        String soDT = bundle.getString("dhctSoDT");
        String diaChi = bundle.getString("dhctDiaChi");
        img_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dia_chi_nhan_hang, null);
                builder.setView(v);
                Dialog dialog = builder.create();
                dialog.show();
                donHang = new DonHang();
                TextView tvTitle = dialog.findViewById(R.id.donhang_tvTitle);
                tvTitle.setText("Sửa thông tin nhận hàng");
                EditText edHoTen, edSoDT, edDiaChi;
                edHoTen = dialog.findViewById(R.id.donhang_edHoTen);
                edSoDT = dialog.findViewById(R.id.donhang_edSoDT);
                edDiaChi = dialog.findViewById(R.id.donhang_edDiaChi);
                edHoTen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edHoTen.setText("");
                    }
                });
                edSoDT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edSoDT.setText("");
                    }
                });
                edDiaChi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edDiaChi.setText("");
                    }
                });
                edHoTen.setText(hoTen);
                edSoDT.setText(soDT);
                edDiaChi.setText(diaChi);
                Button btnSave, btnCancel;
                btnSave = dialog.findViewById(R.id.donhang_btnSave);
                btnCancel = dialog.findViewById(R.id.donhang_btnCancel);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edHoTen.getText().toString().isEmpty() || edSoDT.getText().toString().isEmpty() ||
                                edDiaChi.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            dao = new DonHangDao(getContext());
                            donHang.setMaDonHang(maDonHang);
                            donHang.setTenNguoiDung(edHoTen.getText().toString());
                            donHang.setSoDt(edSoDT.getText().toString());
                            donHang.setDiaChi(edDiaChi.getText().toString());
                            if (dao.updateDonHang(donHang) > 0) {
                                Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                tvHoTen.setText("Họ tên: " + donHang.getTenNguoiDung());
                                tvSoDT.setText("Số điện thoại: " + donHang.getSoDt());
                                tvDiaChi.setText("Địa chỉ: " + donHang.getDiaChi());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Sửa không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edHoTen.setText("");
                        edSoDT.setText("");
                        edDiaChi.setText("");
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    //update trạng thái thanh toán
    public void openUpdateTrangThai(View view) {
        Bundle bundle = this.getArguments();
        String maDonHang = bundle.getString("dhctMaDonHang");
        btn_Update = view.findViewById(R.id.dhct_btnUpdate);
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bạn có muốn hủy đơn hàng này không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao = new DonHangDao(getContext());
                        if (dao.deleteDonHang(maDonHang) > 0) {
                            Toast.makeText(getContext(), "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                            tvHoTen.setText("");
                            tvSoDT.setText("");
                            tvDiaChi.setText("");
                            tvNgay.setText("");
                            tvTongTien.setText("");
                            tvTrangThai.setText("");
                            dialog.dismiss();
                            Fragment fragment = new DonHangFragment();
                            FragmentTransaction transaction = ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.mainFrame_collection_fragment,fragment).commit();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
}