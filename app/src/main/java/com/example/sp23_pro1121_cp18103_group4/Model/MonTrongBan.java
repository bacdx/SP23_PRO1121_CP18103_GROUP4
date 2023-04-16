package com.example.sp23_pro1121_cp18103_group4.Model;

public class MonTrongBan {
    private int id;
    private String maBan;
    private String maMon;
    private String maHoaDon;
    private float tien;
    private float aTien;
    private int soLuong;

    private byte[] imgMon;

    public byte[] getImgMon() {
        return imgMon;
    }

    public void setImgMon(byte[] imgMon) {
        this.imgMon = imgMon;
    }



    String tenMon;

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public MonTrongBan() {
    }

    public MonTrongBan(int id, String maBan, String maMon, int soLuong) {
        this.id = id;
        this.maBan = maBan;
        this.maMon = maMon;
        this.soLuong = soLuong;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getTien() {
        return tien;
    }

    public void setTien(float tien) {
        this.tien = tien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }


}
