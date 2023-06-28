package com.example.my_rock_app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button my_collection;

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_STORAGE_PERMISSION = 101;
    RecyclerView recyclerView;
    FloatingActionButton btnOpenDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add note
    ArrayList<Object> arrFolder =new ArrayList<>();
    FolderAdapter adapter;
    recyclerView = findViewById(R.id.recycler_view);
    btnOpenDialog= findViewById(R.id.add_icon_float);

    btnOpenDialog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.fragment_create_folder);
            EditText folder_name = dialog.findViewById(R.id.add_folder_text);
            Button done= dialog.findViewById(R.id.done_icon);

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fld_name = "";
                    if (!folder_name.getText().toString().equals("")){
                      fld_name= folder_name.getText().toString();
                    } else {
                        Toast.makeText(MainActivity.this,"Please enter name ",Toast.LENGTH_SHORT).show();
                    }

                arrFolder.add(new ViewModel(fld_name));
                adapter.notifyItemInserted(arrFolder.size()-1);
                recyclerView.scrollToPosition(arrFolder.size()-1);
                dialog.dismiss();
                }
            });
            dialog.show();
        }
    });

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter=new FolderAdapter(this,arrFolder);
    recyclerView.setAdapter(adapter);

    }
}