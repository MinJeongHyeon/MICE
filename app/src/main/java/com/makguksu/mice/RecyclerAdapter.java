package com.makguksu.mice;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<Map<String, Object>> data;
    private FirebaseStorage storage;

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
        storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference();
        String docName = data.get(position).get("document").toString();
        String childName = docName + ".jpg";
        StorageReference submitProfile= ref.child(childName);
        submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.mouse_image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {

            }
        });
        holder.mouse_name.setText(data.get(position).get("mouse_name").toString());
        holder.price.setText(data.get(position).get("price").toString()+"원");
        holder.mouse_height.setText("높이:"+data.get(position).get("height").toString());
        holder.mouse_width.setText("가로:"+data.get(position).get("width").toString());
        holder.mouse_length.setText("세로:"+data.get(position).get("length").toString());
        holder.mouse_weight.setText("무게:"+data.get(position).get("weight").toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}