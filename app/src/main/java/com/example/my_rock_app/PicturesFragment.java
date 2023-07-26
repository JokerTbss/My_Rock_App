package com.example.my_rock_app;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
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
        ImageView imageView = view.findViewById(R.id.listentry_image);
        TextView textView = view.findViewById(R.id.listentry_text);

        // Retrieve the folder name from the arguments
        String folderName = getArguments().getString(ARG_FOLDER_NAME);
        String s = getArguments().getString("imagePath");
        String t = getArguments().getString("maxStone");

        TextView title = view.findViewById(R.id.my_collection_text);
        title.setText(folderName);

        if (s != null) {
            File imagefile = new File(s);
            try {
                imageView.setImageURI(Uri.fromFile(imagefile));
            } catch (Exception e) {
                Toast.makeText(requireContext(), "image didn't work", Toast.LENGTH_LONG).show();
            }
        }
        if (t != null){
            try{
                textView.setText(t);
            } catch (Exception e){
                Toast.makeText(requireContext(), "text didn't work", Toast.LENGTH_LONG).show();
            }
        }


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

