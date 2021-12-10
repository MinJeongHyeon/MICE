package com.makguksu.mice;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    ImageView mouse_image;
    TextView mouse_name;
    TextView price;
    TextView mouse_height;
    TextView mouse_width;
    TextView mouse_length;
    TextView mouse_weight;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mouse_image = itemView.findViewById(R.id.mouse_image);
        mouse_name= itemView.findViewById(R.id.mouse_name);
        price= itemView.findViewById(R.id.price);
        mouse_height= itemView.findViewById(R.id.mouse_height);
        mouse_width= itemView.findViewById(R.id.mouse_width);
        mouse_length= itemView.findViewById(R.id.mouse_length);
        mouse_weight= itemView.findViewById(R.id.mouse_weight);
    }
}