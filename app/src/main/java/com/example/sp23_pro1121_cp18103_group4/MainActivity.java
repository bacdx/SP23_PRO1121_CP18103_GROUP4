package com.example.sp23_pro1121_cp18103_group4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sp23_pro1121_cp18103_group4.Activity.Login;
import com.example.sp23_pro1121_cp18103_group4.DAO.NguoiDungDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.Fragment.AllMonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ChangePassFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.DoanhThuFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.DonHangFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.FragmentAllMon;
import com.example.sp23_pro1121_cp18103_group4.Fragment.GioHangFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.HoaDonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.HomeFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.KhachHangFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.LoaiMonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.NguoiDungFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.NhanVienFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ThemBanFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ThongTinFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.Top5Fragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.HoaDonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.HomeFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.LoaiMonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ThemBanFragment;

import com.example.sp23_pro1121_cp18103_group4.Model.NguoiDung;

import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView nav_View;
    String username = "";
    TextView tvHeaderUser;
    ImageView imgHeaderUser;
    NhanVienDao dao;
    NguoiDungDao nguoiDungDao;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Quản Lý Quán Ăn");
        init();
        setDraw();
    }

    //ánh xạ
    public void init() {
        drawerLayout = findViewById(R.id.draw_layout);
        toolbar = findViewById(R.id.main_toolBar);
        nav_View = findViewById(R.id.nav_view);
    }

    //thiết lập drawable , toolbar , navigation
    public void setDraw() {
//        toolbar.setTitle("Quản Lý Quán Ăn");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_draw, R.string.close_draw);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        replaceFragment(new HomeFragment());

        //set init header view
        View mHeaderView = nav_View.getHeaderView(0);
        tvHeaderUser = mHeaderView.findViewById(R.id.tvUser);
        //check user name
        Intent mIntent = getIntent();
        user = mIntent.getStringExtra("user");
        if (user.equalsIgnoreCase("admin")) {
            tvHeaderUser.setText("Welcome: Admin");
            nav_View.getMenu().findItem(R.id.nav_dat_hang).setVisible(false);
            nav_View.getMenu().findItem(R.id.nav_infoUser).setVisible(false);
            nav_View.getMenu().findItem(R.id.toolbar_giohang).setVisible(false);
            nav_View.getMenu().findItem(R.id.nav_DoiMK).setVisible(false);
        }//Set quyền addUser nếu là admin
        else {
            username = user;
            dao = new NhanVienDao(this);
            if (dao.checkTaiKhoan(user) > 0) {
                NhanVien nhanVien = dao.getID(user);
                username = nhanVien.getHoten();
                tvHeaderUser.setText("Wellcome: " + username);
                int uyquyen = nhanVien.getUyQuyen();
                if (uyquyen==NhanVien.QUANLY) {
                    nav_View.getMenu().findItem(R.id.nav_ThanhVien).setVisible(false);
                    nav_View.getMenu().findItem(R.id.nav_thong_ke).setVisible(false);
                    nav_View.getMenu().findItem(R.id.nav_DoanhThu).setVisible(false);
                    nav_View.getMenu().findItem(R.id.nav_Top5).setVisible(false);
                    nav_View.getMenu().findItem(R.id.nav_dat_hang).setVisible(false);
                    nav_View.getMenu().findItem(R.id.nav_NguoiDung).setVisible(false);
                    nav_View.getMenu().findItem(R.id.nav_infoUser).setVisible(false);
                    nav_View.getMenu().findItem(R.id.toolbar_giohang).setVisible(false);

                } else {
                    nav_View.getMenu().findItem(R.id.nav_ThanhVien).setVisible(false);
                    nav_View.getMenu().findItem(R.id.nav_DoanhThu).setVisible(true);
                    nav_View.getMenu().findItem(R.id.nav_Top5).setVisible(true);
                    nav_View.getMenu().findItem(R.id.nav_dat_hang).setVisible(false);
                    nav_View.getMenu().findItem(R.id.nav_NguoiDung).setVisible(true);
                    nav_View.getMenu().findItem(R.id.nav_infoUser).setVisible(false);
                    nav_View.getMenu().findItem(R.id.toolbar_giohang).setVisible(false);


                }
            } else {
                username = user;
                nguoiDungDao = new NguoiDungDao(this);
                NguoiDung nguoiDung = nguoiDungDao.getID(user);
                String username = nguoiDung.getHoTen();
                tvHeaderUser.setText("Welcome: " + username);
                nav_View.getMenu().findItem(R.id.nav_thong_ke).setVisible(false);
                nav_View.getMenu().findItem(R.id.nav_quan_ly).setVisible(false);
                nav_View.getMenu().findItem(R.id.nav_dat_mon).setVisible(true);
                nav_View.getMenu().findItem(R.id.toolbar_giohang).setVisible(false);
            }

        }
        nav_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Home:
                        toolbar.setTitle("Home");
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.nav_ThucDon:
                        toolbar.setTitle("Quản lý loại món");
                        replaceFragment(new LoaiMonFragment());
                        break;
                    case R.id.nav_BanAn:
                        toolbar.setTitle("Quản lý bàn ăn");
                        replaceFragment(new ThemBanFragment());
                        break;
                    case R.id.nav_HoaDon:
                        toolbar.setTitle("Quản lý hóa đơn");
                        replaceFragment(new HoaDonFragment());
                        break;
                    case R.id.nav_ThanhVien:
                        toolbar.setTitle("Quản lý nhân viên");
                        replaceFragment(new NhanVienFragment());
                        break;
                    case R.id.nav_KhachHang:
                        toolbar.setTitle("Quản lý khách hàng");
                        replaceFragment(new NguoiDungFragment());
                        break;
                    case R.id.nav_DoanhThu:
                        toolbar.setTitle("Quản lý doanh thu");
                        replaceFragment(new DoanhThuFragment());
                        break;
                    case R.id.nav_Top5:
                        toolbar.setTitle("Top 5 sản phẩm");
                        replaceFragment(new Top5Fragment());
                        break;
                    case R.id.nav_dat_mon:
                        toolbar.setTitle("Đặt món");
                        replaceFragment(new FragmentAllMon());
                        break;
                    case R.id.toolbar_giohang:
                        toolbar.setTitle("Giỏ hàng");
                        replaceFragment(new GioHangFragment());
                        break;
                    case R.id.nav_NguoiDung:
                        toolbar.setTitle("Quản lý người dùng");
                        replaceFragment(new NhanVienFragment());
                        break;
                    case R.id.nav_DoiMK:
                        toolbar.setTitle("Đổi mật khẩu");
                        replaceFragment(new ChangePassFragment());
                        break;
                    case R.id.nav_infoUser:
                        toolbar.setTitle("Thông tin cá nhân");
                        Intent intentThongTin = getIntent();
                        user = intentThongTin.getStringExtra("user");
                        Bundle bundleThongTin = new Bundle();
                        bundleThongTin.putString("user", user);
                        Fragment fragmentThongTin = new ThongTinFragment();
                        fragmentThongTin.setArguments(bundleThongTin);
                        replaceFragment(fragmentThongTin);
                        break;
                    case R.id.nav_don_hang_cua_ban:
                        toolbar.setTitle("Đơn hàng của bạn");
//                        Intent intentDonHang = getIntent();
//                        user = intentDonHang.getStringExtra("user");
//                        Bundle bundleDonHang = new Bundle();
//                        bundleDonHang.putString("user", user);
//                        Fragment fragmentDonHang = new DonHangFragment();
//                        fragmentDonHang.setArguments(bundleDonHang);
//                        replaceFragment(fragmentDonHang);
                        replaceFragment(new DonHangFragment());
                        break;
                    case R.id.nav_DangXuat:
                        finish();
                        startActivity(new Intent(MainActivity.this, Login.class));
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame_collection_fragment, fragment);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        menu.findItem(R.id.toolbar_giohang).setVisible(true);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        dao = new NhanVienDao(this);
        if (user.equalsIgnoreCase("admin")) {
            menu.findItem(R.id.toolbar_giohang).setVisible(false);
        } else if (dao.checkTaiKhoan(user) > 0) {
            NhanVien nhanVien = dao.getID(user);
            menu.findItem(R.id.toolbar_giohang).setVisible(false);
        } else {
            menu.findItem(R.id.toolbar_giohang).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        if (id == R.id.toolbar_giohang) {
            toolbar.setTitle("Giỏ hàng");
            NguoiDung nguoiDung = nguoiDungDao.getID(user);
            Bundle bundle = new Bundle();
            bundle.putString("usernameGioHang", nguoiDung.getUsername());
            bundle.putString("hoTenGioHang", nguoiDung.getHoTen());
            bundle.putString("soDTGioHang", nguoiDung.getSoDT());
            bundle.putString("diaChiGioHang", nguoiDung.getDiaChi());
            Fragment fragment = new GioHangFragment();
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        }
        return super.onOptionsItemSelected(item);
    }
}