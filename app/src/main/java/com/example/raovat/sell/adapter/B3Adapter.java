package com.example.raovat.sell.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.raovat.R;
import com.example.raovat.sell.CategoryParents;
import com.example.raovat.sell.OnSelectCategrory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class B3Adapter extends RecyclerView.Adapter<B3Adapter.ViewHolder> {
    OnSelectCategrory onSelectCategrory;
    int selectedPosition = -1;
    Context context;
    ArrayList<CategoryParents> list;


    public B3Adapter(OnSelectCategrory onSelectCategrory, Context context, ArrayList<CategoryParents> list) {
        this.onSelectCategrory = onSelectCategrory;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public B3Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_sell, parent, false);
        return new B3Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull B3Adapter.ViewHolder holder, final int position) {
        holder.txtName.setText(list.get(position).getName());
        holder.radioButton.setChecked(selectedPosition == position);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tv_nameCategory);
            radioButton = itemView.findViewById(R.id.rb_select);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = getAdapterPosition();
                    onSelectCategrory.sendPosition(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
