package com.example.my_rock_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onItemClicked(Note note) {
        lastClickedNote = note;
        //TODO: For the assignment you can add the code for opening the AddNoteActivity
        // for an existing Note - and handling its update - here
    }


}