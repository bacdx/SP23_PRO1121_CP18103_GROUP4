package com.example.sp23_pro1121_cp18103_group4.Model;

public class NhanVien {

   private int MaNV,NamSinh ;

    private String gioiTinh;
    private String hoten ,userName,PassWord,SoDienThoai ,Startus, UyQuyen;

    public NhanVien() {

    }

    public NhanVien(int maNV, int namSinh, String gioiTinh, String hoten, String userName, String passWord, String soDienThoai, String startus, String uyQuyen) {
        MaNV = maNV;
        NamSinh = namSinh;
        this.gioiTinh = gioiTinh;
        this.hoten = hoten;
        this.userName = userName;
        PassWord = passWord;
        SoDienThoai = soDienThoai;
        Startus = startus;
        UyQuyen = uyQuyen;
    }


    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }

    public int getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(int namSinh) {
        NamSinh = namSinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getStartus() {
        return Startus;
    }

    public void setStartus(String startus) {
        Startus = startus;
    }

    public String getUyQuyen() {
        return UyQuyen;
    }

    public void setUyQuyen(String uyQuyen) {
        UyQuyen = uyQuyen;
    }
}
