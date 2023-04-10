package com.example.sp23_pro1121_cp18103_group4.Model;

public class DatHang {
    private int maDatHang;
    private int soLuong;
    private int giaTien;
    private int maMon;

    public DatHang() {
    }

    public DatHang(int maDatHang, int soLuong, int giaTien, int maMon) {
        this.maDatHang = maDatHang;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.maMon = maMon;
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

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }


}
