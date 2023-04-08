package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Model.Top5;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;

public class Top5Adapter extends RecyclerView.Adapter<Top5Adapter.ViewTop5> {


    ArrayList<Top5> list ;
    Context context;

    public Top5Adapter(ArrayList<Top5> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewTop5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewba = layoutInflater.inflate(R.layout.rcvview_top5,parent,false);
        ViewTop5 top5 = new ViewTop5(viewba);
        return top5;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewTop5 holder, int position) {

        holder.temon.setText(list.get(position).getTenmon()+"");
        holder.soluong.setText(list.get(position).getSoluong()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewTop5 extends RecyclerView.ViewHolder{

        TextView temon,soluong;
        public ViewTop5(@NonNull View itemView) {
            super(itemView);

            temon = itemView.findViewById(R.id.tenmon);
            soluong = itemView.findViewById(R.id.soluong);

        }
    }
}
