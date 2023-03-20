package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.BanAnDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonTrongBanDAO;
import com.example.sp23_pro1121_cp18103_group4.Database.Fragment.LoaiMonFragment;
import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.Model.MonTrongBan;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;

public class BanAnAdapter extends RecyclerView.Adapter<BanAnAdapter.ViewBanan> {


    Context context;
    ArrayList<BanAn> list ;
    BanAnDao daoBanAN;

    public BanAnAdapter(Context context, ArrayList<BanAn> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewBanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewba = layoutInflater.inflate(R.layout.rcvitembanan,parent,false);
        ViewBanan viewBanan = new ViewBanan(viewba);
        return viewBanan;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewBanan holder, int position) {

        holder.tenban.setText(list.get(position).getTenBanAN());
        int index = position;
        daoBanAN = new BanAnDao(context);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh Báo");
                builder.setMessage("Bạn Có Muốn Xóa Bàn Này Không ?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(daoBanAN.deleteBanAN(list.get(index))>0){

                            list.remove(index);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa THành Công", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(context, "Xóa THất Bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


                builder.show();
            }
        });


        holder.themmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new LoaiMonFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFrame_collection_fragment,fragment).commit();

                Bundle bundle = new Bundle();
                bundle.putInt("maban",list.get(index).getId());
                bundle.putBundle("banan",bundle);

            }
        });


        holder.hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat);
                dialog.setContentView(R.layout.dialog_tinh_tien);

                RecyclerView rcv = dialog.findViewById(R.id.rcv);
                TextView tenban = dialog.findViewById(R.id.tenban);
                TextView ngay = dialog.findViewById(R.id.ngay);

                MonTrongBanDAO trongBanDAO;
                MonTrongBan monTrongBan;
                ArrayList<MonTrongBan> listmtb;
                MonTrongBanAdapter monTrongBanAdapter;

                listmtb = new ArrayList<>();
                trongBanDAO = new MonTrongBanDAO(context);
                monTrongBan = new MonTrongBan();

                tenban.setText(list.get(index).getTenBanAN());
                ngay.setText("20/03/2023");

                listmtb = trongBanDAO.getAllData();
                monTrongBanAdapter = new MonTrongBanAdapter(listmtb,context);
                rcv.setAdapter(monTrongBanAdapter);

                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewBanan extends RecyclerView.ViewHolder{

        TextView tenban ;
        ImageView delete,themmon,hoadon;
        ImageButton anh ;

        public ViewBanan(@NonNull View itemView) {
            super(itemView);

            tenban = itemView.findViewById(R.id.tenban);
            delete = itemView.findViewById(R.id.delete);
            themmon = itemView.findViewById(R.id.themmon);
            anh  = itemView.findViewById(R.id.anh);
            hoadon = itemView.findViewById(R.id.hoadon);

        }
    }
}