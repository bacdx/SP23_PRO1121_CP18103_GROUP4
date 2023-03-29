package com.example.sp23_pro1121_cp18103_group4.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp23_pro1121_cp18103_group4.DAO.LoaiMonDao;
import com.example.sp23_pro1121_cp18103_group4.DAO.MonDao;
import com.example.sp23_pro1121_cp18103_group4.Fragment.MonFragment;
import com.example.sp23_pro1121_cp18103_group4.Model.LoaiMon;
import com.example.sp23_pro1121_cp18103_group4.MonActivity;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;


public class LoaiMonAdapter extends RecyclerView.Adapter<LoaiMonAdapter.MyViewHolder> implements Filterable {
    Context mContext;
    List<LoaiMon> list;
    List<LoaiMon> listSearchView;
    LoaiMonDao dao;
    MonDao monDao;
    EditText edTenLoaiMon;
    ImageView imgLoaiMon;
    Button btnSave, btnCancel;
    public LoaiMonAdapter(Context mContext, List<LoaiMon> list) {
        this.mContext = mContext;
        this.list = list;
        this.listSearchView = list;
        dao = new LoaiMonDao(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.loaimon_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LoaiMon loaiMon = list.get(position);
        holder.tvTenLoaiMon.setText(loaiMon.getTenLoaiMon());

        if(loaiMon.getImgLoaiMon()==null){

        }else{
            Bitmap imageContent = BitmapFactory.decodeByteArray(loaiMon.getImgLoaiMon(),0,loaiMon.getImgLoaiMon().length);
            holder.imgLoaiMon.setImageBitmap(imageContent);
        }
        Bitmap imageContent = BitmapFactory.decodeByteArray(loaiMon.getImgLoaiMon(),0,loaiMon.getImgLoaiMon().length);
        holder.imgLoaiMon.setImageBitmap(imageContent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thay đổi bundle món activity sang bunlde món fragment
                Bundle bundle = new Bundle();
                bundle.putInt("maLoaiMon",loaiMon.getMaLoaiMon());
                Fragment fragment = new MonFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFrame_collection_fragment,fragment).commit();
                Toast.makeText(mContext, "" + loaiMon.getTenLoaiMon(), Toast.LENGTH_SHORT).show();
            }
        });
        //set popup menu edit, delete;
        holder.popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.popupMenu);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popup_update:
                                openDialogUpdate(Gravity.CENTER, loaiMon);
                                break;
                            case R.id.popup_delete:
                                openDialogDeleteLoaiMon(holder.getAdapterPosition());
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //thiết lập search view
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    list = listSearchView;
                }
                else{
                    List<LoaiMon> mList = new ArrayList<>();
                    for (LoaiMon loaiMon : listSearchView){
                        if (loaiMon.getTenLoaiMon().toLowerCase().contains(strSearch.toLowerCase())){
                            mList.add(loaiMon);
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
                list = (List<LoaiMon>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenLoaiMon;
        ImageView imgLoaiMon,popupMenu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLoaiMon = itemView.findViewById(R.id.loaimon_tvTenLoai);
            imgLoaiMon = itemView.findViewById(R.id.loaimon_imgLoaiMon);
            popupMenu = itemView.findViewById(R.id.img_popupMenu);
        }
    }
    //**********//
    //thiết lập dialog edit loại món
    public void openDialogUpdate(int gravity, LoaiMon loaiMon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loai_mon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        //ánh xạ
        TextView tvTitle;
        tvTitle = view.findViewById(R.id.loaimon_tvTitle);
        tvTitle.setText("Sửa Loại Món");
        edTenLoaiMon = view.findViewById(R.id.loaimon_edTenLoai);
        imgLoaiMon = view.findViewById(R.id.loaimon_addImg);
        byte[] img = loaiMon.getImgLoaiMon();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgLoaiMon.setImageBitmap(bitmap);
        edTenLoaiMon.setText(loaiMon.getTenLoaiMon());
        btnSave = view.findViewById(R.id.loaimon_btnSave);
        btnCancel = view.findViewById(R.id.loaimon_btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao = new LoaiMonDao(mContext);
                loaiMon.setTenLoaiMon(edTenLoaiMon.getText().toString());
                if (dao.updateLoaiMon(loaiMon) > 0){
                    Toast.makeText(mContext, "Update thành công", Toast.LENGTH_SHORT).show();
                    loaiMon.setTenLoaiMon("");
                    list.clear();
                    list = dao.getAll();
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiMon.setTenLoaiMon("");
                dialog.dismiss();
            }
        });
    }

    //************//
    //thiết lập dialog delete loại món
    public void openDialogDeleteLoaiMon(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Bạn có chắc chắn muốn xóa?");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao = new LoaiMonDao(mContext);
                if (dao.deleteLoaiMon(list.get(position)) > 0) {
                    Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    list.clear();
                    list = dao.getAll();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
