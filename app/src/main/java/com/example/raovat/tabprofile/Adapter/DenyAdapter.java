package com.example.raovat.tabprofile.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raovat.Models.Post;
import com.example.raovat.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DenyAdapter extends RecyclerView.Adapter<DenyAdapter.ViewHolder> {
    Context context;
    ArrayList<Post> listPost;

    public DenyAdapter(Context context, ArrayList<Post> listPost) {
        this.context = context;
        this.listPost = listPost;
    }

    @NonNull
    @Override
    public DenyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new DenyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DenyAdapter.ViewHolder holder, int position) {

        DateFormat df = new SimpleDateFormat("dd/MM HH:mm");
        holder.tvName.setText(listPost.get(position).getPostName());
        holder.tvAddress.setText(df.format(listPost.get(position).getPostDate()) + " | " + listPost.get(position).getAddress());
        holder.tvPrice.setText(listPost.get(position).getPrice().toString() + "đ");
        if (listPost.get(position).getPostUrl().size() == 0) {
            holder.ivNumber.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.ic_post_it).into(holder.ivPost);

        } else {
            holder.ivNumber.setVisibility(View.VISIBLE);
            holder.tvNumber.setVisibility(View.VISIBLE);
            holder.tvNumber.setText(listPost.get(position).getPostUrl().size() + "");
            try {
                Glide.with(context).load(listPost.get(position).getPostUrl().get(0)).into(holder.ivPost);
            } catch (Exception e) {

                Log.d("AAA", e + "");
            }
        }
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivPost;
        TextView tvName;
        TextView tvPrice;
        TextView tvAddress;
        RelativeLayout rlPost;
        TextView tvNumber;
        ImageView ivNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.iv_post);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAddress = itemView.findViewById(R.id.tv_address);
            rlPost = itemView.findViewById(R.id.rl_post);
            ivNumber = itemView.findViewById(R.id.iv_numberImg);
            tvNumber = itemView.findViewById(R.id.tv_number);
            rlPost.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            menu.add(getAdapterPosition(), 123, 0, "Xóa bài đăng");

            menu.add(getAdapterPosition(), 124, 0, "Đăng lại tin");


        }
    }
}
