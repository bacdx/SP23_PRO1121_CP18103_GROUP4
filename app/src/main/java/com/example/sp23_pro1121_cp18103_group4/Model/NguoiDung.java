package com.example.sp23_pro1121_cp18103_group4.Model;

public class NguoiDung {
    private String username;
    private String hoTen;
    private String password;
    private String soDT;
    private String diaChi;

    public NguoiDung() {
    }

    public NguoiDung(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public NguoiDung(String username, String hoTen, String password, String soDT, String diaChi) {
        this.username = username;
        this.hoTen = hoTen;
        this.password = password;
        this.soDT = soDT;
        this.diaChi = diaChi;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
