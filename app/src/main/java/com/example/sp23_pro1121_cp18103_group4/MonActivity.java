package com.example.sp23_pro1121_cp18103_group4;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Adapter.MonAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class MonActivity extends AppCompatActivity {
    RecyclerView mon_rcView;
    FloatingActionButton flbtnAdd;
    MonDao dao;
    List<Mon> list;
    MonAdapter adapter;
    //thiet lap upload ảnh
    private static final int PICK_IMAGE_REQUEST = 100;
    static byte[] imageContent;
    //dialog
    EditText edTenMon, edGiaTien;
    CheckBox chkTrangThai;
    int maLoaiMon;
    ImageView imgMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon);
        init();
        showData();
        insertMon();
        imgUpdateMon();
    }
    //init ánh xạ

    public void init() {
        mon_rcView = findViewById(R.id.mon_rcView);
        flbtnAdd = findViewById(R.id.mon_btnAdd);
    }

    //đổ dữ liệu
    public void showData() {
        dao = new MonDao(this);
        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getBundleExtra("getId");
        maLoaiMon = bundle.getInt("maLoaiMon");
        list = dao.getAllWithId(maLoaiMon);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mon_rcView.setLayoutManager(manager);
        adapter = new MonAdapter(this, list);
        mon_rcView.setAdapter(adapter);
        mon_rcView.setHasFixedSize(true);
    }

    //thêm món ăn
    public void insertMon() {
        flbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogMon(Gravity.CENTER);
            }
        });
    }
    //update món ăn
    public void imgUpdateMon(){
        mon_rcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(Gravity.CENTER);
            }
        });
    }
    //validate check

    //dialog thêm món ăn
    public void openDialogMon(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_mon);
        dialog.create();
        dialog.show();
        //custom dialog
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        //tạo mới model
        Mon mon = new Mon();
        TextView mon_tvTitle;
        Button btnSave , btnCancel;
        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getBundleExtra("getId");
        maLoaiMon = bundle.getInt("maLoaiMon");
        imgMon = dialog.findViewById(R.id.mon_addImg);
        imgMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        edTenMon = dialog.findViewById(R.id.mon_edTenMon);
        edGiaTien = dialog.findViewById(R.id.mon_edGiaTien);
        chkTrangThai = dialog.findViewById(R.id.mon_chkTrangThai);
        btnSave = dialog.findViewById(R.id.mon_btnSave);
        btnCancel = dialog.findViewById(R.id.mon_btnCancel);
        mon_tvTitle = dialog.findViewById(R.id.mon_tvTitle);
        mon_tvTitle.setText("Thêm Món Ăn");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkTrangThai.isChecked()) {
                    mon.setTrangThai("Còn hàng");
                } else {
                    mon.setTrangThai("Hết hàng");
                }
                mon.setTenMon(edTenMon.getText().toString());
                mon.setGiaTien(Integer.parseInt("" + edGiaTien.getText().toString()));
                mon.setMaLoaiMon(maLoaiMon);
                mon.setImgMon(imageContent);
                dao = new MonDao(getApplicationContext());
                if (validate() > 0) {
                    if (dao.insertMon(mon) > 0) {
                        Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                        edTenMon.setText("");
                        edGiaTien.setText("");
                        chkTrangThai.setChecked(false);
                        list.clear();
                        dialog.dismiss();
                        list = dao.getAllWithId(mon.getMaLoaiMon());
                        adapter = new MonAdapter(getApplicationContext(), list);
                        mon_rcView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "that bai", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTenMon.setText("");
                edGiaTien.setText("");
                dialog.dismiss();
            }
        });
    }
    //dialog update
    public void openDialogUpdate(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_mon);
        dialog.create();
        dialog.show();
        //custom dialog
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        //tạo mới model
        Mon mon = new Mon();
        TextView mon_tvTitle;
        Button btnSave;
        updateMon();
        imgMon = dialog.findViewById(R.id.mon_addImg);
        imgMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        edTenMon = dialog.findViewById(R.id.mon_edTenMon);
        edGiaTien = dialog.findViewById(R.id.mon_edGiaTien);
        chkTrangThai = dialog.findViewById(R.id.mon_chkTrangThai);
        btnSave = dialog.findViewById(R.id.mon_btnSave);
        mon_tvTitle = dialog.findViewById(R.id.mon_tvTitle);
        mon_tvTitle.setText("Thêm Món Ăn");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkTrangThai.isChecked()) {
                    mon.setTrangThai("Còn hàng");
                } else {
                    mon.setTrangThai("Hết hàng");
                }
                mon.setTenMon(edTenMon.getText().toString());
                mon.setGiaTien(Integer.parseInt("" + edGiaTien.getText().toString()));
                mon.setMaLoaiMon(maLoaiMon);
                mon.setImgMon(imageContent);
                dao = new MonDao(getApplicationContext());
                if (validate() > 0) {
                    if (dao.insertMon(mon) > 0) {
                        Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                        edTenMon.setText("");
                        edGiaTien.setText("");
                        chkTrangThai.setChecked(false);
                        list.clear();
                        dialog.dismiss();
                        list = dao.getAllWithId(mon.getMaLoaiMon());
                        adapter = new MonAdapter(getApplicationContext(), list);
                        mon_rcView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "that bai", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
    }
    public void updateMon(){
        if (getIntent().getBundleExtra("monData") != null){
            Bundle bundle = getIntent().getBundleExtra("monData");
            edTenMon.setText(bundle.getString("tenMon"));
            edGiaTien.setText(bundle.getInt("giaTien"));
            chkTrangThai.setText("trangThai");
            //set image
            imageContent = bundle.getByteArray("imgMon");
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageContent,0,imageContent.length);
            imgMon.setImageBitmap(bitmap);
        }
    }
    //validate check
    public int validate() {
        int check = 1;
        if (edTenMon.getText().toString().isEmpty() || edGiaTien.getText().toString().isEmpty()) {
            Toast.makeText(this, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!edGiaTien.getText().toString().matches("\\d+")) {
            Toast.makeText(this, "Yêu cầu giá tiền phải là số", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
    //thiết lập lấy hình ảnh
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri imageUri = intent.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgMon.setImageBitmap(bitmap);
                imageContent = getBytes(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //lấy loại hình ảnh
    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}