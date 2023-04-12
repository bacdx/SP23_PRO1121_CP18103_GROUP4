package com.example.sp23_pro1121_cp18103_group4;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Activity.Dialog_MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.Adapter.MonTrongBanAdapter;
import com.example.sp23_pro1121_cp18103_group4.Adapter.Spiner_KhachHang;
import com.example.sp23_pro1121_cp18103_group4.Adapter.Spiner_NhanVien;
import com.example.sp23_pro1121_cp18103_group4.DAO.BanAnDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.HoaDonDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.KhachHangDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBanDAO;
import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.Model.HoaDon;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DialogThanhToan extends Dialog {
    public static final BanAn BAN_MANG_VE=new BanAn("0","MangVe","");
    RecyclerView rcv;
    TextView tenban, ngay, checkBox1, tong, thanhtoan;
    float tongTien = 0;
    float tienGiamGia = 0;
    String maBan;
    ArrayList<MonTrongBan> list=new ArrayList<>();

        public DialogThanhToan(@NonNull Context context, String maBan) {
        super(context);
        this.maBan = maBan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tinh_tien, null);
        setContentView(view);
        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity= Gravity.CENTER;
        getWindow().setAttributes(lp);
        // anhs xaj
         rcv = view.findViewById(R.id.rcv);
         tenban = view.findViewById(R.id.tenban);
         ngay = view.findViewById(R.id.ngay);
         checkBox1 = view.findViewById(R.id.checkb);
         tong = view.findViewById(R.id.tong);
         thanhtoan = view.findViewById(R.id.thanhtoan);


        DateFormat df = new SimpleDateFormat("d/M/yyyy");

        //lay list MTB
        try{
            list = new MonTrongBanDAO(getContext()).getDataByMaBanAndNull(maBan);
        }
        catch (Exception e){

        }


        int tongAdapter;
        BanAn banAn=new BanAn();
        if(maBan== DialogThanhToan.BAN_MANG_VE.getId()){
            banAn=DialogThanhToan.BAN_MANG_VE;
        }
        else {
           banAn = new BanAnDao(getContext()).getDataByID(maBan);
        }
        MonTrongBanAdapter monTrongBanAdapter;
        tenban.setText(banAn.getTenBanAN());
        ngay.setText(df.format(Calendar.getInstance().getTime()));
        monTrongBanAdapter = new MonTrongBanAdapter(list, getContext());
        rcv.setAdapter(monTrongBanAdapter);

        tongTien = tinhTong(list);
        tienGiamGia =  tongTien / 100 * 10;
        tong.setText(String.valueOf(tongTien));

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = ((CheckBox) v).isChecked();
                if (check) {
                    tongTien = tongTien - tienGiamGia;
                    tong.setText(String.valueOf(tongTien));

                } else {
                    tongTien = tongTien + tienGiamGia;
                    tong.setText(String.valueOf(tongTien));
                }
            }
        });
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user=preferences.getString("Username","");
                NhanVien nhanVien=new NhanVienDao(getContext()).getID(user);

                HoaDon hoaDon =new HoaDon();
                hoaDon.setMaNV(String.valueOf(nhanVien.getMaNV()));
                hoaDon.setTongTien(tongTien);
                hoaDon.setNgayLap(ngay.getText().toString());

                HoaDonDao hoaDonDao=new HoaDonDao(getContext());
                if(hoaDonDao.insertHoaDon(hoaDon)>0){
                    int maHoaDon=hoaDonDao.getDataNew();
                    MonTrongBanDAO monTrongBanDAO= new MonTrongBanDAO(getContext());
                    for(int i=0;i<list.size();i++) {
                        list.get(i).setMaHoaDon(String.valueOf(maHoaDon));
                        monTrongBanDAO.update(list.get(i));
                    }
                    Toast.makeText(view.getContext(),"Tao Thanh Cong",Toast.LENGTH_LONG);
                    cancel();
                }else {
                    Toast.makeText(view.getContext(),"Tao That Bai",Toast.LENGTH_LONG);
                }



//
//                Dialog dialog1 = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat);
//                dialog1.setContentView(R.layout.dialog_thanhtoan_hoa_don);
//
//                Spinner spn_nhanvien = dialog1.findViewById(R.id.spn_nhanvien);
//                Spinner spn_khachhang = dialog1.findViewById(R.id.spn_khachhang);
//
//                Button luu = dialog1.findViewById(R.id.luu);
//                Button huy = dialog1.findViewById(R.id.huy);
//
//
//
//                NhanVienDao daonv = new NhanVienDao(context);
//                Spiner_NhanVien nhanVien = new Spiner_NhanVien(context);
//
//                list1 = new ArrayList<>();
//                list1 = daonv.getAll();
//                nhanVien.setDaTa(list1);
//                spn_nhanvien.setAdapter(nhanVien);
//
//                spn_nhanvien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        tenNV = list1.get(position).getHoten();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//
//                KhachHangDao hangDao = new KhachHangDao(context);
//                Spiner_KhachHang spiner_khachHang = new Spiner_KhachHang(context);
//                listkh = new ArrayList<>();
//                listkh = hangDao.getAll();
//                spiner_khachHang.setDaTA(listkh);
//                spn_khachhang.setAdapter(spiner_khachHang);
//
//                spn_khachhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                        tenKH = listkh.get(position).getHoTen();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//                huy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog1.dismiss();
//                    }
//                });
//
//
//
//                luu.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        HoaDon hoaDon;
//                        hoaDon = new HoaDon();
//                        hoaDonDao = new HoaDonDao(context);
//
//                        hoaDon.setMaBan(String.valueOf(list.get(index).getId()));
//                        try {
//                            hoaDon.setMaKH(tenKH);
//                            hoaDon.setMaNV(tenNV);
//
//                        }catch (Exception e){
//                            Toast.makeText(context, "Chưa ĐỦ Thông Tin", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        hoaDon.setNgayLap(ngay.getText().toString());
//                        hoaDon.setTongTien(Integer.parseInt(tong.getText().toString()));
//                        hoaDonDao.insertHoaDon(hoaDon);
//                        try {
//                            trongBanDAO.DeleteAll(String.valueOf(list.get(index).getId()));
//                        }catch (Exception e){
//                            Toast.makeText(context, "Thanh Toán Thành CÔng", Toast.LENGTH_SHORT).show();
//                            holder.anh.setImageResource(R.drawable.banan);
//
//                        }
//                        dialog1.dismiss();
//                    }
//
//                });
//                dialog1.show();

            }
        });


    }

    public int tinhTong(ArrayList<MonTrongBan> list) {
        int tong = 0;
        for (int i = 0; i< list.size(); i++) {
            tong += list.get(i).getTien()*list.get(i).getSoLuong();
        }
        return tong;

    }

}
