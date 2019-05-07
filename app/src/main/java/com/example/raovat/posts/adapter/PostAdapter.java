package com.example.raovat.posts.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raovat.Models.Post;
import com.example.raovat.R;
import com.example.raovat.posts.PostDetailActivity;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    Context context;
    ArrayList<Post> listPost;
    int value;
    private int lastPosition = -1;
    Activity activity;


    public PostAdapter(Context context, ArrayList<Post> listPost) {
        this.context = context;
        this.listPost = listPost;
        activity = (Activity) context;


    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, final int position) {
        value = 0;
        DateFormat df = new SimpleDateFormat("dd/MM HH:mm");
        holder.tvName.setText(listPost.get(position).getPostName());
        holder.tvAddress.setText(df.format(listPost.get(position).getPostDate()) + " | " + listPost.get(position).getAddress());


        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = Double.parseDouble(listPost.get(position).getPrice());
        String formattedNumber = formatter.format(myNumber);
        holder.tvPrice.setText(formattedNumber + " đ");

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

            Log.d("AAA", listPost.get(position).getPostUrl().get(0) + "");
        }


        Log.d("AAA", listPost.get(position).getStatus() + "");

        holder.rlPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(listPost.get(position));
                intent.putExtra("PostNew", myJson);
                context.startActivity(intent);
               activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
        setAnimation(holder.rlPost, position);


    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
