package com.example.sp23_pro1121_cp18103_group4.Model;

public class Mon {
    private int maMon;
    private String tenMon;
    private int giaTien;
    private String trangThai;
    private int maLoaiMon;
    private byte[] imgMon;
    public Mon() {
    }

    public Mon(int maMon, String tenMon, int giaTien, String trangThai, int maLoaiMon) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
        this.maLoaiMon = maLoaiMon;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaLoaiMon() {
        return maLoaiMon;
    }

    public void setMaLoaiMon(int maLoaiMon) {
        this.maLoaiMon = maLoaiMon;
    }

    public byte[] getImgMon() {
        return imgMon;
    }

    public void setImgMon(byte[] imgMon) {
        this.imgMon = imgMon;
    }

}
