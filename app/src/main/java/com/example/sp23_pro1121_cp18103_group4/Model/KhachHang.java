package com.example.sp23_pro1121_cp18103_group4.Model;

public class KhachHang {
    private int maKhachHang;
    private String name;
    private String numberPhone;

    public KhachHang() {
    }

    public KhachHang(int maKhachHang, String name, String numberPhone) {
        this.maKhachHang = maKhachHang;
        this.name = name;
        this.numberPhone = numberPhone;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}
