package com.example.raovat.listpost.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raovat.R;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    Context context;
    List<String> listImg;

    public PagerAdapter(Context context, List<String> listImg) {
        this.context = context;
        this.listImg = listImg;
    }

    @NonNull
    @Override

    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View item = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_page, container, false);
        ImageView ivPost = item.findViewById(R.id.iv_imgPost);
        Glide.with(context).load(listImg.get(position)).into(ivPost);
        container.addView(item);
        return item;
    }


    @Override
    public int getCount() {
        return listImg.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
