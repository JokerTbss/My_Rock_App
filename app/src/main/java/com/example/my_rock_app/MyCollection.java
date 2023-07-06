package com.example.my_rock_app;

import static com.example.my_rock_app.R.id.list_folder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCollection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  MyCollection extends Fragment {
    private ListView listView;

    Button addButton ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<String> folderList;
    private RecyclerView recyclerView;
    private FolderAdapter adapter;


    public MyCollection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCollection.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCollection newInstance(String param1, String param2) {
        MyCollection fragment = new MyCollection();
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


        View view= inflater.inflate(R.layout.fragment_my_collection, container, false);

        recyclerView = view.findViewById(list_folder);

        // Inflate the layout for this fragment
        Button back= view.findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_myCollection_to_main_screen);
            }
        });
        Button add = view.findViewById(R.id.add_icon);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("Create New Folder");
                alert.setMessage("Enter the name of the folder");

                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        folderList.add(value);
                        adapter.notifyDataSetChanged();

                        // Create the new folder in the storage location
                        File storageDirectory = getActivity().getFilesDir();
                        File newFolder = new File(storageDirectory, value);
                        if (!newFolder.exists()) {
                            boolean isCreated = newFolder.mkdir();
                            if (isCreated) {
                                // Folder created successfully
                            } else {
                                // Failed to create folder
                            }
                        } else {
                            // Folder with the same name already exists
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
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Retrieve the list of folders
        folderList = getFolderListFromStorage();

        NavController navController = Navigation.findNavController(requireView());
        // Create and set up the RecyclerView adapter
        adapter = new FolderAdapter(folderList, getActivity(), navController);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<String> getFolderListFromStorage() {
        List<String> folderList = new ArrayList<>();
        // Retrieve the list of folder names from the storage location
        File storageDirectory = getActivity().getFilesDir();
        File[] files = storageDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    folderList.add(file.getName());
                }
            }
        }

        return folderList;
    }
}
