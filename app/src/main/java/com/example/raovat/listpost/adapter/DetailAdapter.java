package com.example.raovat.listpost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raovat.R;
import com.example.raovat.listpost.OnClickDetail;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.textView.setText(arrayList.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                onClickDetail.sendDetail(arrayList.get(position),position);
                notifyDataSetChanged();
            }
        });
        if(index==position){
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.yellow));

        }else {
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.underline));
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
            cardView = itemView.findViewById(R.id.cv_detail);
        }
    }
}
