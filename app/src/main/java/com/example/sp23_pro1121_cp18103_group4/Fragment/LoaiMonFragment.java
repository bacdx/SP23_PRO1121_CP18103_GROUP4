package com.example.sp23_pro1121_cp18103_group4.Fragment;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Adapter.LoaiMonAdapter;
import com.example.sp23_pro1121_cp18103_group4.Adapter.MonAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.LoaiMonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.LoaiMon;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
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
    private SearchView loaimon_searchView;
    ImageView loaimon_filter;
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
        openSearchViewLoaiMon(view);
        openFilterLoaiMon(view);
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
    //thiết lập search view tìm loại món

    public void openSearchViewLoaiMon(View view){
        loaimon_searchView = view.findViewById(R.id.loaimon_searchView);
        loaimon_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    //thiết lập filter sắp xếp tên loại món
    public void openFilterLoaiMon(View view){
        loaimon_filter = view.findViewById(R.id.loaimon_filter);
        loaimon_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), loaimon_filter);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_loaimon, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.sortUpLoaiMon:
                                sortUpTenLoai();
                                break;
                            case R.id.sortDownLoaiMon:
                                sortDownTenLoai();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    //*********//
    //phương thức sắp xếp tên loại món tăng dần
    public void sortUpTenLoai(){
        Comparator<LoaiMon> com = new Comparator<LoaiMon>() {
            @Override
            public int compare(LoaiMon o1, LoaiMon o2) {
                return o1.getTenLoaiMon().compareToIgnoreCase(o2.getTenLoaiMon());
            }
        };
        Collections.sort(list,com);
        adapter = new LoaiMonAdapter(getContext(),list);
        rc_loaiMon.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    //*********//
    //phương thức sắp xếp tên loại món tăng dần
    public void sortDownTenLoai(){
        Comparator<LoaiMon> com = new Comparator<LoaiMon>() {
            @Override
            public int compare(LoaiMon o1, LoaiMon o2) {
                return o2.getTenLoaiMon().compareToIgnoreCase(o1.getTenLoaiMon());
            }
        };
        Collections.sort(list,com);
        adapter = new LoaiMonAdapter(getContext(),list);
        rc_loaiMon.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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