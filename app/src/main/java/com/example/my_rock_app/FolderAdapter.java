package com.example.my_rock_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    Context context;
    ArrayList<Object>arrFolder;
    FolderAdapter(Context context, ArrayList<Object> arrFolder){
        this.context= context;
        this.arrFolder=arrFolder;}
    @NonNull
    @Override
    public FolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview,parent,false);
        return new RecyclerView.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewModel model= (ViewModel) arrFolder.get(position);
        holder.img.setImage
        holder.folder_name.setText(model.name);

        holder.folder_name.

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder {
    }
}
