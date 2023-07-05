package com.example.my_rock_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PicturesFragment extends Fragment {
    private static final String ARG_FOLDER_NAME = "folderName";

    private List<String> pictureList;
    private RecyclerView recyclerView;

    public static PicturesFragment newInstance(String folderName) {
        PicturesFragment fragment = new PicturesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FOLDER_NAME, folderName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictures, container, false);
        recyclerView = view.findViewById(R.id.list_pictures); // recycler view and adapter have to be implemented

        // Retrieve the folder name from the arguments
        String folderName = getArguments().getString(ARG_FOLDER_NAME);

        TextView title = view.findViewById(R.id.my_collection_text);
        title.setText(folderName);

        // Retrieve the list of pictures inside the folder
        pictureList = getPictureListFromFolder(folderName); // has to be implemented

        // Inflate the layout for this fragment
        Button back= view.findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_pictures_to_collection);
            }
        });

        return view;
    }

    private List<String> getPictureListFromFolder(String folderName) {
        List<String> pictureList = new ArrayList<>();

        // Implement your logic to retrieve the list of pictures from the specified folder
        // Use the folder name to access the pictures in the storage location

        return pictureList;
    }
}

