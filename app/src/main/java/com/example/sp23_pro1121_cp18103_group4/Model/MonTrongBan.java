package com.example.sp23_pro1121_cp18103_group4.Model;

public class MonTrongBan {
    private int id;
    private String maBan;
    private String maMon;
    private int soLuong;

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

}
