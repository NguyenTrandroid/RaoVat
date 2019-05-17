package com.example.raovat.sell.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.raovat.R;
import com.example.raovat.sell.OnDelImg;
import com.example.raovat.tabprofile.SendPositionRemove;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ImageSellAdapter extends RecyclerView.Adapter<ImageSellAdapter.ViewHolder> {
    ArrayList<String> listFrameAsset;
    Context context;
    SendPositionRemove sendPositionRemove;
    OnDelImg onDelImg;

    public ImageSellAdapter(ArrayList<String> listFrameAsset, Context context) {
        this.listFrameAsset = listFrameAsset;
        this.context = context;
        sendPositionRemove = (SendPositionRemove) context;
        onDelImg = (OnDelImg) context;
    }

    @NonNull
    @Override
    public ImageSellAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_imgsell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSellAdapter.ViewHolder holder, final int position) {


        Glide.with(context).load(listFrameAsset.get(position)).into(holder.imageView);
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listFrameAsset.remove(position);
                sendPositionRemove.sendPosition(position);
                onDelImg.checkDel(true,position);

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFrameAsset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView ivDel;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_photo);
            ivDel = itemView.findViewById(R.id.iv_del);
            relativeLayout = itemView.findViewById(R.id.rl_imgPost);
        }
    }
}
