package com.example.my_rock_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


    }
}