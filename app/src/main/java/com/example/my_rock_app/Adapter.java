package com.example.my_rock_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<Image>ImageList= new ArrayList<>();

    public Adapter(List<Image> imageList) {
        ImageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(ImageList.get(position).getImageId() );
        holder.textView.setText( ImageList.get(position).getImgName() );

    }

    @Override
    public int getItemCount() {
        return ImageList.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            textView= itemView.findViewById(R.id.folder_name);
        }
    }
}
