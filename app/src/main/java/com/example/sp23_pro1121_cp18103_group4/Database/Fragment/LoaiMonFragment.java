package com.example.sp23_pro1121_cp18103_group4.Database.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Adapter.LoaiMonAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.LoaiMonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.LoaiMon;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;



public class LoaiMonFragment extends Fragment {
    RecyclerView rc_loaiMon;
    FloatingActionButton flbtnAdd;
    LoaiMonDao dao;
    LoaiMonAdapter adapter;
    LoaiMon loaiMon;
    List<LoaiMon> list;
    //thiet lap upload ảnh
    private static final int PICK_IMAGE_REQUEST = 100;
    static byte[] imageContent;
    //dialog
    EditText edTenLoaiMon;
    ImageView imgLoaiMon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loai_mon, container, false);
        init(view);
        getListLoaiMon();
        insertLoaiMon();
        return view;
    }

    //ánh xạ
    public void init(View view) {
        rc_loaiMon = view.findViewById(R.id.loaimon_rcView);
        flbtnAdd = view.findViewById(R.id.loaimon_btnAdd);
    }

    //đổ dữ liệu
    public void getListLoaiMon() {
        dao = new LoaiMonDao(getContext());
        list = dao.getAll();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        rc_loaiMon.setLayoutManager(manager);
        adapter = new LoaiMonAdapter(getContext(), list);
        rc_loaiMon.setAdapter(adapter);
    }

    //thêm loại món
    public void insertLoaiMon() {
        flbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(Gravity.CENTER);
            }
        });
    }

    //tạo dialog thêm loại món
    public void openDialog(int gravity) {
        //thiết lập dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loai_mon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        loaiMon = new LoaiMon();
        //ánh xạ
        TextView tvTitle;
        tvTitle = view.findViewById(R.id.loaimon_tvTitle);
        tvTitle.setText("Thêm Loại Món");
        edTenLoaiMon = view.findViewById(R.id.loaimon_edTenLoai);
        imgLoaiMon = view.findViewById(R.id.loaimon_addImg);
        imgLoaiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        Button btnSave, btnCancel;
        btnSave = view.findViewById(R.id.loaimon_btnSave);
        btnCancel = view.findViewById(R.id.loaimon_btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiMon.setTenLoaiMon(edTenLoaiMon.getText().toString());
                loaiMon.setImgLoaiMon(imageContent);
                dao = new LoaiMonDao(getContext());
                if (validate() > 0) {
                    if (dao.insertLoaiMon(loaiMon) > 0) {
                        Toast.makeText(getContext(), "Thêm loại món thành công", Toast.LENGTH_SHORT).show();
                        edTenLoaiMon.setText("");
                        list.clear();
                        list = dao.getAll();
                        adapter = new LoaiMonAdapter(getContext(), list);
                        rc_loaiMon.setAdapter(adapter);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm loại món thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTenLoaiMon.setText("");
                dialog.dismiss();
            }
        });
    }

    //validate check
    public int validate() {
        int check = 1;
        if (edTenLoaiMon.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Yêu cầu không để trống", Toast.LENGTH_SHORT).show();
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
                InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgLoaiMon.setImageBitmap(bitmap);
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