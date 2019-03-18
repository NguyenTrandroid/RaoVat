package com.example.raovat.tabprofile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raovat.Models.Post;
import com.example.raovat.R;
import com.example.raovat.listpost.PostDetailActivity;
import com.example.raovat.listpost.adapter.PostAdapter;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.ViewHolder> {
    Context context;
    ArrayList<Post> listPost;
    int value;

    public SellAdapter(Context context, ArrayList<Post> listPost) {
        this.context = context;
        this.listPost = listPost;
    }

    @NonNull
    @Override
    public SellAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new SellAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellAdapter.ViewHolder holder, final int position) {
        value = 0;
        DateFormat df = new SimpleDateFormat("dd/MM HH:mm");
        holder.tvName.setText(listPost.get(position).getPostName());
        holder.tvAddress.setText(df.format(listPost.get(position).getPostDate()) + " | " + listPost.get(position).getAddress());
        holder.tvPrice.setText(listPost.get(position).getPrice().toString() + "đ");
        if (listPost.get(position).getPostUrl().size() == 0) {
            holder.ivNumber.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.ic_post_it).into(holder.ivPost);

        } else if (listPost.get(position).getPostUrl().size() == 1 && listPost.get(position).getPostUrl().get(0).equals("")) {
            holder.ivNumber.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.ic_post_it).into(holder.ivPost);
        } else {
            holder.ivNumber.setVisibility(View.VISIBLE);
            holder.tvNumber.setVisibility(View.VISIBLE);
            for (int i = 0; i < listPost.get(position).getPostUrl().size(); i++) {
                if (!listPost.get(position).getPostUrl().get(i).equals("")) {
                    value++;

                }
            }
            holder.tvNumber.setText(value + "");
            if (listPost.get(position).getPostUrl().get(0).equals("")) {
                Glide.with(context).load(listPost.get(position).getPostUrl().get(1)).into(holder.ivPost);
            } else {
                Glide.with(context).load(listPost.get(position).getPostUrl().get(0)).into(holder.ivPost);
            }
//            try {
//                Glide.with(context).load(listPost.get(position).getPostUrl().get(0)).into(holder.ivPost);
//            } catch (Exception e) {
//
//                Log.d("AAA", e + "");
//            }
        }
        holder.rlPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditPostActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(listPost.get(position));
                intent.putExtra("PostEdit", myJson);
                context.startActivity(intent);
            }
        });
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

            menu.add(getAdapterPosition(), 121, 0, "Đã bán");
            menu.add(getAdapterPosition(), 122, 1, "Xóa bài đăng");

        }
    }
}
