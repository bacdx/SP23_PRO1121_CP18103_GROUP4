package com.example.sp23_pro1121_cp18103_group4.Model;

public class BanAn {

    String id;
    String TenBanAN;
    String GhiChu;

    public BanAn(String id, String tenBanAN, String ghiChu) {
        this.id = id;
        TenBanAN = tenBanAN;
        GhiChu = ghiChu;
    }

    public BanAn() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenBanAN() {
        return TenBanAN;
    }

    public void setTenBanAN(String tenBanAN) {
        TenBanAN = tenBanAN;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}
