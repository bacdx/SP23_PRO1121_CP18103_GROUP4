package com.example.sp23_pro1121_cp18103_group4.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.LoaiMonAdapter;
import com.example.sp23_pro1121_cp18103_group4.Adapter.MonAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.LoaiMon;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MonFragment extends Fragment {


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

    //filter - search view
    private SearchView searchView;
    ImageView mon_filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mon, container, false);
        ((Activity)getContext()).setTitle("Quản lý món");
        init(view);
        showData();
        insertMon();
        openSearchView(view);
        openFilter();
        return view;
    }
    //init ánh xạ

    public void init(View view) {
        mon_rcView = view.findViewById(R.id.mon_rcView);
        flbtnAdd = view.findViewById(R.id.mon_btnAdd);
        mon_filter = view.findViewById(R.id.mon_filter);
    }

    //*******//
    //thiết lập search view tìm tên món
    public void openSearchView(View view) {
        searchView = view.findViewById(R.id.mon_searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    //*********//
    //thiết lập filter sắp xếp
    public void openFilter() {
        mon_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), mon_filter);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_mon, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.filterAllMon:
                                restoreAllMon();
                                break;
                            case R.id.sortUpTenMon:
                                sortUpTen();
                                break;
                            case R.id.sortDownTenMon:
                                sortDownTen();
                                break;
                            case R.id.sortUpGiaTien:
                                sortUpGiaTien();
                                break;
                            case R.id.sortDownGiaTien:
                                sortDownGiaTien();
                                break;
                            case R.id.sortGiaTienMin:
                                sortGiaTienMin();
                                break;
                            case R.id.sortGiaTienBetween:
                                sortGiaBetween();
                                break;
                            case R.id.sortGiaTienMax:
                                sortGiaMax();
                                break;
                            case R.id.filterCheckMon:
                                filterCheckMon();
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
    //khôi phục all món
    public void restoreAllMon() {
        adapter = new MonAdapter(getContext(), list);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //*********//
    //phương thức sắp xếp tên món tăng dần
    public void sortUpTen() {
        Comparator<Mon> com = new Comparator<Mon>() {
            @Override
            public int compare(Mon o1, Mon o2) {
                return o1.getTenMon().compareToIgnoreCase(o2.getTenMon());
            }
        };
        Collections.sort(list, com);
        adapter = new MonAdapter(getContext(), list);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //*********//
    //phương thức sắp xếp tên món giảm dần
    public void sortDownTen() {
        Comparator<Mon> com = new Comparator<Mon>() {
            @Override
            public int compare(Mon o1, Mon o2) {
                return o2.getTenMon().compareToIgnoreCase(o1.getTenMon());
            }
        };
        Collections.sort(list, com);
        adapter = new MonAdapter(getContext(), list);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //*********//
    //phương thức sắp xếp giá tiền  tăng dần
    public void sortUpGiaTien() {
        Comparator<Mon> com = new Comparator<Mon>() {
            @Override
            public int compare(Mon o1, Mon o2) {
                return Float.valueOf(o1.getGiaTien()).compareTo(o2.getGiaTien());
            }
        };
        Collections.sort(list, com);
        adapter = new MonAdapter(getContext(), list);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //*********//
    //phương thức sắp xếp giá tiền  giảm dần
    public void sortDownGiaTien() {
        Comparator<Mon> com = new Comparator<Mon>() {
            @Override
            public int compare(Mon o1, Mon o2) {
                return Float.valueOf(o2.getGiaTien()).compareTo(o1.getGiaTien());
            }
        };
        Collections.sort(list, com);
        adapter = new MonAdapter(getContext(), list);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //*********//
    //phương thức sắp xếp giá tiền dưới 50.000đ
    public void sortGiaTienMin() {
        List<Mon> temp_arraylist = new ArrayList<Mon>();

        for (int i = 0; i < list.size(); i++) {

            int price = (Integer.parseInt("" + list.get(i).getGiaTien()));

            if (price < 20000) {
                temp_arraylist.add(list.get(i));
            }

        }

        adapter = new MonAdapter(getContext(), temp_arraylist);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //*********//
    //phương thức sắp xếp giá tiền từ 20.000 - 50.000đ
    public void sortGiaBetween() {
        List<Mon> temp_arraylist = new ArrayList<Mon>();

        for (int i = 0; i < list.size(); i++) {

            int price = (Integer.parseInt("" + list.get(i).getGiaTien()));

            if (price >= 20000 && price <= 50000) {
                temp_arraylist.add(list.get(i));
            }

        }

        adapter = new MonAdapter(getContext(), temp_arraylist);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //*********//
    //phương thức sắp xếp giá tiền trên 50.000đ
    public void sortGiaMax() {
        List<Mon> temp_arraylist = new ArrayList<Mon>();

        for (int i = 0; i < list.size(); i++) {

            int price = (Integer.parseInt("" + list.get(i).getGiaTien()));

            if (price > 50000) {
                temp_arraylist.add(list.get(i));
            }

        }

        adapter = new MonAdapter(getContext(), temp_arraylist);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    //*********//
    //phương thức sắp xếp món còn hàng
    public void filterCheckMon() {
        List<Mon> temp_arraylist = new ArrayList<Mon>();
//        int trangThai=0;
        for (int i = 0; i < list.size(); i++) {

             String trangThai = list.get(i).getTrangThai();
            if (trangThai.equalsIgnoreCase("Còn hàng")) {
                temp_arraylist.add(list.get(i));
            }

        }

        adapter = new MonAdapter(getContext(), temp_arraylist);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    //đổ dữ liệu
    public void showData() {
        dao = new MonDao(getContext());
        Bundle bundle = this.getArguments();
        maLoaiMon = bundle.getInt("maLoaiMon");
        list = dao.getAllWithId(maLoaiMon);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mon_rcView.setLayoutManager(manager);
        adapter = new MonAdapter(getContext(), list);
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

    //dialog thêm món ăn
    public void openDialogMon(int gravity) {
        Dialog dialog = new Dialog(getContext());
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
        Button btnSave, btnCancel;
        Bundle bundle = this.getArguments();
        maLoaiMon = bundle.getInt("maLoaiMon");
        list = dao.getAllWithId(maLoaiMon);
        imgMon = dialog.findViewById(R.id.mon_addImg);

        try {
            imgMon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
        }



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

                if (imgMon == null){
                    Toast.makeText(getContext(), "Chưa Có Ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (chkTrangThai.isChecked()) {
                    mon.setTrangThai("Còn hàng");
                } else {
                    mon.setTrangThai("Hết hàng");
                }
                mon.setTenMon(edTenMon.getText().toString());
                mon.setGiaTien(Integer.parseInt("" + edGiaTien.getText().toString()));
                mon.setMaLoaiMon(maLoaiMon);
                mon.setImgMon(imageContent);
                dao = new MonDao(getContext());
                if (validate() > 0) {
                    if (dao.insertMon(mon) > 0) {
                        Toast.makeText(getContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                        edTenMon.setText("");
                        edGiaTien.setText("");
                        chkTrangThai.setChecked(false);
                        list.clear();
                        dialog.dismiss();
                        list = dao.getAllWithId(mon.getMaLoaiMon());
                        adapter = new MonAdapter(getContext(), list);
                        mon_rcView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "that bai", Toast.LENGTH_SHORT).show();
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

    //validate check
    public int validate() {

        int check = 1;
        if (edTenMon.getText().toString().isEmpty() || edGiaTien.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!edGiaTien.getText().toString().matches("\\d+")) {
            Toast.makeText(getContext(), "Yêu cầu giá tiền phải là số", Toast.LENGTH_SHORT).show();
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
                InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
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