package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;

public class MonTrongBanAdapter extends RecyclerView.Adapter<MonTrongBanAdapter.View_montrongban> {


    ArrayList<MonTrongBan> list;
    Context context;

    public MonTrongBanAdapter(ArrayList<MonTrongBan> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View_montrongban onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewba = layoutInflater.inflate(R.layout.rcvitembanan,parent,false);
        View_montrongban view_montrongban = new View_montrongban(viewba);
        return view_montrongban;

    }

    @Override
    public void onBindViewHolder(@NonNull View_montrongban holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class View_montrongban extends RecyclerView.ViewHolder{
        public View_montrongban(@NonNull View itemView) {
            super(itemView);
        }
    }
}
