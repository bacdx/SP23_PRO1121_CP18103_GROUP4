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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sp23_pro1121_cp18103_group4.Activity.Login;
import com.example.sp23_pro1121_cp18103_group4.DAO.NhanVienDao;
import com.example.sp23_pro1121_cp18103_group4.Fragment.DoanhThuFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.HoaDonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.HomeFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.KhachHangFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.LoaiMonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.NhanVienFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ThemBanFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.Top5Fragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.HoaDonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.HomeFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.LoaiMonFragment;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ThemBanFragment;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        toolbar.setTitle("Quản Lý Quán Ăn");
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
        String user = mIntent.getStringExtra("user");
        if (user.equalsIgnoreCase("admin")){
            tvHeaderUser.setText("Welcome: Admin");
        }else{
            username = user;
            dao = new NhanVienDao(this);
            NhanVien nhanVien = dao.getID(user);
            String username = nhanVien.getHoten();
            tvHeaderUser.setText("Wellcome: "+username);
            String uyquyen = nhanVien.getUyQuyen();
            if(uyquyen.equals("quanli")){
                nav_View.getMenu().findItem(R.id.nav_ThanhVien).setVisible(true);
                nav_View.getMenu().findItem(R.id.nav_AddThanhVien).setVisible(false);
                nav_View.getMenu().findItem(R.id.nav_DoanhThu).setVisible(true);
                nav_View.getMenu().findItem(R.id.nav_Top5).setVisible(true);
            }else{
                nav_View.getMenu().findItem(R.id.nav_ThanhVien).setVisible(false);
                nav_View.getMenu().findItem(R.id.nav_AddThanhVien).setVisible(false);
                nav_View.getMenu().findItem(R.id.nav_DoanhThu).setVisible(false);
                nav_View.getMenu().findItem(R.id.nav_Top5).setVisible(false);
            }
        }
        nav_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.nav_ThucDon:
                        replaceFragment(new LoaiMonFragment());
                        break;
                    case R.id.nav_BanAn:
                        replaceFragment(new ThemBanFragment());
                        break;
                    case R.id.nav_HoaDon:
                        replaceFragment(new HoaDonFragment());
                        break;
                    case R.id.nav_ThanhVien:
                        replaceFragment(new NhanVienFragment());
                        break;
                    case R.id.nav_KhachHang:
                        replaceFragment(new KhachHangFragment());
                        break;
                    case R.id.nav_DoanhThu:
                        replaceFragment(new DoanhThuFragment());
                        break;
                    case R.id.nav_Top5:
                        replaceFragment(new Top5Fragment());
                        break;
                    case R.id.nav_DangXuat:
                        finish();
                        startActivity(new Intent(MainActivity.this, Login.class));
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


}