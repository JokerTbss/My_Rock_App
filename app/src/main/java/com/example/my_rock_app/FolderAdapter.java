package com.example.my_rock_app;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private List<String> folderList;
    private List<String> imagePathList;
    private List<String> maxStoneList;
    Activity activity;
    NavController navController;
    String s;
    String t;

    // Interface to handle item clicks


    public FolderAdapter(List<String> folderList, List<String> imagePathList, List<String> maxStoneList, Activity activity, NavController navController) {
        this.folderList = folderList;
        this.imagePathList = imagePathList;
        this.maxStoneList = maxStoneList;
        this.activity = activity;
        this.navController = navController;



    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        String folderName = folderList.get(position);
        holder.folderNameTextView.setText(folderName);

        String imagePath = imagePathList.get(position);
        String maxStone = maxStoneList.get(position);

        // Add click listener to navigate to PicturesFragment
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("folderName", folderName);
                bundle.putString("imagePath", imagePath);
                bundle.putString("maxStone", maxStone);
                navController.navigate(R.id.action_myCollection_to_picturesFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    class FolderViewHolder extends RecyclerView.ViewHolder {
        TextView folderNameTextView;
        CardView card;

        FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            folderNameTextView = itemView.findViewById(R.id.folderNameTextView);
            card = itemView.findViewById(R.id.item_card);
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showDeletePrompt(folderNameTextView.getText().toString());
                    return false;
                }
            });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("folderName",folderNameTextView.getText().toString());
                    bundle.putString("imagePath",s);
                    bundle.putString("maxStone", t);
                    navController.navigate(R.id.action_myCollection_to_picturesFragment, bundle);
                }
            });
        }
    }

    private  void showDeletePrompt(String folderName) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        alert.setTitle("Delete Folder");
        alert.setMessage("Are you sure you want to delete the folder '" + folderName + "'?");

        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Delete the folder
                boolean isDeleted = deleteFolder(folderName);
                if (isDeleted) {
                    folderList.remove(folderName);
                    FolderAdapter.this.notifyDataSetChanged();
                } else {
                    // Failed to delete the folder
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    private boolean deleteFolder(String folderName) {
        File storageDirectory = activity.getFilesDir();
        File folder = new File(storageDirectory, folderName);

        if (folder.exists() && folder.isDirectory()) {
            // Delete the folder and its contents
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            return folder.delete();
        }
        return false;
    }
}

