package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.DatHangDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.Fragment.ChiTietSanPhamFragment;
import com.example.sp23_pro1121_cp18103_group4.Model.DatHang;
import com.example.sp23_pro1121_cp18103_group4.Model.Mon;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;



public class AllMonAdapter extends RecyclerView.Adapter<AllMonAdapter.MyViewHolder> implements Filterable {
    Context mContext;
    List<Mon> list;
    List<Mon> listSearchView;
    MonDao dao;
    DatHang datHang;
    DatHangDao datHangDao;
    List<DatHang> listDatHang;
    //thiet lap upload ảnh
    private static final int PICK_IMAGE_REQUEST = 100;
    static byte[] imageContent;
    //dialog

    public AllMonAdapter(Context mContext, List<Mon> list) {
        this.mContext = mContext;
        this.list = list;
        this.listSearchView = list;
        dao = new MonDao(mContext);
        datHangDao = new DatHangDao(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_of_item = LayoutInflater.from(mContext).inflate(R.layout.dat_mon_item, parent, false);
        return new MyViewHolder(view_of_item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mon mon = list.get(position);
        holder.mon_tvTenMon.setText(mon.getTenMon());
        holder.mon_tvGiaTien.setText("Giá tiền: " + mon.getGiaTien());
        if (mon.getTrangThai()==Mon.CON_HANG) {
            holder.mon_tvTrangThai.setText("Còn hàng");
            holder.mon_tvTrangThai.setTextColor(Color.BLUE);
            holder.mon_tvTrangThai.setPaintFlags(holder.mon_tvGiaTien.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        } else {
            holder.mon_tvTrangThai.setText("Hết hàng");
            holder.mon_tvTrangThai.setTextColor(Color.RED);
            holder.mon_tvTrangThai.setPaintFlags(holder.mon_tvGiaTien.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        Bitmap imageContent = BitmapFactory.decodeByteArray(mon.getImgMon(), 0, mon.getImgMon().length);
        holder.mon_imgMon.setImageBitmap(imageContent);
        if (mon.getTrangThai()==Mon.CON_HANG) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("chiTietMaMon", mon.getMaMon());
                    bundle.putString("chiTietTenMon",mon.getTenMon());
                    bundle.putFloat("chiTietGiaTien", mon.getGiaTien());
                    bundle.putByteArray("chiTietImgMon", mon.getImgMon());
                    Fragment fragment = new ChiTietSanPhamFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainFrame_collection_fragment, fragment).commit();
                }
            });
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mon_tvTenMon, mon_tvGiaTien, mon_tvTrangThai;
        ImageView mon_imgMon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mon_tvTenMon = itemView.findViewById(R.id.mon_tvTenMon);
            mon_tvGiaTien = itemView.findViewById(R.id.mon_tvGiaTien);
            mon_tvTrangThai = itemView.findViewById(R.id.mon_tvTrangThai);
            mon_imgMon = itemView.findViewById(R.id.mon_imgMon);
        }
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    list = listSearchView;
                }
                else{
                    List<Mon> mList = new ArrayList<>();
                    for (Mon mon : listSearchView){
                        if (mon.getTenMon().toLowerCase().contains(strSearch.toLowerCase())){
                            mList.add(mon);
                        }
                    }
                    list = mList;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<Mon>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
