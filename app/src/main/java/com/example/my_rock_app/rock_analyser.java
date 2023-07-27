package com.example.my_rock_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rock_analyser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rock_analyser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<String> PicturesList;
    private RecyclerView recyclerView1;
    private FolderAdapter adapter1;

    String t;
    String s;


    public rock_analyser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rock_analyser.
     */
    // TODO: Rename and change types and number of parameters
    public static rock_analyser newInstance(String param1, String param2) {
        rock_analyser fragment = new rock_analyser();
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
        View view = inflater.inflate(R.layout.fragment_rock_analyser, container, false);
        TextView textView = view.findViewById(R.id.rock_name);
        ImageView imageView = view.findViewById(R.id.rock_pic);



        Bundle arg2 = getArguments();
        if (arg2 != null) {
            t = arg2.getString("result_maxStone");
            String a = arg2.getString("result_allStones");
            s = arg2.getString("imagePath");

            if (s != null) {
                File imagefile = new File(s);
                try {
                    imageView.setImageURI(Uri.fromFile(imagefile));
                    textView.setText("This picture shows:  " + t + "\n" + "\n" + a);
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "image didn't work", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(requireContext(), "bundle is empty", Toast.LENGTH_LONG).show();
        }
      //  recyclerView1 = view.findViewById();
        Button add = view.findViewById(R.id.save_analysed_rock);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("Save Picture");
                alert.setMessage("Enter the name of picture");

                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();

                        //Navigate back to MyCollection fragment and pass the entered name as an argument
                        if (!value.isEmpty()) {
                            // Navigate back to MyCollection fragment and pass the entered name as an argument
                            Bundle bundle = new Bundle();
                            bundle.putString("newFolderName", value);
                            bundle.putString("imagePath", s);
                            bundle.putBoolean("fromSpecificClass", true);
                            bundle.putString("maxStone", t);
                            Navigation.findNavController(view).navigate(R.id.action_rock_analyser_to_my_collection, bundle);
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
        });
        Button back= view.findViewById(R.id.rock_properties);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t.equals("Marble")){
                   // Intent intent = new Intent(requireActivity(), marble_properties.class);;
                   // boolean isConditionMet1 = false; // Replace this with your condition or flag
                  //  intent.putExtra("CONDITION_FLAG", isConditionMet1);
                    //startActivity(intent);
                Navigation.findNavController(view).navigate(R.id.action_rock_analyser_to_marble_properties);}
                if (t.equals("Coal")){
                    Intent intent = new Intent(requireActivity(), coal_properties.class);;
                    boolean isConditionMet = false; // Replace this with your condition or flag
                    intent.putExtra("CONDITION_FLAG", isConditionMet);
                    startActivity(intent);
                    Navigation.findNavController(view).navigate(R.id.action_rock_analyser_to_coal_properties);
                }
                if (t.equals("Sandstone")){
                    Intent intent = new Intent(requireActivity(), sandstone_properties.class);;
                    boolean isConditionMet = false; // Replace this with your condition or flag
                    intent.putExtra("CONDITION_FLAG", isConditionMet);
                    startActivity(intent);
                    Navigation.findNavController(view).navigate(R.id.action_rock_analyser_to_sandstone_properties);
                }
                if (t.equals("Limestone")){
                    Intent intent = new Intent(requireActivity(), limestone_properties.class);;
                    boolean isConditionMet = false; // Replace this with your condition or flag
                    intent.putExtra("CONDITION_FLAG", isConditionMet);
                    startActivity(intent);
                    Navigation.findNavController(view).navigate(R.id.action_rock_analyser_to_limestone_properties);
                }
                if (t.equals("Granite")){
                    Intent intent = new Intent(requireActivity(), granite_properties.class);;
                    boolean isConditionMet = false; // Replace this with your condition or flag
                    intent.putExtra("CONDITION_FLAG", isConditionMet);
                    startActivity(intent);
                    Navigation.findNavController(view).navigate(R.id.action_rock_analyser_to_granite_properties);
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }



}