package com.makguksu.mice;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    TextView mouse_name;
    TextView price;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mouse_name= itemView.findViewById(R.id.mouse_name);
        price= itemView.findViewById(R.id.price);
    }
}