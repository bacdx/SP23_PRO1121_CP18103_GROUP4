package com.example.sp23_pro1121_cp18103_group4.Database.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sp23_pro1121_cp18103_group4.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    ViewFlipper viewQuangCao;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        viewQuangCao = view.findViewById(R.id.home_viewFlipper);
        AnimationViewFlipper();
        return view;
    }

    //banner quang cao
    public void AnimationViewFlipper() {
        List<String> listQuangCao = new ArrayList<>();
        //khoai tay chien
        listQuangCao.add("https://img.freepik.com/premium-vector/fast-food-banner-with-3d-french-fries-love-package-vector-illustration_548887-167.jpg?w=2000");
        //burger
        listQuangCao.add("https://img.freepik.com/premium-psd/delicious-hamburger-house-with-mock-up-banner_23-2148421438.jpg");
        //hotdog
        listQuangCao.add("https://i.pinimg.com/736x/70/73/6c/70736c1e30ec8ad6ebb184da101b51d9.jpg");
        //ice cream
        listQuangCao.add("https://t4.ftcdn.net/jpg/03/51/32/81/360_F_351328144_bTvUCqDyLb6eHXliKcCXiPtVgBPvuTEL.jpg");
        //milk tea
        listQuangCao.add("https://img.freepik.com/premium-vector/bubble-tea-banner-ads-with-splashing-milk-green-leaves-3d-illustration_317442-1436.jpg");
        //pizza
        listQuangCao.add("https://img.pikbest.com/backgrounds/20210706/yellow-background-creative-fashion-pizza-promotion-propaganda-banner-template_6047757.jpg!bw700");
        for (int i = 0; i < listQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(listQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewQuangCao.addView(imageView);
        }
        viewQuangCao.setFlipInterval(4000);
        viewQuangCao.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.anim_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.anim_out_right);
        viewQuangCao.setInAnimation(slide_in);
        viewQuangCao.setOutAnimation(slide_out);
    }
}