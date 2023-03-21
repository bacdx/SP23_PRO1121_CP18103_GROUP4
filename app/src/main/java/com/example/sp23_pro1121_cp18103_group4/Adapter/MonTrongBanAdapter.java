package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;

public class MonTrongBanAdapter extends RecyclerView.Adapter<MonTrongBanAdapter.View_montrongban> {


    ArrayList<MonTrongBan> list;
    Context context;
    MonDao monDao ;
    Mon mon;



    public MonTrongBanAdapter(ArrayList<MonTrongBan> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View_montrongban onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewba = layoutInflater.inflate(R.layout.rcv_montrongban,parent,false);
        View_montrongban view_montrongban = new View_montrongban(viewba);
        return view_montrongban;

    }

    @Override
    public void onBindViewHolder(@NonNull View_montrongban holder, int position) {

        MonTrongBan monTrongBan = list.get(position);

        mon = new Mon();
        monDao = new MonDao(context);
        int index = position;


        mon = monDao.getID(list.get(position).getMaMon());

        holder.tenmon.setText(mon.getTenMon());
        holder.soluong.setText(list.get(index).getSoLuong()+"");
        holder.tongtien.setText(list.get(index).getSoLuong() * mon.getGiaTien()+" VND");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class View_montrongban extends RecyclerView.ViewHolder{

        TextView tenmon,soluong,tongtien;

        public View_montrongban(@NonNull View itemView) {
            super(itemView);

            tenmon = itemView.findViewById(R.id.tenmon);
            soluong = itemView.findViewById(R.id.soluong);
            tongtien = itemView.findViewById(R.id.tongtien);

        }
    }
}
