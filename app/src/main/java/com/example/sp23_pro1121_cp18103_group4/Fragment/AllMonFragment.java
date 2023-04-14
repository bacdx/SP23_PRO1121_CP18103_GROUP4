package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.sp23_pro1121_cp18103_group4.Adapter.MonAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AllMonFragment extends Fragment {

    RecyclerView mon_rcView;
    MonDao dao;
    List<Mon> list;
    MonAdapter adapter;
    //filter - search view
    private SearchView searchView;
    ImageView allMon_filter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_mon, container, false);
        init(view);
        showData();
        openSearchView(view);
        openFilter();
        return view;
    }

    //init ánh xạ

    public void init(View view) {
        mon_rcView = view.findViewById(R.id.allmon_rcView);
        allMon_filter = view.findViewById(R.id.allmon_filter);
    }
    //đổ dữ liệu
    public void showData() {
        dao = new MonDao(getContext());
        list = dao.getAll();
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mon_rcView.setLayoutManager(manager);
        adapter = new MonAdapter(getContext(), list);
        mon_rcView.setAdapter(adapter);
        mon_rcView.setHasFixedSize(true);
    }

    //*******//
    //thiết lập search view tìm tên món
    public void openSearchView(View view) {
        searchView = view.findViewById(R.id.allmon_searchView);
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
        allMon_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), allMon_filter);
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

        for (int i = 0; i < list.size(); i++) {

            String trangThai = list.get(i).getTrangThai();
           // trangThai.equalsIgnoreCase("Còn hàng"
            if (trangThai.equalsIgnoreCase("Còn hàng")) {
                temp_arraylist.add(list.get(i));
            }

        }

        adapter = new MonAdapter(getContext(), temp_arraylist);
        mon_rcView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}