package com.example.sp23_pro1121_cp18103_group4.Model;

public class HoaDon {
    private String maHoaDon;

    private String maNV;
    private String maKH;
    private String ngayLap;
    private float tongTien;



    public HoaDon() {
    }


    public HoaDon(String maHoaDon, String maNV, String maKH, String ngayLap, int tongTien) {
        this.maHoaDon = maHoaDon;

        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;

    }



    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }


    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }



}
