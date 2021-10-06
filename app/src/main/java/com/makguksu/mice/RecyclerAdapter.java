package com.makguksu.mice;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<Map<String, Object>> data;

    public RecyclerAdapter(Context context, List<Map<String, Object>> data) {
        super();
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mouse_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.mouse_name.setText(data.get(position).get("mouse_name").toString());
        holder.price.setText(data.get(position).get("price").toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}