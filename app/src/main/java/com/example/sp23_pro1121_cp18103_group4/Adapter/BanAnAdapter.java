package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;

public class BanAnAdapter extends RecyclerView.Adapter<BanAnAdapter.ViewBanan> {


    Context context;
    ArrayList<BanAn> list ;

    public BanAnAdapter(Context context, ArrayList<BanAn> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewBanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.rcvitemsach,parent,false);
//
//        ViewSach view1 = new ViewSach(view);
//
//        return view1;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewba = layoutInflater.inflate(R.layout.rcvitembanan,parent,false);
        ViewBanan viewBanan = new ViewBanan(viewba);
        return viewBanan;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewBanan holder, int position) {

        holder.tenban.setText(list.get(position).getTenBanAN());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewBanan extends RecyclerView.ViewHolder{

        TextView tenban ;

        public ViewBanan(@NonNull View itemView) {
            super(itemView);

            tenban = itemView.findViewById(R.id.tenban);

        }
    }
}
