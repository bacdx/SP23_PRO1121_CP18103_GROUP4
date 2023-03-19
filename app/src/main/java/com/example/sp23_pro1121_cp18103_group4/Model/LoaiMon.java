package com.example.sp23_pro1121_cp18103_group4.Model;

public class LoaiMon {
    private int maLoaiMon;
    private String tenLoaiMon;
    private byte[] imgLoaiMon;

    public LoaiMon() {
    }

    public LoaiMon(int maLoaiMon, String tenLoaiMon) {
        this.maLoaiMon = maLoaiMon;
        this.tenLoaiMon = tenLoaiMon;
    }


    public int getMaLoaiMon() {
        return maLoaiMon;
    }

    public void setMaLoaiMon(int maLoaiMon) {
        this.maLoaiMon = maLoaiMon;
    }

    public String getTenLoaiMon() {
        return tenLoaiMon;
    }

    public void setTenLoaiMon(String tenLoaiMon) {
        this.tenLoaiMon = tenLoaiMon;
    }

    public byte[] getImgLoaiMon() {
        return imgLoaiMon;
    }

    public void setImgLoaiMon(byte[] imgLoaiMon) {
        this.imgLoaiMon = imgLoaiMon;
    }
}
