package com.example.sp23_pro1121_cp18103_group4.Model;

public class DatHang {
    private int maDatHang;
    private int soLuong;
    private float giaTien;
    private int maMon;
    private String maDonHang;
    public DatHang() {
    }

    public DatHang(int maDatHang, int soLuong, float giaTien, int maMon,String maDonHang) {
        this.maDatHang = maDatHang;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.maMon = maMon;
        this.maDonHang=maDonHang;
    }

    public int getMaDatHang() {
        return maDatHang;
    }

    public void setMaDatHang(int maDatHang) {
        this.maDatHang = maDatHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(float giaTien) {
        this.giaTien = giaTien;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getmaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }
}
