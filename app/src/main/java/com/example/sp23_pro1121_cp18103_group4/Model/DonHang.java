package com.example.sp23_pro1121_cp18103_group4.Model;

public class DonHang {
    private String maDonHang;
    private String ngayThanhToan;
    private String trangThai;//dang xử lý
    private int tongTien;
    private int maDatHang;
    private String maNguoiDung;
    private String tenNguoiDung;
    private String SoDt;
    private String diaChi;

    public DonHang() {
    }

    public DonHang(String maDonHang, String ngayThanhToan, String trangThai, int tongTien, int maDatHang, String maNguoiDung, String tenNguoiDung, String soDt, String diaChi) {
        this.maDonHang = maDonHang;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.maDatHang = maDatHang;
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        SoDt = soDt;
        this.diaChi = diaChi;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaDatHang() {
        return maDatHang;
    }

    public void setMaDatHang(int maDatHang) {
        this.maDatHang = maDatHang;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getSoDt() {
        return SoDt;
    }

    public void setSoDt(String soDt) {
        SoDt = soDt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
