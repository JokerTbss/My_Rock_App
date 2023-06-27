package com.example.my_rock_app;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PicAnalyse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PicAnalyse extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PicAnalyse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PicAnalyse.
     */
    // TODO: Rename and change types and number of parameters
    public static PicAnalyse newInstance(String param1, String param2) {
        PicAnalyse fragment = new PicAnalyse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pic_analyse, container, false);

        //implementation for the image captured before
        ImageView imageView = view.findViewById(R.id.image_view);
        Bundle args = getArguments();
        if (args != null && args.containsKey("imagePath")){
            String imagepath = args.getString("imagePath");
            if (imagepath != null){
                File imagefile = new File(imagepath);
                try{
                    imageView.setImageURI(Uri.fromFile(imagefile));}
                catch(Exception e){
                    Toast.makeText(requireContext(), "image didn't work", Toast.LENGTH_LONG).show();
                }

            }
        }

        Button return_pic= view.findViewById(R.id.retake_picture);
        return_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_picAnalyse_to_cameraScreen);
            }

        });
        Button out = view.findViewById(R.id.exit);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_picAnalyse_to_main_screen);

            }
        });
        Button analyse = view.findViewById(R.id.analyse);
        analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_picAnalyse_to_loading);

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}