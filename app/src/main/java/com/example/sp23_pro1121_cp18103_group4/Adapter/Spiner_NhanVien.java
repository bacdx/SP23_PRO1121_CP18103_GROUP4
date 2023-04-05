package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.Model.NhanVien;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;

public class Spiner_NhanVien extends BaseAdapter {

    ArrayList<NhanVien> list ;
    Context context;

    public Spiner_NhanVien(Context context) {
        this.context = context;
    }

    public void setDaTa(ArrayList<NhanVien> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public final class Myviewholder{

        TextView tenNhanVien;
        public Myviewholder() {
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Myviewholder myviewholder =  null;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        if(convertView == null){

            myviewholder = new Myviewholder();
            convertView = inflater.inflate(R.layout.spiner_nhanvien,null);

            myviewholder.tenNhanVien = convertView.findViewById(R.id.tenNhanVien);

            convertView.setTag(myviewholder);
        }else{
            myviewholder = (Myviewholder) convertView.getTag();
        }

        int index = position;

        myviewholder.tenNhanVien.setText(list.get(index).getHoten());

        return convertView;

    }
}
