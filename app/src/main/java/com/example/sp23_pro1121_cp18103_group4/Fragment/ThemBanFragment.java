package com.example.sp23_pro1121_cp18103_group4.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sp23_pro1121_cp18103_group4.Adapter.BanAnAdapter;
import com.example.sp23_pro1121_cp18103_group4.DAO.BanAnDao;
import com.example.sp23_pro1121_cp18103_group4.DialogThanhToan;
import com.example.sp23_pro1121_cp18103_group4.Model.BanAn;
import com.example.sp23_pro1121_cp18103_group4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class ThemBanFragment extends Fragment {

    ArrayList<BanAn> list ;
    BanAnAdapter adapterBanAn;
    BanAnDao daott;
    RecyclerView rcv;
    FloatingActionButton buttonMangVe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_them_ban, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         rcv = view.findViewById(R.id.rcv);
        FloatingActionButton flt = view.findViewById(R.id.flt_thembanan);
        buttonMangVe=view.findViewById(R.id.flt_mangve);
        daott = new BanAnDao(getContext());

        daott = new BanAnDao(getContext());
        list = new ArrayList<>();
        list = daott.getALL();
        adapterBanAn = new BanAnAdapter(getContext(),list);
        rcv.setAdapter(adapterBanAn);



        BanAn banAn = new BanAn();

        flt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(getContext(), androidx.appcompat.R.style.Theme_AppCompat);
                dialog.setContentView(R.layout.dialog_banan);


                TextInputEditText tenbanan = dialog.findViewById(R.id.tenbanan);
                Button luu = dialog.findViewById(R.id.luu);
                Button huy = dialog.findViewById(R.id.huy);

                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                luu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        list = new ArrayList<>();

                        if(tenbanan.getText().toString().length()==0){
                            Toast.makeText(getContext(), "Không Được Để Trống ", Toast.LENGTH_SHORT).show();
                            return ;
                        }

                        banAn.setTenBanAN(tenbanan.getText().toString());
                        if(daott.InSertBanAN(banAn)>0){

                            Toast.makeText(getContext(), "Thên Thành CÔng ", Toast.LENGTH_SHORT).show();
                            daott = new BanAnDao(getContext());
                            list = new ArrayList<>();
                            list = daott.getALL();
                            adapterBanAn = new BanAnAdapter(getContext(),list);
                            rcv.setAdapter(adapterBanAn);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Thêm THất Bại", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

//                        list = daott.getALL();
//                        adapterBanAn = new BanAnAdapter(getContext(),list);
//                        rcv.setAdapter(adapterBanAn);

                    }
                });

                dialog.show();
            }
        });
        buttonMangVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogThanhToan dialogThanhToan=new DialogThanhToan(getContext(),"0");
                dialogThanhToan.show();
            }
        });


    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        daott = new BanAnDao(getContext());
//        list = new ArrayList<>();
//        list = daott.getALL();
//        adapterBanAn = new BanAnAdapter(getContext(),list);
//        rcv.setAdapter(adapterBanAn);
//    }
}