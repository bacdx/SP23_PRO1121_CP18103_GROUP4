package com.example.sp23_pro1121_cp18103_group4.Model;

public class Mon {
    public static final int CON_HANG=1;
    public static final int HET_HANG=0;
    private String maMon;
    private String tenMon;
    private float giaTien;
    private int trangThai=this.CON_HANG;
    private int maLoaiMon;
    private byte[] imgMon;
    public Mon() {
    }

    public Mon(String maMon, String tenMon, float giaTien, int trangThai, int maLoaiMon) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
        this.maLoaiMon = maLoaiMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public float getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(float giaTien) {
        this.giaTien = giaTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
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
