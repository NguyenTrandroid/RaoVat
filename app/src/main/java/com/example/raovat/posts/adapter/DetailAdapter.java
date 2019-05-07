package com.example.raovat.posts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.raovat.R;
import com.example.raovat.posts.OnClickDetail;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList;
    OnClickDetail onClickDetail;
    int index=-1;

    public DetailAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        onClickDetail = (OnClickDetail) context;
    }

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_detail, parent, false);
            return new DetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailAdapter.ViewHolder holder, final int position) {
        holder.button.setText(arrayList.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                onClickDetail.sendDetail(arrayList.get(position),position);
                notifyDataSetChanged();
            }
        });
        if(index==position){
            holder.button.setBackgroundResource(R.drawable.custom_buttton_yellow);

        }else {
            holder.button.setBackgroundResource(R.drawable.custom_button);
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.bt_detail);
        }
    }
}
